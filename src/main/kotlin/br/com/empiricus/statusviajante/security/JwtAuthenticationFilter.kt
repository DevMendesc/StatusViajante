package br.com.empiricus.statusviajante.security

import br.com.empiricus.statusviajante.model.UserLogin
import br.com.empiricus.statusviajante.model.UserSecurity
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.client.HttpClientErrorException.BadRequest
import java.util.*

class JwtAuthenticationFilter(
    private val authenticationManager: AuthenticationManager
) :
    UsernamePasswordAuthenticationFilter() {

    private val secret = "SEGREDO"
    private val expiration = 60 * 60 * 24 * 1000

    override fun attemptAuthentication(req: HttpServletRequest, response: HttpServletResponse): Authentication {
        val credentials = ObjectMapper().readValue(req.inputStream, UserLogin::class.java)
        val auth = UsernamePasswordAuthenticationToken(
            credentials.usuario,
            credentials.senha,
            Collections.singleton(SimpleGrantedAuthority("user"))
        )
        return authenticationManager.authenticate(auth)
    }

    override fun successfulAuthentication(
        req: HttpServletRequest?, res: HttpServletResponse, chain: FilterChain?,
        auth: Authentication
    ) {
        val username = (auth.principal as UserSecurity).username
        val token: String = JWT.create()
            .withSubject(username)
            .withExpiresAt(Date(System.currentTimeMillis()+ expiration))
            .sign(Algorithm.HMAC512(secret.toByteArray()))
        val headerBody: String = "Bearer $token"
        res.setHeader(HttpHeaders.AUTHORIZATION, headerBody)
    }

    override fun unsuccessfulAuthentication(
        request: HttpServletRequest,
        response: HttpServletResponse,
        failed: AuthenticationException
    ) {
        val error = BadCredentialsError()
        response.status = error.status
        response.contentType = "application/json"
        response.writer.append(error.toString())
    }

}

private data class BadCredentialsError(
    val timestamp: Long = Date().time,
    val status: Int = 401,
    val message: String = "Usuario ou Senha incorretos",
){
    override fun toString(): String {
        return ObjectMapper().writeValueAsString(this)
    }
}

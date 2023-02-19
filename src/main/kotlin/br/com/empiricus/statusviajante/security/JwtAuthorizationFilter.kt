package br.com.empiricus.statusviajante.security

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpHeaders.AUTHORIZATION
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import java.io.IOException
import java.util.*


class JwtAuthorizationFilter(
    private val userDetailService: UserDetailsService,
    authManager: AuthenticationManager,

    ) : BasicAuthenticationFilter(authManager) {

    private val tokenPrefix: String = "Bearer "
    private val secret = "SEGREDO"

    @Throws(IOException::class, ServletException::class)
    override fun doFilterInternal(
        req: HttpServletRequest,
        res: HttpServletResponse,
        chain: FilterChain
    ) {
        val token = req.getHeader(AUTHORIZATION)
        if (token == null || !token.startsWith(tokenPrefix)) {
            chain.doFilter(req, res)
            return
        }
        val auth = getAuthentication(token)
        SecurityContextHolder.getContext().authentication = auth
        chain.doFilter(req, res)
    }

    private fun getAuthentication(token: String?): UsernamePasswordAuthenticationToken {
        val username: String = JWT.require(Algorithm.HMAC512(secret.toByteArray()))
            .build()
            .verify(token?.replace(tokenPrefix,""))
            .subject
        return UsernamePasswordAuthenticationToken(username, null, Collections.emptyList())
    }
}

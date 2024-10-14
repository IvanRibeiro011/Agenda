package br.com.agenda.config;

import br.com.agenda.entities.AuthToken;
import br.com.agenda.repositories.AuthTokenRepository;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
public class AuthTokenFilter implements Filter {

    @Autowired
    private AuthTokenRepository authTokenRepository;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String path = httpRequest.getRequestURI();
        if (path.startsWith("/usuario/login")
                || path.startsWith("usuario/esqueceu-senha")
                || path.startsWith("/usuario/salvar")
                || path.startsWith("/swagger-ui/") ||
                path.startsWith("/v3/api-docs") ||
                path.startsWith("/swagger-resources") ||
                path.startsWith("/webjars/")) {
            chain.doFilter(request, response);
            return;
        }

        String authHeader = httpRequest.getHeader("Authorization");
        if (authHeader != null) {
            AuthToken authToken = authTokenRepository.findByToken(authHeader);

            if (authToken != null && authToken.isAtivo() && authToken.getExpiracao().isAfter(LocalDateTime.now())) {
                chain.doFilter(request, response);
                return;
            }

            if (authToken.isAtivo() && authToken.getExpiracao().isBefore(LocalDateTime.now())) {
                authTokenRepository.deactivateToken(authHeader);
                httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                httpResponse.getWriter().write("Token expirado favor realizar novamente o login.");
            }
        }

        httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        httpResponse.getWriter().write("Token inválido ou ausente.");
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }
}

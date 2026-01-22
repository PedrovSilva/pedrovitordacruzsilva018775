package br.gov.mt.seplag.artist_album_api.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

@Component
@RequiredArgsConstructor // Injeta JwtService e UserDetailsService
@Slf4j // Para logging
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        // 1. Ignorar caminhos que não precisam de autenticação (login, swagger, etc.)
        final String requestPath = request.getServletPath();
        if (requestPath.startsWith("/api/v1/auth/") ||
                requestPath.startsWith("/v1/api-docs") ||
                requestPath.startsWith("/v1/swagger-ui") ||
                requestPath.equals("/actuator/health")) {
            filterChain.doFilter(request, response);
            return;
        }

        // 2. Extrair o token do cabeçalho Authorization
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail; // ou username, dependendo do seu UserDetails

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            log.warn("Requisição sem token Bearer para: {}", request.getRequestURI());
            filterChain.doFilter(request, response);
            return;
        }

        jwt = authHeader.substring(7); // Remove "Bearer "
        log.debug("Token JWT extraído: {}...", jwt.substring(0, Math.min(jwt.length(), 10)));

        try {
            // 3. Extrair username/email do token
            userEmail = jwtService.extractUsername(jwt);

            // 4. Se temos um username E não há autenticação atual no contexto
            if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                // 5. Carregar UserDetails do banco
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
                log.debug("UserDetails carregado para: {}", userEmail);

                // 6. Validar token + UserDetails
                if (jwtService.isTokenValid(jwt, userDetails)) {
                    log.info("Token válido para usuário: {}", userEmail);

                    // 7. Criar objeto de autenticação do Spring Security
                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails,
                                    null, // credentials - geralmente null após autenticação
                                    userDetails.getAuthorities()
                            );

                    // 8. Adicionar detalhes da requisição (IP, sessionId, etc.)
                    authToken.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(request)
                    );

                    // 9. Atualizar o SecurityContextHolder
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                    log.debug("Contexto de segurança atualizado para: {}", userEmail);
                } else {
                    log.warn("Token inválido ou expirado para: {}", userEmail);
                }
            }
        } catch (Exception e) {
            log.error("Falha ao processar token JWT: {}", e.getMessage());
            // Não limpa o contexto para evitar problemas, apenas registra o erro
        }

        // 10. Continuar a cadeia de filtros
        filterChain.doFilter(request, response);
    }
}

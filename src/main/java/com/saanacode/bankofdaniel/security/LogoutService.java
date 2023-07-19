package com.saanacode.bankofdaniel.security;

import com.yassir.bankservice.repository.TokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {

    private final TokenRepository tokenRepository;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        final String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return;
        }
        tokenRepository.findByToken(authorizationHeader.substring(7))
                .ifPresent(storedToken -> {
                    storedToken.setExpired(true);
                    storedToken.setRevoked(true);
                    tokenRepository.save(storedToken);
                    SecurityContextHolder.clearContext();
                });
    }
}

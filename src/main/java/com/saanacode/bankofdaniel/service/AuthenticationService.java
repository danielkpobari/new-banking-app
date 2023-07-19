package com.saanacode.bankofdaniel.service;

import com.saanacode.bankofdaniel.dto.request.AdminRegistrationRequest;
import com.saanacode.bankofdaniel.dto.request.AuthenticationRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static java.lang.String.format;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Log4j2
@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private static final Logger logger = LogManager.getLogger(AuthenticationService.class);
    private final AdminRepository repository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Transactional
    public void register(@Valid AdminRegistrationRequest request) {
        try {
            var newAdmin = new Admin(request.getFirstname(), request.getLastname(), request.getEmail(),
                    passwordEncoder.encode(request.getPassword()), ADMIN);
             repository.save(newAdmin);
        } catch (Exception e) {
            logger.error(format("An error occurred while creating admin account, please contact support. " +
                    "Possible reasons: %s", e.getLocalizedMessage()));
            throw new YassirException("An error occurred while creating admin account, please contact support",
                    INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    public AuthenticationResponse authenticate(@Valid AuthenticationRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);

            var admin = (Admin) authentication.getPrincipal();
            var token = jwtService.generateJwtToken(admin);
            revokeAllAdminTokens(admin);
            persistAdminToken(admin, token);

            return new AuthenticationResponse(token);
        } catch (Exception e) {
            logger.error(format("An error occurred while authenticating login request, please contact support. " +
                    "Possible reasons: %s", e.getLocalizedMessage()));
            throw new UnAuthorizedException("Invalid email or/and password", INVALID_CREDENTIALS);
        }
    }

    private void persistAdminToken(Admin admin, String token) {
        tokenRepository.save(new Token(token, admin));
    }

    private void revokeAllAdminTokens(Admin admin) {
        tokenRepository.invalidateAllAdminTokens(admin.getId());
    }
}

package com.demo.demo.security.api;

import com.demo.demo.security.db.UserEntity;
import com.demo.demo.security.db.UserRepository;
import com.demo.demo.security.dto.JwtAuthDto;
import com.demo.demo.security.dto.RefreshTokenDto;
import com.demo.demo.security.dto.UserCredentialsDto;
import com.demo.demo.security.jwt.JwtService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.NoSuchElementException;


@Service
public class UserService implements IUserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final JwtService jwtService;
    private final UserRepository userRepository;

    public UserService(JwtService jwtService, UserRepository userRepository) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    @Override
    public JwtAuthDto refreshToken(RefreshTokenDto dto) {
        String email = jwtService.getEmailFromToken(dto.token());
        return jwtService.refreshBaseToken(email, dto.refreshToken());
    }

    @Override
    @Transactional
    public UserCredentialsDto getUser(String auth) throws NoSuchElementException {
        String email_token = jwtService.getEmailFromToken(auth);
        UserEntity user = userRepository.getUserEntityByEmail(email_token);
        if (user == null) {
            throw new NoSuchElementException("Us not found");
        }
        return user.toDto();
    }

    @Override
    @Transactional
    public JwtAuthDto addUser(UserCredentialsDto userCredentials) {
        var result = userRepository.save(userCredentials.toEntity());
        logger.info(result.getEmail());
        return new JwtAuthDto(
                jwtService.generateJwtToken(result.getEmail()),
                jwtService.generateRefreshToken(result.getEmail())
        );
    }
}

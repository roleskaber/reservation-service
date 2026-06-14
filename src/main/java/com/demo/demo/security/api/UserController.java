package com.demo.demo.security.api;

import com.demo.demo.security.dto.JwtAuthDto;
import com.demo.demo.security.dto.RefreshTokenDto;
import com.demo.demo.security.dto.UserCredentialsDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public ResponseEntity<UserCredentialsDto> getUserCredentialsById (
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorization
    ) {
        return ResponseEntity.ok(userService.getUser(authorization.substring(7)));
    }

    @PostMapping("/register")
    public ResponseEntity<JwtAuthDto> postUserCredentials (
            @RequestBody UserCredentialsDto userCredentialsDto
    ) {
        return ResponseEntity.ok(userService.addUser(userCredentialsDto));
    }

    @PostMapping("/token/refresh")
    public ResponseEntity<JwtAuthDto> updateUserToken (@RequestBody RefreshTokenDto dto) {
        return ResponseEntity.ok(userService.refreshToken(dto));
    }

}

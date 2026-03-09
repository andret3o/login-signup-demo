package com.web.match_me.security;


import com.web.match_me.user.User;
import com.web.match_me.user.UserDto;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<UserDto.SummaryResponse> signUp(@Valid @RequestBody UserDto.CreateRequest userDto) {
        UserDto.SummaryResponse response = authService.registerUser(userDto);

        String jwt = authService.authenticateUser(
                new UserDto.LoginRequest(userDto.email(), userDto.password())
        );

        ResponseCookie cookie = ResponseCookie.from("access_token", jwt)
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(24 * 60 * 60)
                .sameSite("Lax")
                .build();

        return ResponseEntity.status(HttpStatus.CREATED)
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<UserDto.SummaryResponse> login(@Valid @RequestBody UserDto.LoginRequest dto) {
        String jwt = authService.authenticateUser(dto);
        User user = authService.getUserByEmail(dto.email());

        // Create HttpOnly cookie
        ResponseCookie cookie = ResponseCookie.from("access_token", jwt)
                .httpOnly(true)       // Prevents JavaScript access (XSS protection)
                .secure(false)       // Set to 'true' in production (requires HTTPS)
                .path("/")           // Available for all routes
                .maxAge(24 * 60 * 60) // 24 hours expiration
                .sameSite("Lax")     // CSRF protection
                .build();

        String name = null;
        String pfpUrl = null;

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(new UserDto.SummaryResponse(user.getId(), name, pfpUrl));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        ResponseCookie cookie = ResponseCookie.from("access_token", "")
                .httpOnly(true)
                .secure(false) // Set to true in production
                .path("/")
                .maxAge(0)    // 0 means delete immediately
                .sameSite("Lax")
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body("Logged out successfully");
    }

}


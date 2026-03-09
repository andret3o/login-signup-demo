package com.web.match_me.security;

import com.web.match_me.exception.DuplicateResourceException;
import com.web.match_me.exception.ResourceNotFoundException;
import com.web.match_me.profile.Profile;
import com.web.match_me.profile.ProfileRepository;
import com.web.match_me.user.User;
import com.web.match_me.user.UserDto;
import com.web.match_me.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final ProfileRepository profileRepository;

    @Autowired
    private JwtUtil jwtUtil;

    public UserDto.SummaryResponse registerUser(UserDto.CreateRequest dto) {
        if (userRepository.existsByEmail(dto.email()))  {
            throw new DuplicateResourceException("Email '" + dto.email() + "' is already registered.");
        }

        String encodedPassword = passwordEncoder.encode(dto.password());
        User user = new User(dto.email(), encodedPassword);
        User savedUser = userRepository.save(user);

//        Profile profile = new Profile(savedUser);
//        profileRepository.save(profile);

        return new UserDto.SummaryResponse(savedUser.getId(), null, null);
    }

    public String authenticateUser(UserDto.LoginRequest dto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        dto.email(),
                        dto.password()
                )
        );
        return jwtUtil.generateToken(dto.email());
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));
    }
}

package az.library.library.service.impl;

import az.library.library.dto.request.LoginRequest;
import az.library.library.dto.request.RegisterUserRequest;
import az.library.library.dto.response.AuthResponse;
import az.library.library.dto.response.UserDetailedResponse;
import az.library.library.entity.User;
import az.library.library.enums.Role;
import az.library.library.repository.UserRepository;
import az.library.library.security.JwtService;
import az.library.library.service.UserService;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class UserServiceImpl implements UserService {

    private final UserRepository repo;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserDetailedResponse register(RegisterUserRequest request) {
        if (repo.existsByUsername(request.getUsername()))
            throw new IllegalArgumentException("Username " + request.getUsername() + " already exists");

        if (repo.existsByEmail(request.getEmail()))
            throw new IllegalArgumentException("Email " + request.getEmail() + " already exists");

        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.ROLE_USER)
                .build();

        User saved = repo.save(user);

        return toDetailedResponse(saved);
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        User user = repo.findByUsername(request.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("Invalid username or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword()))
            throw new IllegalArgumentException("Invalid username or password");

        String token = jwtService.generateToken(user.getId(), user.getUsername(), user.getRole());
        return new AuthResponse(token, user.getId(), user.getUsername(), user.getEmail(), user.getRole());
    }

    private UserDetailedResponse toDetailedResponse(User user) {
        UserDetailedResponse response = new UserDetailedResponse();
        response.setId(user.getId());
        response.setUsername(user.getUsername());
        response.setEmail(user.getEmail());
        response.setRole(user.getRole());
        response.setCreatedAt(user.getCreatedAt());
        return response;
    }

}

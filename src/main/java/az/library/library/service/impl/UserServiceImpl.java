package az.library.library.service.impl;

import az.library.library.dto.request.RegisterUserRequest;
import az.library.library.dto.response.UserDetailedResponse;
import az.library.library.entity.User;
import az.library.library.enums.Role;
import az.library.library.repository.UserRepository;
import az.library.library.service.UserService;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository repo;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public UserDetailedResponse register(RegisterUserRequest request) {
        if (repo.existsByUsername(request.getUsername()))
            throw new IllegalArgumentException("Username " + request.getUsername() + " already exists");

        if (repo.existsByEmail(request.getEmail()))
            throw new IllegalArgumentException("Email " + request.getEmail() + " already exists");

        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();

        User saved = repo.save(user);

        return toDetailedResponse(saved);
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

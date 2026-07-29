package az.library.library.service;

import az.library.library.dto.request.LoginRequest;
import az.library.library.dto.request.RegisterUserRequest;
import az.library.library.dto.response.AuthResponse;
import az.library.library.dto.response.UserDetailedResponse;

public interface UserService {

    UserDetailedResponse register(RegisterUserRequest request);

    AuthResponse login(LoginRequest request);

}

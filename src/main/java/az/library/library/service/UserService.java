package az.library.library.service;

import az.library.library.dto.request.RegisterUserRequest;
import az.library.library.dto.response.UserDetailedResponse;

public interface UserService {

    UserDetailedResponse register(RegisterUserRequest request);

}

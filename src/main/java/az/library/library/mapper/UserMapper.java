package az.library.library.mapper;

import az.library.library.dto.request.CreateUserRequest;
import az.library.library.dto.request.UpdateUserRequest;
import az.library.library.dto.response.UserDetailedResponse;
import az.library.library.dto.response.UserSummaryResponse;
import az.library.library.entity.User;
import az.library.library.enums.Role;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "role", source = "role", qualifiedByName = "mapRole")
    User toEntityForCreate(CreateUserRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "role", source = "role", qualifiedByName = "mapRole")
    void updateEntity(UpdateUserRequest request, @MappingTarget User user);

    UserDetailedResponse toDetailedResponse(User user);

    UserSummaryResponse toSummaryResponse(User user);

    @Named("mapRole")
    default Role mapRole(String role) {
        if (role == null || role.isBlank()) {
            return Role.ROLE_USER;
        }
        String normalized = role.toUpperCase().startsWith("ROLE_") ? role.toUpperCase() : "ROLE_" + role.toUpperCase();
        try {
            return Role.valueOf(normalized);
        } catch (IllegalArgumentException e) {
            return Role.ROLE_USER;
        }
    }

}

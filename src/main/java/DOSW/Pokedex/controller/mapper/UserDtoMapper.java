package DOSW.Pokedex.controller.mapper;

import DOSW.Pokedex.controller.dto.response.UserResponse;
import DOSW.Pokedex.core.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserDtoMapper {

    UserResponse toResponse(User user);
}

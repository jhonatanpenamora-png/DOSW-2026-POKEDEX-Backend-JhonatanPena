package DOSW.Pokedex.mapper;

import DOSW.Pokedex.dto.response.UserResponse;
import DOSW.Pokedex.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserDtoMapper {

    UserResponse toResponse(User user);
}

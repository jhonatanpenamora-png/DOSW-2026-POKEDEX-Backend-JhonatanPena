package DOSW.Pokedex.persistence.mapper;

import DOSW.Pokedex.core.model.User;
import DOSW.Pokedex.persistence.entity.relational.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserPersistenceMapper {

    User toDomain(UserEntity entity);

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    UserEntity toEntity(User user);
}

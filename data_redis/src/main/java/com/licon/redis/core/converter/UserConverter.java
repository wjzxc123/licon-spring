package com.licon.redis.core.converter;

import com.licon.redis.core.api.dto.UserDto;
import com.licon.redis.core.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserConverter {

    UserDto userToUserDto(User user);

    User userDtoToUser(UserDto userDto);
}

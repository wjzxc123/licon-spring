package com.licon.redis.core.converter;

import com.licon.redis.core.api.dto.UserDto;
import com.licon.redis.core.entity.User;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserConverter {

    @Mapping(target = "matchPassword", ignore = true)
    UserDto userToUserDto(User user);

    @InheritInverseConfiguration
    User userDtoToUser(UserDto userDto);
}

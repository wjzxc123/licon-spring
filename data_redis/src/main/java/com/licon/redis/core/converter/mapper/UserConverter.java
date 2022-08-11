package com.licon.redis.core.converter.mapper;

import com.licon.redis.core.api.dto.UserDto;
import com.licon.redis.core.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserConverter {
    UserConverter INSTANCE = Mappers.getMapper( UserConverter.class );


    UserDto userToUserDto(User user);

    User userDtoToUser(UserDto userDto);
}

package com.pomingmatgo.userservice.domain.user.mapper;

import com.pomingmatgo.userservice.domain.user.User;
import com.pomingmatgo.userservice.domain.user.UserTmp;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "id", ignore = true) // id 필드 매핑 무시
    @Mapping(target = "signupDate", expression = "java(java.time.LocalDateTime.now())") // signupDate를 현재 시각으로 설정
    User toUser(UserTmp request);
}


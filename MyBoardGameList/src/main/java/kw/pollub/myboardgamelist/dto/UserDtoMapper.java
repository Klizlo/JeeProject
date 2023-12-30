package kw.pollub.myboardgamelist.dto;

import kw.pollub.myboardgamelist.model.User;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserDtoMapper {

    public static List<UserDto> mapToStudentDtos(List<User> users) {
        return users.stream()
                .map(UserDtoMapper::mapToStudentDto)
                .toList();
    }

    public static UserDto mapToStudentDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }

}

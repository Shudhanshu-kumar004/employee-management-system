package myemsproject.Mapper;

import myemsproject.Dto.UsersDto;
import myemsproject.Entity.Users;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;


@Component
public class LoginMapper {

    private static final ModelMapper mapper = new ModelMapper();

    //Converts entity to dto...
    public static UsersDto convertUsersToDto(Users users){
        return mapper.map(users, UsersDto.class);
    }


    //Converts dto to entity...
    public static Users convertUsersDtoToLogin(UsersDto usersDto){
        return mapper.map(usersDto, Users.class);
    }
}
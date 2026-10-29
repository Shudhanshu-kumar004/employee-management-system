package myemsproject.Dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginDto {

    private Long id;
    private String emailId;
    private String password;
}

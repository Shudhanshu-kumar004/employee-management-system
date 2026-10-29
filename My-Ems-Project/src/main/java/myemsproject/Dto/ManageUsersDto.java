package myemsproject.Dto;

import lombok.Getter;
import lombok.Setter;
import myemsproject.Entity.Leaves;
import myemsproject.Entity.Resignations;
import myemsproject.enums.Role;
import myemsproject.enums.UserStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ManageUsersDto {

    private Long id;
    private String name;
    private String emailId;
    private String department;
    private Role role;
    private UserStatus status;
}

package myemsproject.Dto;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
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
public class UsersDto {

    private Long id;
    private List<LeavesDto> leaves = new ArrayList<>();
    private List<ResignationDto> resignations = new ArrayList<>();
    private String name;
    private String emailId;
    private String phoneNumber;
    private String password;
    private Role role;
    private String address;
    private String department;
    private String designation;
    private BigDecimal salary;
    private UserStatus status;
    private LocalDate joiningDate;
    private LocalDate createdAt;
    private LocalDate updatedAt;
}

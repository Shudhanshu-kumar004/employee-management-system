package myemsproject.Dto;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import myemsproject.Entity.Users;
import myemsproject.enums.ResignationStatus;

import java.time.LocalDate;

@Getter
@Setter
public class ResignationDto {

    private Long id;
    private String reason;
    private LocalDate resignationDate;
    private ResignationStatus status;
    private LocalDate approvedAt;

    private Long userId;
    private String userName;
}

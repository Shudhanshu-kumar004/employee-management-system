package myemsproject.Dto;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import myemsproject.Entity.Users;
import myemsproject.enums.LeaveStatus;

import java.time.LocalDate;

@Getter
@Setter
public class LeavesDto {

    private Long id;
    private LocalDate fromDate;
    private LocalDate toDate;
    private String reason;
    private LeaveStatus status;
    private LocalDate appliesAt;
    private LocalDate approvedAt;

    private Long userId;
    private String userName;
}

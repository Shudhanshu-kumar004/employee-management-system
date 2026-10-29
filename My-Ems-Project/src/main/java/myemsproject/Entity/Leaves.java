package myemsproject.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import myemsproject.enums.LeaveStatus;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "leaves")
public class Leaves {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "from_date", nullable = false)
    private LocalDate fromDate;

    @Column(name = "to_date", nullable = false)
    private LocalDate toDate;

    @Column(name = "reason", nullable = false, length = 150)
    private String reason;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private LeaveStatus status;

    @Column(name = "applies_at", nullable = false)
    private LocalDate appliesAt;

    @Column(name = "approved_at")
    private LocalDate approvedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id", nullable = false)
    private Users users;
}
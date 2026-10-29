package myemsproject.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import myemsproject.enums.ResignationStatus;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "resignations")
public class Resignations {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "reason", nullable = false, length = 150)
    private String reason;

    @Column(name = "resignation_date", nullable = false)
    private LocalDate resignationDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ResignationStatus status;

    @Column(name = "approved_at")
    private LocalDate approvedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id", nullable = false)
    private Users users;
}
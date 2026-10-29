package myemsproject.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import myemsproject.enums.Role;
import myemsproject.enums.UserStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "users")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "users", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Leaves> leaves = new ArrayList<>();

    @OneToMany(mappedBy = "users", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Resignations> resignations = new ArrayList<>();

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email_id", nullable = false, unique = true)
    private String emailId;

    @Column(name = "phone_number", nullable = false, unique = true)
    private String phoneNumber;

    @Column(name = "password", nullable = false, length = 255)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "department", nullable = false)
    private String department;

    @Column(name = "designation", nullable = false)
    private String designation;

    @Column(name = "salary", nullable = false, precision = 10, scale = 2)
    private BigDecimal salary;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private UserStatus status;


    @Column(name = "joining_date", nullable = false)
    private LocalDate joiningDate;

    @Column(name = "created_at", nullable = false)
    private LocalDate createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDate updatedAt;

    // Automatically set both dates when inserting a new employee
    @PrePersist
    protected void onCreate() {
        LocalDate today = LocalDate.now(ZoneId.of("Asia/Kolkata"));

        this.createdAt = today;
        this.updatedAt = today;
    }

    // Automatically update updatedAt when modifying an employee
    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDate.now(ZoneId.of("Asia/Kolkata"));
    }

}
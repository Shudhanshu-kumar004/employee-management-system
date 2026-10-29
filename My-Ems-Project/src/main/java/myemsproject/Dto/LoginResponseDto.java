package myemsproject.Dto;

import myemsproject.enums.UserStatus;

import java.math.BigDecimal;
import java.time.LocalDate;

public class LoginResponseDto {

    private String token;
    private Long id;
    private String role;
    private String emailId;
    private String name;
    private String designation;
    private LocalDate joiningDate;
    private UserStatus status;
    private String department;
    private String address;
    private String phoneNumber;
    private BigDecimal salary;
    private LocalDate createdAt;
    private LocalDate updatedAt;

    public LoginResponseDto(
            String token, Long id, String role, String emailId, String name, String designation, LocalDate joiningDate, UserStatus status, String department, String address, String phoneNumber, BigDecimal salary, LocalDate createdAt, LocalDate updatedAt
    ) {
        this.token = token;
        this.id = id;
        this.role = role;
        this.emailId = emailId;
        this.name = name;
        this.designation = designation;
        this.joiningDate = joiningDate;
        this.status = status;
        this.department = department;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.salary = salary;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public LocalDate getJoiningDate() {
        return joiningDate;
    }

    public void setJoiningDate(LocalDate joiningDate) {
        this.joiningDate = joiningDate;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDate getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}

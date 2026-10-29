package myemsproject.Service;

import myemsproject.Entity.Users;

public interface employeeService {

    Users employeeDetails(String emailId, String password);
}

package myemsproject.Controller;

import myemsproject.Dto.ApiResponse;
import myemsproject.Dto.UsersDto;
import myemsproject.Entity.Users;
import myemsproject.Service.employeeServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ems/v1/employee")
public class EmployeeController {

//    private employeeServiceImpl employeeserviceimpl;
//
//    public EmployeeController(employeeServiceImpl employeeserviceimpl) {
//        this.employeeserviceimpl = employeeserviceimpl;
//    }
//
//
//    public ResponseEntity<ApiResponse<String>> employeeDetails(String emailId, String password){
//        Users users = employeeserviceimpl.employeeDetails(emailId, password);
//        ApiResponse<String> response = new ApiResponse<>();
//
//        if (users == null){
//            response.setMessage("User not found!");
//        }
//    }
}

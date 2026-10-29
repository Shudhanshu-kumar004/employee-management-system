package myemsproject.Controller;

import myemsproject.Dto.*;
import myemsproject.Entity.Leaves;
import myemsproject.Entity.Login;
import myemsproject.Entity.Users;
import myemsproject.Repository.UsersRepository;
import myemsproject.Service.EmsServiceImpl;
import myemsproject.Service.JwtService;
import myemsproject.enums.Role;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/ems")
@CrossOrigin(origins = "http://localhost:4200")
public class EmsController {

    private EmsServiceImpl emsServiceImpl;
    private AuthenticationManager authenticationManager;
    private JwtService jwtService;
    private UsersRepository usersRepository;

    public EmsController(EmsServiceImpl emsServiceImpl, AuthenticationManager authenticationManager, JwtService jwtService, UsersRepository usersRepository) {
        this.emsServiceImpl = emsServiceImpl;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.usersRepository = usersRepository;
    }

    @PostMapping("/save")
    public ResponseEntity<ApiResponse<UsersDto>> save(@RequestBody UsersDto usersDto){
        UsersDto save = emsServiceImpl.save(usersDto);
        ApiResponse<UsersDto> response = new ApiResponse<>();
        if (save != null){
            response.setMessage("Data Saved");
            response.setStatus(200);
            response.setData(save);

            return ResponseEntity.ok(response);
        }

        response.setMessage("Data has not saved!");
        response.setStatus(401);
        response.setData(save);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

//    @GetMapping("/users")
//    public ResponseEntity<ApiResponse<List<UsersDto>>> findAll(){
//        List<UsersDto> users = emsServiceImpl.findAll();
//
//        ApiResponse<List<UsersDto>> response = new ApiResponse<>();
//        response.setStatus(200);
//        response.setMessage("Users fetched successfully");
//        response.setData(users);
//
//        return ResponseEntity.ok(response);
//    }

    @PatchMapping("/update/users/{id}")
    public ResponseEntity<UsersDto> updateUsers(
            @RequestBody UsersDto usersDto,
            @PathVariable Long id) {

        UsersDto updatedUser = emsServiceImpl.updateUsers(usersDto, id);
        return ResponseEntity.ok(updatedUser);
    }

    @GetMapping("/update/users/{id}")
    public ResponseEntity<UsersDto> getUserById(@PathVariable Long id) {

        UsersDto user = emsServiceImpl.getUserById(id);

        return ResponseEntity.ok(user);
    }

    @GetMapping("/manage/users")
    public List<ManageUsersDto> ManageUsers(){
        List<ManageUsersDto> manageUsersDto = emsServiceImpl.ManageUsers();
        return manageUsersDto;
    }

    @GetMapping("/filter/search/users")
    public List<UsersDto> filterSearch(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String status
    ){
        //        @RequestParam is used in a Controller to receive values from an HTTP request (URL query parameters or form data).
        //        @Param is used in a Repository to bind a Java method parameter to a named parameter inside a JPQL or Native SQL query.
        return emsServiceImpl.filterSearch(keyword, status);
    }

    @GetMapping("/view/employee/users/{id}")
    public UsersDto viewEmployees(
            @PathVariable Long id
    ){
        return emsServiceImpl.viewEmployees(id);

    }

    @DeleteMapping("/delete/users/{id}")
    public String deleteUsers(
            @PathVariable Long id
    ){
        emsServiceImpl.deleteUsers(id);
        return "user deleted!";
    }

    @GetMapping("/findall/pagination")
    public ResponseEntity<ApiResponse<List<UsersDto>>> findAllPagination(
            @RequestParam(name = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(name = "pageSize", defaultValue = "5", required = false) int pageSize,
            @RequestParam(name = "sort", defaultValue = "id", required = false) String sortBy
    ){
        Page<UsersDto> findAllPagination = emsServiceImpl.findAllPagination(pageNo, pageSize, sortBy);

        ApiResponse response = new ApiResponse();

        response.setMessage("Data fetched");
        response.setStatus(200);
        response.setData(findAllPagination);

        return new ResponseEntity<> (response, HttpStatus.OK);
    }

//    -------------Count Employees--------------------

    @GetMapping("/count")
    public Long usersCount(){
        return emsServiceImpl.count();
    }

    @GetMapping("/count/working/employee")
    public Long countWorkingEmployees(){
        return emsServiceImpl.countWorkingEmployees();
    }

    @GetMapping("/count/leaves")
    public Long countLeaves(){
        return emsServiceImpl.countLeaves();
    }

    @GetMapping("/count/leave/request")
    public Long countLeaveRequests(){
        return emsServiceImpl.countLeaveRequest();
    }

    // ----------------------------------


//    ---------------------- Leaves ------------------

    @PostMapping("/save/leaves")
    public String saveLeaves(@RequestBody LeavesDto leavesDto){
        emsServiceImpl.saveLeaves(leavesDto);
        return "data saved";
    }

    @PutMapping("/update/leaves/{id}")
    public String updateLeaves(
            @PathVariable Long id,
            @RequestBody LeavesDto leavesDto
    ){
        emsServiceImpl.updateLeaves(leavesDto,id);
        return "Data updated";
    }

    @GetMapping("/leaves")
    public List<LeavesDto> Leaves(){
        List<LeavesDto> leavesDto = emsServiceImpl.Leaves();
        return leavesDto;
    }

    //Login Method------------------------------------------


    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponseDto>> login(
            @RequestBody LoginDto loginDto) {

        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                loginDto.getEmailId(),
                                loginDto.getPassword()
                        )
                );

        Users user = usersRepository.findByEmailId(
                authentication.getName()
        );

        String token = jwtService.generateToken(
                user.getEmailId(),
                user.getRole()
        );

        LoginResponseDto loginData = new LoginResponseDto(
                token,
                user.getId(),
                user.getRole().name(),
                user.getEmailId(),
                user.getName(),
                user.getDesignation(),
                user.getJoiningDate(),
                user.getStatus(),
                user.getDepartment(),
                user.getAddress(),
                user.getPhoneNumber(),
                user.getSalary(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );

        ApiResponse<LoginResponseDto> response = new ApiResponse<>();
        response.setMessage("Login successful");
        response.setStatus(200);
        response.setData(loginData);

        return ResponseEntity.ok(response);
    }

    //Employee side'
    @GetMapping("/profile/{id}")
    public ResponseEntity<UsersDto> getProfileById(
            @PathVariable Long id) {

        return ResponseEntity.ok(emsServiceImpl.getProfileById(id));
    }

    @PutMapping("/profile/{id}")
    public ResponseEntity<UsersDto> updateProfile(
            @RequestBody UsersDto usersDto,
            @PathVariable Long id) {

        return ResponseEntity.ok(emsServiceImpl.updateProfile(usersDto, id));
    }

}

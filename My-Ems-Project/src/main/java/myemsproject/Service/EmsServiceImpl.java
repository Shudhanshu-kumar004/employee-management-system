package myemsproject.Service;


import jakarta.transaction.Transactional;
import myemsproject.Dto.ApiResponse;
import myemsproject.Dto.LeavesDto;
import myemsproject.Dto.ManageUsersDto;
import myemsproject.Dto.UsersDto;
import myemsproject.Entity.Leaves;
import myemsproject.Entity.Login;
import myemsproject.Entity.Resignations;
import myemsproject.Entity.Users;
import myemsproject.Mapper.LeavesMapper;
import myemsproject.Mapper.LoginMapper;
import myemsproject.Repository.LeavesRepository;
import myemsproject.Repository.ResignationRepository;
import myemsproject.Repository.UsersRepository;
import myemsproject.enums.LeaveStatus;
import myemsproject.enums.UserStatus;
import org.apache.catalina.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;


@Service
public class EmsServiceImpl implements EmsService{

    private UsersRepository usersRepository;
    private LoginMapper loginMapper;
    private PasswordEncoder passwordEncoder;
    private LeavesRepository leavesRepository;
    private LeavesMapper leavesMapper;
    private ResignationRepository resignationRepository;


    public EmsServiceImpl(UsersRepository usersRepository, LoginMapper loginMapper, PasswordEncoder passwordEncoder, LeavesRepository leavesRepository, LeavesMapper leavesMapper,
                          ResignationRepository resignationRepository) {
        this.usersRepository = usersRepository;
        this.loginMapper = loginMapper;
        this.passwordEncoder = passwordEncoder;
        this.leavesRepository = leavesRepository;
        this.leavesMapper = leavesMapper;
        this.resignationRepository = resignationRepository;
    }

    @Override
    public UsersDto save(UsersDto usersDto){
        Users user = loginMapper.
                convertUsersDtoToLogin(usersDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Users savedlogin = usersRepository.save(user);

        UsersDto dto = loginMapper.
                convertUsersToDto(savedlogin);

        return dto;
    }

//    @Override
//    public List<UsersDto> findAll(){
//        List<Users> users = usersRepository.findAll();
//
//        return users.stream()
//                .map(LoginMapper::convertUsersToDto)
//                .toList();
//    }

    @Override
    public UsersDto updateUsers(UsersDto usersDto, Long id) {

        Users user = usersRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found!"));

        // Update only fields that are provided
        if (usersDto.getName() != null) {
            user.setName(usersDto.getName());
        }

        if (usersDto.getEmailId() != null) {
            user.setEmailId(usersDto.getEmailId());
        }

        if (usersDto.getPhoneNumber() != null) {
            user.setPhoneNumber(usersDto.getPhoneNumber());
        }

        if (usersDto.getDepartment() != null) {
            user.setDepartment(usersDto.getDepartment());
        }

        if (usersDto.getDesignation() != null) {
            user.setDesignation(usersDto.getDesignation());
        }

        if (usersDto.getSalary() != null) {
            user.setSalary(usersDto.getSalary());
        }

        if (usersDto.getAddress() != null) {
            user.setAddress(usersDto.getAddress());
        }

        Users updatedUser = usersRepository.save(user);

        return loginMapper.convertUsersToDto(updatedUser);
    }

    @Override
    public UsersDto getUserById(Long id) {

        Users user = usersRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        UsersDto usersDto = new UsersDto();

        usersDto.setId(user.getId());
        usersDto.setName(user.getName());
        usersDto.setEmailId(user.getEmailId());
        usersDto.setPhoneNumber(user.getPhoneNumber());
        usersDto.setRole(user.getRole());
        usersDto.setDepartment(user.getDepartment());
        usersDto.setDesignation(user.getDesignation());
        usersDto.setSalary(user.getSalary());
        usersDto.setStatus(user.getStatus());
        usersDto.setJoiningDate(user.getJoiningDate());
        usersDto.setAddress(user.getAddress());

        return usersDto;
    }

    @Override
    public List<ManageUsersDto> ManageUsers(){
        List<Users> usersList = usersRepository.findAll();

        List<ManageUsersDto> dtoList = new ArrayList<>();

        for(Users users : usersList){
            ManageUsersDto manageUsersDto = new ManageUsersDto();

            manageUsersDto.setId(users.getId());
            manageUsersDto.setName(users.getName());
            manageUsersDto.setEmailId(users.getEmailId());
            manageUsersDto.setDepartment(users.getDepartment());
            manageUsersDto.setRole(users.getRole());
            manageUsersDto.setStatus(users.getStatus());

            dtoList.add(manageUsersDto);

        }
        return dtoList;
    }

    @Override
    public List<UsersDto> filterSearch(String keyword, String status){
        return usersRepository.filterSearch(keyword, status)
                .stream()
                .map(LoginMapper::convertUsersToDto)
                .toList();
    }

    @Override
    public UsersDto viewEmployees(Long id){
        Users findByIdEmp = usersRepository.findById(id).
                orElseThrow(
                        () -> new RuntimeException("User not found!")
                );

        return LoginMapper.convertUsersToDto(findByIdEmp);
    }

    @Override
    @Transactional
    public String deleteUsers(Long id) {

        Users user = usersRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if ("EMPLOYEE".equals(user.getRole())) {
            leavesRepository.deleteByUsers_Id(id);
            resignationRepository.deleteByUsers_id(id);
            usersRepository.deleteById(id);
        }

        return "User deleted!";
    }

    @Override
    public Page<UsersDto> findAllPagination(int pageNo, int pageSize, String sortBy) {

        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));

        Page<Users> usersPage = usersRepository.findAll(pageable);

        return usersPage.map(LoginMapper::convertUsersToDto);
    }

// --------------------------+ Count Employees +---------------------------

    @Override
    public Long count(){
        return usersRepository.count();
    }

    @Override
    public Long countWorkingEmployees(){
        return usersRepository.workingEmployee();
    }

    @Override
    public Long countLeaves(){
        return leavesRepository.countOnLeaveEmployees();
    }

    @Override
    public Long countLeaveRequest(){
        return leavesRepository.countLeaveRequest();
    }


    //---------------------------------------Leaves--------------------------------------------------

    @Override
    public LeavesDto saveLeaves(LeavesDto leavesDto) {

        Leaves leaves = leavesMapper.converLeavesDtoToLeaves(leavesDto);

        // Fetch user from database
        Users user = usersRepository.findById(leavesDto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Set the relationship
        leaves.setUsers(user);
        leaves.setAppliesAt(LocalDate.now());
        leaves.setStatus(LeaveStatus.PENDING);

        Leaves savedData = leavesRepository.save(leaves);

        return leavesMapper.convertLeavesToLeavesDto(savedData);
    }

    @Override
    public LeavesDto updateLeaves(LeavesDto leavesDto, Long id) {

        Leaves leaves = leavesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Leave not found"));

        // Update only the status on the existing entity
        leaves.setStatus(leavesDto.getStatus());


        if (leaves.getStatus() == LeaveStatus.APPROVED) {
            leaves.setApprovedAt(LocalDate.now());

        } else {
            leaves.setApprovedAt(null);
        }

        Leaves savedLeave = leavesRepository.save(leaves);

        return leavesMapper.convertLeavesToLeavesDto(savedLeave);
    }


    @Transactional
    @Scheduled(cron = "0 0 0 * * *", zone = "Asia/Kolkata")
    public void scheduledLeaves() {

        LocalDate today = LocalDate.now(ZoneId.of("Asia/Kolkata"));

        // Update employee status based on joining date
        List<Users> joiningUsers =
                usersRepository.findByJoiningDateLessThanEqual(today);

        for (Users user : joiningUsers) {

            if (user.getJoiningDate() != null) {
                user.setStatus(UserStatus.ACTIVE);
                usersRepository.save(user);

                System.out.println("Employee activated: " + user.getJoiningDate());
            }if(user.getJoiningDate().equals(today)){
                user.setStatus(UserStatus.ACTIVE);
                usersRepository.save(user);
            }
        }

        // Existing leave logic
        List<Leaves> leaves =
                leavesRepository.findByStatus(LeaveStatus.APPROVED);

        for (Leaves leave : leaves) {

            Users users = leave.getUsers();

            if (users == null) {
                continue;
            }

            // Leave starts today
            if (leave.getFromDate().equals(today)) {
                users.setStatus(UserStatus.ON_LEAVE);
            }

            // Leave ended yesterday
            if (leave.getToDate().plusDays(1).equals(today)) {
                users.setStatus(UserStatus.ACTIVE);
            }

            usersRepository.save(users);
        }

        System.out.println("Scheduled method running...");
        System.out.println("Today: " + today);
    }


    @Override
    public List<LeavesDto> Leaves(){
        List<Leaves> leaves = leavesRepository.leavesList();
        return leaves.stream()
                .map(LeavesMapper::convertLeavesToLeavesDto)
                .toList();
    }


    //Employee side
    @Override
    public UsersDto getProfileById(Long id) {

        Users user = usersRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("User not found with id: " + id)
                );

        UsersDto dto = new UsersDto();

        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmailId(user.getEmailId());
        dto.setPhoneNumber(user.getPhoneNumber());
        dto.setAddress(user.getAddress());

        return dto;
    }

    @Override
    public UsersDto updateProfile(UsersDto usersDto, Long id) {

        Users user = usersRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("User not found with id: " + id)
                );

        user.setName(usersDto.getName());
        user.setEmailId(usersDto.getEmailId());
        user.setPhoneNumber(usersDto.getPhoneNumber());
        user.setAddress(usersDto.getAddress());

        Users updatedUser = usersRepository.save(user);

        UsersDto dto = new UsersDto();

        dto.setId(updatedUser.getId());
        dto.setName(updatedUser.getName());
        dto.setEmailId(updatedUser.getEmailId());
        dto.setPhoneNumber(updatedUser.getPhoneNumber());
        dto.setAddress(updatedUser.getAddress());

        return dto;
    }





}


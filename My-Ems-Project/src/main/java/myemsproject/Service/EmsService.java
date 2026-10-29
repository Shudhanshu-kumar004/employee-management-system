package myemsproject.Service;

import myemsproject.Dto.LeavesDto;
import myemsproject.Dto.LoginDto;
import myemsproject.Dto.ManageUsersDto;
import myemsproject.Dto.UsersDto;
import myemsproject.Entity.Users;
import myemsproject.enums.UserStatus;
import org.springframework.data.domain.Page;

import java.util.List;

public interface EmsService {

//    -----------------Users-------------------
    public UsersDto save(UsersDto usersDto);
//    List<UsersDto> findAll();
    UsersDto updateUsers(UsersDto usersDto, Long id);
    UsersDto getUserById(Long id);
    List<ManageUsersDto> ManageUsers();
    List<UsersDto> filterSearch(String keyword, String status);
    UsersDto viewEmployees(Long id);
//    UsersDto updateUsers(Long id, UsersDto usersDto);
    String deleteUsers(Long id);
    Page<UsersDto> findAllPagination(int pageNo, int pageSize, String sortBy);

//    Employee side
    UsersDto getProfileById(Long id);
    UsersDto updateProfile(UsersDto usersDto, Long id);

    // ---------Count Employees -------------------
    Long count();
    Long countWorkingEmployees();
    Long countLeaves();
    Long countLeaveRequest();


//    ------------------ Leaves ------------------

    LeavesDto saveLeaves(LeavesDto leavesDto);
    LeavesDto updateLeaves(LeavesDto leavesDto,Long id);
    List<LeavesDto> Leaves();
}


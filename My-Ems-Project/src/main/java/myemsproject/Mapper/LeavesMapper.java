package myemsproject.Mapper;

import myemsproject.Dto.LeavesDto;
import myemsproject.Entity.Leaves;
import myemsproject.Entity.Users;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class LeavesMapper {

    private static final ModelMapper mapper = new ModelMapper();


    // Convert Entity to DTO
    public static LeavesDto convertLeavesToLeavesDto(Leaves leaves) {

        LeavesDto dto = mapper.map(leaves, LeavesDto.class);

        if (leaves.getUsers() != null) {
            dto.setUserId(leaves.getUsers().getId());
            dto.setUserName(leaves.getUsers().getName());
        }

        return dto;
    }

    // Convert DTO to Entity
    public static Leaves converLeavesDtoToLeaves(LeavesDto leavesDto) {

        Leaves leaves = mapper.map(leavesDto, Leaves.class);

        if (leavesDto.getUserId() != null) {
            Users user = new Users();
            user.setId(leavesDto.getUserId());
            leaves.setUsers(user);
        }

        return leaves;
    }
}

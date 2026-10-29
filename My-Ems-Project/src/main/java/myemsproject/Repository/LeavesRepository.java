package myemsproject.Repository;

import myemsproject.Entity.Leaves;
import myemsproject.enums.LeaveStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface LeavesRepository extends JpaRepository<Leaves, Long> {
//    Leaves findById();
    Long deleteByUsers_Id(Long id);
    List<Leaves> findByUsers_Id(Long id);
    List<Leaves> findByStatus(LeaveStatus leaveStatus);

    @Query(value = """
SELECT COUNT(*) FROM users u
WHERE u.status = 'ON_LEAVE'
""", nativeQuery = true)
    Long countOnLeaveEmployees();


    @Query(value = """
SELECT COUNT(*) FROM leaves l
WHERE l.status = 'PENDING'
""", nativeQuery = true)
    Long countLeaveRequest();

    @Query(value = """
SELECT * FROM leaves l
WHERE l.status='PENDING'
""", nativeQuery = true)
    List<Leaves> leavesList();
}

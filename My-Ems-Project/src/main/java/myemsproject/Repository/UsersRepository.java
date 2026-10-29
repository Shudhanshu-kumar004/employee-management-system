package myemsproject.Repository;

import myemsproject.Dto.UsersDto;
import myemsproject.Entity.Users;
import myemsproject.enums.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface UsersRepository extends JpaRepository<Users, Long> {

    /*
    * @Query is a Spring Data JPA annotation.
    * It lets you write your own custom query instead of relying on methods like findByName().
    * value contains the SQL query.
    * Triple quotes (""") are Java Text Blocks (Java 15+), which allow multi-line strings without using +.
    * nativeQuery = true
         This tells Spring:
          "Treat this as actual SQL, not JPQL."
           Because of this, you must use:
           table name: users
           column names: name, email_id
           instead of entity names and Java field names.

          -------------
     *LOWER(u.name) LIKE LOWER(CONCAT('%', :keyword, '%'))
     Let's break this into parts.
     LOWER(u.name)
     Converts the name in the database to lowercase.
     Example:
        Stored Name
        After LOWER
        John
        john
        SHUDHANSHU
        shudhanshu
        *
        LOWER(:keyword)
        If the user searches:

        JoHn
        it becomes:
        john
CONCAT('%', :keyword, '%')
% is the SQL wildcard.
%john → ends with "john"
john% → starts with "john"
%john% → contains "john"
Example:
If:
keyword = "shu"
then
CONCAT('%', 'shu', '%')
becomes
%shu%
This matches:
Shudhanshu
Ashutosh
Shubham
    * */


    @Query(value = """
SELECT * FROM users u
WHERE (
        :keyword IS NULL
        OR :keyword = ''
        OR LOWER(u.name) LIKE LOWER(CONCAT('%', :keyword, '%'))
        OR LOWER(u.email_id) LIKE LOWER(CONCAT('%', :keyword, '%'))
      )
AND (
        :status IS NULL
        OR :status = 'ALL EMPLOYEES'
        OR u.status = :status
      )
""", nativeQuery = true)
    List<Users> filterSearch(
            @Param("keyword") String keyword,
            @Param("status") String status
    );

    @Query(value = """
SELECT COUNT(*) FROM users u
WHERE u.status = 'ACTIVE'
""", nativeQuery = true)
    Long workingEmployee();


    List<Users> findByJoiningDateLessThanEqual(LocalDate today);


    //Login feature------------------------------------
    Users findByEmailId(String emailId);
}

package myemsproject.Repository;

import myemsproject.Entity.Resignations;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResignationRepository extends JpaRepository<Resignations, Long> {
    List<Resignations> findByUsers_id(Long id);
    Long deleteByUsers_id(Long id);
}

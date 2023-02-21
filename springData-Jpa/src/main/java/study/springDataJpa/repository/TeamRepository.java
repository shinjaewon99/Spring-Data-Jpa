package study.springDataJpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.springDataJpa.entity.Team;

public interface TeamRepository extends JpaRepository<Team, Long> {

}

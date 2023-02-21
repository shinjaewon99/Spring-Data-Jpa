package study.springDataJpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.springDataJpa.entity.Member;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {

    List<Member> findByUsernameAndAgeGreaterThan(String username, int age);

    List<Member> findByUsername(String username);

}

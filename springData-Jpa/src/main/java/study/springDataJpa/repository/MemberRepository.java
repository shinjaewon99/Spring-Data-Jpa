package study.springDataJpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import study.springDataJpa.dto.MemberDto;
import study.springDataJpa.entity.Member;

import java.util.Collection;
import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {

    List<Member> findByUsernameAndAgeGreaterThan(String username, int age);



    @Query(name = "Member.findByUsername")
    // name의 파람을 입력해줘야됨 (엔티티 클래스에서 파라미터 name을 지정했음으로)
    List<Member> findByUsername(@Param("username") String username);


    @Query("select m from Member m where m.username = :username and m.age = :age")
    List<Member> findUser(@Param("username") String username, @Param("age") int age);


    @Query("select m.username from Member m")
    List<String> findUsernameList();

    // Dto로 조회시 new 오퍼레이션을 "꼭" 써줘야된다 (패키지명을 다 적어줘야된다.)
    @Query("select new study.springDataJpa.dto.MemberDto(m.id, m.username, t.name) from Member m join m.team t")
    List<MemberDto> findMemberDto();

    @Query("select m from Member m where m.username in:names")
    List<Member> findByNames(@Param("names") Collection<String> names);

}

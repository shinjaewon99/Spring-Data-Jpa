package study.springDataJpa.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import study.springDataJpa.dto.MemberDto;
import study.springDataJpa.entity.Member;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

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

    List<Member> findListByUsername (String username); // 컬렉션
    Member findMemberByUsername (String username); // 단건
    Optional<Member> findOptionalByUsername (String username); // 단건 Optional





    // count쿼리를 날릴경우 필요없는 조인을 하기때문에 그 부분을 제거하기위해 쿼리를 작성
    @Query(value = "select m from Member m left join m.team t",
            countQuery = "select count (m) from Member m ")
    // 인터페이스만으로도 구현이 가능하다.
    Page<Member> findByAge(int age, Pageable pageable);


}

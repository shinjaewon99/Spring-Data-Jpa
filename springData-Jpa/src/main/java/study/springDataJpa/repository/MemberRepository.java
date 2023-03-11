package study.springDataJpa.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import study.springDataJpa.dto.MemberDto;
import study.springDataJpa.entity.Member;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom, JpaSpecificationExecutor<Member> {

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

    List<Member> findListByUsername(String username); // 컬렉션

    Member findMemberByUsername(String username); // 단건

    Optional<Member> findOptionalByUsername(String username); // 단건 Optional


    // count쿼리를 날릴경우 필요없는 조인을 하기때문에 그 부분을 제거하기위해 쿼리를 작성
    @Query(value = "select m from Member m left join m.team t",
            countQuery = "select count (m) from Member m ")
    // 인터페이스만으로도 구현이 가능하다.
    Page<Member> findByAge(int age, Pageable pageable);


    // 순수 JPA에서 .executeUpdate(); 와 같이 동작한다 (꼭 추가해줘야된다.)
    // true를 해줌으로써 영속성 컨텍스트를 자동으로 clear 해준다.
    @Modifying(clearAutomatically = true)
    @Query("update Member m set m.age = m.age + 1 where m.age >= :age")
    int bulkAgePlus(@Param("age") int age);


    // fetch 조인을 하게되면 Member를 조회할때 연관된 team을 "같이" 한방 쿼리로 조회한다
    @Query("select m from Member m join fetch m.team")
    List<Member> findMemberFetchJoin();

    @Override
    @EntityGraph(attributePaths = {"team"})
    List<Member> findAll();


    @QueryHints(value = @QueryHint(name = "org.hibernate.readOnly", value = "true"))
    Member findReadOnlyByUsername(String username);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    List<Member> findLockByUsername(String username);


    // 엔티티 클래스가 아닌 DTO 클래스에서 조회
    List<UsernameOnlyDto> findByProjectionsUsername(@Param("username") String username);
    

    // 네이티브 쿼리 (쿼리 그대로 로그에 찍힌다), 제약 사항이 많아 사용 X
    @Query(value = "select * from member where username = ?", nativeQuery = true)
    Member findByNativeQuery(String username);


    @Query(value = "select m.member_id as id, m.usernmae, t.name as teamName" +
            "from member m left join team t",
            countQuery = "select count(*) from member",
            nativeQuery = true)
    Page<MemberProjection> findByNativeProjection(Pageable pageable);




}

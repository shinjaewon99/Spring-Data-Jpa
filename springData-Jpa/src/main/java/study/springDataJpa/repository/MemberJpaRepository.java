package study.springDataJpa.repository;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import study.springDataJpa.entity.Member;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MemberJpaRepository {

    @PersistenceContext
    private EntityManager em;

    public Member save(Member member) {
        em.persist(member);
        return member;
    }

    public void delete(Member member){
        em.remove(member);

    }

    public Optional<Member> findById(Long id){
        Member member = em.find(Member.class, id);
        // null일수도있고 아닐수도있다.
        return Optional.ofNullable(member);
    }

    public List<Member> findAll(){
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();

    }

    public long count(){
        return em.createQuery("select count(m) from Member m", Long.class)
                // 단 건 조회
                .getSingleResult();
    }

    public Member find(Long id) {
        return em.find(Member.class, id);
    }

    // 유저명과 나이조건
    public List<Member> findByUsernameAndGreaterThen(String username, int age){
        return em.createQuery("select m from Member m where m.username = :username and m.age > :age")
                .setParameter("username", username)
                .setParameter("age", age)
                .getResultList();

    }

    public List<Member> findByUsername(String username) {
        // 정의 해놨던 (@NamedQuery)를 불러와서 재사용 할수있다.
        return em.createNamedQuery("Member.findByUsername", Member.class)
                .setParameter("username", username)
                .getResultList();
    }

    public List<Member> findByPage(int age, int offset, int limit){
       return em.createQuery("select m from Member m where m.age = :age order by m.username desc")
                .setParameter("age", age)
                // 어디서부터 가져올지 지정
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }

    public long totalCount(int age){
        return em.createQuery("select count (m) from Member m where m.age = :age", Long.class)
                .setParameter("age", age)
                .getSingleResult();
    }


    // DB의 값을 전체적으로 바꿔야되는경우 벌크성 쿼리 작성
    public int bulkAgePlus(int age){
        // 파라미터로 넘어온 age가 같거나 그 이상인 사람들은 1살이 증가
        return em.createQuery("update Member m set m.age = m.age + 1 " +
                        "where m.age >= :age")
                .setParameter("age", age)
                .executeUpdate();

    }
}

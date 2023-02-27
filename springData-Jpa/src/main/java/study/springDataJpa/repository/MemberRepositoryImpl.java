package study.springDataJpa.repository;

import lombok.RequiredArgsConstructor;
import study.springDataJpa.entity.Member;

import javax.persistence.EntityManager;
import java.util.List;

@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepositoryCustom {

    private final EntityManager em;

    @Override
    public List<Member> findMemberCustom() {
        // 순수 JPA 쿼리, DB에 접근하여 네이티브 쿼리를 사용하고 싶은 경우
        return em.createQuery("select m from Member m")
                .getResultList();
    }
}

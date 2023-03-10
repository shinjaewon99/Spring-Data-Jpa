package study.springDataJpa.repository;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;
import study.springDataJpa.entity.Member;
import study.springDataJpa.entity.Team;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;

public class MemberSpec {

    // 실무에서는 실용성이 떨어져 사용할 일이 없다.
    public static Specification<Member> teamName(final String teamName) {
        return (Specification<Member>) (root, query, builder) -> {

            if (StringUtils.isEmpty(teamName)) {
                return null;
            }
            // JPA Criteria 문법임, 회원과 조인
            Join<Object, Team> t = root.join("team", JoinType.INNER);
            return builder.equal(t.get("name"), teamName);

        };
    }

    public static Specification<Member> userName(final String username) {
        return (Specification<Member>) (root, query, builder) ->
            builder.equal(root.get("username"), username);
    }


}

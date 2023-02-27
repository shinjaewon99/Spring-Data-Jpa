package study.springDataJpa.repository;

import study.springDataJpa.entity.Member;

import java.util.List;

public interface MemberRepositoryCustom {
    List<Member> findMemberCustom();

}

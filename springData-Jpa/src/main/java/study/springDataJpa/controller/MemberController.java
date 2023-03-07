package study.springDataJpa.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import study.springDataJpa.dto.MemberDto;
import study.springDataJpa.entity.Member;
import study.springDataJpa.repository.MemberRepository;

import javax.annotation.PostConstruct;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberRepository memberRepository;

    @GetMapping("/members/{id}")
    public String findMember(@PathVariable("id") Long id){
        Member member = memberRepository.findById(id).get();
        return member.getUsername();
    }


    @GetMapping("/members2/{id}")
    // 권장하지 않음. (조회용으로만 사용)
    public String findMember2(@PathVariable("id") Member member){
        // 스프링이 중간에서 컨버팅 하는 과정을 자동으로 해줘 자동으로 주입된다.
        return member.getUsername();
    }

    @GetMapping("/members")
    public Page<MemberDto> list(@PageableDefault(size = 5, sort = "username") Pageable pageable){
        // sort(정렬) Repository가 호출된다.

        return memberRepository.findAll(pageable)
                .map(member -> new MemberDto(member.getId(), member.getUsername(), null));
    }

    @PostConstruct
    public void init() {
        memberRepository.save(new Member("shin"));
    }
}

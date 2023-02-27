package study.springDataJpa.entity;


import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString(of = {"id", "username", "age"}) // 연관관계 필드는 ToString 하지 않는걸 권장 (여기서는 teem, 무한루프에 빠질수있다)
@NamedQuery(
        name= "Member.findByUsername",
        query= "select m from Member m where m.username = :username"
)
public class Member extends BaseEntity{

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;
    private String username;
    private int age;

//@NoArgsConstructor(access = AccessLevel.PROTECTED) 같은 의미
    protected Member() {
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

    public Member(String username) {
        this.username = username;
    }

    public Member(String username, int age, Team team) {
        this.username = username;
        this.age = age;
        if(team != null){
            changeTeam(team);
        }

    }

    public Member(String username, int age) {
        this.username = username;
        this.age = age;
    }

    public void changeTeam(Team team){
        this.team = team;
        team.getMembers().add(this);
    }
}

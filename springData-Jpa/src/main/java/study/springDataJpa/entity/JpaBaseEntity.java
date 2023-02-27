package study.springDataJpa.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;


// 데이터만 상속 (진짜 상속관계가 X)
@MappedSuperclass
@Getter
@Setter
// 등록일 수정일
public class JpaBaseEntity {
    // 값을 실수로 바꿔도, 업데이트가 되지않는다.
    @Column(updatable = false)
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    // Persist하기전 이벤트가 발생
    @PrePersist
    public void prePersist(){
        LocalDateTime now = LocalDateTime.now();
        createdDate = now;
        updatedDate = now;
    }
    @PreUpdate
    public void preUpdate(){
        updatedDate = LocalDateTime.now();
    }
}

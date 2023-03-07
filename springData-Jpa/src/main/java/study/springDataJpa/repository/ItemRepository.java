package study.springDataJpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.springDataJpa.entity.Item;

public interface ItemRepository extends JpaRepository<Item, Long> {

}

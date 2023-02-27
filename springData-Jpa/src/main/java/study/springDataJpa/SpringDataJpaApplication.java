package study.springDataJpa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.Optional;
import java.util.UUID;

@SpringBootApplication
// 모든 엔티티에 들어가는 공통 컬럼들을 공통Entity를 뽑아내서 사용할때 사용 (대표적으로, 등록일자 수정일자)
@EnableJpaAuditing
public class SpringDataJpaApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringDataJpaApplication.class, args);
	}


	@Bean
	public AuditorAware<String> auditorAware(){


		return () -> Optional.of(UUID.randomUUID().toString());

		// 람다식 사용 X
		/*return new AuditorAware<String>() {
			@Override
			public Optional<String> getCurrentAuditor() {
				return Optional.of(UUID.randomUUID().toString());
			}
		};*/
	}

}

package org.bs.x1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication

// JPA Auditing을 간편하게 활성화 가능
// 엔티티 클래스의 생성일자와 수정일자를 편리하게 관리 가능
@EnableJpaAuditing
public class X1Application {

	public static void main(String[] args) {
		SpringApplication.run(X1Application.class, args);
	}

}

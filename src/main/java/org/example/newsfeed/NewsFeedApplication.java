package org.example.newsfeed;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class NewsFeedApplication {

	public static void main(String[] args) {
		SpringApplication.run(NewsFeedApplication.class, args);
	}


}

// 오늘의 해결 방식 과 생각 흐름 정리해서 트러블슈팅 작성 시도 방법 등 작성하기.
// 에러가 발생했을때, 질문을 해야한는데, 또는 해결해야할때, 명확하게 어느부분에서 에러가 나는지 확인해야됨. 디버깅모드 기본으로 실행하기
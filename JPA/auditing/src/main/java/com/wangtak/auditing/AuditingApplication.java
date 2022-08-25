package com.wangtak.auditing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;
import java.util.UUID;

@EnableJpaAuditing
@SpringBootApplication
public class AuditingApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuditingApplication.class, args);
	}

	@Bean
	public AuditorAware<String> auditorProvider() {
		return () -> Optional.of(UUID.randomUUID().toString());
	}

/*	@Bean
	public AuditorAware<String> auditorProvider() {
		return () -> Optional.ofNullable(SecurityContextHolder.getContext())
				.map(SecurityContext::getAuthentication)
				.filter(authentication -> !authentication.getPrincipal().equals("anonymousUser"))
				.map(Authentication::getPrincipal)
				.map(SessionMemberInfo.class::cast)
				.map(SessionMemberInfo::getEmail);
	}*/

}

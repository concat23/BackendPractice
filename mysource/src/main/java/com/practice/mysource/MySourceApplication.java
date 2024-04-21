package com.practice.mysource;

import com.practice.mysource.domain.RequestContext;
import com.practice.mysource.entity.Role;
import com.practice.mysource.enumeration.Authority;
import com.practice.mysource.init.InMemoryRole;
import com.practice.mysource.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableJpaAuditing
@EnableAspectJAutoProxy
@EnableAsync
@RequiredArgsConstructor
public class MySourceApplication implements CommandLineRunner{

	private final InMemoryRole inMemoryRole;
	public static void main(String[] args) {
		SpringApplication.run(MySourceApplication.class, args);
	}


	@Override
	public void run(String... args) throws Exception {
		inMemoryRole.commandLineRunner();
	}
}

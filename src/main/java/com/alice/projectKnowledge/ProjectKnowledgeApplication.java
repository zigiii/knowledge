package com.alice.projectKnowledge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class ProjectKnowledgeApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjectKnowledgeApplication.class, args);
	}
}

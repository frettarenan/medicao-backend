package com.br.projetomedicao.medicaobackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;

import com.br.projetomedicao.medicaobackend.config.property.MedicaoBackendProperty;

@SpringBootApplication
@EnableConfigurationProperties(MedicaoBackendProperty.class)
public class MedicaoBackendApplication {
	
	private static ApplicationContext APPLICATION_CONTEXT;

	public static void main(String[] args) {
		APPLICATION_CONTEXT = SpringApplication.run(MedicaoBackendApplication.class, args);
	}
	
	public static <T> T getBean(Class<T> type) {
		return APPLICATION_CONTEXT.getBean(type);
	}
}
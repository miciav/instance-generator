package it.unimib.instancegenerator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(ConfProperties.class)
public class InstanceGeneratorApplication {


	
	public static void main(String[] args) {
		SpringApplication.run(InstanceGeneratorApplication.class, args);
	}
}



package it.unimib.instancegenerator;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;


@ConfigurationProperties(prefix="instance")
@Data
public class ConfProperties {
	
	
	private Integer numItems;
	
	private Integer numFamilies;
	
	private Integer numKnapsacks;
	
	private String outputDir;

	
}

package it.unimib.instancegenerator;

import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@ConfigurationProperties(prefix="instance")
@Data
public class ConfProperties {

    @Data
    @NoArgsConstructor
    @ConfigurationProperties(prefix="extremes")
    public class Extremes {
        private Integer minimum =2;
        private Integer maximum =15;
    }


    private Extremes numItems = new Extremes();
	
	private Extremes numFamilies = new Extremes();
	
	private Integer numKnapsacks;
	
	private String outputDir;

	private Integer numInstancesPerGroup =10;

	private Extremes requirementCpu= new Extremes();

	private Extremes requirementMemory =new Extremes();

	private List<Double> weights = Arrays.asList(0.25, 0.50, 0.75);
	
}

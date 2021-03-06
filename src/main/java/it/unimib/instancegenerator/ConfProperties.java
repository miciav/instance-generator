package it.unimib.instancegenerator;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

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

    private Integer numInstancesPerGroup = 10;

    private Extremes requirementCpu = new Extremes();

    private Extremes requirementMemory = new Extremes();

    private List<Double> weights = Arrays.asList(0.25, 0.50, 0.75);

    private int[] arrayTotalNumItems; // are used to generate multiple sets of instances

    private int[] arrayNumKnapsacks; // are used to generate multiple sets of instances

    private int[] arrayNunDimensions; // are used to generate multiple sets of instances

    private String typeName; // are used to generate multiple sets of instances

}

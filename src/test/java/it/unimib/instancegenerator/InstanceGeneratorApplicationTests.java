package it.unimib.instancegenerator;

import it.unimib.instancegenerator.utils.GeneratorUtils;
import org.assertj.core.data.Percentage;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@Import(TestApplicationRunner.class)
public class InstanceGeneratorApplicationTests {

	@Autowired
	private ConfProperties properties;

	@Autowired
    private GeneratorUtils utils;


	@Test
	public void numFamilies(){
        int min=properties.getNumFamilies().getMinimum();
        int max=properties.getNumFamilies().getMaximum();
        IntStream.range(1,20).forEach(i->assertThat(utils.generateRandomNumFamilies()).isBetween(min, max));
	}

    @Test
    public void numItems(){
        int min=properties.getNumItems().getMinimum();
        int max=properties.getNumItems().getMaximum();
        IntStream.range(1,20).forEach(i->assertThat(utils.generateRandomNumItems()).isBetween(min, max));
    }

    @Test
    public void testRandomCPUValues(){
        int min=properties.getRequirementCpu().getMinimum();
        int max=properties.getRequirementCpu().getMaximum();
        IntStream.range(1,20).forEach(i->assertThat(utils.generateRandomCPUValues()).isBetween(min, max));
    }
    @Test
    public void testRandomMemoryValues(){
        int min=properties.getRequirementMemory().getMinimum();
        int max=properties.getRequirementMemory().getMaximum();
        IntStream.range(1,20).forEach(i->assertThat(utils.generateRandomMemoryValues()).isBetween(min, max));
    }
    @Test
    public void testRandomApha(){
        OptionalDouble optMin = properties.getWeights().stream().mapToDouble(Double::doubleValue).min();
        OptionalDouble optMax = properties.getWeights().stream().mapToDouble(Double::doubleValue).max();
        BigDecimal min = BigDecimal.valueOf(optMin.orElse(0.25));
        BigDecimal max = BigDecimal.valueOf(optMax.orElse(0.75));
        IntStream.range(1,100).forEach(i->
            assertThat(utils.generateRandomAlpha()).isBetween(min, max));
    }
    @Test
    public void testGenerateCPUKnapsack(){
	    Integer numKnapsack = 5;

	    IntStream.range(1, 50).forEach(i-> {
            Integer numItems = utils.generateRandomNumItems();
            BigDecimal alpha = utils.generateRandomAlpha();
            List<Integer> cpuValues = IntStream.range(1, numItems).boxed().collect(Collectors.toList());
            int sumCPUValue = cpuValues.stream().mapToInt(Integer::intValue).sum();
            int cpuKnapSack = utils.generateCPUKnapsack(numKnapsack, alpha, cpuValues);
            double testValue = (alpha.doubleValue() * sumCPUValue) / (double) numKnapsack;
            assertThat(testValue).isCloseTo(cpuKnapSack, Percentage.withPercentage(6.0));
        });

    }
    @Test
    public void testRandomProfit(){
        int maxCPU = properties.getRequirementCpu().getMaximum();
        int maxMem = properties.getRequirementMemory().getMaximum();
        int sumReq = maxCPU+maxMem;
        IntStream.range(1, 50).forEach(i->
                assertThat(utils.generateRandomProfit())
                        .isBetween(1, BigDecimal.valueOf(0.5*sumReq).toBigInteger().intValue()+1));
    }
    @Test
    public void testRandomPenalty(){
        int maxCPU = properties.getRequirementCpu().getMaximum();
        int maxMem = properties.getRequirementMemory().getMaximum();
        int sumReq = maxCPU+maxMem;
        IntStream.range(1, 50).forEach(i->
                assertThat(utils.generateRandomPenalty())
                        .isBetween(1, BigDecimal.valueOf(0.25*sumReq).toBigInteger().intValue()+1));
    }




}

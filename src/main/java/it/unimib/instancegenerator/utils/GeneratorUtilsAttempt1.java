package it.unimib.instancegenerator.utils;

import it.unimib.instancegenerator.ConfProperties;
import it.unimib.instancegenerator.domain.Family;
import it.unimib.instancegenerator.domain.Item;
import it.unimib.instancegenerator.domain.Knapsack;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.OptionalDouble;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class GeneratorUtilsAttempt1 {

    private ConfProperties properties;

    public GeneratorUtilsAttempt1(ConfProperties properties) {
        this.properties = properties;
    }

    public int generateRandomNumFamilies() {
        int min = properties.getNumFamilies().getMinimum();
        int max = properties.getNumFamilies().getMaximum();
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }

    public int generateRandomNumItems(){
        int min = properties.getNumItems().getMinimum();
        int max = properties.getNumItems().getMaximum();
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }

    public int generateRandomCPUValues(){
        int min = properties.getRequirementCpu().getMinimum();
        int max = properties.getRequirementCpu().getMaximum();
        return ThreadLocalRandom.current().nextInt(min, max+1);
    }

    public int generateRandomMemoryValues(){
        int min = properties.getRequirementMemory().getMinimum();
        int max = properties.getRequirementMemory().getMaximum();
        return ThreadLocalRandom.current().nextInt(min, max+1);
    }

    public BigDecimal generateRandomAlpha(){
        OptionalDouble optMin = properties.getWeights().stream().mapToDouble(Double::doubleValue).min();
        OptionalDouble optMax = properties.getWeights().stream().mapToDouble(Double::doubleValue).max();
        Double min = optMin.orElse(0.25);
        Double max = optMax.orElse(0.75);
        Double res =ThreadLocalRandom.current().nextDouble(min, max);
        return new BigDecimal(res).setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal generateRandomBeta(){
        return generateRandomAlpha();
    }

    public int generateCPUKnapsack(Integer numKnapsack, BigDecimal alpha, List<Integer> cpuValues){
           Integer sumCPURequirements = cpuValues.stream().mapToInt(Integer::intValue).sum();
           BigDecimal sum = BigDecimal.valueOf(sumCPURequirements);
        return alpha.multiply(sum).divide(BigDecimal.valueOf(numKnapsack), RoundingMode.HALF_UP).toBigInteger().intValue();
    }

    public int generateMemoryKnapsack(Integer numKnapsack, BigDecimal beta, List<Integer> memoryValue){
        return generateCPUKnapsack(numKnapsack, beta, memoryValue);
    }

    public int generateRandomProfit(){
        int maxCPU = properties.getRequirementCpu().getMaximum();
        int maxMem = properties.getRequirementMemory().getMaximum();
        int sumReq = maxCPU+maxMem;
        int bound = BigDecimal.valueOf(0.5 * sumReq).toBigInteger().intValue()+1;
        return  ThreadLocalRandom.current().nextInt(1, bound);
    }

    public int generateRandomPenalty(){
        int maxCPU = properties.getRequirementCpu().getMaximum();
        int maxMem = properties.getRequirementMemory().getMaximum();
        int sumReq = maxCPU+maxMem;
        int bound = BigDecimal.valueOf(0.25 * sumReq).toBigInteger().intValue()+1;
        return  ThreadLocalRandom.current().nextInt(1, bound);
    }

    public Item generateRandomItem(int famlilyId){
        int cpuValue = generateRandomCPUValues();
        int memoryValue = generateRandomMemoryValues();
        Item item = new Item();
        item.setFamilyId(famlilyId);
        item.getWeights().add(cpuValue);
        item.getWeights().add(memoryValue);
        return item;
    }

    public Family generateRandomFamily(int familyId){
        int numItems = generateRandomNumItems();
        List<Item> items = IntStream.range(1, numItems+1)
                .mapToObj(i -> generateRandomItem(familyId))
                .collect(Collectors.toList());
        Family family = new Family();
        family.setFamilyId(familyId);
        family.setItems(items);
        family.setPenalty(generateRandomPenalty());
        family.setProfit(generateRandomProfit());
        return family;
    }

    public List<Family> generateListOfRandomFamily() {
        int numFamilies = generateRandomNumFamilies();
        List<Family> families = IntStream.range(1, numFamilies+1)
                .mapToObj(this::generateRandomFamily).collect(Collectors.toList());
        List<Item> items = families.stream()
                .flatMap(family -> family.getItems().stream()).collect(Collectors.toList());
        IntStream.range(0, items.size()).forEach(i->items.get(i).setItemId(i+1));
        return families;
    }

    public Knapsack generateRandomKnapsack(int knapsakid, int numKnapsack, BigDecimal alpha, BigDecimal beta,  List<Family> families){
        Knapsack knapsack = new Knapsack();
        knapsack.setKnapsackId(knapsakid);
        List<Item> items = families.stream()
                .flatMap(family -> family.getItems().stream()).collect(Collectors.toList());
        List<Integer> cpuValues = items.stream().mapToInt(i->i.getWeights().get(0)).boxed().collect(Collectors.toList());
        List<Integer> memValues = items.stream().mapToInt(i->i.getWeights().get(1)).boxed().collect(Collectors.toList());
        Integer cpuKnapsack = generateCPUKnapsack(numKnapsack,alpha, cpuValues);
        Integer memKnapsack = generateMemoryKnapsack(numKnapsack, beta, memValues);
        knapsack.getCapacities().add(cpuKnapsack);
        knapsack.getCapacities().add(memKnapsack);
        return knapsack;
    }

    public List<Knapsack> generate10Knapsacks(int numKnapsack, List<Family> families) {
        return IntStream.range(1, 11)
                .mapToObj(i -> generateRandomKnapsack(i, numKnapsack, generateRandomAlpha(), generateRandomBeta(), families))
                .collect(Collectors.toList());
    }

}

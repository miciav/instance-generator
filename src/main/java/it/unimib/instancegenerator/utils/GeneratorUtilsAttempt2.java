package it.unimib.instancegenerator.utils;

import it.unimib.instancegenerator.ConfProperties;
import it.unimib.instancegenerator.domain.Family;
import it.unimib.instancegenerator.domain.Item;
import it.unimib.instancegenerator.domain.Knapsack;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class GeneratorUtilsAttempt2 {

    ConfProperties properties;

    public GeneratorUtilsAttempt2(ConfProperties properties) {
        this.properties = properties;
    }

    public int generateRandomNumFamilies() {
        int min = properties.getNumFamilies().getMinimum();
        int max = properties.getNumFamilies().getMaximum();
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }

    public int generateRandomTotalNumberOfItems400to600() {
        List<Integer> numItemsList = Arrays.asList(400, 500, 600);
        return numItemsList.get(new Random().nextInt(numItemsList.size()));
    }

    public int generateRandomNumberOfItems(int maxNumber) {
        int min = Math.min(properties.getNumItems().getMinimum(), maxNumber);
        int max = Math.min(properties.getNumItems().getMaximum(), maxNumber);
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }

    public int generateRandomCPUValues() {
        int min = properties.getRequirementCpu().getMinimum();
        int max = properties.getRequirementCpu().getMaximum();
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }

    public int generateRandomMemoryValues() {
        int min = properties.getRequirementMemory().getMinimum();
        int max = properties.getRequirementMemory().getMaximum();
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }

    public BigDecimal generateRandomAlpha() {
        Double min = 0.75;
        Double max = 0.85;
        Double res = ThreadLocalRandom.current().nextDouble(min, max);
        return new BigDecimal(res).setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal generateRandomBeta() {
        return generateRandomAlpha();
    }

    public int generateCPUKnapsack(Integer numKnapsack, BigDecimal alpha, List<Integer> cpuValues) {
        Integer sumCPURequirements = cpuValues.stream().mapToInt(Integer::intValue).sum();
        BigDecimal sum = BigDecimal.valueOf(sumCPURequirements);
        return alpha.multiply(sum).divide(BigDecimal.valueOf(numKnapsack), RoundingMode.HALF_UP).toBigInteger().intValue();
    }

    public int generateMemoryKnapsack(Integer numKnapsack, BigDecimal beta, List<Integer> memoryValue) {
        return generateCPUKnapsack(numKnapsack, beta, memoryValue);
    }

    public int generateRandomProfit() {
        int maxCPU = properties.getRequirementCpu().getMaximum();
        int maxMem = properties.getRequirementMemory().getMaximum();
        int sumReq = maxCPU + maxMem;
        int bound = BigDecimal.valueOf(0.5 * sumReq).toBigInteger().intValue() + 1;
        return ThreadLocalRandom.current().nextInt(1, bound);
    }

    public int generateRandomPenaltyCorrelated(int profit) {
        double max = 0.5;
        double min = 0.2;
        int maxPenalty = BigDecimal.valueOf(max * profit).toBigInteger().intValue() + 1;
        int minPenalty = BigDecimal.valueOf(min * profit).toBigInteger().intValue();
        return ThreadLocalRandom.current().nextInt(minPenalty, maxPenalty);
    }

    public Item generateRandomItem(int famlilyId) {
        int cpuValue = generateRandomCPUValues();
        int memoryValue = generateRandomMemoryValues();
        Item item = new Item();
        item.setFamilyId(famlilyId);
        item.getWeights().add(cpuValue);
        item.getWeights().add(memoryValue);
        return item;
    }

    public Family generateRandomFamily(int familyId, int maxItems) {
        List<Item> items = generateListRandomItems(familyId, generateRandomNumberOfItems(maxItems));

        Family family = new Family();
        family.setFamilyId(familyId);
        family.setItems(items);
        int profit = generateRandomProfit();
        family.setPenalty(generateRandomPenaltyCorrelated(profit));
        family.setProfit(profit);
        return family;
    }

    public List<Item> generateListRandomItems(int familyId, int numItems) {
        return IntStream
                .range(1, numItems + 1)
                .mapToObj(i -> generateRandomItem(familyId))
                .collect(Collectors.toList());
    }

    public List<Family> generateListOfRandomFamily(int numItems) {
        int remainingNumItems = numItems;
        int familyId = 1;
        List<Family> families = new ArrayList<>();
        while (remainingNumItems > 0) {
            Family family = generateRandomFamily(familyId, remainingNumItems);
            families.add(family);
            familyId++;
            remainingNumItems = remainingNumItems - family.getNumItems();
        }

        List<Item> items = families.stream()
                .flatMap(family -> family.getItems().stream()).collect(Collectors.toList());
        IntStream.range(0, items.size()).forEach(it -> items.get(it).setItemId(it + 1));
        return families;
    }

    public Knapsack generateRandomKnapsack(int knapsakid, int numKnapsack, BigDecimal alpha, BigDecimal beta, List<Family> families) {
        Knapsack knapsack = new Knapsack();
        knapsack.setKnapsackId(knapsakid);
        List<Item> items = families
                .stream()
                .flatMap(family -> family.getItems().stream())
                .collect(Collectors.toList());
        List<Integer> cpuValues = items.stream().mapToInt(i -> i.getWeights().get(0)).boxed().collect(Collectors.toList());
        List<Integer> memValues = items.stream().mapToInt(i -> i.getWeights().get(1)).boxed().collect(Collectors.toList());
        Integer cpuKnapsack = generateCPUKnapsack(numKnapsack, alpha, cpuValues);
        Integer memKnapsack = generateMemoryKnapsack(numKnapsack, beta, memValues);
        knapsack.getCapacities().add(cpuKnapsack);
        knapsack.getCapacities().add(memKnapsack);
        knapsack.setAlpha(alpha);
        knapsack.setBeta(beta);
        return knapsack;
    }

    public List<Knapsack> generateNKnapsacks(int numKnapsack, List<Family> families) {
        return IntStream.range(1, numKnapsack + 1)
                .mapToObj(knapsackId -> generateRandomKnapsack(knapsackId, numKnapsack, generateRandomAlpha(), generateRandomBeta(), families))
                .collect(Collectors.toList());
    }

}

package it.unimib.instancegenerator.utils;

import it.unimib.instancegenerator.ConfProperties;
import it.unimib.instancegenerator.domain.Family;
import it.unimib.instancegenerator.domain.Item;
import it.unimib.instancegenerator.domain.Knapsack;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;


@Component("Utils3")
public class GeneratorUtilsAttempt3 extends GeneratorUtilsAttempt2 {

    public GeneratorUtilsAttempt3(ConfProperties properties) {
        super(properties);
    }

    @Override
    public List<Knapsack> generateNKnapsacks(int numKnapsack, List<Family> families) {
        return IntStream.range(1, numKnapsack + 1)
                .mapToObj(knapsackId -> generateUnbalancedKnapsacks(knapsackId,
                        numKnapsack,
                        generateRandomAlpha(),
                        generateRandomBeta(),
                        families))
                .collect(Collectors.toList());
    }


    protected Knapsack generateUnbalancedKnapsacks(int knapsackId, int numKnapsack, BigDecimal alpha, BigDecimal beta, List<Family> families) {


        return knapsackId == 1 ?
                generateBigKnapsack(knapsackId, families) :
                generateRandomKnapsack(knapsackId, numKnapsack, alpha, beta, families);

    }


    private Knapsack generateBigKnapsack(int knapsackId, List<Family> families) {
        Knapsack knapsack = new Knapsack();
        knapsack.setKnapsackId(knapsackId);


        List<Supplier<Stream<Item>>> allItems = families
                .stream()
                .map(this::createStreamFamilySupplier)
                .collect(Collectors.toList());

        Integer maxSumCpuValue = allItems.stream().mapToInt(
                itemStream -> itemStream.get().mapToInt(i -> i.getWeights().get(0)).sum()
        ).max().orElse(0);

        Integer maxSumMemValue = allItems.stream()
                .mapToInt(
                        itemStream -> itemStream.get()
                                .mapToInt(i -> i.getWeights().get(1))
                                .sum()
                ).max().orElse(0);


        knapsack.getCapacities().add(maxSumCpuValue);
        knapsack.getCapacities().add(maxSumMemValue);
        knapsack.setAlpha(BigDecimal.valueOf(1)); // alpha
        knapsack.setBeta(BigDecimal.valueOf(1));  // beta
        return knapsack;
    }

    protected Supplier<Stream<Item>> createStreamFamilySupplier(Family family) {
        return () -> family.getItems().stream();
    }

}

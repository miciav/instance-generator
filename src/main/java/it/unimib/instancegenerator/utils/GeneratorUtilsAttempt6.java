package it.unimib.instancegenerator.utils;


import it.unimib.instancegenerator.ConfProperties;
import it.unimib.instancegenerator.domain.Family;
import it.unimib.instancegenerator.domain.Item;
import it.unimib.instancegenerator.domain.Knapsack;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Component("Utils6")
public class GeneratorUtilsAttempt6 extends GeneratorUtilsAttempt3 {

    public GeneratorUtilsAttempt6(ConfProperties properties) {
        super(properties);
    }

    public Item generateRandomItem(int famlilyId, int dim) {
        Item item = new Item();
        item.setFamilyId(famlilyId);
        IntStream.range(1, dim + 1).map(i -> generateRandomCPUValues()).forEach(w -> item.getWeights().add(w));
        return item;
    }


    public List<Family> generateListOfRandomFamily(int numItems, int dim) {
        int remainingNumItems = numItems;
        int familyId = 1;
        List<Family> families = new ArrayList<>();
        while (remainingNumItems > 0) {
            Family family = generateRandomFamily(familyId, remainingNumItems, dim);
            families.add(family);
            familyId++;
            remainingNumItems = remainingNumItems - family.getNumItems();
        }

        List<Item> items = families.stream()
                .flatMap(family -> family.getItems().stream()).collect(Collectors.toList());
        IntStream.range(0, items.size()).forEach(it -> items.get(it).setItemId(it + 1));
        return families;
    }


    public List<Knapsack> generateNKnapsacks(int numKnapsack, List<Family> families, int dim) {
        return IntStream.range(1, numKnapsack + 1)
                .mapToObj(knapsackId -> generateUnbalancedKnapsacks6(knapsackId,
                        numKnapsack,
                        IntStream.range(0, dim).mapToObj(i -> generateRandomAlpha()).collect(Collectors.toList()),
                        families,
                        dim))
                .collect(Collectors.toList());
    }


    public Knapsack generateUnbalancedKnapsacks6(int knapsackId,
                                                 int numKnapsack,
                                                 List<BigDecimal> fillRatio,
                                                 List<Family> families,
                                                 int dim) {


        return (knapsackId == 1) ?
                generateBigKnapsack(knapsackId,
                        fillRatio,
                        families,
                        dim) :
                generateRandomKnapsack(knapsackId,
                        numKnapsack,
                        fillRatio,
                        families,
                        dim);


    }


    private <R> Knapsack generateBigKnapsack(int knapsackId, List<BigDecimal> fillRatio, List<Family> families, int dim) {
        Knapsack knapsack = new Knapsack();
        knapsack.setKnapsackId(knapsackId);


        // lista di funzioni che ritornano stream di item
        List<Supplier<Stream<Item>>> allFamilies = families
                .stream()
                .map(this::createStreamFamilySupplier)
                .collect(Collectors.toList());


        //per ogni dimensione idx, stream di tanti elementi quante sono le famiglie
        //ogni elemento è la somma dei pesi della dimensione idx
        //applico quindi il massimo per elemento dello stream il massimo

        List<Integer> listOfMax = IntStream.range(0, dim)
                .mapToObj(idx -> allFamilies.stream()
                        .mapToInt(family -> family.get()
                                .mapToInt(item -> item.getWeights().get(idx))
                                .sum()))
                .mapToInt(perFamily_El_Idx -> perFamily_El_Idx.max().orElse(0)).boxed().collect(Collectors.toList());


        IntStream.range(0, dim).mapToObj(idx -> new Pair(idx, listOfMax.get(idx))).forEach(pair -> {
            knapsack.getCapacities().add((Integer) pair.v2);
            knapsack.getFillRatio().add(fillRatio.get(pair.v1));
        });


        return knapsack;
    }


    public Family generateRandomFamily(int familyId, int maxItems, int dim) {
        List<Item> items = generateListRandomItems(familyId, generateRandomNumberOfItems(maxItems), dim);
        Family family = new Family();
        family.setFamilyId(familyId);
        family.setItems(items);
        int profit = generateRandomProfit();
        family.setPenalty(generateRandomPenaltyCorrelated(profit));
        family.setProfit(profit);
        return family;
    }

    public List<Item> generateListRandomItems(int familyId, int numItems, int dim) {
        return IntStream
                .range(1, numItems + 1)
                .mapToObj(i -> generateRandomItem(familyId, dim))
                .collect(Collectors.toList());
    }


    public Knapsack generateRandomKnapsack(int knapsakid, int numKnapsack, List<BigDecimal> fillRatio, List<Family> families, int dim) {
        Knapsack knapsack = new Knapsack();
        knapsack.setKnapsackId(knapsakid);
        List<Item> items = families
                .stream()
                .flatMap(family -> family.getItems().stream())
                .collect(Collectors.toList());

        IntStream.range(0, dim)
                .mapToObj(j -> new Pair(j, items.stream()
                        .mapToInt(i -> i.getWeights()
                                .get(j))
                        .boxed().collect(Collectors.toList())))
                .map(p -> new Pair(p.v1, generateCPUKnapsack(numKnapsack, fillRatio.get(p.v1), (List<Integer>) p.v2)))
                .forEach(p -> {
                    knapsack.getCapacities().add((Integer) p.v2);
                    knapsack.getFillRatio().add(fillRatio.get(p.v1));
                });

        return knapsack;
    }


    @AllArgsConstructor
    class Pair {
        final int v1;
        final Object v2;

    }


}

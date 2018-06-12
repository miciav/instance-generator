package it.unimib.instancegenerator.utils;


import it.unimib.instancegenerator.ConfProperties;
import it.unimib.instancegenerator.domain.Family;
import it.unimib.instancegenerator.domain.Item;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component("Utils4")
public class GeneratorUtilsAttempt4 extends GeneratorUtilsAttempt2 {
    public GeneratorUtilsAttempt4(ConfProperties properties) {
        super(properties);
    }

    @Override
    public List<Family> generateListOfRandomFamily(int numItems) {
        int remainingNumItems = numItems;
        int familyId = 1;
        int maxItems = 0;
        List<Family> families = new ArrayList<>();
        while (remainingNumItems > 0) {

            maxItems = remainingNumItems > 4 ? remainingNumItems / 4 : remainingNumItems;

            Family family = generateRandomFamily(familyId, maxItems);
            families.add(family);
            familyId++;
            remainingNumItems = remainingNumItems - family.getNumItems();
        }

        List<Item> items = families.stream()
                .flatMap(family -> family.getItems().stream()).collect(Collectors.toList());
        IntStream.range(0, items.size()).forEach(it -> items.get(it).setItemId(it + 1));
        return families;
    }


}

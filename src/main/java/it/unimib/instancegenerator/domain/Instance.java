package it.unimib.instancegenerator.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Instance {

    private List<Item> items;

    private List<Family> families;

    private List<Knapsack> knapsacks;

    public boolean validate() {

        boolean ck1 = checkNumFamilies();
        boolean ck2 = checkNumItemsPerFamily();
        boolean ck3 = checkTotalNumItems();
        boolean ck4 = checkfamiliesWithZeroItems();
        return ck1 &&
                ck2 &&
                ck3 &&
                ck4;
    }

    private boolean checkNumFamilies() {
        Integer numfamilies = families.stream().mapToInt(Family::getFamilyId).max().orElse(0);

        Integer numfamiliesFromItems = items.stream().mapToInt(Item::getFamilyId).max().orElse(0);

        boolean chk = numfamilies.equals(numfamiliesFromItems);
        return chk;

    }

    private boolean checkNumItemsPerFamily() {
        return !families.stream()
                .map(f -> items.stream()
                        .filter(i -> i.getFamilyId() == f.getFamilyId())
                        .count() == f.getNumItems())
                .anyMatch(e -> e == Boolean.FALSE);
    }

    private boolean checkTotalNumItems() {
        return families.stream()
                .mapToInt(Family::getNumItems).sum() == items.size();
    }

    private boolean checkfamiliesWithZeroItems() {
        return families.stream().mapToInt(Family::getNumItems).noneMatch(i -> i == 0);
    }

}

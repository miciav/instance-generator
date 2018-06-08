package it.unimib.instancegenerator.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Family {
    int familyId;
    int profit;
    int penalty;
    int numItems;
    List<Item> items;

    public void setItems(List<Item> items) {
        this.items = items;
        numItems = items.size();
    }
}

package it.unimib.instancegenerator.structs;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class Family {
    int familyId;
    int profit;
    int penalty;
    int numItems;
    List<Item> items;
    public void setItems(List<Item> items){
        this.items = items;
        numItems = items.size();
    }
}

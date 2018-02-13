package it.unimib.instancegenerator.structs;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class Item {
    int itemId;
    private int familyId;
    List<Integer> weights = new ArrayList<>();
}

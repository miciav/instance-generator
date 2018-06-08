package it.unimib.instancegenerator.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class Item {
    int itemId;
    List<Integer> weights = new ArrayList<>();
    private int familyId;
}

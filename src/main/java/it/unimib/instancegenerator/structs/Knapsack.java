package it.unimib.instancegenerator.structs;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class Knapsack {
    int knapsackId;
    List<Integer> capacities = new ArrayList<>();
}

package it.unimib.instancegenerator.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class Knapsack {
    int knapsackId;
    List<Integer> capacities = new ArrayList<>();
    BigDecimal alpha;
    BigDecimal beta;
}

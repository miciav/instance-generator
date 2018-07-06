package it.unimib.instancegenerator.utils;


import it.unimib.instancegenerator.ConfProperties;
import it.unimib.instancegenerator.domain.Item;
import org.springframework.stereotype.Component;

@Component("Utils5")
public class GeneratorUtilsAttempt5 extends GeneratorUtilsAttempt2 {

    public GeneratorUtilsAttempt5(ConfProperties properties) {
        super(properties);
    }

    @Override
    public Item generateRandomItem(int famlilyId) {
        int cpuValue = generateRandomCPUValues() * 4;
        int memoryValue = generateRandomMemoryValues() * 4;
        Item item = new Item();
        item.setFamilyId(famlilyId);
        item.getWeights().add(cpuValue);
        item.getWeights().add(memoryValue);
        return item;
    }


}

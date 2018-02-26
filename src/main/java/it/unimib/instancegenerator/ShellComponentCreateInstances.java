package it.unimib.instancegenerator;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import it.unimib.instancegenerator.domain.Family;
import it.unimib.instancegenerator.domain.Item;
import it.unimib.instancegenerator.domain.Knapsack;
import it.unimib.instancegenerator.utils.GeneratorUtilsAttempt1;
import it.unimib.instancegenerator.utils.GeneratorUtilsAttempt2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.util.FileSystemUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author michele
 */
@ShellComponent
class ShellComponentCreateInstances {

    public final Configuration cfg;
    private final GeneratorUtilsAttempt1 utilsType1;
    private final GeneratorUtilsAttempt2 utilsType2;
    private final ConfProperties properties;

    @Autowired
    public ShellComponentCreateInstances(Configuration cfg, GeneratorUtilsAttempt1 utilsType1, GeneratorUtilsAttempt2 utilsType2, ConfProperties properties) {
        this.cfg = cfg;
        this.utilsType1 = utilsType1;
        this.utilsType2 = utilsType2;
        this.properties = properties;
    }

    @ShellMethod("command to create the first type of instance set")
    public void createInstancesType1() throws IOException {
        Path dir = CleanOutputDir("type1");
        IntStream.range(1, 101).forEach(i -> {
            try {
                createInstanceOfType1(i, dir.toString());
            } catch (IOException | TemplateException e) {
                e.printStackTrace();
            }
        });
    }

    @ShellMethod("command to create the second type of instance set")
    public String createInstancesType2() throws IOException {
        Path dir = CleanOutputDir("type2");
        int numInstancesPerGroup = 10;
        for (int numKnapsacks : new int[]{3, 5, 10}) {
            for (int nunItems : new int[]{400, 500, 600}) {
                for (int instanceId = 1; instanceId <= 10; instanceId++) {
                    try {
                        createInstanceOfType2(numKnapsacks, nunItems, instanceId, dir.toString());
                    } catch (IOException | TemplateException e) {
                        return e.getMessage();
                    }
                }
            }
        }
        return "Instances generated !!";


    }


    private Path CleanOutputDir(String type) throws IOException {
        Path resDir = Paths.get(System.getProperty("user.dir"), properties.getOutputDir() + "_" + type);
        FileSystemUtils.deleteRecursively(resDir);
        return Files.createDirectories(resDir);
    }

    private void createInstanceOfType1(int i, String dir) throws IOException, TemplateException {
        Template template = cfg.getTemplate("instance.ftl");
        Map<String, Object> input = new HashMap<>();

        List<Family> families = utilsType1.generateListOfRandomFamily();
        int numFamilies = families.size();
        List<Item> items = enumerateItems(families);
        int numItems = items.size();
        List<Knapsack> knapsacks = utilsType1.generate10Knapsacks(properties.getNumKnapsacks(), families);

        input.put("numItems", numItems);
        input.put("numFamilies", numFamilies);
        input.put("numKnapsacks", properties.getNumKnapsacks());
        input.put("items", items);
        input.put("families", families);
        input.put("knapsacks", knapsacks);
        // Writer consoleWriter = new OutputStreamWriter(System.out);
        // template.process(input, consoleWriter);

        // to be finished

        String nameFile = String.format("instance_%d.txt", i);
        File output = new File(dir, nameFile);

        try (Writer fileWriter = new FileWriter(output)) {
            template.process(input, fileWriter);
        }
    }


    //------------------------------------------------------------------------------------------------------------------
    private void createInstanceOfType2(int numKnapsacks, int numItems, int instanceId, String dir) throws IOException, TemplateException {
        Template template = cfg.getTemplate("instance.ftl");
        Map<String, Object> input = new HashMap<>();

        List<Family> families = utilsType2.generateListOfRandomFamily(numItems);
        List<Item> items = enumerateItems(families);
        List<Knapsack> knapsacks = utilsType2.generateNKnapsacks(numKnapsacks, families);

        input.put("numItems", items.size());
        input.put("numFamilies", families.size());
        input.put("numKnapsacks", numKnapsacks);
        input.put("items", items);
        input.put("families", families);
        input.put("knapsacks", knapsacks);
        // Writer consoleWriter = new OutputStreamWriter(System.out);
        // template.process(input, consoleWriter);

        // to be finished

        String nameFile = String.format("instance_%d.txt", instanceId);
        Path fileDir = Paths.get(dir, String.format("instances_%d_%d", numKnapsacks, numItems));
        if (!new File(fileDir.toUri()).exists())
            Files.createDirectory(fileDir);
        File output = new File(fileDir.toString(), nameFile);

        try (Writer fileWriter = new FileWriter(output)) {
            template.process(input, fileWriter);
        }
    }

    private List<Item> enumerateItems(List<Family> families) {
        return families.stream().flatMap(family -> family.getItems().stream()).collect(Collectors.toList());
    }


}


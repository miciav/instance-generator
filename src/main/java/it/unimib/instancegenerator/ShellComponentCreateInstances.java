package it.unimib.instancegenerator;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import it.unimib.instancegenerator.domain.Family;
import it.unimib.instancegenerator.domain.Instance;
import it.unimib.instancegenerator.domain.Item;
import it.unimib.instancegenerator.domain.Knapsack;
import it.unimib.instancegenerator.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
    private final GeneratorUtilsAttempt3 utilsType3;
    private final GeneratorUtilsAttempt4 utilsType4;
    private final GeneratorUtilsAttempt5 utilsType5;
    private final GeneratorUtilsAttempt6 utilsType6;
    private final ConfProperties properties;

    @Autowired
    public ShellComponentCreateInstances(Configuration cfg,
                                         GeneratorUtilsAttempt1 utilsType1,
                                         @Qualifier("Utils2") GeneratorUtilsAttempt2 utilsType2,
                                         @Qualifier("Utils3") GeneratorUtilsAttempt3 utilsType3,
                                         @Qualifier("Utils4") GeneratorUtilsAttempt4 utilsType4,
                                         @Qualifier("Utils5") GeneratorUtilsAttempt5 utilsType5,
                                         @Qualifier("Utils6") GeneratorUtilsAttempt6 utilsType6,
                                         ConfProperties properties) {
        this.cfg = cfg;
        this.utilsType1 = utilsType1;
        this.utilsType2 = utilsType2;
        this.utilsType3 = utilsType3;
        this.utilsType4 = utilsType4;
        this.utilsType5 = utilsType5;
        this.utilsType6 = utilsType6;
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
    public String createInstancesType2() throws Exception {
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

    @ShellMethod("command to create big instances the second type of instance set")
    public String createInstancesTypeBig2() throws Exception {
        Path dir = CleanOutputDir("type2-big");
        int numInstancesPerGroup = 10;
        for (int numKnapsacks : new int[]{3, 5, 10}) {
            for (int nunItems : new int[]{1000, 3000, 5000}) {
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


    @ShellMethod("command to create the third type of instance set")
    public String createInstancesType3() throws Exception {
        assert utilsType3.getMinAlpha() != -1;
        String DirName = "type3-" + String.valueOf(utilsType3.getMinAlpha()) + "-" + String.valueOf(utilsType3.getMaxAlpha());
        Path dir = CleanOutputDir(DirName);
        int numInstancesPerGroup = 10;
        for (int numKnapsacks : new int[]{3, 5, 10}) {
            for (int nunItems : new int[]{400, 500, 600}) {
                for (int instanceId = 1; instanceId <= numInstancesPerGroup; instanceId++) {
                    try {
                        createInstanceOfType3(numKnapsacks, nunItems, instanceId, dir.toString());
                    } catch (IOException | TemplateException e) {
                        return e.getMessage();
                    }
                }
            }
        }
        return "Instances generated !!";
    }

    @ShellMethod("command to create big instances of the third type")
    public String createInstancesTypeBig3() throws Exception {
        assert utilsType3.getMinAlpha() != -1;
        String DirName = "type3-big-" + String.valueOf(utilsType3.getMinAlpha()) + "-" + String.valueOf(utilsType3.getMaxAlpha());
        Path dir = CleanOutputDir(DirName);
        int numInstancesPerGroup = 10;
        for (int numKnapsacks : new int[]{3, 5, 10}) {
            for (int nunItems : new int[]{1000, 3000, 5000}) {
                for (int instanceId = 1; instanceId <= numInstancesPerGroup; instanceId++) {
                    try {
                        createInstanceOfType3(numKnapsacks, nunItems, instanceId, dir.toString());
                    } catch (IOException | TemplateException e) {
                        return e.getMessage();
                    }
                }
            }
        }
        return "Instances generated !!";
    }

    @ShellMethod("command to create the fourth type of instance set")
    public String createInstancesType4() throws Exception {
        assert utilsType4.getMinAlpha() != -1;
        String DirName = "type4-" + String.valueOf(utilsType4.getMinAlpha()) + "-" + String.valueOf(utilsType4.getMaxAlpha());
        Path dir = CleanOutputDir(DirName);
        int numInstancesPerGroup = 10;
        for (int numKnapsacks : new int[]{3, 5, 10}) {
            for (int nunItems : new int[]{400, 500, 600}) {
                for (int instanceId = 1; instanceId <= numInstancesPerGroup; instanceId++) {
                    try {
                        createInstanceOfType4(numKnapsacks, nunItems, instanceId, dir.toString());
                    } catch (IOException | TemplateException e) {
                        return e.getMessage();
                    }
                }
            }
        }
        return "Instances generated !!";
    }

    @ShellMethod("command to create big instances of the forth type")
    public String createInstancesTypeBig4() throws Exception {
        assert utilsType4.getMinAlpha() != -1;
        String DirName = "type4-big-" + String.valueOf(utilsType4.getMinAlpha()) + "-" + String.valueOf(utilsType4.getMaxAlpha());
        Path dir = CleanOutputDir(DirName);
        int numInstancesPerGroup = 10;
        for (int numKnapsacks : new int[]{3, 5, 10}) {
            for (int nunItems : new int[]{1000, 3000, 5000}) {
                for (int instanceId = 1; instanceId <= numInstancesPerGroup; instanceId++) {
                    try {
                        createInstanceOfType4(numKnapsacks, nunItems, instanceId, dir.toString());
                    } catch (IOException | TemplateException e) {
                        return e.getMessage();
                    }
                }
            }
        }
        return "Instances generated !!";
    }


    @ShellMethod("command to create the fifth type of instance set")
    public String createInstancesType5() throws Exception {
        assert utilsType5.getMinAlpha() != -1;
        String DirName = "type5-" + String.valueOf(utilsType5.getMinAlpha()) + "-" + String.valueOf(utilsType5.getMaxAlpha());
        Path dir = CleanOutputDir(DirName);
        int numInstancesPerGroup = 10;
        for (int numKnapsacks : new int[]{3, 5, 10}) {
            for (int nunItems : new int[]{400, 500, 600}) {
                for (int instanceId = 1; instanceId <= numInstancesPerGroup; instanceId++) {
                    try {
                        createInstanceOfType5(numKnapsacks, nunItems / 4, instanceId, dir.toString());
                    } catch (IOException | TemplateException e) {
                        return e.getMessage();
                    }
                }
            }
        }
        return "Instances generated !!";
    }

    @ShellMethod("command to create big instances of the fifth type")
    public String createInstancesTypeBig5() throws Exception {
        assert utilsType5.getMinAlpha() != -1;
        String DirName = "type5-big-" + String.valueOf(utilsType5.getMinAlpha()) + "-" + String.valueOf(utilsType5.getMaxAlpha());
        Path dir = CleanOutputDir(DirName);
        int numInstancesPerGroup = 10;
        for (int numKnapsacks : new int[]{3, 5, 10}) {
            for (int nunItems : new int[]{1000, 3000, 5000}) {
                for (int instanceId = 1; instanceId <= numInstancesPerGroup; instanceId++) {
                    try {
                        createInstanceOfType5(numKnapsacks, nunItems / 4, instanceId, dir.toString());
                    } catch (IOException | TemplateException e) {
                        return e.getMessage();
                    }
                }
            }
        }
        return "Instances generated !!";
    }


    @ShellMethod("command to create instances of the sixth type")
    public String createInstancesType6() throws Exception {
        assert utilsType6.getMinAlpha() != -1;
        String DirName = "type6-" + String.valueOf(utilsType6.getMinAlpha()) + "-" + String.valueOf(utilsType6.getMaxAlpha());
        Path dir = CleanOutputDir(DirName);
        int numInstancesPerGroup = 10;
        for (int numKnapsacks : new int[]{10}) {
            for (int nunItems : new int[]{600}) {
                for (int dim : new int[]{2, 4, 6, 8}) {

                    for (int instanceId = 1; instanceId <= numInstancesPerGroup; instanceId++) {
                        try {
                            createInstanceOfType6(numKnapsacks, nunItems, dim, instanceId, dir.toString());
                        } catch (IOException | TemplateException e) {
                            return e.getMessage();
                        }
                    }
                }
            }
        }
        return "Instances generated !!";
    }

    @ShellMethod("command to create instances of the seventh type")
    public String createInstancesType7() throws Exception {
        assert utilsType6.getMinAlpha() != -1;
        String DirName = "type7-" + String.valueOf(utilsType6.getMinAlpha()) + "-" + String.valueOf(utilsType6.getMaxAlpha());
        Path dir = CleanOutputDir(DirName);
        int numInstancesPerGroup = 10;
        for (int numKnapsacks : new int[]{20}) {
            for (int nunItems : new int[]{1200}) {
                for (int dim : new int[]{2}) {

                    for (int instanceId = 1; instanceId <= numInstancesPerGroup; instanceId++) {
                        try {
                            createInstanceOfType6(numKnapsacks, nunItems, dim, instanceId, dir.toString());
                        } catch (IOException | TemplateException e) {
                            return e.getMessage();
                        }
                    }
                }
            }
        }
        return "Instances generated !!";
    }

    @ShellMethod("command to create instances of the eight type")
    public String createInstancesType8() throws Exception {
        assert utilsType6.getMinAlpha() != -1;
        String DirName = "type8-" + String.valueOf(utilsType6.getMinAlpha()) + "-" + String.valueOf(utilsType6.getMaxAlpha());
        Path dir = CleanOutputDir(DirName);
        int numInstancesPerGroup = 10;
        for (int numKnapsacks : new int[]{40}) {
            for (int nunItems : new int[]{2400}) {
                for (int dim : new int[]{2}) {

                    for (int instanceId = 1; instanceId <= numInstancesPerGroup; instanceId++) {
                        try {
                            createInstanceOfType6(numKnapsacks, nunItems, dim, instanceId, dir.toString());
                        } catch (IOException | TemplateException e) {
                            return e.getMessage();
                        }
                    }
                }
            }
        }
        return "Instances generated !!";
    }

    @ShellMethod("command to create instances of the nineth type")
    public String createInstancesType9() throws Exception {
        assert utilsType6.getMinAlpha() != -1;
        String DirName = "type9-" + String.valueOf(utilsType6.getMinAlpha()) + "-" + String.valueOf(utilsType6.getMaxAlpha());
        Path dir = CleanOutputDir(DirName);
        int numInstancesPerGroup = 10;

        int[] numKnapsacks = new int[]{11, 12, 14};

        int[] nunItems = new int[]{660, 720, 840};

        for (int i = 0; i < 3; i++) {
            for (int dim : new int[]{2}) {

                for (int instanceId = 1; instanceId <= numInstancesPerGroup; instanceId++) {
                    try {
                        createInstanceOfType6(numKnapsacks[i], nunItems[i], dim, instanceId, dir.toString());
                    } catch (IOException | TemplateException e) {
                        return e.getMessage();
                    }
                }
            }

        }
        return "Instances generated !!";
    }

    @ShellMethod("command to create instances of the tenth type")
    public String createInstancesType10() throws Exception {
        assert utilsType6.getMinAlpha() != -1;
        String DirName = "type10-" + String.valueOf(utilsType6.getMinAlpha()) + "-" + String.valueOf(utilsType6.getMaxAlpha());
        Path dir = CleanOutputDir(DirName);
        int numInstancesPerGroup = 10;

        int[] numKnapsacks = new int[]{12};

        int[] nunItems = new int[]{720};

        for (int i = 0; i < numKnapsacks.length; i++) {
            for (int dim : new int[]{4, 6, 8}) {

                for (int instanceId = 1; instanceId <= numInstancesPerGroup; instanceId++) {
                    try {
                        createInstanceOfType6(numKnapsacks[i], nunItems[i], dim, instanceId, dir.toString());
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
    private void createInstanceOfType2(int numKnapsacks, int numItems, int instanceId, String dir) throws Exception {

        List<Family> families = utilsType2.generateListOfRandomFamily(numItems);
        List<Item> items = enumerateItems(families);
        List<Knapsack> knapsacks = utilsType2.generateNKnapsacks(numKnapsacks, families);
        createInstanceFile(numKnapsacks, numItems, instanceId, dir, families, items, knapsacks, "instance.ftl");
    }

    //------------------------------------------------------------------------------------------------------------------
    private void createInstanceOfType3(int numKnapsacks, int numItems, int instanceId, String dir) throws Exception {

        /*
         * Queste istanze sono create come quelle del tipo 2 con la differenza che uno degli zaini è abbastanza
         * grande a contenere la famiglia più grande
         * */

        List<Family> families = utilsType3.generateListOfRandomFamily(numItems);
        List<Item> items = enumerateItems(families);
        List<Knapsack> knapsacks = utilsType3.generateNKnapsacks(numKnapsacks, families);
        createInstanceFile(numKnapsacks, numItems, instanceId, dir, families, items, knapsacks, "instance.ftl");
    }

    //------------------------------------------------------------------------------------------------------------------
    private void createInstanceOfType4(int numKnapsacks, int numItems, int instanceId, String dir) throws Exception {


        /*
         * Le istanze di tipo 4 sono come quelle di tipo 2 ma ogni famiglia ha un numero minore (circa 1/4) di item rispetto a quelle
         * del tipo 2.
         */
        List<Family> families = utilsType4.generateListOfRandomFamily(numItems);
        List<Item> items = enumerateItems(families);
        List<Knapsack> knapsacks = utilsType4.generateNKnapsacks(numKnapsacks, families);
        createInstanceFile(numKnapsacks, numItems, instanceId, dir, families, items, knapsacks, "instance.ftl");
    }

    //------------------------------------------------------------------------------------------------------------------
    private void createInstanceOfType5(int numKnapsacks, int numItems, int instanceId, String dir) throws Exception {


        /*
         * Le istanze di tipo 5 sono come quelle di tipo 2 ma ogni famiglia ha un numero minore (circa 1/4) di item rispetto a quelle
         * del tipo 2 ed ogni item e' circa 4 volte piu pesante.
         */
        List<Family> families = utilsType5.generateListOfRandomFamily(numItems);
        List<Item> items = enumerateItems(families);
        List<Knapsack> knapsacks = utilsType5.generateNKnapsacks(numKnapsacks, families);
        createInstanceFile(numKnapsacks, numItems, instanceId, dir, families, items, knapsacks, "instance.ftl");
    }

    //------------------------------------------------------------------------------------------------------------------
    private void createInstanceOfType6(int numKnapsacks, int numItems, int dim, int instanceId, String dir) throws Exception {


        /*
         * Le istanze di tipo 5 sono come quelle di tipo 3 Inoltre le istanze hanno un numero variabile di dimensioni
         */
        List<Family> families = utilsType6.generateListOfRandomFamily(numItems, dim);
        List<Item> items = enumerateItems(families);
        List<Knapsack> knapsacks = utilsType6.generateNKnapsacks(numKnapsacks, families, dim);
        createInstanceFileType6(numKnapsacks, numItems, instanceId, dir, families, items, knapsacks, dim, "instanceExtended.ftl");
    }

    private void createInstanceFile(int numKnapsacks,
                                    int numItems,
                                    int instanceId,
                                    String dir,
                                    List<Family> families,
                                    List<Item> items,
                                    List<Knapsack> knapsacks,
                                    String templateName) throws Exception {

        Instance instance = new Instance(items, families, knapsacks);
        if (!instance.validate()) throw new Exception("instance not valid");

        //Template template = cfg.getTemplate("instance.ftl");
        Template template = cfg.getTemplate(templateName);
        Map<String, Object> input = new HashMap<>();

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

    private void createInstanceFileType6(int numKnapsacks,
                                         int numItems,
                                         int instanceId,
                                         String dir,
                                         List<Family> families,
                                         List<Item> items,
                                         List<Knapsack> knapsacks,
                                         int dim,
                                         String templateName) throws Exception {

        Instance instance = new Instance(items, families, knapsacks);
        if (!instance.validate()) throw new Exception("instance not valid");

        //Template template = cfg.getTemplate("instance.ftl");
        Template template = cfg.getTemplate(templateName);
        Map<String, Object> input = new HashMap<>();

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
        Path fileDir = Paths.get(dir, String.format("instances_%d_%d_%d", numKnapsacks, numItems, dim));
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


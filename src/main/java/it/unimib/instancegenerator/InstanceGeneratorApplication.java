package it.unimib.instancegenerator;

import freemarker.core.ParseException;
import freemarker.template.*;
import it.unimib.instancegenerator.structs.Family;
import it.unimib.instancegenerator.structs.Item;
import it.unimib.instancegenerator.structs.Knapsack;
import it.unimib.instancegenerator.utils.GeneratorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.util.FileSystemUtils;

import java.io.*;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@SpringBootApplication
@EnableConfigurationProperties(ConfProperties.class)
public class InstanceGeneratorApplication {


	
	public static void main(String[] args) {
		SpringApplication.run(InstanceGeneratorApplication.class, args);
	}
}
/**
 * @author michele
 *
 */
@ShellComponent
class CreateInstanceSet {

    @Autowired
    private GeneratorUtils utils;
	@Autowired
    private ConfProperties properties;
	
	@Autowired
	public Configuration cfg;
	
	@ShellMethod ("command to create the instance set")
	public void createInstances() throws TemplateNotFoundException, MalformedTemplateNameException, ParseException, IOException, TemplateException {
        Path resDir = Paths.get(System.getProperty("user.dir"), "results");
        FileSystemUtils.deleteRecursively(resDir);
        Path dir = Files.createDirectories(resDir);
        IntStream.range(1,101).forEach(i-> {
            try {
                createInstance(i, dir.toString());
            } catch (IOException e) {
                e.printStackTrace();
            } catch (TemplateException e) {
                e.printStackTrace();
            }
        });

        
	}

	private void createInstance(int i, String dir) throws IOException, TemplateException {
        Template template = cfg.getTemplate("instance.ftl");
        Map<String, Object> input = new HashMap<String, Object>();

        List<Family> families = utils.generateRandomFamilyList();
        int numFamilies = families.size();
        List<Item> items = families.stream().flatMap(family -> family.getItems().stream()).collect(Collectors.toList());
        int numItems = items.size();
        List<Knapsack> knapsacks = generate10Knapsacks(properties.getNumKnapsacks(), families);

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
        File output =new File(dir, nameFile);
        Writer fileWriter = new FileWriter(output);

        try {
            template.process(input, fileWriter);
        } finally {
            fileWriter.close();
        }
    }

	private  List<Knapsack> generate10Knapsacks(int numKnapsack, List<Family> families){
       return IntStream.range(1,11)
               .mapToObj(i-> utils.generateRandomKnapsack(i, numKnapsack, utils.generateRandomAlpha(), utils.generateRandomBeta(), families))
               .collect(Collectors.toList());
    }


}



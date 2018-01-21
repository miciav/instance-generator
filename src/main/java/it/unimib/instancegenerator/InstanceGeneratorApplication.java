package it.unimib.instancegenerator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import freemarker.core.ParseException;
import freemarker.template.Configuration;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateException;
import freemarker.template.TemplateNotFoundException;

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
    private ConfProperties properties;
	
	@Autowired
	public Configuration cfg;
	
	@ShellMethod ("command to create the instance set")
	public void createInstances() throws TemplateNotFoundException, MalformedTemplateNameException, ParseException, IOException, TemplateException {
	
		Template template = cfg.getTemplate("instance.ftl");
		Map<String, Object> input = new HashMap<String, Object>();
		input.put("numItems", 20);
		input.put("numFamilies", 30);
		input.put("numKnapsacks", 3);
		input.put("items", 29);
		input.put("families", 2000);
		input.put("knapsacks", 33);
		Writer consoleWriter = new OutputStreamWriter(System.out);
        template.process(input, consoleWriter);
        
        
        // to be finished
        Writer fileWriter = new FileWriter(new File("output.html"));
        try {
            template.process(input, fileWriter);
        } finally {
            fileWriter.close();
        }
        
        
	}
}



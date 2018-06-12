package it.unimib.instancegenerator;

import it.unimib.instancegenerator.utils.GeneratorUtilsAttempt4;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.shell.Shell;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@Import(TestApplicationRunner.class)
public class CreateInstancesType4Test {

    @Autowired
    private Shell shell;
    @Autowired
    @Qualifier("Utils4")
    private GeneratorUtilsAttempt4 utils4;


    @Test
    public void instancesOfTypeBig4_045_055() {
        utils4.setMinAlpha(0.45);
        utils4.setMaxAlpha(0.55);
        assertThat(shell.evaluate(() -> "create-instances-type-big4")).isEqualTo("Instances generated !!");
    }

    @Test
    public void instancesOfTypeBig4_075_085() {
        utils4.setMinAlpha(0.75);
        utils4.setMaxAlpha(0.85);
        assertThat(shell.evaluate(() -> "create-instances-type-big4")).isEqualTo("Instances generated !!");
    }


    @Test
    public void instancesOfType4_045_055() {
        utils4.setMinAlpha(0.45);
        utils4.setMaxAlpha(0.55);
        assertThat(shell.evaluate(() -> "create-instances-type4")).isEqualTo("Instances generated !!");
    }

    @Test
    public void instancesOfType4_075_085() {
        utils4.setMinAlpha(0.75);
        utils4.setMaxAlpha(0.85);
        assertThat(shell.evaluate(() -> "create-instances-type4")).isEqualTo("Instances generated !!");
    }
}

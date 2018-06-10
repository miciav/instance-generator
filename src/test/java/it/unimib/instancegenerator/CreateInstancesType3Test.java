package it.unimib.instancegenerator;

import it.unimib.instancegenerator.utils.GeneratorUtilsAttempt3;
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
public class CreateInstancesType3Test {

    @Autowired
    private Shell shell;
    @Autowired
    @Qualifier("Utils3")
    private GeneratorUtilsAttempt3 utils3;


    @Test
    public void instancesOfTypeBig3_045_055() {
        utils3.setMinAlpha(0.45);
        utils3.setMaxAlpha(0.55);
        assertThat(shell.evaluate(() -> "create-instances-type-big3")).isEqualTo("Instances generated !!");
    }

    @Test
    public void instancesOfTypeBig3_075_085() {
        utils3.setMinAlpha(0.75);
        utils3.setMaxAlpha(0.85);
        assertThat(shell.evaluate(() -> "create-instances-type-big3")).isEqualTo("Instances generated !!");
    }


    @Test
    public void instancesOfType3_045_055() {
        utils3.setMinAlpha(0.45);
        utils3.setMaxAlpha(0.55);
        assertThat(shell.evaluate(() -> "create-instances-type3")).isEqualTo("Instances generated !!");
    }

    @Test
    public void instancesOfType3_075_085() {
        utils3.setMinAlpha(0.75);
        utils3.setMaxAlpha(0.85);
        assertThat(shell.evaluate(() -> "create-instances-type3")).isEqualTo("Instances generated !!");
    }
}

package it.unimib.instancegenerator;

import it.unimib.instancegenerator.utils.GeneratorUtilsAttempt5;
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
public class CreateInstancesType5Test {

    @Autowired
    private Shell shell;
    @Autowired
    @Qualifier("Utils5")
    private GeneratorUtilsAttempt5 utils5;


    @Test
    public void instancesOfTypeBig5_045_055() {
        utils5.setMinAlpha(0.45);
        utils5.setMaxAlpha(0.55);
        assertThat(shell.evaluate(() -> "create-instances-type-big5")).isEqualTo("Instances generated !!");
    }

    @Test
    public void instancesOfTypeBig5_075_085() {
        utils5.setMinAlpha(0.75);
        utils5.setMaxAlpha(0.85);
        assertThat(shell.evaluate(() -> "create-instances-type-big5")).isEqualTo("Instances generated !!");
    }


    @Test
    public void instancesOfType5_045_055() {
        utils5.setMinAlpha(0.45);
        utils5.setMaxAlpha(0.55);
        assertThat(shell.evaluate(() -> "create-instances-type5")).isEqualTo("Instances generated !!");
    }

    @Test
    public void instancesOfType5_075_085() {
        utils5.setMinAlpha(0.75);
        utils5.setMaxAlpha(0.85);
        assertThat(shell.evaluate(() -> "create-instances-type5")).isEqualTo("Instances generated !!");
    }
}

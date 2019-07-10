package it.unimib.instancegenerator;


import it.unimib.instancegenerator.utils.GeneratorUtilsAttempt6;
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
public class CreateInstancesType7_8_9Test {

    @Autowired
    private Shell shell;
    @Autowired
    @Qualifier("Utils6")
    private GeneratorUtilsAttempt6 utils6;


/*    @Test
    public void instancesOfTypeBig7_027_033() {
        // queste istanze sono quelle di grandissime dimensioni 20x1200 usate nell'ultima versione del paper
        utils6.setMinAlpha(0.27);
        utils6.setMaxAlpha(0.33);
        assertThat(shell.evaluate(() -> "create-instances-type7")).isEqualTo("Instances generated !!");
    }*/

/*    @Test
    public void instancesOfTypeBig8_027_033() {
        // queste istanze sono quelle di grandissime dimensioni 40x2400 usate nell'ultima versione del paper
        utils6.setMinAlpha(0.27);
        utils6.setMaxAlpha(0.33);
        assertThat(shell.evaluate(() -> "create-instances-type8")).isEqualTo("Instances generated !!");
    }*/

    @Test
    public void instancesOfTypeBig9_027_033() {
        // queste istanze sono quelle di grandi dimensioni
        // 1) 11 zaini e 660 items
        // 2) 12 zaini e 720 items
        // 3) 14 zaini e 840 items
        // usate nell'ultima versione del paper

        utils6.setMinAlpha(0.27);
        utils6.setMaxAlpha(0.33);
        assertThat(shell.evaluate(() -> "create-instances-type9")).isEqualTo("Instances generated !!");
    }

}

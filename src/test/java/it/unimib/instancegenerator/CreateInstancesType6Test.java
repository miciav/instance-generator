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
public class CreateInstancesType6Test {

    @Autowired
    private Shell shell;
    @Autowired
    @Qualifier("Utils6")
    private GeneratorUtilsAttempt6 utils6;


    @Test
    public void instancesOfTypeBig6_027_033() {
        utils6.setMinAlpha(0.27);
        utils6.setMaxAlpha(0.33);
        assertThat(shell.evaluate(() -> "create-instances-type6")).isEqualTo("Instances generated !!");
    }

}

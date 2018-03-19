package it.unimib.instancegenerator;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.shell.Shell;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest
@Import(TestApplicationRunner.class)
public class CreateInstancesType2Test {
    @Autowired
    private Shell shell;

    @Test
    public void playerCanRecordEntireScoreOfGame() {
        assertThat(shell.evaluate(() -> "create-instances-type3")).isEqualTo("Instances generated !!");
    }

}

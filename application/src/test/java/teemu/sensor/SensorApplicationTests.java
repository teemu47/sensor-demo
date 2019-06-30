package teemu.sensor;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;
import teemu.sensor.controller.DifferenceController;
import teemu.sensor.controller.SummaryController;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@ComponentScan(basePackageClasses = {SummaryController.class, DifferenceController.class})
public class SensorApplicationTests {
  
  @Autowired
  SummaryController summaryController;
  
  @Autowired
  DifferenceController differenceController;
  
  @Test
  public void contextLoads() {
    assertThat(summaryController).isNotNull();
    assertThat(differenceController).isNotNull();
  }
  
}

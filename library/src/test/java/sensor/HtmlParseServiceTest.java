package sensor;

import static org.assertj.core.api.Assertions.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import sensor.model.HtmlParseException;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = HtmlParseService.class)
public class HtmlParseServiceTest {
  
  @Autowired
  HtmlParseService htmlParseService;
  
  @Test
  public void getTemperatureReturnsDouble() throws Exception {
    assertThat(htmlParseService.getTemperature("helsinki")).isNotNaN();
  }
  
  @Test(expected = HtmlParseException.class)
  public void getTemperatureThrowsSensorCityNotFoundException() throws Exception {
      htmlParseService.getTemperature("mockCity");
  }
}

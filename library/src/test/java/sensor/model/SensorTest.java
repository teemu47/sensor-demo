package sensor.model;

import static org.assertj.core.api.Assertions.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
public class SensorTest {
  
  Sensor sensor;
  
  @Before
  public void init() {
    sensor = new Sensor("mockId");
  }
  
  @Test
  public void addTemperature() {
    assertThat(sensor.getCount()).isEqualTo(0);
    assertThat(sensor.getSum()).isEqualTo(0);
    sensor.addTemperature(1000);
    assertThat(sensor.getCount()).isEqualTo(1);
    assertThat(sensor.getSum()).isEqualTo(1000);
  }
  
  @Test
  public void countAverage() {
    sensor.addTemperature(2000);
    sensor.addTemperature(1000);
    assertThat(sensor.getAvgTemp()).isEqualTo(0);
    sensor.countAverage();
    assertThat(sensor.getAvgTemp()).isEqualTo(15.0);
  }
}

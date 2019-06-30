package sensor;

import static org.assertj.core.api.Assertions.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import sensor.model.Sensor;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SensorLibrary.class)
public class SensorLibraryTest {
  
  @Autowired
  private SensorLibrary sensorLibrary;
  
  @MockBean
  private DatabaseService databaseService;
  
  @MockBean
  private HtmlParseService htmlParseService;
  
  private List<Sensor> sensorList;
  
  @Before
    public void init() throws Exception {
    sensorList = new ArrayList<>();
    Sensor sensor = new Sensor("mockID");
    sensor.addTemperature(1000);
    sensor.addTemperature(2000);
    sensorList.add(sensor);
    
    Mockito.when(databaseService.getSensors()).thenReturn(sensorList);
    Mockito.when(databaseService.getLatestTemperature("mockID")).thenReturn(20.0);
    Mockito.when(htmlParseService.getTemperature(Mockito.any())).thenReturn(15.0);
  }
  
  @Test
  public void getSummaryReturnsList() throws Exception {
    assertThat(sensorLibrary.getSummary().size()).isGreaterThan(0);
  }
  
  @Test
  public void getSummaryCalculatesAverages() throws Exception {
    assertThat(sensorList.get(0).getAvgTemp()).isEqualTo(0);
    sensorList = sensorLibrary.getSummary();
    assertThat(sensorList.get(0).getAvgTemp()).isEqualTo(15);
  }
  
  @Test
  public void getDifferenceCalculatesDifference() throws Exception {
    assertThat(sensorLibrary.getLatestSensorTemperature("mockID")).isEqualTo(20.0);
    assertThat(sensorLibrary.getLocalTemperature("mockCity")).isEqualTo(15.0);
  }
  
}

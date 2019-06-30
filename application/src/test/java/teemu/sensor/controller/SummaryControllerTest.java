package teemu.sensor.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import sensor.SensorLibrary;
import sensor.model.Sensor;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(SummaryController.class)
public class SummaryControllerTest {
  
  @Autowired
  private MockMvc mockMvc;
  
  @MockBean
  SensorLibrary sensorLibrary;
  
  private List<Sensor> sensorList;
  
  @Before
  public void init() {
    sensorList = new ArrayList<>();
    Sensor sensor = new Sensor("mockID");
    sensor.addTemperature(2000);
    sensor.countAverage();
    sensorList.add(sensor);
  }
  
  @Test
  public void getSummaryTest() throws Exception {
    Mockito.when(sensorLibrary.getSummary()).thenReturn(sensorList);
    mockMvc.perform(get("/api/summary"))
      .andDo(print())
      .andExpect(status().isOk())
      .andExpect(content().string(containsString("sensors")))
      .andExpect(content().string(containsString("\"sensorId\":\"mockID\"")))
      .andExpect(content().string(containsString("\"avgTemp\":20.0")))
      .andExpect(content().string(containsString("\"count\":1")))
      .andExpect(content().string(containsString("\"sum\":2000")));
  }
}

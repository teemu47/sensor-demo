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

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(DifferenceController.class)
public class DifferenceControllerTest {
  
  @Autowired
  private MockMvc mockMvc;
  
  @MockBean
  SensorLibrary sensorLibrary;
  
  @Before
  public void init() throws Exception {
    Mockito.when(sensorLibrary.getLatestSensorTemperature("mockID")).thenReturn(22.5);
    Mockito.when(sensorLibrary.getLocalTemperature(anyString())).thenReturn(20.0);
  }
  
  @Test
  public void getDifferenceTest() throws Exception {
    mockMvc.perform(get("/api/diff/mockID"))
      .andDo(print())
      .andExpect(status().isOk())
      .andExpect(content().string(is("{\"difference\":2.5}")));
  }
  
}

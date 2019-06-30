package teemu.sensor.model;

import sensor.model.Sensor;

import java.util.ArrayList;
import java.util.List;

public class SensorList {
  private List<Sensor> sensors;
  
  public SensorList(){
    this.sensors = new ArrayList<>();
  }
  
  public List<Sensor> getSensors() {
    return sensors;
  }
  
  public void setSensors(List<Sensor> sensors) {
    this.sensors = sensors;
  }
}

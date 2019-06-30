package sensor.model;

public class Sensor {
  private String sensorId;
  private int count;
  private double avgTemp;
  private int sum;
  
  public Sensor(String sensorId) {
    this.sensorId = sensorId;
    this.count = 0;
    this.avgTemp = 0;
    this.sum = 0; // temperatures in database are * 100
  }
  
  public String getSensorId() {
    return sensorId;
  }
  
  public void setSensorId(String sensorId) {
    this.sensorId = sensorId;
  }
  
  public int getCount() {
    return count;
  }
  
  public void setCount(int count) {
    this.count = count;
  }
  
  public double getAvgTemp() {
    return avgTemp;
  }
  
  public void setAvgTemp(double avgTemp) {
    this.avgTemp = avgTemp;
  }
  
  public int getSum() {
    return sum;
  }
  
  public void setSum(int sum) {
    this.sum = sum;
  }
  
  // Not counting the average here because of performance
  public void addTemperature(int temperature) {
    this.sum += temperature;
    this.count++;
  }
  
  public void countAverage(){
    this.avgTemp = Math.round((double) getSum() / (double) getCount() / 100.0 * 100.0) / 100.0;
  }
}

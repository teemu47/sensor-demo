package sensor.model;

public class SensorDatabase {
  
  private String driver;
  private String connectionString;
  
  public SensorDatabase(String driver, String connectionString) {
    this.driver = driver;
    this.connectionString = connectionString;
  }
  
  public String getDriver() {
    return driver;
  }
  
  public void setDriver(String driver) {
    this.driver = driver;
  }
  
  public String getConnectionString() {
    return connectionString;
  }
  
  public void setConnectionString(String connectionString) {
    this.connectionString = connectionString;
  }
}

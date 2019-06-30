package sensor.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Reads application.properties file of client application
 */
@ConfigurationProperties("sensor")
public class SensorLibraryProperties {
  
  private String databaseDriver;
  private String connectionString;
  
  public String getDatabaseDriver() {
    return databaseDriver;
  }
  
  public void setDatabaseDriver(String databaseDriver) {
    this.databaseDriver = databaseDriver;
  }
  
  public String getConnectionString() {
    return connectionString;
  }
  
  public void setConnectionString(String connectionString) {
    this.connectionString = connectionString;
  }
}

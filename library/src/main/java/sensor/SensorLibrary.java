package sensor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import sensor.model.HtmlParseException;
import sensor.model.Sensor;
import sensor.config.SensorLibraryProperties;
import sensor.model.SensorDatabase;
import sensor.model.SensorNotFoundException;

import java.util.List;

/**
 * Main interface of the library
 */
@Service
@EnableConfigurationProperties(SensorLibraryProperties.class)
public class SensorLibrary {
  
  SensorDatabase sensorDatabase;
  
  @Autowired
  private DatabaseService databaseService;
  
  @Autowired
  private HtmlParseService htmlParseService;
  
  /**
   * Constructor for Spring style @Autowire initialization: Clients fill required information in application.properties file
   */
  @Autowired
  public SensorLibrary(SensorLibraryProperties sensorLibraryProperties) {
    this.sensorDatabase = new SensorDatabase(sensorLibraryProperties.getDatabaseDriver(), sensorLibraryProperties.getConnectionString());
  }
  
  /**
   * Constructor for other than Spring clients
   */
  public SensorLibrary(SensorDatabase sensorDatabase) {
    this.sensorDatabase = sensorDatabase;
    this.databaseService = new DatabaseService(sensorDatabase);
    this.htmlParseService = new HtmlParseService();
  }
  
  /**
   * Returns information of all of the sensors
   */
  public List<Sensor> getSummary() {
    List<Sensor> sensorList = databaseService.getSensors();
    // Calculate averages here because we don't want to do it after each data row in databaseService in order to save performance
    addAverages(sensorList);
    return sensorList;
  }
  
  /**
   * Returns latest temperature of given sensor
   */
  public double getLatestSensorTemperature(String sensorId) throws SensorNotFoundException {
    return databaseService.getLatestTemperature(sensorId);
  }
  
  /**
   * Returns current temperature of given location
   */
  public double getLocalTemperature(String location) throws HtmlParseException {
    return htmlParseService.getTemperature(location);
  }
  
  /**
   * Client can change the database on the fly
   */
  public void setSensorDatabase(SensorDatabase sensorDatabase) {
    this.sensorDatabase = sensorDatabase;
    this.databaseService = new DatabaseService(sensorDatabase);
  }
  
  /**
   * Calculates average temperature for each sensor
   */
  private void addAverages(List<Sensor> sensors) {
    for (Sensor sensor: sensors) {
      sensor.countAverage();
    }
  }
}

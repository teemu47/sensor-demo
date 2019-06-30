package sensor;

import org.apache.commons.dbutils.DbUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import sensor.config.SensorLibraryProperties;
import sensor.model.SensorDatabase;
import sensor.model.SensorNotFoundException;
import sensor.model.Sensor;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Service for interacting with database
 */
@Service
@EnableConfigurationProperties(SensorLibraryProperties.class)
public class DatabaseService {
  
  final Logger logger = LoggerFactory.getLogger(DatabaseService.class);
  
  private SensorDatabase sensorDatabase;
  
  /**
   * Normal constructor
   */
  protected DatabaseService(SensorDatabase sensorDatabase) {
    this.sensorDatabase = sensorDatabase;
  }
  
  @Autowired
  SensorLibraryProperties sensorLibraryProperties;
  
  /**
   * Spring style constructor
   */
  @Autowired
  protected DatabaseService(SensorLibraryProperties sensorLibraryProperties){
    this.sensorDatabase = new SensorDatabase(sensorLibraryProperties.getDatabaseDriver(), sensorLibraryProperties.getConnectionString());
  }
  
  /**
   * Creates Sensor objects and adds temperatures to them
   * Returns the sensors as a list
   */
  public List<Sensor> getSensors() {
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    List<Sensor> result = new ArrayList<>();
    
    try {
      connection = getConnection();
      String sql = "select sensorId, temperature from cubesensors_data";
      preparedStatement = connection.prepareStatement(sql);
      resultSet = preparedStatement.executeQuery();
      
      // Use hash map to store sensor data in order to be fast
      HashMap<String, Sensor> sensorMap = new HashMap<>();
      
      // Create Sensor objects from result
      while (resultSet.next()) {
        String id = resultSet.getString(1);
        int temperature = resultSet.getInt(2);
        // try to get sensor with same id from map
        Sensor sensor = sensorMap.get(id);
        if (sensor == null) {
          // if not present, create new object and add it to the map
          sensor = new Sensor(id);
          sensorMap.put(id, sensor);
        }
        // add temperature entry
        sensor.addTemperature(temperature);
      }
      
      // return a list Sensors
      result = new ArrayList<>(sensorMap.values());
    } catch (Exception e) {
      logger.error("DatabaseService::getSensors failed an exception: " + e.getMessage());
    } finally {
      DbUtils.closeQuietly(resultSet);
      DbUtils.closeQuietly(preparedStatement);
      DbUtils.closeQuietly(connection);
    }
    return result;
  }
  
  /**
   * Returns the latest temperature sensor with given sensorID
   * Throws a SensorNotFoundException if there is no data with given sensorID
   */
  public Double getLatestTemperature(String sensorId) throws SensorNotFoundException {
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    
    try {
      connection = getConnection();
      String sql = "select Temperature, MeasurementTime " +
        "from cubesensors_data " +
        "where SensorId = ? " +
        "order by MeasurementTime desc " +
        "limit 1";
      preparedStatement = connection.prepareStatement(sql);
      preparedStatement.setString(1, sensorId);
    
      resultSet = preparedStatement.executeQuery();
      double temperature;
      if (resultSet.next()) {
        temperature = resultSet.getInt(1);
      } else {
        throw new SensorNotFoundException("DatabaseService::getLatestTemperature failed an exception: Couldn't find sensor with id " + sensorId);
      }
      return temperature / 100.0; // temperatures are stored as hundreds of degrees
    } catch (SensorNotFoundException e) {
      throw new SensorNotFoundException(e.getMessage());
    } catch (Exception e) {
      logger.error("DatabaseService::getLatestTemperature failed an exception: " + e.getMessage());
    } finally {
      DbUtils.closeQuietly(resultSet);
      DbUtils.closeQuietly(preparedStatement);
      DbUtils.closeQuietly(connection);
    }
    return null;
  }
  
  private Connection getConnection() throws ClassNotFoundException, SQLException {
    Class.forName(sensorDatabase.getDriver());
    return DriverManager.getConnection(sensorDatabase.getConnectionString());
  }
}

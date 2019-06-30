package teemu.sensor.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import sensor.SensorLibrary;
import sensor.model.HtmlParseException;
import sensor.model.SensorDatabase;
import sensor.model.SensorNotFoundException;
import teemu.sensor.model.SensorDifference;

/**
 * Controller for getting temperature difference from sensor data
 */
@RestController
@CrossOrigin
public class DifferenceController {
  
  Logger logger = LoggerFactory.getLogger(DifferenceController.class);
  
//  Non Spring way of using sensor library
//  private SensorDatabase sensorDatabase = new SensorDatabase("org.sqlite.JDBC", "jdbc:sqlite:path_to_file");
//  private SensorLibrary sensorLibrary = new SensorLibrary(sensorDatabase);
  
  // SensorLibrary properties in application.properties file
  @Autowired
  private SensorLibrary sensorLibrary;
  
  @GetMapping(value = "/api/diff/{sensorId}", produces = "application/json;charset=UTF-8")
  public SensorDifference getDifference(@PathVariable String sensorId) {
    String city = "Helsinki";
    try {
      double sensorTemp = sensorLibrary.getLatestSensorTemperature(sensorId);
      double locationTemp = sensorLibrary.getLocalTemperature(city);
      double diff = Math.round((sensorTemp - locationTemp) * 100.0) / 100.0; // multiple + divide is done in order to round the number
  
      return new SensorDifference(diff);
    } catch (SensorNotFoundException e) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
    } catch (Exception e) {
      logger.error("DifferenceController::getDifference failed an exception: " + e.getMessage());
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong, please try again later", e);
    }
  }
}

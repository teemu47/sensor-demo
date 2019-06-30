package teemu.sensor.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import sensor.SensorLibrary;
import sensor.model.SensorDatabase;
import teemu.sensor.model.SensorList;

/**
 * Controller for getting summary of sensor data
 */
@RestController
@CrossOrigin
public class SummaryController {
  
  Logger logger = LoggerFactory.getLogger(SummaryController.class);
  
//  Non Spring way of using sensor library
//  private SensorDatabase sensorDatabase = new SensorDatabase("org.sqlite.JDBC", "jdbc:sqlite:path_to_file");
//  private SensorLibrary sensorLibrary = new SensorLibrary(sensorDatabase);
  
  // SensorLibrary properties in application.properties file
  @Autowired
  SensorLibrary sensorLibrary;
  
  @GetMapping(value = "/api/summary", produces = "application/json;charset=UTF-8")
  public SensorList getSummary() {
    try {
      SensorList sensorList = new SensorList();
      sensorList.setSensors(sensorLibrary.getSummary());
      return sensorList;
    } catch (Exception e) {
      logger.error("SummaryController::getSummary failed an exception: " + e.getMessage());
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong, please try again later", e);
    }
  }
}

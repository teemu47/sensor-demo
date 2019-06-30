package teemu.sensor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import teemu.sensor.controller.DifferenceController;
import teemu.sensor.controller.SummaryController;

/**
 * Entry point of application
 */
@SpringBootApplication(scanBasePackages = "sensor", scanBasePackageClasses = {DifferenceController.class, SummaryController.class})
public class SensorApplication {
  
  public static void main(String[] args) {
    SpringApplication.run(SensorApplication.class, args);
  }
}

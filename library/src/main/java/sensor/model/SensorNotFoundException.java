package sensor.model;

public class SensorNotFoundException extends Exception {
  private final String message;
  
  public SensorNotFoundException(String message) {
    this.message = message;
  }
  
  @Override
  public String getMessage() {
    return message;
  }
  
}

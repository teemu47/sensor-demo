package sensor.model;

public class HtmlParseException extends Exception {
  private final String message;
  
  public HtmlParseException(String message) {
    this.message = message;
  }
  
  @Override
  public String getMessage() {
    return message;
  }
  
}

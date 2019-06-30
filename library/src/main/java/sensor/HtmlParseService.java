package sensor;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import sensor.model.HtmlParseException;

/**
 * Service to get the current temperature from wttr.in
 */
@Service
public class HtmlParseService {
  
  final Logger logger = LoggerFactory.getLogger(HtmlParseService.class);
  
  private String baseUrl;
  
  /**
   * Constructor
   */
  protected HtmlParseService() {
    this.baseUrl = "http://wttr.in/";
  }
  
  /**
   * Returns current temperature of given location as double
   */
  public Double getTemperature(String location) throws HtmlParseException {
    String url = baseUrl + location;
    try {
      Document document = Jsoup.connect(url).get(); // throws exceptions if not found
      
      return getTemperatureFromDocument(document.html());
    } catch (HtmlParseException e) {
      logger.error(e.getMessage() + location);
      throw new HtmlParseException(e.getMessage() + location);
    } catch (Exception e) {
      String cityError = "Couldn't get HTML from " + baseUrl + location;
      logger.error(cityError);
    }
    return null;
  }
  
  /**
   * Returns temperature as double from given html document
   */
  private double getTemperatureFromDocument(String document) throws HtmlParseException {
    String parseError = "Couldn't parse temperature from " + baseUrl;
    // current temperature is between the first "<span>" and "</span> °C" element
    int indexOfEnd = getIndexOfEnd(document);
    if (indexOfEnd < 0) {
      throw new HtmlParseException(parseError);
    }
    
    int indexOfStart = getIndexOfStart(document, indexOfEnd);
    if (indexOfStart < 0) {
      throw new HtmlParseException(parseError + "");
    }
    
    double temperature = parseTemperatureFromSpan(document, indexOfStart, indexOfEnd);
    if (Double.isNaN(temperature)) {
      throw new HtmlParseException(parseError);
    }
    
    return temperature;
  }
  
  /**
   * Returns temperature as double from between the given indexes
   */
  private double parseTemperatureFromSpan(String document, int indexOfStart, int indexOfEnd) {
    StringBuilder temperatureBuilder = new StringBuilder();
    for (int i = indexOfStart; i < indexOfEnd; i++) {
      temperatureBuilder.append(document.charAt(i));
    }
    return Double.parseDouble(temperatureBuilder.toString());
  }
  
  /**
   * Returns the index of the end of the temperature
   */
  private int getIndexOfEnd(String document) {
    int indexOfEnd = -1;
    indexOfEnd = document.indexOf("</span> °C");
    return indexOfEnd;
  }
  
  /**
   * Returns the index of the start of the temperature
   */
  private int getIndexOfStart(String document, int indexOfEnd) {
    int indexOfStart = -1;
    // iterate the document backwards until '>' is found
    for (int i = indexOfEnd; i >= 0 ; i--) {
      char c = document.charAt(i);
      if (c == '>') {
        indexOfStart = i + 1;
        break;
      }
    }
    return indexOfStart;
  }
  
}

package swu.musling.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import swu.musling.service.WeatherService;

import java.net.MalformedURLException;
import java.util.Map;

@RestController
public class WeatherController {

    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    //위도, 경도를 받아와 날씨를 조회하는 api
    @GetMapping("/read/weather")
    Map<String, Object> readWeather(@RequestParam double lat,
                     @RequestParam double lon) throws MalformedURLException {
       return weatherService.getWeather(lat, lon);
    }
}

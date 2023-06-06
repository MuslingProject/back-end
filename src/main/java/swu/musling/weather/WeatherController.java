package swu.musling.weather;

import org.springframework.web.bind.annotation.*;
import swu.musling.weather.WeatherService;

import java.net.MalformedURLException;
import java.util.Map;

@RestController
public class WeatherController {

    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    //위도, 경도를 받아와 날씨를 조회하는 api
    @PostMapping("/read/weather")
    Map<String, Object> readWeather(@RequestBody WeatherVo weatherVo) throws MalformedURLException {
       return weatherService.getWeather(weatherVo);
    }
}

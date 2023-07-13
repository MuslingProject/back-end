package swu.musling.weather;

import org.springframework.web.bind.annotation.*;

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
    DateWeather readWeather(@RequestBody WeatherRequestDto weatherRequestDto) throws MalformedURLException {
       return weatherService.getWeather(weatherRequestDto);
    }
}

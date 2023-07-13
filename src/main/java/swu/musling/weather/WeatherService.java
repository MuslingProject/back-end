package swu.musling.weather;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class WeatherService {
    @Value("${openweathermap.key}") //스프링부트에 이미 지정되어 있는 변수를 가져온다. (application.properties 설정)
    private String apiKey;
    private double latitude;    //위도
    private double longitude;   //경도
    private DateWeatherRepository dateWeatherRepository;

    public WeatherService(DateWeatherRepository dateWeatherRepository) {
        this.dateWeatherRepository = dateWeatherRepository;
    }

    public DateWeather getWeather(WeatherRequestDto weatherRequestDto) throws MalformedURLException {
        this.latitude = weatherRequestDto.getLat();
        this.longitude = weatherRequestDto.getLon();

        /*
        //open weater map 에서 데이터 받아오기
        String weatherData = getWeatherString();
        //System.out.println(getWeatherString());

        //받아온 날씨 데이터 파싱하기
        Map<String, Object> parsedWeather = parseWeather(weatherData);
         */

        //날씨 데이터 가져오기(API에서 가져오거나 DB에서 가져오기)
        DateWeather dateWeather = getDateWeather(LocalDate.now());


        //파싱한 데이터 반환
        return dateWeather;
    }

    private DateWeather getDateWeather(LocalDate localDate) throws MalformedURLException {
        List<DateWeather> dateWeatherList = dateWeatherRepository.findAllByDate(localDate);
        if (dateWeatherList.size() == 0) {
            //새로 api에서 날씨 정보 가져옴
            //과거 날씨를 가져오는 것은 유료기 때문에 정책상 현재 날씨 정보 조회
            return getWeatherFromApi();
        } else {
            return dateWeatherList.get(0);
        }
    }

    //openweathermap에서 데이터를 받아오는 로직
    private String getWeatherString() throws MalformedURLException {

        String apiUrl = "https://api.openweathermap.org/data/2.5/weather?lat=" + latitude + "&lon=" + longitude
                + "&appid=" + apiKey;

        try {
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET"); //URL을 GET으로 조회
            int responseCode = connection.getResponseCode();   //응답 결과 코드(200, 400, ...)
            BufferedReader br;
            if (responseCode == 200) {  //정상 조회
                br = new BufferedReader(new InputStreamReader(connection.getInputStream()));    //응답 객체 조회
            } else {
                br = new BufferedReader(new InputStreamReader(connection.getErrorStream()));    //오류 조회
            }
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            br.close();
            return response.toString();

        }catch (Exception e) {
            return "Failed to get response";
        }
    }

    //받아온 날씨 데이터를 파싱하는 로직
    //getWeatherString()에서 openweathermap api로 받아온 날씨 데이터를 String 값으로 반환
    //String 값을 JsonParser을 이용해 파싱하고
    //원하는 데이터를 HashMap으로 꾸려 반환
    private Map<String, Object> parseWeather(String jsonString) {
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject;

        try {
            jsonObject = (JSONObject) jsonParser.parse(jsonString);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        Map<String, Object> resultMap = new HashMap<>();

        JSONObject mainData = (JSONObject) jsonObject.get("main");
        //Kelvin 온도 조정, 소수점 1째자리에서 반올림
        resultMap.put("temp", Math.round((double) mainData.get("temp")-273.15));

        //500 error
        //JSON 형식 살펴보면 weather이 {} 아닌 [] 로 감싸져 있어서 JSONArray 사용해 주어야 함
        JSONArray weatherArray = (JSONArray) jsonObject.get("weather");
        JSONObject weatherData = (JSONObject) weatherArray.get(0);  //array 안에 객체 수 (1)
        //resultMap.put("main", weatherData.get("main"));
        resultMap.put("id", weatherData.get("id"));
        //resultMap.put("icon", weatherData.get("icon"));
        return resultMap;
    }

    //정해둔 매 시간마다 날씨 정보 저장
    @Transactional
    @Scheduled(cron = "0 0 1 * * *")    //매일 새벽 1시마다
    public void saveWeatherDate() throws MalformedURLException {
        dateWeatherRepository.save(getWeatherFromApi());
    }

    private DateWeather getWeatherFromApi() throws MalformedURLException {
        //open weater map 에서 데이터 받아오기
        String weatherData = getWeatherString();
        //System.out.println(getWeatherString());

        //받아온 날씨 데이터 파싱하기
        Map<String, Object> parsedWeather = parseWeather(weatherData);

        DateWeather dateWeather = new DateWeather();
        dateWeather.setDate(LocalDate.now());
        dateWeather.setWeather(Integer.parseInt(parsedWeather.get("id").toString()));
        dateWeather.setTemperature(Integer.parseInt(String.valueOf(parsedWeather.get("temp"))));

        return dateWeather;
    }
}

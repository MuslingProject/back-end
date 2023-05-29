package swu.musling.weather;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

@Service
public class WeatherService {
    @Value("${openweathermap.key}") //스프링부트에 이미 지정되어 있는 변수를 가져온다. (application.properties 설정)
    private String apiKey;
    private double latitude;    //위도
    private double longitude;   //경도

    public Map<String, Object> getWeather(WeatherVo weatherVo) throws MalformedURLException {
        this.latitude = weatherVo.getLat();
        this.longitude = weatherVo.getLon();

        //open weater map 에서 데이터 받아오기
        String weatherData = getWeatherString();
        System.out.println(getWeatherString());

        //받아온 날씨 데이터 파싱하기
        Map<String, Object> parsedWeather = parseWeather(weatherData);

        //파싱한 데이터 반환
        return parsedWeather;
    }

    //openweathermap에서 데이터를 받아오는 로직
    private String getWeatherString() throws MalformedURLException {

        String apiUrl = "https://api.openweathermap.org/data/2.5/weather?lat=" + latitude + "&lon=" + longitude + "&appid=" + apiKey;

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
        resultMap.put("temp", mainData.get("temp"));
        //500 error
        //JSON 형식 살펴보면 weather이 {} 아닌 [] 로 감싸져 있어서 JSONArray 사용해 주어야 함
        JSONArray weatherArray = (JSONArray) jsonObject.get("weather");
        JSONObject weatherData = (JSONObject) weatherArray.get(0);  //array 안에 객체 수 (1)
        resultMap.put("main", weatherData.get("main"));
        resultMap.put("icon", weatherData.get("icon"));
        return resultMap;
    }
}

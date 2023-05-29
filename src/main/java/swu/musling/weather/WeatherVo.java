package swu.musling.weather;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WeatherVo {
    private double lat; //위도
    private double lon; //경도
}

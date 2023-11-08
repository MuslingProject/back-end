package swu.musling;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)  //직접 인스턴스화 할 수 없으며 상속하는 서브클래스나 같은 패키지 내에서만 인스턴스화 가능
@AllArgsConstructor
public class ApiResponse<T> {   //data 필드가 여러 타입을 받을 수 있음

    //ApiResponse의 결과 상태를 나타내는 상수 문자열들을 정의
    private static final String SUCCESS_RESULT = "success";
    private static final String FAIL_RESULT = "fail";
    private static final String ERROR_RESULT = "error";

    private String result;
    private HttpStatus httpStatus;
    private String message;
    private T data;

    public static <T> ApiResponse<T> createSuccess(T data) {
        return new ApiResponse<>(SUCCESS_RESULT, HttpStatus.OK, null, data);
    }

    public static ApiResponse<?> createSuccessWithNoData(String message) {
        return new ApiResponse<>(SUCCESS_RESULT, HttpStatus.OK, message, null);
    }

    public static ApiResponse<?> createError(HttpStatus httpStatus, String message) {
        return new ApiResponse<>(ERROR_RESULT, httpStatus, message, null);
    }
}

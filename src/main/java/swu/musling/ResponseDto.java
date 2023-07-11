package swu.musling;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ResponseDto<T> {

    private int status;
    private String message;
    private T data;

    public ResponseDto(int status, String message) {
        this.status = status;
        this.message = message;
        this.data = null;
    }

    public static<T> ResponseDto<T> response(int status, String message) {
        return response(status, message, null);
    }

    public static<T> ResponseDto<T> response(int status, String message, T t) {
        return ResponseDto.<T>builder()
                .status(status)
                .message(message)
                .data(t)
                .build();
    }
}

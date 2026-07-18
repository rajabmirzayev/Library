package az.library.library.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import static az.library.library.utils.Constants.SUCCESS_MESSAGE;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T> {
    boolean success;
    String message;
    String errorCode;
    T data;

    public static <T> ApiResponse<T> success() {
        return ApiResponse.<T>builder()
                .success(true)
                .message(SUCCESS_MESSAGE)
                .errorCode(null)
                .data(null)
                .build();
    }

    public static <T> ApiResponse<T> success(T data) {
        return ApiResponse.<T>builder()
                .success(true)
                .message(SUCCESS_MESSAGE)
                .errorCode(null)
                .data(data)
                .build();
    }

    public static <T> ApiResponse<T> success(T data, String message) {
        return ApiResponse.<T>builder()
                .success(true)
                .message(message)
                .errorCode(null)
                .data(data)
                .build();
    }

}

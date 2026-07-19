package az.library.library.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import static az.library.library.utils.Constants.SUCCESS_MESSAGE;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Əməliyyat nəticəsi wrapper-ı")
public class ApiResponse<T> {

    @Schema(description = "Əməliyyat uğurlu oldumu", example = "true")
    boolean success;

    @Schema(description = "Əməliyyat mesajı", example = "Successfully Completed")
    String message;

    @Schema(description = "Xəta kodu", example = "null")
    String errorCode;

    @Schema(description = "Əməliyyat nəticəsi datası")
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

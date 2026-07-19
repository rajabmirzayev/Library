package az.library.library.exception;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ProblemDetail handleNotFound(ResourceNotFoundException ex) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(
                HttpStatus.NOT_FOUND, ex.getMessage());
        problem.setTitle("Resurs tapılmadı");
        problem.setType(URI.create("/errors/resource-not-found"));
        return problem;
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ProblemDetail handleBadArgument(IllegalArgumentException ex) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST, ex.getMessage());
        problem.setTitle("Yanlış sorğu");
        problem.setType(URI.create("/errors/invalid-argument"));
        return problem;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleValidation(MethodArgumentNotValidException ex) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST, "Daxil edilən məlumatlarda xətalar mövcuddur");
        problem.setTitle("Validasiya xətası");
        problem.setType(URI.create("/errors/validation-error"));

        List<Map<String, String>> fieldErrors = ex.getBindingResult().getFieldErrors().stream()
                .map(e -> Map.of(
                        "field", e.getField(),
                        "rejectedValue", String.valueOf(e.getRejectedValue()),
                        "message", e.getDefaultMessage() != null ? e.getDefaultMessage() : "Keçərsiz dəyər"))
                .toList();
        problem.setProperty("errors", fieldErrors);
        return problem;
    }

//    @ExceptionHandler(Exception.class)
//    public ProblemDetail handleGeneric(Exception ex) {
//        ProblemDetail problem = ProblemDetail.forStatusAndDetail(
//                HttpStatus.INTERNAL_SERVER_ERROR,
//                "Gözlənilməz xəta baş verdi. Zəhmət olmasa bir az sonra yenidən cəhd edin");
//        problem.setTitle("Daxili server xətası");
//        problem.setType(URI.create("/errors/internal-server-error"));
//        return problem;
//    }

}

package az.library.library.controller;

import az.library.library.dto.ApiResponse;
import az.library.library.dto.request.CreateBookRequest;
import az.library.library.dto.response.CreateBookResponse;
import az.library.library.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @PostMapping
    public ResponseEntity<ApiResponse<CreateBookResponse>> createBook(
            @Valid @RequestBody CreateBookRequest request
    ) {
        CreateBookResponse response = bookService.createBook(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(response));
    }

}

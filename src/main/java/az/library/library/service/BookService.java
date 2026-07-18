package az.library.library.service;

import az.library.library.dto.request.CreateBookRequest;
import az.library.library.dto.response.CreateBookResponse;

public interface BookService {

    CreateBookResponse createBook(CreateBookRequest request);

}

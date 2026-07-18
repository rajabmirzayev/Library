package az.library.library.service.impl;

import az.library.library.dto.request.CreateBookCopyRequest;
import az.library.library.dto.request.UpdateBookCopyRequest;
import az.library.library.dto.response.BookCopyDetailedResponse;
import az.library.library.dto.response.BookCopySummaryResponse;
import az.library.library.entity.Book;
import az.library.library.entity.BookCopy;
import az.library.library.enums.BookCopyStatus;
import az.library.library.exception.ResourceNotFoundException;
import az.library.library.mapper.BookCopyMapper;
import az.library.library.repository.BookCopyRepository;
import az.library.library.repository.BookRepository;
import az.library.library.service.BookCopyService;

import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookCopyServiceImpl implements BookCopyService {
    private final BookCopyRepository repo;
    private final BookRepository bookRepo;
    private final BookCopyMapper mapper;

    @Override
    @Transactional
    public BookCopyDetailedResponse create(CreateBookCopyRequest request) {
        if (repo.existsByBarcode(request.getBarcode()))
            throw new IllegalArgumentException("Barcode " + request.getBarcode() + " already exists");
        BookCopy entity = mapper.toEntityForCreate(request);
        if (request.getBookId() != null)
            entity.setBook(bookRepo.findById(request.getBookId())
                    .orElseThrow(() -> new ResourceNotFoundException("Book", request.getBookId())));
        entity.setStatus(BookCopyStatus.AVAILABLE);
        return mapper.toDetailedResponse(repo.save(entity));
    }

    @Override
    public BookCopyDetailedResponse findById(Long id) {
        return mapper.toDetailedResponse(repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("BookCopy", id)));
    }

    @Override
    public List<BookCopySummaryResponse> findAll() {
        return repo.findAll().stream().map(mapper::toSummaryResponse).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public BookCopyDetailedResponse update(Long id, UpdateBookCopyRequest request) {
        BookCopy entity = repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("BookCopy", id));
        if (request.getBarcode() != null) {
            if (!entity.getBarcode().equals(request.getBarcode()) && repo.existsByBarcode(request.getBarcode()))
                throw new IllegalArgumentException("Barcode " + request.getBarcode() + " already exists");
            entity.setBarcode(request.getBarcode());
        }
        if (request.getShelfLocation() != null) entity.setShelfLocation(request.getShelfLocation());
        if (request.getCondition() != null)
            entity.setCondition(az.library.library.enums.BookCopyCondition.valueOf(request.getCondition()));
        if (request.getBookId() != null)
            entity.setBook(bookRepo.findById(request.getBookId())
                    .orElseThrow(() -> new ResourceNotFoundException("Book", request.getBookId())));
        return mapper.toDetailedResponse(repo.save(entity));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        repo.delete(repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("BookCopy", id)));
    }
}

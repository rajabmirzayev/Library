package az.library.library.controller;

import az.library.library.dto.ApiResponse;
import az.library.library.dto.request.CreateMemberRequest;
import az.library.library.dto.request.UpdateMemberRequest;
import az.library.library.dto.response.MemberDetailedResponse;
import az.library.library.dto.response.MemberSummaryResponse;
import az.library.library.dto.response.PageResponse;
import az.library.library.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService service;

    @PostMapping
    public ResponseEntity<ApiResponse<MemberDetailedResponse>> create(@Valid @RequestBody CreateMemberRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(service.create(req)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<MemberDetailedResponse>> findById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(service.findById(id)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<MemberSummaryResponse>>> findAll(
            @PageableDefault(size = 20, sort = "id") Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.success(PageResponse.of(service.findAll(pageable))));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<MemberDetailedResponse>> update(@PathVariable Long id, @Valid @RequestBody UpdateMemberRequest req) {
        return ResponseEntity.ok(ApiResponse.success(service.update(id, req)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}

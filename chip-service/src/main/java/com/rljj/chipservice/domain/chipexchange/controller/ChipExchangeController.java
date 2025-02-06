package com.rljj.chipservice.domain.chipexchange.controller;

import com.rljj.chipservice.domain.chipexchange.dto.ChipExchangeCreateRequest;
import com.rljj.chipservice.domain.chipexchange.dto.ChipExchangeResponse;
import com.rljj.chipservice.domain.chipexchange.dto.ChipExchangeStatusUpdateRequest;
import com.rljj.chipservice.domain.chipexchange.service.ChipExchangeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController("/api/chip/exchange")
public class ChipExchangeController {

    private final ChipExchangeService chipExchangeService;

    @GetMapping("/{chipPostId}")
    public ResponseEntity<Page<ChipExchangeResponse>> getChipExchanges(@PathVariable Long chipPostId, Pageable pageable) {
        return ResponseEntity.ok(chipExchangeService.getChipExchanges(chipPostId, pageable));
    }

    @PostMapping
    public ResponseEntity<Void> createChipExchange(@AuthenticationPrincipal UserDetails userDetails,
                                                   @RequestBody ChipExchangeCreateRequest request) {
        chipExchangeService.createChipExchange(userDetails, request);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/status")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> completeChipExchange(@RequestBody ChipExchangeStatusUpdateRequest request) {
        chipExchangeService.updateStatus(request);
        return ResponseEntity.ok().build();
    }
}

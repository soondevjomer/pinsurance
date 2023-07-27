package com.soondevjomer.pinsurance.controller;

import com.soondevjomer.pinsurance.dto.InsuranceDto;
import com.soondevjomer.pinsurance.entity.Insurance;
import com.soondevjomer.pinsurance.service.InsuranceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/insurances")
@RequiredArgsConstructor
public class InsuranceController {

    private final InsuranceService insuranceService;

    @PostMapping
    public ResponseEntity<InsuranceDto> addInsurance(@RequestBody InsuranceDto insuranceDto) {

        return new ResponseEntity<>(insuranceService.addInsurance(insuranceDto), HttpStatus.CREATED);
    }

    @GetMapping("/{insuranceId}")
    public ResponseEntity<InsuranceDto> getInsurance(@PathVariable Integer insuranceId) {

        return ResponseEntity.ok(insuranceService.getInsurance(insuranceId));
    }

    @GetMapping
    public ResponseEntity<List<InsuranceDto>> getInsurances() {

        return ResponseEntity.ok(insuranceService.getInsurances());
    }

    @PutMapping("/{insuranceId}")
    public ResponseEntity<InsuranceDto> updateInsurance(@PathVariable Integer insuranceId, InsuranceDto insuranceDto) {

        return ResponseEntity.ok(insuranceService.updateInsurance(insuranceId, insuranceDto));
    }

    @DeleteMapping("/{insuranceId}")
    public ResponseEntity<String> deleteInsurance(@PathVariable Integer insuranceId) {

        return ResponseEntity.ok(insuranceService.deleteInsurance(insuranceId));
    }

}

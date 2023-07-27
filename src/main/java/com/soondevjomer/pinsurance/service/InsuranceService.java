package com.soondevjomer.pinsurance.service;

import com.soondevjomer.pinsurance.dto.InsuranceDto;

import java.util.List;

public interface InsuranceService {

    InsuranceDto addInsurance(InsuranceDto insuranceDto);

    InsuranceDto getInsurance(Integer insuranceId);

    List<InsuranceDto> getInsurances();

    InsuranceDto updateInsurance(Integer insuranceId, InsuranceDto insuranceDto);

    String deleteInsurance(Integer insuranceId);
}

package com.soondevjomer.pinsurance.service.impl;

import com.soondevjomer.pinsurance.constant.LocationOfRisk;
import com.soondevjomer.pinsurance.dto.InsuranceDto;
import com.soondevjomer.pinsurance.entity.Insurance;
import com.soondevjomer.pinsurance.entity.Tax;
import com.soondevjomer.pinsurance.repository.InsuranceRepository;
import com.soondevjomer.pinsurance.repository.TaxRepository;
import com.soondevjomer.pinsurance.service.InsuranceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InsuranceServiceImpl implements InsuranceService {

    private final InsuranceRepository insuranceRepository;
    private final TaxRepository taxRepository;
    private Double premium = 0D;
    private Double limitOfLiability = 0D;
    @Transactional
    @Override
    public InsuranceDto addInsurance(InsuranceDto insuranceDto) {

        // Use area to get the premium and limit of liability
        getPremiumAndLimitOfLiability(insuranceDto.getArea());
        BigDecimal premium = BigDecimal.valueOf(this.premium);
        BigDecimal limitOfLiability = BigDecimal.valueOf(this.limitOfLiability);

        if (insuranceDto.getLocationOfRisk().equals(LocationOfRisk.DOUBLE_PREMIUM)) {
            premium = premium.multiply(BigDecimal.valueOf(2));
        }

        Optional<Tax> tax = Optional.of(taxRepository.findAll().stream()
                .filter(Tax::isEnabled)
                .findAny()
                .get());

        // Now calculate the taxes
        BigDecimal docStamp = premium.multiply(BigDecimal.valueOf(tax.get().getDocStamp()));
        BigDecimal vat = premium.multiply(BigDecimal.valueOf(tax.get().getVat()));
        BigDecimal lgt = premium.multiply(BigDecimal.valueOf(tax.get().getLgt()));
        BigDecimal total = premium.add(docStamp).add(vat).add(lgt);

        Insurance insurance = new Insurance();
        insurance.setPolicyNumber(insuranceDto.getPolicyNumber());
        insurance.setInsuredName(insuranceDto.getInsuredName());
        insurance.setAddress(insuranceDto.getAddress());
        insurance.setArea(insuranceDto.getArea());
        insurance.setInsuredDate(insuranceDto.getInsuredDate());
        insurance.setExpiredDate(insuranceDto.getInsuredDate().plusYears(1));
        insurance.setPremium(premium);
        insurance.setLimitOfLiability(limitOfLiability);
        insurance.setDocStamp(docStamp);
        insurance.setVat(vat);
        insurance.setLgt(lgt);
        insurance.setTotal(total);

        Insurance savedInsurance = insuranceRepository.save(insurance);

        return InsuranceDto.builder()
                .id(savedInsurance.getId())
                .policyNumber(savedInsurance.getPolicyNumber())
                .insuredName(savedInsurance.getInsuredName())
                .address(savedInsurance.getAddress())
                .area(savedInsurance.getArea())
                .insuredDate(savedInsurance.getInsuredDate())
                .expiredDate(savedInsurance.getExpiredDate())
                .premium(savedInsurance.getPremium())
                .limitOfLiability(savedInsurance.getLimitOfLiability())
                .docStamp(savedInsurance.getDocStamp())
                .vat(savedInsurance.getVat())
                .lgt(savedInsurance.getLgt())
                .total(savedInsurance.getTotal())
                .build();
    }

    @Override
    public InsuranceDto getInsurance(Integer insuranceId) {

        Insurance insurance = insuranceRepository.findById(insuranceId).orElseThrow(NoSuchElementException::new);

        return InsuranceDto.builder()
                .id(insurance.getId())
                .policyNumber(insurance.getPolicyNumber())
                .insuredName(insurance.getInsuredName())
                .address(insurance.getAddress())
                .area(insurance.getArea())
                .insuredDate(insurance.getInsuredDate())
                .expiredDate(insurance.getExpiredDate())
                .premium(insurance.getPremium())
                .limitOfLiability(insurance.getLimitOfLiability())
                .docStamp(insurance.getDocStamp())
                .vat(insurance.getVat())
                .lgt(insurance.getLgt())
                .total(insurance.getTotal())
                .build();
    }

    @Override
    public List<InsuranceDto> getInsurances() {

        return insuranceRepository.findAll().stream()
                .map(insurance -> {
                    return InsuranceDto.builder()
                            .id(insurance.getId())
                            .policyNumber(insurance.getPolicyNumber())
                            .insuredName(insurance.getInsuredName())
                            .address(insurance.getAddress())
                            .area(insurance.getArea())
                            .insuredDate(insurance.getInsuredDate())
                            .expiredDate(insurance.getExpiredDate())
                            .premium(insurance.getPremium())
                            .limitOfLiability(insurance.getLimitOfLiability())
                            .docStamp(insurance.getDocStamp())
                            .vat(insurance.getVat())
                            .lgt(insurance.getLgt())
                            .total(insurance.getTotal())
                            .build();
                })
                .collect(Collectors.toList());
    }

    @Override
    public InsuranceDto updateInsurance(Integer insuranceId, InsuranceDto insuranceDto) {

        Insurance insurance = insuranceRepository.findById(insuranceId)
                .orElseThrow(NoSuchElementException::new);

        // Use area to get the premium and limit of liability
        getPremiumAndLimitOfLiability(insuranceDto.getArea());
        BigDecimal premium = BigDecimal.valueOf(this.premium);
        BigDecimal limitOfLiability = BigDecimal.valueOf(this.limitOfLiability);

        if (insuranceDto.getLocationOfRisk().equals(LocationOfRisk.DOUBLE_PREMIUM)) {
            premium = premium.multiply(BigDecimal.valueOf(2));
        }

        Optional<Tax> tax = Optional.of(taxRepository.findAll().stream()
                .filter(Tax::isEnabled)
                .findAny()
                .get());

        // Now calculate the taxes
        BigDecimal docStamp = premium.multiply(BigDecimal.valueOf(tax.get().getDocStamp()));
        BigDecimal vat = premium.multiply(BigDecimal.valueOf(tax.get().getVat()));
        BigDecimal lgt = premium.multiply(BigDecimal.valueOf(tax.get().getLgt()));
        BigDecimal total = premium.add(docStamp).add(vat).add(lgt);

        // Then update the current to new value of insurance
        insurance.setPolicyNumber(insuranceDto.getPolicyNumber());
        insurance.setInsuredName(insuranceDto.getInsuredName());
        insurance.setAddress(insuranceDto.getAddress());
        insurance.setArea(insuranceDto.getArea());
        insurance.setInsuredDate(insuranceDto.getInsuredDate());
        insurance.setExpiredDate(insuranceDto.getInsuredDate().plusYears(1));
        insurance.setPremium(premium);
        insurance.setLimitOfLiability(limitOfLiability);
        insurance.setDocStamp(docStamp);
        insurance.setVat(vat);
        insurance.setLgt(lgt);
        insurance.setTotal(total);

        return null;
    }

    @Override
    public String deleteInsurance(Integer insuranceId) {

        insuranceRepository.deleteById(insuranceId);

        return "Insurance deleted successfully.";
    }

    private void getPremiumAndLimitOfLiability(Integer area) {

        if (area >= 0 && area <= 20) {
            premium = 577.80;
            limitOfLiability = 100000.00;
        } else if (area >= 21 && area <= 30) {
            premium = 770.40;
            limitOfLiability = 150000.00;
        } else if (area >= 31 && area <= 40) {
            premium = 914.85;
            limitOfLiability = 200000.00;
        } else if (area >= 41 && area <= 50) {
            premium = 1011.15;
            limitOfLiability = 250000.00;
        } else if (area >= 51 && area <= 60) {
            premium = 1155.60;
            limitOfLiability = 300000.00;
        } else if (area >= 61 && area <= 70) {
            premium = 1300.05;
            limitOfLiability = 350000.00;
        } else if (area >= 71 && area <= 80) {
            premium = 1519.87;
            limitOfLiability = 400000.00;
        } else if (area >= 81 && area <= 90) {
            premium = 1540.80; //di malinaw ch
            limitOfLiability = 450000.00;
        } else if (area >= 91 && area <= 100) {
            premium = 1637.10; //di malinaw
            limitOfLiability = 500000.00;
        } else if (area >= 101 && area <= 110) {
            premium = 1733.40;
            limitOfLiability = 550000.00;
        } else if (area >= 111 && area <= 120) {
            premium = 1829.70;
            limitOfLiability = 600000.00;
        } else if (area >= 121 && area <= 130) {
            premium = 1926.00;
            limitOfLiability = 650000.00;
        } else if (area >= 131 && area <= 140) {
            premium = 2022.30;
            limitOfLiability = 700000.00;
        } else if (area >= 141 && area <= 150) {
            premium = 2134.65;
            limitOfLiability = 750000.00;
        } else if (area >= 151 && area <= 160) {
            premium = 2214.90;
            limitOfLiability = 800000.00;
        } else if (area >= 161 && area <= 170) {
            premium = 2311.20;
            limitOfLiability = 850000.00;
        } else if (area >= 171 && area <= 180) {
            premium = 2407.50;
            limitOfLiability = 900000.00;
        } else if (area >= 181 && area <= 190) {
            premium = 2503.80;
            limitOfLiability = 950000.00;
        } else if (area >= 191 && area <= 200) {
            premium = 2600.10;
            limitOfLiability = 1000000.00;
        } else if (area >= 201 && area <= 300) {
            premium = 3370.50;
            limitOfLiability = 1500000.00;
        } else if (area >= 301 && area <= 400) {
            premium = 4333.50;
            limitOfLiability = 2000000.00;
        } else if (area >= 401 && area <= 500) {
            premium = 5007.60;
            limitOfLiability = 2500000.00;
        } else if (area >= 501 && area <= 600) {
            premium = 5392.80;
            limitOfLiability = 3000000.00;
        } else if (area >= 601 && area <= 700) {
            premium = 5681.70;
            limitOfLiability = 3500000.00;
        } else if (area >= 701 && area <= 800) {
            premium = 5970.60; //di masyado malinaw
            limitOfLiability = 4000000.00;
        } else if (area >= 801 && area <= 900) {
            premium = 6259.50; //di malinaw
            limitOfLiability = 4500000.00;
        } else if (area >= 901 && area <= 1000) {
            premium = 6500.25;
            limitOfLiability = 5000000.00;
        } else if (area >= 1001 && area <= 1200) {
            premium = 6741.00;
            limitOfLiability = 5000000.00;
        } else if (area >= 1201 && area <= 1500) {
            premium = 7800.30;
            limitOfLiability = 5000000.00;
        } else if (area >= 1501 && area <= 5000) {
            premium = 8281.80;
            limitOfLiability = 5000000.00;
        } else if (area >= 5001 && area <= 6000) {
            premium = 8329.95;
            limitOfLiability = 5000000.00;
        } else if (area >= 6001 && area <= 7000) {
            premium = 8378.10;
            limitOfLiability = 5000000.00;
        } else if (area >= 7001 && area <= 8000) {
            premium = 8426.25;
            limitOfLiability = 5000000.00;
        } else if (area >= 8001 && area <= 9000) {
            premium = 8506.50;
            limitOfLiability = 5000000.00;
        } else if (area >= 9001 && area <= 10000) {
            premium = 8570.70;
            limitOfLiability = 5000000.00;
        } else if (area >= 10001 && area <= 11000) {
            premium = 8618.85;
            limitOfLiability = 5000000.00;
        } else if (area >= 11001 && area <= 12000) {
            premium = 8667.00;
            limitOfLiability = 5000000.00;
        } else if (area >= 12001 && area <= 13000) {
            premium = 8715.15;
            limitOfLiability = 5000000.00;
        } else if (area >= 13001 && area <= 14000) {
            premium = 8811.15;
            limitOfLiability = 5000000.00;
        } else if (area >= 14001 && area <= 15000) {
            premium = 8859.60;
            limitOfLiability = 5000000.00;
        } else if (area >= 15001 && area <= 16000) {
            premium = 8907.75;
            limitOfLiability = 5000000.00;
        } else if (area >= 16001 && area <= 17000) {
            premium = 8955.90;
            limitOfLiability = 5000000.00;
        } else if (area >= 17001 && area <= 18000) {
            premium = 9004.05;
            limitOfLiability = 5000000.00;
        } else if (area >= 18001 && area <= 19000) {
            premium = 9100.35;
            limitOfLiability = 5000000.00;
        } else if (area >= 19001 && area <= 20000) {
            premium = 9148.50;
            limitOfLiability = 5000000.00;
        } else if (area >= 20001 && area <= 21000) {
            premium = 9196.65;
            limitOfLiability = 5000000.00;
        } else if (area >= 21001 && area <= 22000) {
            premium = 9292.95;
            limitOfLiability = 5000000.00;
        } else if (area >= 22001 && area <= 23000) {
            premium = 9341.10;
            limitOfLiability = 5000000.00;
        } else if (area >= 23001 && area <= 24000) {
            premium = 9389.25;
            limitOfLiability = 5000000.00;
        } else if (area >= 24001 && area <= 25000) {
            premium = 9437.40;
            limitOfLiability = 5000000.00;
        } else if (area >= 25001 && area <= 26000) {
            premium = 9485.55;
            limitOfLiability = 5000000.00;
        }
    }
}

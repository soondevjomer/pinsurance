package com.soondevjomer.pinsurance.dto;

import com.soondevjomer.pinsurance.constant.LocationOfRisk;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InsuranceDto {

    private Integer id;
    private String policyNumber;
    private String insuredName;

    private String address;

    private Integer area;

    private LocalDate insuredDate;
    private LocalDate expiredDate;

    @Enumerated(EnumType.STRING)
    private LocationOfRisk locationOfRisk;

    private BigDecimal premium;
    private BigDecimal limitOfLiability;
    private BigDecimal docStamp;
    private BigDecimal vat;
    private BigDecimal lgt;
    private BigDecimal total;
}

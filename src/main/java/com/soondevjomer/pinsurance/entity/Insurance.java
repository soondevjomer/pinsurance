package com.soondevjomer.pinsurance.entity;

import com.soondevjomer.pinsurance.constant.LocationOfRisk;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "insurances")
public class Insurance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "insurance_id")
    private Integer id;

    @Column(name = "policy_number", nullable = false, unique = true)
    private String policyNumber;

    @Column(name = "insured_name", nullable = false)
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

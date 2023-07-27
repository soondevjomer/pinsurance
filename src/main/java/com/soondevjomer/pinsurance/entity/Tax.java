package com.soondevjomer.pinsurance.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "taxes")
public class Tax {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "insurance_id")
    private Integer id;

    private Double docStamp;
    private Double vat;
    private Double lgt;
    private boolean isEnabled;
}

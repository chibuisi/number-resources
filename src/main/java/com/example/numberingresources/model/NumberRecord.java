package com.example.numberingresources.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NumberRecord {
    private String code;
    private Integer min;
    private Integer max;
    private Integer total;
}

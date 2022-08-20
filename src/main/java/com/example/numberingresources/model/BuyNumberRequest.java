package com.example.numberingresources.model;

import lombok.Data;

@Data
public class BuyNumberRequest {
    private String areaCode;
    private String accessCode;
    private Integer quantity;
    private Integer min;
    private Integer max;
}

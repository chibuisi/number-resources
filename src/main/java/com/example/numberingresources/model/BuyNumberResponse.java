package com.example.numberingresources.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class BuyNumberResponse {
    private String areaCode;
    private String accessCode;
    private Integer quantity;
    private Integer min;
    private Integer max;
    private List<Integer> numberList;
    private String message;
    private String accessToken;
    private String refreshToken;
}

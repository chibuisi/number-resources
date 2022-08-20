package com.example.numberingresources.controller;

import com.example.numberingresources.model.BuyNumberRequest;
import com.example.numberingresources.model.BuyNumberResponse;
import com.example.numberingresources.model.NumberRecord;
import com.example.numberingresources.service.NumberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/numbers")
public class BuyNumberController {
    @Autowired
    private Random random;
    @Autowired
    private NumberService numberService;

    Map<String,Integer> soldNumbers = new HashMap<>();

    @PostMapping("/buy")
    public BuyNumberResponse buy(@RequestBody BuyNumberRequest buyNumberRequest, @RequestHeader("Authorization") String auth){
        if(auth == null || auth.length()==0 || !auth.startsWith("Bearer "))
            return BuyNumberResponse.builder().message("Invalid Request").build();
        String jwt = auth.substring(7);
        //todo validate token
        String code = buyNumberRequest.getAreaCode()+buyNumberRequest.getAccessCode();
        int min = buyNumberRequest.getMin();
        int max = buyNumberRequest.getMax();
        int qty = buyNumberRequest.getQuantity();
        boolean result = validateRequest(code, min, max, qty);
        if(result==false)
            return BuyNumberResponse.builder().message("Invalid Request").build();

        //generate random numbers, check not already sold from sold numbers
        // and put in a list of available numbers to be sold
        List<Integer> numbersToSell = new ArrayList<>();
        while(numbersToSell.size() < qty){
            int randomNumber = random.nextInt(max + 1 - min) + min;
            if(!soldNumbers.containsValue(code+randomNumber)){
                numbersToSell.add(randomNumber);
            }
        }

        //put available numbers in the sold numbers to avoid selling again
        for(int i = 0; i < numbersToSell.size(); i++){
            soldNumbers.put(code, numbersToSell.get(i));
        }
        int total = numberService.getNumberRecord(code).getTotal();
        numberService.getNumberRecord(code).setTotal(total-qty);

        //generate access token and refresh token from jwt
        String accessToken = "cdxkjoprjbhsfseuijncegsdfee";
        String refreshToken = "jj98yuoiucne9uy9877su7hg4o";
        //build our response
        BuyNumberResponse buyNumberResponse = BuyNumberResponse.builder()
                .areaCode(buyNumberRequest.getAreaCode())
                .accessCode(buyNumberRequest.getAccessCode())
                .quantity(qty).min(min).max(max)
                .numberList(numbersToSell)
                .accessToken(accessToken)
                .refreshToken(refreshToken).message("Success").build();

        return buyNumberResponse;
    }

    private boolean validateRequest(String code, int min, int max, int qty){
        NumberRecord numberRecord = numberService.getNumberRecord(code);
        if(numberRecord == null){
            return false;
        }
        if(min < numberRecord.getMin() || min > numberRecord.getMax()){
            return false;
        }
        if(max > numberRecord.getMax() || max < numberRecord.getMin()){
            return false;
        }
        if(numberRecord.getTotal() < qty){
            return false;
        }
        return true;
    }
}

package com.example.numberingresources.service;

import com.example.numberingresources.model.NumberRecord;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class NumberService {

    Map<String, NumberRecord> data = new HashMap<>();

    public NumberRecord getNumberRecord(String code){
        if(data.size() == 0){
            initData();
        }
        if(data.containsKey(code))
            return data.get(code);
        return null;
    }

    private void initData(){
        List<NumberRecord> record = Arrays.asList(
                new NumberRecord("01249", 2490000, 2499999, 10000),
                new NumberRecord("01270", 2700000, 2709999, 10000)
        );

        record.forEach(numberRecord -> {
            data.put(numberRecord.getCode(), numberRecord);
        });
    }
}

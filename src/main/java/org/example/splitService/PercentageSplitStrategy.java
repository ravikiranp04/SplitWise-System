package org.example.splitService;

import org.example.utils.User;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

public class PercentageSplitStrategy implements SplitStrategy{

    @Override
    public Map<User, BigDecimal> createSplit(BigDecimal totalAmountPaid, Map<User,BigDecimal> userToSplitShareMap){
         //Iterate for all Users and Update the Map with the amount in rupees

        Map<User,BigDecimal> userToSplitAmountMap = new HashMap<>();

        for(Map.Entry<User,BigDecimal> entry: userToSplitShareMap.entrySet()){
            User user = entry.getKey();
            BigDecimal percentageShare = entry.getValue();
            BigDecimal amountShare = percentageShare
                    .multiply(totalAmountPaid)
                    .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
            userToSplitAmountMap.put(user, amountShare);
        }

        return userToSplitAmountMap;
    }
}

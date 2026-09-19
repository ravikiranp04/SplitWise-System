package org.example.splitService;

import org.example.utils.User;

import java.math.BigDecimal;
import java.util.Map;

public class EqualOrSpecificAmountSplitStrategy implements SplitStrategy{
    @Override
    public Map<User, BigDecimal> createSplit(BigDecimal totalAmountPaid, Map<User,BigDecimal> userToSplitShareMap){
        //return the split map as it is.
        return userToSplitShareMap;
    }
}

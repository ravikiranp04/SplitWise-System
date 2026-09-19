package org.example.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

public class BalanceSheet {



    //represents the amount the currentUser Owes to other Users in the group
    private Map<User, BigDecimal> usersToAmountMap;



    private BigDecimal totalOweAmount;

    BalanceSheet(){
        this.usersToAmountMap=new HashMap<>();
        this.totalOweAmount=BigDecimal.ZERO;
    }

    //Increases the amount the current user owes to that particular user
    //If amount is positive, then the current user owes to that particular user in the group.
    //If amount is negative, then the current user needs to get that amount from the particular user.
    public void addOweAmount(User user, BigDecimal amount){
        usersToAmountMap.compute(user, (k, currentOweToUser) -> currentOweToUser.add(amount));
        totalOweAmount=totalOweAmount.add(amount).setScale(2, RoundingMode.HALF_UP);;
    }

    //Reduces the amount the current user owes to that particular user
    public void reduceOweAmount(User user, BigDecimal amount){
        usersToAmountMap.compute(user, (k, currentOweToUser) -> currentOweToUser.subtract(amount));
        totalOweAmount=totalOweAmount.subtract(amount).setScale(2, RoundingMode.HALF_UP);;
    }

    public BigDecimal getTotalOweAmount() {
        return totalOweAmount;
    }

    public Map<User, BigDecimal> getUsersToAmountMap() {
        return usersToAmountMap;
    }

}

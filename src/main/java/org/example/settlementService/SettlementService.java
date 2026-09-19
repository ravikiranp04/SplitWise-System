package org.example.settlementService;


import org.example.utils.BalanceSheet;
import org.example.utils.User;

import java.math.BigDecimal;
import java.util.Map;
import java.util.logging.Logger;


public class SettlementService {
    private static final Logger log = Logger.getLogger(SettlementService.class.getName());

    public static void settleOweAmountForUsersInGroup(User currentUser,String groupId){
        BalanceSheet balanceSheet = currentUser.getUserBalanceSheetOfGroup(groupId);
        // Prevent updating balance sheet of user by other members of group during settlement.
        synchronized(balanceSheet){
            BigDecimal amountToBeSettled = balanceSheet.getTotalOweAmount();
            if(amountToBeSettled.equals(0)){
                log.info("No Dues Pending");
                return;
            }

            //<--Pay and Settle Via Payment Service-->
            //If payment fails, then return

            //If payment success, then
            updateGroupMembersOwes(groupId, currentUser, balanceSheet);
        }
    }

    public static void updateGroupMembersOwes(String groupId, User currentUser, BalanceSheet balanceSheet){
        Map<User,BigDecimal> usersToAmountMap = balanceSheet.getUsersToAmountMap();
        for(Map.Entry<User,BigDecimal> entry : usersToAmountMap.entrySet()){
            BigDecimal currentOwe = entry.getValue();
            if(currentOwe.compareTo(BigDecimal.ZERO)>0){
                User user = entry.getKey();
                BalanceSheet userBalanceSheet = user.getUserBalanceSheetOfGroup(groupId);

                //Update the other member's balance sheet
                userBalanceSheet.addOweAmount(currentUser, currentOwe);

                //Update current user balance sheet
                balanceSheet.reduceOweAmount(user, currentOwe);
            }
        }
    }
}

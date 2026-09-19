package org.example.splitService;

import org.example.groupUtils.Group;
import org.example.groupUtils.GroupFactory;
import org.example.utils.BalanceSheet;
import org.example.utils.Split;
import org.example.utils.User;

import java.math.BigDecimal;
import java.util.Map;

public class SplitService {
     public static void createSplit(String groupId, User paidUser, BigDecimal totalAmountPaid, Map<User, BigDecimal> userToShareSplit, SplitStrategy splitStrategy, String description){
        // Create the Split sheet according to the strategy i.e, equal amounts, dynamic, percentage split
        // The Values in every pair in the map userToShareSplit consists in the form of Percentage/Amount depeneding on strategy selected
        Map<User,BigDecimal> splitSheet = splitStrategy.createSplit(totalAmountPaid, userToShareSplit);
        Group group = GroupFactory.getGroup(groupId);

        //Build a split with the data (Builder pattern)
        Split split = new Split.SplitBuilder().forGroup(groupId).paidBy(paidUser).amountPaid(totalAmountPaid).splitData(splitSheet).splitStrategy(splitStrategy).addDescription(description).build();

        //Updating balances for all eligible members
        updateGroupMembersWithNewBalances(groupId, paidUser, splitSheet);

        //add the split to the group split history
         group.addNewSplit(split);

    }

    public static void updateGroupMembersWithNewBalances(String groupId, User paidUser, Map<User, BigDecimal> splitSheet){

         BalanceSheet paidUserBalanceSheet  = paidUser.getUserBalanceSheetOfGroup(groupId);


         for(Map.Entry<User,BigDecimal> entry: splitSheet.entrySet()){
             User toBeUpdatedUser = entry.getKey();
             //skip Updation if current User and paid User are same
             if(toBeUpdatedUser.equals(paidUser)){
                 continue;
             }
             BalanceSheet currentUserBalanceSheet = toBeUpdatedUser.getUserBalanceSheetOfGroup(groupId);
             BigDecimal amountToBeUpdated = entry.getValue();

             //Update the amount this currentUser owes in the paid user balance sheet
             paidUserBalanceSheet.reduceOweAmount(toBeUpdatedUser,amountToBeUpdated);

             // Update the amount in the currentUuser balance sheet
             currentUserBalanceSheet.addOweAmount(paidUser,amountToBeUpdated);

         }
    }
}

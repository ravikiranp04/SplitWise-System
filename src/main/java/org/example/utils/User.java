package org.example.utils;

import org.example.groupUtils.Group;
import org.example.settlementService.SettlementService;

import java.util.Map;
import java.util.logging.Logger;

public class User {


    private String userId;
    private String name;

    private Map<String, Group> groupIdToGroupMap;
    private Map<String,BalanceSheet> groupIdToBalanceSheetMap;
    private static final Logger log = Logger.getLogger(User.class.getName());

    public void addGroupToUserState(String groupId, Group group){
        groupIdToGroupMap.put(groupId,group);
        BalanceSheet balanceSheet = new BalanceSheet();
        groupIdToBalanceSheetMap.put(groupId,balanceSheet);
    }

    public void removeGroupFromUserState(String groupId, Group group){
        groupIdToGroupMap.remove(groupId);
    }

    void deleteGroupPermanently(String groupId){
        Group group = groupIdToGroupMap.get(groupId);
        groupIdToGroupMap.remove(groupId);
        groupIdToBalanceSheetMap.remove(groupId);
    }

    public BalanceSheet getUserBalanceSheetOfGroup(String groupId){
        return groupIdToBalanceSheetMap.get(groupId);
    }

    public String getName() {
        return name;
    }

    public void settleOweAmountInTheGroup(String groupId){
        SettlementService.settleOweAmountForUsersInGroup(this,groupId);
    }


    @Override
    public String toString() {
        return name;
    }

}

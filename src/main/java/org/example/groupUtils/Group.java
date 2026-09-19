package org.example.groupUtils;

import org.example.utils.Split;
import org.example.utils.User;

import java.util.*;

public class Group {

    private String groupId;
    private List<User> membersList;
    private Map<String,Split> splitHistory; //splitIdToSplitMap
    private User createdByUser;
    Group(User createdByUser, List<User> membersToBeAddedList){
        this.groupId = UUID.randomUUID().toString();
        this.membersList = new ArrayList<>(membersToBeAddedList);
        this.splitHistory = new HashMap<>();
        this.membersList.add(createdByUser);
    }

    public List<User> getMembersList() {
        return membersList;
    }


    //Adding selected members into the group
    public void addMembers(List<User> membersList){
        for(User member: membersList){
            membersList.add(member);
        }

    }

    //removing selected members from the group
    public void removeMembers(List<User> membersList){
        for(User member: membersList){
            membersList.remove(member);
        }
    }

    public String getGroupId() {
        return groupId;
    }

    public void addNewSplit(Split split){
        splitHistory.put(split.getSplitId(), split);
    }
}

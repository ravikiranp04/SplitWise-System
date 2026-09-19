package org.example.groupUtils;

import org.example.utils.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class GroupFactory {
    private static Map<String, Group> groupIdToGroupMap;
    private static final Logger log = Logger.getLogger(GroupFactory.class.getName());

    GroupFactory(){
        this.groupIdToGroupMap = new HashMap<>();
    }

    public static Group getGroup(String groupId){
        return groupIdToGroupMap.get(groupId);
    }

    //Creates a group and updates groupIdToGroup map
    public void createGroup(User createdByUser, List<User> membersToBeAddedList){
        //creates new group
        Group group = new Group(createdByUser, membersToBeAddedList);
        String groupId = group.getGroupId();
        groupIdToGroupMap.put(groupId,group);
        // Update the group reference in User State
        addGroupToUsersState(membersToBeAddedList, group);
    }

    // Filters out already existing members in the group and adds only new members
    public void addMembers(List<User> membersToBeAddedList, String groupId){
        Group group = groupIdToGroupMap.get(groupId);
        List<User> existingMembersList= group.getMembersList();
        List<User> removedUsers = new ArrayList<>();

        //Filter out already existing members
        for (User user : membersToBeAddedList) {
            if (existingMembersList.contains(user)) {
                removedUsers.add(user);
            }
        }
        log.info("Members "+removedUsers+" already exist in the group.");

        membersToBeAddedList.removeAll(removedUsers);
        // Add members to the group
        group.addMembers(membersToBeAddedList);
        log.info("Members "+membersToBeAddedList+" added to the group.");

        //Update user states
        addGroupToUsersState(membersToBeAddedList, group);
    }

    //Removes existing members.
    public void removeMembers(List<User> membersToBeRemovedList, String groupId){
        Group group = groupIdToGroupMap.get(groupId);
        //remove the members
        group.removeMembers(membersToBeRemovedList);

        log.info("Members "+membersToBeRemovedList+" removed from the group.");
        removeGroupFromUsersState(membersToBeRemovedList, group);
    }


    //Adds this group reference to Users groups list
    void addGroupToUsersState(List<User> membersToBeUpdated, Group group){
        String groupId = group.getGroupId();
        for(User member: membersToBeUpdated){
            member.addGroupToUserState(groupId,group);
        }
    }

    //Removes this group reference from Users groups list
    void removeGroupFromUsersState(List<User> membersToBeUpdated, Group group){
        String groupId = group.getGroupId();
        for(User member: membersToBeUpdated){
            member.removeGroupFromUserState(groupId,group);
        }
    }

}

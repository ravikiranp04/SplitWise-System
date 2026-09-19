package org.example;

import org.example.groupUtils.Group;
import org.example.groupUtils.GroupFactory;
import org.example.splitService.EqualOrSpecificAmountSplitStrategy;
import org.example.splitService.SplitService;
import org.example.utils.User;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        // Create users
        User ravi = new User("U1", "Ravi");
        User amit = new User("U2", "Amit");
        User rahul = new User("U3", "Rahul");

        // Create group
        GroupFactory groupFactory = new GroupFactory();

        Group group = groupFactory.createGroup(
                ravi,
                new ArrayList<>(Arrays.asList(amit, rahul))
        );

        String groupId = group.getGroupId();

        // Create split
        SplitService splitService = new SplitService();

        Map<User, BigDecimal> split = new HashMap<>();
        split.put(ravi, new BigDecimal("300"));
        split.put(amit, new BigDecimal("300"));
        split.put(rahul, new BigDecimal("300"));

        splitService.createSplit(
                groupId,
                ravi,
                new BigDecimal("900"),
                split,
                new EqualOrSpecificAmountSplitStrategy(),
                "Dinner"
        );

        // Print balances
        System.out.println("Ravi: " +
                ravi.getUserBalanceSheetOfGroup(groupId));

        System.out.println("Amit: " +
                amit.getUserBalanceSheetOfGroup(groupId));

        System.out.println("Rahul: " +
                rahul.getUserBalanceSheetOfGroup(groupId));
    }
}
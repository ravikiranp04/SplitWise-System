package org.example.utils;

import org.example.splitService.SplitStrategy;

import java.awt.image.BufferedImage;
import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

public class Split {
    private String splitId;
    private String groupId;
    private User paidUser;
    private BigDecimal totalAmountPaid;
    private Map<User,BigDecimal> splitSheet;
    private SplitStrategy splitStrategy;
    private String description;
    private Split(SplitBuilder splitBuilder){
        this.splitId=splitBuilder.splitId;
        this.groupId = splitBuilder.groupId;
        this.paidUser =splitBuilder.paidUser;
        this.totalAmountPaid = splitBuilder.totalAmountPaid;
        this.splitSheet = splitBuilder.splitSheet;
        this.splitStrategy = splitBuilder.splitStrategy;
        this.description=splitBuilder.description;
    }

    public String getSplitId(){
        return splitId;
    }
    public static class SplitBuilder{
        private String splitId;
        private String groupId;
        private User paidUser;
        private BigDecimal totalAmountPaid;
        private Map<User,BigDecimal> splitSheet;
        private SplitStrategy splitStrategy;
        private String description;

        public SplitBuilder(){
            this.splitId = UUID.randomUUID().toString();
        }

        public SplitBuilder forGroup(String groupId){
            this.groupId=groupId;
            return  this;
        }

        public SplitBuilder paidBy(User paidUser){
            this.paidUser=paidUser;
            return this;
        }

        public SplitBuilder amountPaid(BigDecimal totalAmountPaid){
            this.totalAmountPaid=totalAmountPaid;
            return this;
        }

        public SplitBuilder splitData(Map<User,BigDecimal> splitSheet){
            this.splitSheet=splitSheet;
            return this;
        }

        public SplitBuilder splitStrategy(SplitStrategy splitStrategy){
            this.splitStrategy = splitStrategy;
            return this;
        }
        public SplitBuilder addDescription(String description){
            this.description=description;
            return this;
        }

        public Split build(){
            return new Split(this);
        }

    }
}

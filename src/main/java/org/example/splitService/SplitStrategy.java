package org.example.splitService;

import org.example.utils.User;

import java.math.BigDecimal;
import java.util.Map;

public interface SplitStrategy {
    public Map<User, BigDecimal> createSplit(BigDecimal totalAmountPaid, Map<User,BigDecimal> userToSplitShare);
}

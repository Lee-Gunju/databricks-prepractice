package com.bigdata.bricks.discount;

import com.bigdata.bricks.member.Grade;
import com.bigdata.bricks.member.Member;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
//@Qualifier("subDiscountPolicy")
@SubDiscountPolicy
public class FixDiscountPolicy implements DiscountPolicy {
    private int discountFixAmount = 1000; // 할인할 고정 금액

    @Override
    public int discount(Member member, int price) {
        if (member.getGrade() == Grade.VIP) {
            return discountFixAmount;
        } else {
            return 0;
        }
    }
}

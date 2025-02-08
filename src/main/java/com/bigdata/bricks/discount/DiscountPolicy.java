package com.bigdata.bricks.discount;

import com.bigdata.bricks.member.Member;

public interface DiscountPolicy {
    int discount(Member member, int price);
}

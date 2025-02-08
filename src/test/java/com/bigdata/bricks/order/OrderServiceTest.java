package com.bigdata.bricks.order;

import com.bigdata.bricks.member.Grade;
import com.bigdata.bricks.member.Member;
import com.bigdata.bricks.member.MemberService;
import com.bigdata.bricks.member.MemberServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class OrderServiceTest {
    MemberService memberService = new MemberServiceImpl();
    OrderService orderService = new OrderServiceImpl();

    @Test
    void 주문하기() {
        long memberId = 1L;
        Member member = new Member(memberId, "실험체1", Grade.VIP);
        memberService.join(member);
        Order order = orderService.createOrder(memberId, "USB", 249000);
        Assertions.assertEquals(order.getDiscountPrice(), 1000);
    }
}

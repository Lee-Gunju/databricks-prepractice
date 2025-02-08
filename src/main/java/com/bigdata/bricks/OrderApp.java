package com.bigdata.bricks;

import com.bigdata.bricks.member.Grade;
import com.bigdata.bricks.member.Member;
import com.bigdata.bricks.member.MemberService;
import com.bigdata.bricks.member.MemberServiceImpl;
import com.bigdata.bricks.order.Order;
import com.bigdata.bricks.order.OrderService;
import com.bigdata.bricks.order.OrderServiceImpl;

public class OrderApp {
    public static void main(String[] args) {
        MemberService memberService = new MemberServiceImpl();
        OrderService orderService = new OrderServiceImpl();
        long memberId = 1L;
        Member member = new Member(memberId, "실험체1", Grade.VIP);
        memberService.join(member);
        Order order = orderService.createOrder(memberId, "USB", 24900);
        System.out.println("order = " + order);
    }
}

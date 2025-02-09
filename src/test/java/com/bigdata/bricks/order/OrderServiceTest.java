package com.bigdata.bricks.order;

import com.bigdata.bricks.AppConfig;
import com.bigdata.bricks.member.Grade;
import com.bigdata.bricks.member.Member;
import com.bigdata.bricks.member.MemberService;
import com.bigdata.bricks.member.MemberServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class OrderServiceTest {
//    AppConfig appConfig = new AppConfig();
//    MemberService memberService = appConfig.memberService();
//    OrderService orderService = appConfig.orderService();
    ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
    MemberService memberService = (MemberService) ac.getBean("memberService");
    OrderService orderService = ac.getBean("orderService", OrderService.class);

    @Test
    void 주문하기_고정할인() {
        long memberId = 1L;
        Member member = new Member(memberId, "실험체1", Grade.VIP);
        memberService.join(member);
        Order order = orderService.createOrder(memberId, "USB", 24900);
        Assertions.assertEquals(order.getDiscountPrice(), 1000);
    }

    @Test
    void 주문하기_정률할인() {
        long memberId = 1L;
        Member member = new Member(memberId, "실험체1", Grade.VIP);
        memberService.join(member);
        Order order = orderService.createOrder(memberId, "USB", 24900);
        Assertions.assertEquals(order.getDiscountPrice(), 2490);
    }
}

package com.bigdata.bricks.autoScan;

import com.bigdata.bricks.AutoAppConfig;
import com.bigdata.bricks.discount.DiscountPolicy;
import com.bigdata.bricks.member.Grade;
import com.bigdata.bricks.member.Member;
import com.bigdata.bricks.member.MemberRepository;
import com.bigdata.bricks.member.MemberService;
import com.bigdata.bricks.order.Order;
import com.bigdata.bricks.order.OrderService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Map;

public class autoScanTest {

    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AutoAppConfig.class);

    @Test
    @DisplayName("모든 빈 출력하기")
    void findAllBean() {
        String[] beanDefinitionNames = ac.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            Object bean = ac.getBean(beanDefinitionName);
            System.out.println("name=" + beanDefinitionName + " Object " + bean);
        }
    }
    @Component
    static class OrderService1 implements OrderService {
        private MemberRepository memberRepository;
        private DiscountPolicy discountPolicy;

        @Autowired
        public OrderService1(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
            this.memberRepository = memberRepository;
            this.discountPolicy = discountPolicy;
        }

        @Override
        public Order createOrder(Long memberId, String itemName, int itemPrice) {
            Member member = memberRepository.findById(memberId);
            int discountPrice = discountPolicy.discount(member, itemPrice);
            return new Order(memberId, itemName, itemPrice, discountPrice);
        }
    }

    @Component
    static class OrderService2 implements OrderService {
        private MemberRepository memberRepository;
        private DiscountPolicy discountPolicy;

        @Autowired
        public OrderService2(MemberRepository memberRepository, @Qualifier("subDiscountPolicy") DiscountPolicy discountPolicy) {
            this.memberRepository = memberRepository;
            this.discountPolicy = discountPolicy;
        }

        @Override
        public Order createOrder(Long memberId, String itemName, int itemPrice) {
            Member member = memberRepository.findById(memberId);
            int discountPrice = discountPolicy.discount(member, itemPrice);
            return new Order(memberId, itemName, itemPrice, discountPrice);
        }
    }


    @Component
    static class OrderService3 {
        private MemberRepository memberRepository;
        private Map<String, DiscountPolicy> discountPolicyMap;

        @Autowired
        public OrderService3(MemberRepository memberRepository, Map<String, DiscountPolicy> discountPolicyMap) {
            this.memberRepository = memberRepository;
            this.discountPolicyMap = discountPolicyMap;
        }

        public Order createOrder(Long memberId, String itemName, int itemPrice, String policyName) {
            Member member = memberRepository.findById(memberId);
            DiscountPolicy discountPolicy = discountPolicyMap.get(policyName);
            int discountPrice = discountPolicy.discount(member, itemPrice);
            return new Order(memberId, itemName, itemPrice, discountPrice);
        }
    }

    @Test
    @DisplayName("할인 정책 서비스의 할인 가격 확인")
    void getPrimaryDiscountPolicy() {
        Member testMember = new Member(1L, "tester", Grade.VIP);
        ac.getBean(MemberService.class).join(testMember);

        Order primaryServiceOrder = ac.getBean(OrderService1.class).createOrder(1L, "아이템1", 20000);
        Order qualifierServiceOrder = ac.getBean(OrderService2.class).createOrder(1L, "아이템1", 20000);

        System.out.println("primaryServiceOrder=" + primaryServiceOrder);
        System.out.println("qualifierServiceOrder=" + qualifierServiceOrder);

        Assertions.assertEquals(primaryServiceOrder.getDiscountPrice(), 2000);
        Assertions.assertEquals(qualifierServiceOrder.getDiscountPrice(), 1000);
    }

    @Test
    @DisplayName("할인 정책 서비스의 할인 가격 확인-2")
    void getAllDiscountPolicy() {
        Member testMember = new Member(1L, "tester", Grade.VIP);
        ac.getBean(MemberService.class).join(testMember);

        Order fixServiceOrder = ac.getBean(OrderService3.class).createOrder(1L, "아이템1", 20000, "fixDiscountPolicy");
        Order rateServiceOrder = ac.getBean(OrderService3.class).createOrder(1L, "아이템1", 20000, "rateDiscountPolicy");

        System.out.println("fixServiceOrder = " + fixServiceOrder);
        System.out.println("rateServiceOrder = " + rateServiceOrder);

        Assertions.assertEquals(fixServiceOrder.getDiscountPrice(), 1000);
        Assertions.assertEquals(rateServiceOrder.getDiscountPrice(), 2000);
    }

}

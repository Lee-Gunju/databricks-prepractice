package com.bigdata.bricks;

import com.bigdata.bricks.discount.DiscountPolicy;
import com.bigdata.bricks.discount.FixDiscountPolicy;
import com.bigdata.bricks.discount.RateDiscountPolicy;
import com.bigdata.bricks.member.MemberRepository;
import com.bigdata.bricks.member.MemberService;
import com.bigdata.bricks.member.MemberServiceImpl;
import com.bigdata.bricks.member.MemoryMemberRepository;
import com.bigdata.bricks.order.OrderService;
import com.bigdata.bricks.order.OrderServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public MemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }
    @Bean
    public DiscountPolicy discountPolicy() {
        return new RateDiscountPolicy();
    }
    @Bean
    public MemberService memberService() {
        return new MemberServiceImpl(memberRepository());
    }
    @Bean
    public OrderService orderService() {
        return new OrderServiceImpl(memberRepository(), discountPolicy());
    }


}

package com.bigdata.bricks.member;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MemberServiceTest {
    MemberService memberService = new MemberServiceImpl();

    @Test
    void 회원가입() {
        Member member = new Member(1L, "실험체1", Grade.VIP);
        memberService.join(member);
        Member findMember = memberService.findMember(1L);


        Assertions.assertEquals(member, findMember);
    }
}

package com.bigdata.bricks.prototype;


import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

public class PrototypeTest {

    @Test
    @DisplayName("프로토타입 빈 조회")
    public void prototypeBeanFind() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class);
        PrototypeBean prototypeBean1 = ac.getBean(PrototypeBean.class);
        PrototypeBean prototypeBean2 = ac.getBean(PrototypeBean.class);
        System.out.println("prototypeBean1: " + prototypeBean1);
        System.out.println("prototypeBean2: " + prototypeBean2);
        Assertions.assertNotEquals(prototypeBean1, prototypeBean2);
    }

    @Test
    @DisplayName("프로토타입 빈 별개 환경 확인")
    public void prototypeBeanEachCountUp() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class);
        PrototypeBean prototypeBean1 = ac.getBean(PrototypeBean.class);
        PrototypeBean prototypeBean2 = ac.getBean(PrototypeBean.class);
        prototypeBean1.addCount();
        System.out.println("prototypeBean1: " + prototypeBean1.getCount());
        prototypeBean2.addCount();
        System.out.println("prototypeBean2: " + prototypeBean2.getCount());
        Assertions.assertEquals(prototypeBean1.getCount(), prototypeBean2.getCount());
    }





    @Scope("prototype")
    static class PrototypeBean {

        @PostConstruct
        public void init() {
            System.out.println("PrototypeBean init");
        }

        @PreDestroy
        public void destroy() {
            System.out.println("PrototypeBean destroy");
        }

        private int count = 0;

        public void addCount() {
            count++;
        }
        public int getCount() {
            return count;
        }
    }
}

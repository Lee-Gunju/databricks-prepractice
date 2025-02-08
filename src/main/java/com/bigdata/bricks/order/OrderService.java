package com.bigdata.bricks.order;

public interface OrderService {
    Order createOrder(Long memberId, String itemName, int itemPrice);

}

package com.ra.castermovie.model;

import com.ra.castermovie.model.common.Condition;
import com.ra.castermovie.model.order.OrderState;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "order")
public class Order {
    @Id
    private String id;
    @NotNull(message = "Order::condition must not be null")
    private Condition condition;
    private String userId;
    private String payUserId;
    @NotNull(message = "Order::publicInfoId must not be null")
    private String publicInfoId;
    @NotNull(message = "Order::originalCost must not be null")
    // seat cost * user-level's percent
    private Integer originalCost;
    @NotNull(message = "Order::actualCost must not be null")
    // seat cost * user-level's percent * coupon's percent
    private Integer actualCost;
    @NotNull(message = "Order::createTime must not be null")
    private Long createTime;
    @NotNull(message = "Order::orderState must not be null")
    private OrderState orderState;
    @NotNull(message = "Order::seat must not be null")
    private List<Integer> seats;

    public Order(String userId, String publicInfoId, Integer originalCost, Integer actualCost, List<Integer> seats) {
        this.id = UUID.randomUUID().toString();
        this.condition = Condition.EXISTING;
        this.userId = userId;
        this.payUserId = null;
        this.publicInfoId = publicInfoId;
        this.originalCost = originalCost;
        this.actualCost = actualCost;
        this.createTime = System.currentTimeMillis();
        this.orderState = OrderState.UNPAID;
        this.seats = seats;
    }
}

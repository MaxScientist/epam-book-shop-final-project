package com.epam.bookshop.entity;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Objects;


public class Order {

    private Long id;
    private Long userId;
    private User user;
    private OrderStatus status;
    private BigInteger totalPrice;
    private Date createdDate;
    private List<OrderItem> orderItems;
    private Integer orderStatusId;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public BigInteger getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigInteger totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(id, order.id) && Objects.equals(userId, order.userId) && Objects.equals(user, order.user) && Objects.equals(status, order.status) && Objects.equals(totalPrice, order.totalPrice) && Objects.equals(createdDate, order.createdDate) && Objects.equals(orderItems, order.orderItems) && Objects.equals(orderStatusId, order.orderStatusId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, user, status, totalPrice, createdDate, orderItems, orderStatusId);
    }

    public void setOrderStatusId(Integer orderStatusId) {
        this.orderStatusId = orderStatusId;
    }

    public Integer getOrderStatusId() {
        return orderStatusId;
    }
    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", userId=" + userId +
                ", user=" + user +
                ", statusId=" + orderStatusId +
                ", status=" + status +
                ", totalPrice=" + totalPrice +
                ", createdDate=" + createdDate +
                ", orderItems=" + orderItems +
                '}';
    }


}

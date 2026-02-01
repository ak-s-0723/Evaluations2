package org.example.evaluations2;

public class Order {

    private final String orderId;
    private final String customerName;
    private final int quantity;
    private final double price;
    private final double discount;
    private final String couponCode;
    private final boolean giftWrap;

    private Order(Builder builder) {
        this.orderId = builder.orderId;
        this.customerName = builder.customerName;
        this.quantity = builder.quantity;
        this.price = builder.price;
        this.discount = builder.discount;
        this.couponCode = builder.couponCode;
        this.giftWrap = builder.giftWrap;
    }

    public static Builder builder() {
        return new Builder();
    }

    // Getters only (NO setters)

    public String getOrderId() { return orderId; }
    public String getCustomerName() { return customerName; }
    public int getQuantity() { return quantity; }
    public double getPrice() { return price; }
    public double getDiscount() { return discount; }
    public String getCouponCode() { return couponCode; }
    public boolean isGiftWrap() { return giftWrap; }

    // ================= BUILDER =================
    public static class Builder {

        String orderId;
        String customerName;
        int quantity;
        double price;
        double discount;
        String couponCode;
        boolean giftWrap;

        // TODO: Implement builder methods
        // TODO: Implement validation
        // TODO: Implement build()
    }
}

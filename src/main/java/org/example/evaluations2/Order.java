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

    public String getOrderId() { return orderId; }
    public String getCustomerName() { return customerName; }
    public int getQuantity() { return quantity; }
    public double getPrice() { return price; }
    public double getDiscount() { return discount; }
    public String getCouponCode() { return couponCode; }
    public boolean isGiftWrap() { return giftWrap; }

    public static class Builder {

        private String orderId;
        private String customerName;
        private int quantity;
        private double price;
        private double discount;
        private String couponCode;
        private boolean giftWrap;

        public Builder orderId(String orderId) {
            this.orderId = orderId;
            return this;
        }

        public Builder customerName(String customerName) {
            this.customerName = customerName;
            return this;
        }

        public Builder quantity(int quantity) {
            this.quantity = quantity;
            return this;
        }

        public Builder price(double price) {
            this.price = price;
            return this;
        }

        public Builder discount(double discount) {
            this.discount = discount;
            return this;
        }

        public Builder couponCode(String couponCode) {
            this.couponCode = couponCode;
            return this;
        }

        public Builder giftWrap(boolean giftWrap) {
            this.giftWrap = giftWrap;
            return this;
        }

        public Order build() {

            if (orderId == null || orderId.isBlank()) {
                throw new IllegalArgumentException("orderId is mandatory");
            }

            if (customerName == null) {
                throw new IllegalArgumentException("customerName is mandatory");
            }

            if (quantity <= 0) {
                throw new IllegalArgumentException("quantity must be greater than zero");
            }

            if (price <= 0) {
                throw new IllegalArgumentException("price must be greater than zero");
            }

            if (discount < 0) {
                throw new IllegalArgumentException("discount cannot be negative");
            }

            return new Order(this);
        }
    }
}

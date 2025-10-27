package com.solvd.musicstreamingservice;

import java.util.ArrayList;
import java.util.List;

public class SubscriptionPlan {
    private String name;
    private double price;
    private List<User> subscribers;

    public SubscriptionPlan(String name, double price) {
        this.name = name;
        this.price = price;
        this.subscribers = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public List<User> getSubscribers() {
        return subscribers;
    }

    public void addSubscriber(User user) {
        subscribers.add(user);
    }
}

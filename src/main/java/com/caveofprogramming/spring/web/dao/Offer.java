package com.caveofprogramming.spring.web.dao;

import javax.validation.constraints.Size;

public class Offer {

    private int id;

    private User user;

    @Size(min = 5, max = 100, message = "Text must be between 20 and 255 characters.")
    private String text;

    public Offer() {
        this.user = new User();
    }

    public Offer(User user, String text) {
        this.user = user;
        this.text = text;
    }

    public Offer(int id, User user, String text) {
        this.id = id;
        this.user = user;
        this.text = text;
    }

    public String getUsername(){
        return user.getUsername();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Offer{" +
                "id=" + id +
                ", user=" + user +
                ", text='" + text + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Offer offer = (Offer) o;

        if (user != null ? !user.equals(offer.user) : offer.user != null) return false;
        return !(text != null ? !text.equals(offer.text) : offer.text != null);

    }

    @Override
    public int hashCode() {
        int result = user != null ? user.hashCode() : 0;
        result = 31 * result + (text != null ? text.hashCode() : 0);
        return result;
    }
}

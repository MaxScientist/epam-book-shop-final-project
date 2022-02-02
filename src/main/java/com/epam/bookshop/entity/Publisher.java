package com.epam.bookshop.entity;

import java.util.Objects;

public class Publisher {

    private Long id;
    private String publishHouse;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPublishHouse() {
        return publishHouse;
    }

    public void setPublishHouse(String publishHouse) {
        this.publishHouse = publishHouse;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Publisher publisher = (Publisher) o;
        return Objects.equals(id, publisher.id) && Objects.equals(publishHouse, publisher.publishHouse);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, publishHouse);
    }

    @Override
    public String toString() {
        return "Publisher{" +
                "id=" + id +
                ", publishHouse='" + publishHouse + '\'' +
                '}';
    }
}

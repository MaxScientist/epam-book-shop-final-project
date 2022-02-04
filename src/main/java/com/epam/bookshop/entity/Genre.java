package com.epam.bookshop.entity;

import java.io.Serializable;
import java.util.Objects;

public class Genre implements Serializable {

    private Integer id;
    private Integer languageId;
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getLanguageId() {
        return languageId;
    }

    public void setLanguageId(Integer languageId) {
        this.languageId = languageId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Genre genre = (Genre) o;
        return Objects.equals(id, genre.id) && Objects.equals(languageId, genre.languageId) && Objects.equals(name, genre.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, languageId, name);
    }

    @Override
    public String toString() {
        return "Genre{" +
                "id=" + id +
                ", languageId=" + languageId +
                ", name='" + name + '\'' +
                '}';
    }
}

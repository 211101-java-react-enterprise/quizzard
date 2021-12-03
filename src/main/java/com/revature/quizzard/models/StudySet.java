package com.revature.quizzard.models;

import java.util.List;
import java.util.Objects;

public class StudySet {

    private String id;
    private String name;
    private AppUser owner;
    private List<Flashcard> cards;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AppUser getOwner() {
        return owner;
    }

    public void setOwner(AppUser owner) {
        this.owner = owner;
    }

    public List<Flashcard> getCards() {
        return cards;
    }

    public void setCards(List<Flashcard> cards) {
        this.cards = cards;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StudySet studySet = (StudySet) o;
        return Objects.equals(id, studySet.id) && Objects.equals(name, studySet.name) && Objects.equals(owner, studySet.owner) && Objects.equals(cards, studySet.cards);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, owner, cards);
    }

    @Override
    public String toString() {
        return "StudySet{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", owner=" + owner +
                ", cards=" + cards +
                '}';
    }

}

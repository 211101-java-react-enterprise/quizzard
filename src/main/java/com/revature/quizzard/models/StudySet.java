package com.revature.quizzard.models;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "study_sets", uniqueConstraints = {
        @UniqueConstraint(name = "unique_user_set_names", columnNames = {"name", "owner_id"})
})
public class StudySet {

    @Id
    private String id;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private AppUser owner;

    @ManyToMany
    @JoinTable(
            name = "study_set_cards",
            joinColumns = @JoinColumn(name = "study_set_id"),
            inverseJoinColumns = @JoinColumn(name = "card_id")
    )
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

package com.revature.quizzard.set;

import com.revature.quizzard.card.Flashcard;
import com.revature.quizzard.common.domain.Resource;
import com.revature.quizzard.common.domain.ResourceMetadata;
import com.revature.quizzard.user.AppUser;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "study_sets", uniqueConstraints = {
    @UniqueConstraint(name = "unique_user_set_names", columnNames = {"name", "resource_owner_id"})
})
public class StudySet extends Resource {

    @Column(nullable = false)
    private String name;

    @ManyToMany
    @JoinTable(
        name = "study_set_cards",
        joinColumns = @JoinColumn(name = "study_set_id"),
        inverseJoinColumns = @JoinColumn(name = "card_id")
    )
    private List<Flashcard> cards;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        return Objects.equals(name, studySet.name) && Objects.equals(cards, studySet.cards);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, cards);
    }

    @Override
    public String toString() {
        return "StudySet{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", cards=" + cards +
                ", metadata=" + metadata +
                '}';
    }

}

package infrastructure.persistence.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity(name = "puzzle")
public class PuzzleEntity {

    @Id
    private String name;

    @Column
    private String solution;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "device_id")
    private DeviceEntity device;

    @OneToMany(
            mappedBy = "puzzle",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<PuzzleSubscriberEntity> subscribers = new ArrayList<>();

    // getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

    public List<PuzzleSubscriberEntity> getSubscribers() {
        return subscribers;
    }

    public void setSubscribers(List<PuzzleSubscriberEntity> subscribers) {
        this.subscribers = subscribers;
    }

    // equals and hashcode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PuzzleEntity)) return false;
        PuzzleEntity that = (PuzzleEntity) o;
        return getName().equals(that.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }
}
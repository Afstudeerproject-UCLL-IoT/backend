package infrastructure.persistence.entities;

import core.domain.Puzzle;

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
            orphanRemoval = true,
            fetch = FetchType.LAZY
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

    public DeviceEntity getDevice() {
        return device;
    }

    public void setDevice(DeviceEntity device) {
        this.device = device;
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

    // mappings
    public static PuzzleEntity from(Puzzle puzzle){
        var entity = new PuzzleEntity();

        entity.setName(puzzle.getName());
        return entity;
    }
}

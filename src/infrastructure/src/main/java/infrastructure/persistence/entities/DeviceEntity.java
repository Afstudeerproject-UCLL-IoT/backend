package infrastructure.persistence.entities;

import core.domain.Device;
import core.domain.DeviceType;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity(name = "device")
public class DeviceEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private DeviceType type;

    @OneToOne(
            mappedBy = "device",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    @LazyToOne(LazyToOneOption.NO_PROXY)
    private PuzzleEntity puzzle;

    @OneToMany(
            mappedBy = "device",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private List<PuzzleSubscriberEntity> subscriptions = new ArrayList<>();

    // getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DeviceType getType() {
        return type;
    }

    public void setType(DeviceType type) {
        this.type = type;
    }

    public List<PuzzleSubscriberEntity> getSubscriptions() {
        return subscriptions;
    }

    public void setSubscriptions(List<PuzzleSubscriberEntity> subscriptions) {
        this.subscriptions = subscriptions;
    }

    public PuzzleEntity getPuzzle() {
        return puzzle;
    }

    public void setPuzzle(PuzzleEntity puzzle) {
        this.puzzle = puzzle;
    }

    // equals and hashcode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DeviceEntity)) return false;
        DeviceEntity that = (DeviceEntity) o;
        return getId().equals(that.getId()) &&
                getType() == that.getType();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getType());
    }

    // mappings
    public static DeviceEntity from (Device device){
        var entity = new DeviceEntity();
        entity.setType(device.getType());

        return entity;
    }
}

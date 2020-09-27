package infrastructure.persistence.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Objects;

@Entity(name = "puzzle_subscriber")
public class PuzzleSubscriberEntity implements Serializable {

    @Id
    @ManyToOne
    private DeviceEntity device;

    @Id
    @ManyToOne
    private PuzzleEntity puzzle;

    // controllers
    public PuzzleSubscriberEntity() {
    }

    public PuzzleSubscriberEntity(DeviceEntity device, PuzzleEntity puzzle) {
        this.device = device;
        this.puzzle = puzzle;
    }

    // getters and setters
    public DeviceEntity getDevice() {
        return device;
    }

    public void setDevice(DeviceEntity device) {
        this.device = device;
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
        if (!(o instanceof PuzzleSubscriberEntity)) return false;
        PuzzleSubscriberEntity that = (PuzzleSubscriberEntity) o;
        return getDevice().equals(that.getDevice()) &&
                getPuzzle().equals(that.getPuzzle());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDevice(), getPuzzle());
    }
}

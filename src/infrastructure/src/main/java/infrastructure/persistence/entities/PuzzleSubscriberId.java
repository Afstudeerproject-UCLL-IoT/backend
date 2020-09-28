package infrastructure.persistence.entities;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class PuzzleSubscriberId implements Serializable {

    @Column(name = "device_id")
    private long deviceId;

    @Column(name = "puzzle_name")
    private String puzzleName;

    public long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(long deviceId) {
        this.deviceId = deviceId;
    }

    public String getPuzzleName() {
        return puzzleName;
    }

    public void setPuzzleName(String puzzleName) {
        this.puzzleName = puzzleName;
    }

    public PuzzleSubscriberId(){

    }

    public PuzzleSubscriberId(long deviceId, String puzzleName) {
        this.deviceId = deviceId;
        this.puzzleName = puzzleName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PuzzleSubscriberId)) return false;
        PuzzleSubscriberId that = (PuzzleSubscriberId) o;
        return getDeviceId() == that.getDeviceId() &&
                Objects.equals(getPuzzleName(), that.getPuzzleName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDeviceId(), getPuzzleName());
    }
}

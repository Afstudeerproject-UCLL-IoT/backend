package com.ucll.afstudeer.IoT.persistence.device;

import com.ucll.afstudeer.IoT.domain.Device;
import com.ucll.afstudeer.IoT.persistence.PersistenceBase;
import org.jooq.DSLContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jooq.JooqTest;
import org.springframework.transaction.annotation.Transactional;

import static infrastructure.persistence.Tables.DEVICE;
import static infrastructure.persistence.Tables.PUZZLE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@JooqTest
@Transactional
public class AddDeviceWithPuzzleTest extends PersistenceBase {

    public AddDeviceWithPuzzleTest(@Autowired DSLContext dslContext) {
        super(dslContext);
    }

    @Test
    public void deviceWithPuzzleIsInserted() {
        // device that is going to be inserted
        var device = new Device.Builder()
                .fromDeviceName("ARDUINO-Puzzle1")
                .build();

        // add device
        var addedDevice = deviceRepository.addDeviceWithPuzzle(device);

        // check if the device is inserted
        var deviceRecord = context
                .select(DEVICE.ID, DEVICE.TYPE)
                .from(DEVICE)
                .fetchOne();

        assertNotNull(addedDevice);
        assertNotNull(deviceRecord);
        assertEquals(deviceRecord.value1(), addedDevice.getId());
        assertEquals(deviceRecord.value2(), addedDevice.getType().toString());

        // check if the puzzle is inserted
        var puzzleRecord = context
                .select(PUZZLE.NAME, PUZZLE.SOLUTION, PUZZLE.DEVICE_OWNER_ID)
                .from(PUZZLE)
                .fetchOne();

        assertNotNull(addedDevice.getPuzzle());
        assertNotNull(puzzleRecord);
        assertEquals(puzzleRecord.value1(), addedDevice.getPuzzle().getName());
        assertEquals(puzzleRecord.value2(), addedDevice.getPuzzle().getSolution());
        assertEquals(puzzleRecord.value3(), addedDevice.getId());
    }
}

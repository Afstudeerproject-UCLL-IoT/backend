package com.ucll.afstudeer.IoT.persistence.device;

import com.ucll.afstudeer.IoT.domain.Device;
import com.ucll.afstudeer.IoT.domain.DeviceType;
import com.ucll.afstudeer.IoT.persistence.PersistenceBase;
import org.jooq.DSLContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jooq.JooqTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@JooqTest
@Transactional
public class GetAllDevicesWithPuzzlesTest extends PersistenceBase {

    public GetAllDevicesWithPuzzlesTest(@Autowired DSLContext context) {
        super(context);
    }

    @BeforeEach
    public void initEach(){
        // create 3 puzzles
        IntStream.rangeClosed(1,3)
                .forEach(number -> {
                    var device = new Device.Builder()
                            .fromDeviceName("ARDUINO-Puzzle" + number)
                            .build();

                    deviceRepository.addDeviceWithPuzzle(device);
                });
    }

    @Test
    public void queryIsCorrect(){
        var devices = deviceRepository.getAllDevicesWithPuzzles();

        assertEquals(3, devices.size());
        IntStream.rangeClosed(0,2)
                .forEach(number -> {
                    assertEquals(devices.get(number).getType(), DeviceType.ARDUINO);
                    assertEquals(devices.get(number).getPuzzle().getName(), "Puzzle" + (number+1));
                });
    }
}

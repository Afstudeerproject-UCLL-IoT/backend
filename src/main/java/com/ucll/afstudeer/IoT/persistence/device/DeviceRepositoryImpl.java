package com.ucll.afstudeer.IoT.persistence.device;

import com.ucll.afstudeer.IoT.domain.Device;
import com.ucll.afstudeer.IoT.domain.DeviceType;
import com.ucll.afstudeer.IoT.domain.Puzzle;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

import static infrastructure.persistence.Tables.DEVICE;
import static infrastructure.persistence.Tables.PUZZLE;

@Repository
public class DeviceRepositoryImpl implements DeviceRepository {

    private final DSLContext context;

    public DeviceRepositoryImpl(@Autowired DSLContext context) {
        this.context = context;
    }

    @Override
    public Device addDeviceWithPuzzle(Device device) {
        var deviceId = context.insertInto(DEVICE, DEVICE.TYPE)
                .values(device.getType().toString())
                .returningResult(DEVICE.ID)
                .fetchOne()
                .getValue(DEVICE.ID);

        var puzzle = device.getPuzzle();
        context.insertInto(PUZZLE, PUZZLE.NAME, PUZZLE.SOLUTION, PUZZLE.DEVICE_OWNER_ID)
                .values(puzzle.getName(), puzzle.getSolution(), deviceId)
                .execute();

        return new Device.Builder()
                .withId(deviceId)
                .fromDeviceName(device.toString())
                .build();
    }

    @Override
    public List<Device> getAllDevicesWithPuzzles() {
        return context
                .select(DEVICE.ID, DEVICE.TYPE, PUZZLE.NAME, PUZZLE.SOLUTION)
                .from(DEVICE.innerJoin(PUZZLE).on(DEVICE.ID.eq(PUZZLE.DEVICE_OWNER_ID)))
                .fetch()
                .stream()
                .map(record -> new Device.Builder()
                        .withId(record.value1())
                        .withDeviceType(DeviceType.valueOf(record.value2()))
                        .withPuzzle(new Puzzle.Builder()
                                .withName(record.value3())
                                .withSolution(record.value4())
                                .build())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public boolean isPresent(Device device) {
        return context.fetchExists(
                context.selectOne()
                        .from(DEVICE)
                        .where(DEVICE.ID.eq(device.getId()))
        );
    }
}

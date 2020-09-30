package com.ucll.afstudeer.IoT.persistence.device;

import com.ucll.afstudeer.IoT.domain.Device;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.stereotype.Repository;

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
    public boolean isPresent(Device device) {
        return context.fetchExists(
                    context.selectOne()
                    .from(DEVICE)
                    .where(DEVICE.ID.eq(device.getId()))
        );
    }
}

package com.ucll.afstudeer.IoT.persistence.device;

import com.ucll.afstudeer.IoT.domain.ConnectionActivity;
import com.ucll.afstudeer.IoT.domain.Device;
import com.ucll.afstudeer.IoT.domain.DeviceType;
import com.ucll.afstudeer.IoT.domain.Puzzle;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static infrastructure.persistence.Tables.*;

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
                .withDeviceType(device.getType())
                .withPuzzle(device.getPuzzle())
                .build();
    }

    @Override
    public Device addFeedbackDevice(Device device) {
        var deviceId = context.insertInto(DEVICE, DEVICE.TYPE)
                .values(device.getType().toString())
                .returningResult(DEVICE.ID)
                .fetchOne()
                .getValue(DEVICE.ID);

        return new Device.Builder()
                .withId(deviceId)
                .withDeviceType(device.getType())
                .withoutPuzzle()
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
    public ConnectionActivity addDeviceConnectionActivity(Device device, LocalDateTime online) {
        // alias
        var ca = CONNECTION_ACTIVITY.as("ca");

        var record = context
                .insertInto(ca, ca.ONLINE, ca.OFFLINE, ca.DEVICE_ID)
                .values(online, null, device.getId())
                .returningResult(ca.ID, ca.ONLINE, ca.OFFLINE)
                .fetchOne();

        return new ConnectionActivity.Builder()
                .withId(record.value1())
                .withOnlineTime(record.value2())
                .withOfflineTime(record.value3())
                .build();
    }

    @Override
    public ConnectionActivity setLastDeviceConnectionActivityOfflineTime(Device device, LocalDateTime offline) {
        // alias
        var ca = CONNECTION_ACTIVITY.as("ca");

        var record = context
                .update(ca)
                .set(ca.OFFLINE, offline)
                .where(ca.DEVICE_ID.eq(device.getId())
                        .and(ca.OFFLINE.isNull()))
                .returningResult(ca.ID, ca.ONLINE, ca.OFFLINE)
                .fetchOne();

        if (record == null)
            return null;

        return new ConnectionActivity.Builder()
                .withId(record.value1())
                .withOnlineTime(record.value2())
                .withOfflineTime(record.value3())
                .build();
    }

    @Override
    public List<ConnectionActivity> getConnectionActivity(Device device) {
        // alias
        var ca = CONNECTION_ACTIVITY.as("ca");

        var records = context
                .select(ca.ID, ca.ONLINE, ca.OFFLINE)
                .from(ca)
                .where(ca.DEVICE_ID.eq(device.getId()))
                .fetch();

        return records.stream()
                .map(record -> new ConnectionActivity.Builder()
                        .withId(record.value1())
                        .withOnlineTime(record.value2())
                        .withOfflineTime(record.value3())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public Device getDeviceByPuzzle(Puzzle puzzle) {
        // aliases
        var d = DEVICE.as("d");
        var p = PUZZLE.as("p");


        var record = context
                .select(d.ID, d.TYPE, p.NAME, p.SOLUTION)
                .from(p.innerJoin(d)
                        .on(p.NAME.eq(puzzle.getName())
                                .and(p.DEVICE_OWNER_ID.eq(d.ID))))
                .fetchOne();

        if (record == null)
            return null;

        return new Device.Builder()
                .withId(record.value1())
                .withDeviceType(DeviceType.valueOf(record.value2()))
                .withPuzzle(new Puzzle.Builder()
                        .withName(record.value3())
                        .withSolution(record.value4())
                        .build())
                .build();
    }

    @Override
    public Device getFeedbackDevice() {
        var record = context
                .selectFrom(DEVICE)
                .where(DEVICE.TYPE.eq(DeviceType.ARDUINO_FEEDBACK.toString()))
                .fetchOne();

        if(record == null)
            return null;

        return new Device.Builder()
                .withId(record.getId())
                .withDeviceType(DeviceType.valueOf(record.getType()))
                .withoutPuzzle()
                .build();

    }

    @Override
    public Device get(Integer deviceId) {
        var record = context
                .select(DEVICE.ID, DEVICE.TYPE, PUZZLE.NAME, PUZZLE.SOLUTION)
                .from(DEVICE.innerJoin(PUZZLE).on(DEVICE.ID.eq(PUZZLE.DEVICE_OWNER_ID)))
                .where(DEVICE.ID.eq(deviceId))
                .fetchOne();


        if (record != null) {
            return new Device.Builder()
                    .withId(record.value1())
                    .withDeviceType(DeviceType.valueOf(record.value2()))
                    .withPuzzle(new Puzzle.Builder()
                            .withName(record.value3())
                            .withSolution(record.value4()).build())
                    .build();
        }
        return null;
    }

    @Override
    public boolean exists(Integer deviceId) {
        return context.fetchExists(context
                .selectOne()
                .from(DEVICE)
                .where(DEVICE.ID.eq(deviceId)));
    }
}

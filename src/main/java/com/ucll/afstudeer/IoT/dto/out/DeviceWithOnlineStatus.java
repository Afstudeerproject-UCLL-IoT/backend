package com.ucll.afstudeer.IoT.dto.out;

import com.ucll.afstudeer.IoT.domain.constant.DeviceType;

public class DeviceWithOnlineStatus {
    private final int deviceId;
    private final DeviceType deviceType;
    private final boolean onlineStatus;

    public DeviceWithOnlineStatus(int deviceId, DeviceType deviceType, boolean onlineStatus) {
        this.deviceId = deviceId;
        this.deviceType = deviceType;
        this.onlineStatus = onlineStatus;
    }

    public int getDeviceId() {
        return deviceId;
    }

    public DeviceType getDeviceType() {
        return deviceType;
    }

    public boolean isOnlineStatus() {
        return onlineStatus;
    }
}

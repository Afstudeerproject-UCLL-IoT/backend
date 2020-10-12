package com.ucll.afstudeer.IoT.web.controller;

import com.ucll.afstudeer.IoT.domain.ConnectionActivity;
import com.ucll.afstudeer.IoT.domain.Device;
import com.ucll.afstudeer.IoT.dto.out.DeviceWithOnlineStatus;
import com.ucll.afstudeer.IoT.service.device.DeviceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Device calls")
@RestController()
@RequestMapping("/device")
public class DeviceController {

    private final DeviceService deviceService;

    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @GetMapping("/all")
    @Operation(summary = "Get all devices")
    public List<Device> getAllDevices() {
        return deviceService
                .getAllDevices()
                .getValue();
    }

    @GetMapping
    @Operation(summary = "Get all puzzle devices")
    public List<Device> getAllDevicesWithPuzzles() {
        return deviceService
                .getAllDevicesWithPuzzleHandler()
                .getValue();
    }

    @GetMapping("/status")
    @Operation(summary = "Get all devices with their current online status")
    public List<DeviceWithOnlineStatus> getAllDevicesWithTheirOnlineStatus() {
        return deviceService
                .getOnlineStatusForAlDevices()
                .getValue();
    }

    @GetMapping("/{deviceId}")
    @Operation(summary = "Get all of the connection activity for an existing device")
    public List<ConnectionActivity> getConnectionActivity(@PathVariable int deviceId) {
        // create the device
        var device = new Device.Builder()
                .withId(deviceId)
                .withoutPuzzle()
                .withoutDeviceType()
                .build();

        return deviceService
                .getAllConnectionActivity(device)
                .getValue();
    }
}

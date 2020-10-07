package com.ucll.afstudeer.IoT.web.controller;

import com.ucll.afstudeer.IoT.domain.Device;
import com.ucll.afstudeer.IoT.service.device.DeviceService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController()
@RequestMapping("/device")
public class DeviceController {

    private final DeviceService deviceService;

    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @GetMapping
    @Operation(summary = "Get all devices and their puzzles")
    public List<Device> getAllDevicesWithPuzzles() {
        return deviceService
                .getAllDevicesWithPuzzleHandler()
                .getValue();
    }
}

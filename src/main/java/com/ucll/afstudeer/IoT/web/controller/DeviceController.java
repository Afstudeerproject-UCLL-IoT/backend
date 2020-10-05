package com.ucll.afstudeer.IoT.web.controller;

import com.ucll.afstudeer.IoT.dto.DevicePuzzleDto;
import com.ucll.afstudeer.IoT.service.device.DeviceService;
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
    public List<DevicePuzzleDto> getAllDevicesWithPuzzles() {
        return deviceService
                .getAllDevicesWithPuzzleHandler()
                .getValue();
    }
}

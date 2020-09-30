package com.ucll.afstudeer.IoT.service.device.handlers;

import com.ucll.afstudeer.IoT.dto.DevicePuzzleDto;
import com.ucll.afstudeer.IoT.persistence.device.DeviceRepository;

import java.util.List;
import java.util.stream.Collectors;

public class GetAllDevicesWithPuzzleHandler {

    public static List<DevicePuzzleDto> handle(DeviceRepository deviceRepository){
        return deviceRepository.getAllDevicesWithPuzzles()
                .stream()
                .map(device -> new DevicePuzzleDto(
                        device.getId(),
                        device.getType().toString(),
                        device.getPuzzle().getName(),
                        device.getPuzzle().getSolution()))
                .collect(Collectors.toList());
    }
}

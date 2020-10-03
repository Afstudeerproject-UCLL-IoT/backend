package com.ucll.afstudeer.IoT.service.device.handlers;

import com.ucll.afstudeer.IoT.dto.DevicePuzzleDto;
import com.ucll.afstudeer.IoT.persistence.device.DeviceRepository;
import com.ucll.afstudeer.IoT.service.ServiceActionResponse;

import java.util.List;
import java.util.stream.Collectors;

public class GetAllDevicesWithPuzzleHandler {

    public static ServiceActionResponse<List<DevicePuzzleDto>> handle(DeviceRepository deviceRepository){
        var result = deviceRepository.getAllDevicesWithPuzzles()
                .stream()
                .map(device -> new DevicePuzzleDto(
                        device.getId(),
                        device.getType().toString(),
                        device.getPuzzle().getName(),
                        device.getPuzzle().getSolution()))
                .collect(Collectors.toList());

        return new ServiceActionResponse<>(result);
    }
}

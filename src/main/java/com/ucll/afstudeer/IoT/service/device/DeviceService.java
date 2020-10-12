package com.ucll.afstudeer.IoT.service.device;

import com.ucll.afstudeer.IoT.domain.ConnectionActivity;
import com.ucll.afstudeer.IoT.domain.Device;
import com.ucll.afstudeer.IoT.domain.Puzzle;
import com.ucll.afstudeer.IoT.dto.out.DeviceWithOnlineStatus;
import com.ucll.afstudeer.IoT.service.ServiceActionResponse;

import java.time.LocalDateTime;
import java.util.List;

public interface DeviceService {

    // ACTIONS

    /**
     * Register a device and it's puzzle to the system
     *
     * @param device The device
     * @return The registered device is returned, if it was already registered it's also returned
     */
    ServiceActionResponse<Device> registerDeviceWithPuzzle(Device device);

    /**
     * Register the feedback device to the system
     *
     * @param device The device
     * @return The registered feedback device, if it was already registered it's also returned
     */
    ServiceActionResponse<Device> registerFeedbackDevice(Device device);

    /**
     * Update the solution of a puzzle and give it's device a notification of this event
     *
     * @param puzzle      The current puzzle
     * @param newSolution The new solution of the puzzle
     * @return The updated puzzle or a service error indicating the puzzle or device did not exist
     */
    ServiceActionResponse<Puzzle> updatePuzzleSolution(Puzzle puzzle, String newSolution);

    /**
     * Create a online activity for a device in the system.
     *
     * @param device   The device
     * @param onlineAt The datetime it became online
     * @return The created connection activity for the device or a service error indicating the device did not exist
     */
    ServiceActionResponse<ConnectionActivity> deviceOnline(Device device, LocalDateTime onlineAt);

    /**
     * Complete the connection activity for a device by setting it offline
     *
     * @param device    The device
     * @param offlineAt The datetime it became offline
     * @return The completed connection activity or a service error indicating the device does not exist
     * or the device was not online so it could not turn offline
     */
    ServiceActionResponse<ConnectionActivity> deviceOffline(Device device, LocalDateTime offlineAt);

    // QUERIES

    /**
     * Get all the devices and their puzzles
     *
     * @return A list of devices or an empty list when no devices where found
     */
    ServiceActionResponse<List<Device>> getAllDevicesWithPuzzleHandler();

    /**
     * Get the connection activity for a device in the system
     *
     * @param device The device
     * @return A list containing the connection activity for the device or an empty list
     */
    ServiceActionResponse<List<ConnectionActivity>> getAllConnectionActivity(Device device);

    /**
     * Get all the puzzle devices and the feedback device from the system
     *
     * @return A list of all the devices or an empty list when nothing was found
     */
    ServiceActionResponse<List<Device>> getAllDevices();

    /**
     * Get the online status for all devices in the system
     *
     * @return A list containing DeviceWithOnlineStatus objects
     */
    ServiceActionResponse<List<DeviceWithOnlineStatus>> getOnlineStatusForAlDevices();
}

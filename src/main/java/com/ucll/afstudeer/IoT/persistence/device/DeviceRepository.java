package com.ucll.afstudeer.IoT.persistence.device;

import com.ucll.afstudeer.IoT.domain.ConnectionActivity;
import com.ucll.afstudeer.IoT.domain.Device;
import com.ucll.afstudeer.IoT.domain.Puzzle;
import com.ucll.afstudeer.IoT.persistence.GenericRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface DeviceRepository extends GenericRepository<Device, Integer> {
    /**
     * Insert a device with it's puzzle to the data store
     * @param device The device
     * @return The inserted device with it's puzzle and it's identifier set
     */
    Device addDeviceWithPuzzle(Device device);

    /**
     * Insert the feedback device to the data store
     * @param device The feedback device
     * @return The inserted feedback device with it's identifier set
     */
    Device addFeedbackDevice(Device device);

    /**
     * Get all the device puzzles in the data store
     * @return A list with the device puzzles or an empty list
     */
    List<Device> getAllDevicesWithPuzzles();

    /**
     * Insert a device connection activity to the data store
     * @param device The device
     * @param online The time the device became online
     * @return The inserted connection activity with it's identifier set
     */
    ConnectionActivity addDeviceConnectionActivity(Device device, LocalDateTime online);

    /**
     * For a device in the data store that is currently set to online, set it's offline time so that the total connection activity is registered
     * @param device The device
     * @param offline The time it was offline
     * @return The complete connection activity or null when it was not found or the device was not online
     */
    ConnectionActivity setLastDeviceConnectionActivityOfflineTime(Device device, LocalDateTime offline);

    /**
     * Get all the connection activity from a device in the data store
     * @param device The device
     * @return A list of the connection activity of the device or an empty list when the device was not found
     */
    List<ConnectionActivity> getConnectionActivity(Device device);

    /**
     * Retrieve a device from the data store by it's puzzle
     * @param puzzle The puzzle
     * @return If the device is found by it's puzzle the device is returned otherwise a null value is returned
     */
    Device getDeviceByPuzzle(Puzzle puzzle);

    /**
     * Retrieve the feedback device from the data store
     * @return If the feedback device is found it's returned otherwise a null value is returned
     */
    Device getFeedbackDevice();
}

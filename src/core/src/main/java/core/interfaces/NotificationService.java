package core.interfaces;

import core.domain.Device;
import core.domain.Event;

public interface NotificationService<T> {
    // send the notification of the occurrence of an event to devices that listen for all events
    void send(Event event);

    // send the notification of the occurrence of a device to 1 actor
    void send(Device device, Event event);

    void addDeviceConnection(Device device, T connection);

    void removeDeviceConnection(Device device, T connection);
}

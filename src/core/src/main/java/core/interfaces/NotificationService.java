package core.interfaces;

import core.domain.Device;
import core.domain.Event;

import java.util.List;

public interface NotificationService {
    // send the notification of the occurrence of an event to devices that listen for all events
    void send(Event event);

    // send the notification of the occurrence of a device to 1 actor
    void send(Device device, Event event);

    // send the notification of the occurrence of an event to multiple devices
    void send(List<Device> devices, Event event);

    // as a device retrieve my event notifications if I missed them
    List<Event> getNotifications(Device device);

}

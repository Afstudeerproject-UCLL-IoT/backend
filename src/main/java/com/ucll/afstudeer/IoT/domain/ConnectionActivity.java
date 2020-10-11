package com.ucll.afstudeer.IoT.domain;

import java.time.LocalDateTime;

public class ConnectionActivity {

    private final int id;

    // time the connection was set to online
    private final LocalDateTime online;

    // time the connection was dropped
    private final LocalDateTime offline;

    private ConnectionActivity(int id, LocalDateTime online, LocalDateTime offline) {
        this.id = id;
        this.online = online;
        this.offline = offline;
    }

    public int getId() {
        return id;
    }

    public LocalDateTime getOnline() {
        return online;
    }

    public LocalDateTime getOffline() {
        return offline;
    }

    public static class Builder {
        private int id;
        private LocalDateTime online;
        private LocalDateTime offline;

        public Builder withId(int id) {
            this.id = id;
            return this;
        }

        public Builder withoutId() {
            this.id = 0;
            return this;
        }

        public Builder withOnlineTime(LocalDateTime online) {
            this.online = online;
            return this;
        }

        public Builder withOfflineTime(LocalDateTime offline) {
            this.offline = offline;
            return this;
        }

        public Builder withoutOfflineTime() {
            this.offline = null;
            return this;
        }

        public ConnectionActivity build() {
            return new ConnectionActivity(id, online, offline);
        }
    }

}

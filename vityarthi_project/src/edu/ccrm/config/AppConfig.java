package edu.ccrm.config;

import java.nio.file.Path;

public final class AppConfig {
    private static final AppConfig INSTANCE = new AppConfig();

    private Path dataFolder;

    private AppConfig() {
        this.dataFolder = Path.of("test_data");
    }

    public static AppConfig getInstance() {
        return INSTANCE;
    }

    public Path getDataFolder() {
        return dataFolder;
    }

    public void setDataFolder(Path dataFolder) {
        this.dataFolder = dataFolder;
    }
}



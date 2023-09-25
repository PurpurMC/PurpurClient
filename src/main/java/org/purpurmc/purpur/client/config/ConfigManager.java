package org.purpurmc.purpur.client.config;

import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import net.fabricmc.loader.api.FabricLoader;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.hocon.HoconConfigurationLoader;

import java.io.IOException;
import java.nio.file.Path;

public class ConfigManager {
    private Path configFile;
    private Path backupConfigFile;
    private Config config;

    private static HoconConfigurationLoader loader(final Path path) {
        return HoconConfigurationLoader.builder()
            .path(path)
            .build();
    }

    public Config getConfig() {
        if (this.config == null) {
            reload();
        }
        return this.config;
    }

    public void reload() {
        final Path config;
        try {
            config = this.getConfigFile();
        } catch (final IOException e) {
            throw rethrow(e);
        }

        boolean hasConfigVersion = false;
        try {
            final HoconConfigurationLoader loader = loader(config);
            final CommentedConfigurationNode node = loader.load();
            hasConfigVersion = node.hasChild("config-version");
            this.config = node.get(Config.class);
        } catch (IOException e) {
            rethrow(e);
        }

        if (!hasConfigVersion) {
            try {
                Path backupConfig = this.getBackupConfigFile();
                Files.copy(configFile, backupConfig, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                rethrow(e);
            }
            this.config.seats.setAllSeats(0, 0, 0);
        }

        this.save();
    }

    public void save() {
        final HoconConfigurationLoader loader = loader(this.configFile);
        final CommentedConfigurationNode node = loader.createNode();
        try {
            node.set(this.config);
            loader.save(node);
        } catch (final IOException e) {
            rethrow(e);
        }
    }

    @SuppressWarnings("RedundantThrows")
    private Path getBackupConfigFile() throws IOException {
        if (this.backupConfigFile != null) {
            return this.backupConfigFile;
        }
        final Path configDir = FabricLoader.getInstance().getConfigDir();
        this.backupConfigFile = configDir.resolve("purpurclient.conf.backup");
        return this.backupConfigFile;
    }

    @SuppressWarnings("RedundantThrows")
    private Path getConfigFile() throws IOException {
        if (this.configFile != null) {
            return this.configFile;
        }
        final Path configDir = FabricLoader.getInstance().getConfigDir();
        this.configFile = configDir.resolve("purpurclient.conf");
        return this.configFile;
    }

    @SuppressWarnings("unchecked")
    public static <X extends Throwable> RuntimeException rethrow(final Throwable t) throws X {
        throw (X) t;
    }
}

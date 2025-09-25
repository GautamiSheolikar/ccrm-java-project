package edu.ccrm.io;

import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BackupService {
    public Path backupDirectory(Path sourceDir, Path backupsRoot) throws IOException {
        String ts = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        Path target = backupsRoot.resolve("backup_" + ts);
        Files.createDirectories(target);
        if (Files.exists(sourceDir)) {
            Files.walk(sourceDir)
                .forEach(p -> {
                    try {
                        Path rel = sourceDir.relativize(p);
                        Path dest = target.resolve(rel);
                        if (Files.isDirectory(p)) {
                            Files.createDirectories(dest);
                        } else {
                            Files.createDirectories(dest.getParent());
                            Files.copy(p, dest, StandardCopyOption.REPLACE_EXISTING);
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
        }
        return target;
    }

    public long computeTotalSizeRecursive(Path dir) throws IOException {
        if (!Files.exists(dir)) return 0L;
        final long[] sum = new long[]{0L};
        Files.walk(dir).filter(Files::isRegularFile).forEach(p -> {
            try {
                sum[0] += Files.size(p);
            } catch (IOException ignored) {}
        });
        return sum[0];
    }
}



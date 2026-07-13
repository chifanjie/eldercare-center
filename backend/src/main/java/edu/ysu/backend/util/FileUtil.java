package edu.ysu.backend.util;

import edu.ysu.backend.entity.pojo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Slf4j
@Component
public class FileUtil {

    private static Path requiredRoot() {
        String configured = System.getProperty("storage.upload-root");
        if (configured == null || configured.trim().isEmpty()) {
            configured = System.getenv("UPLOAD_ROOT");
        }
        if (configured == null || configured.trim().isEmpty()) {
            throw new IllegalStateException("Upload root is required");
        }
        Path root = Paths.get(configured).toAbsolutePath().normalize();
        try {
            if (!Files.exists(root, LinkOption.NOFOLLOW_LINKS) || Files.isSymbolicLink(root)) {
                throw new IllegalStateException("Upload root must be an existing directory");
            }
            Path realRoot = root.toRealPath();
            if (!Files.isDirectory(realRoot, LinkOption.NOFOLLOW_LINKS)) {
                throw new IllegalStateException("Upload root must be an existing directory");
            }
            return realRoot;
        } catch (IOException exception) {
            throw new IllegalStateException("Upload root cannot be resolved");
        }
    }

    public static String getSystemPath() {
        return requiredRoot().toString() + File.separator;
    }

    public static Result saveFile(MultipartFile file, String path) {
        try {
            Path root = requiredRoot();
            Path requested = Paths.get(path == null ? "" : path).normalize();
            if (requested.isAbsolute() || requested.startsWith("..")) {
                return Result.fail("Invalid storage path");
            }
            Path directory = root.resolve(requested).normalize();
            if (!directory.startsWith(root)) {
                return Result.fail("Invalid storage path");
            }
            rejectLinkedParents(root, directory);
            Files.createDirectories(directory);
            Path realDirectory = directory.toRealPath();
            if (!realDirectory.startsWith(root)) {
                return Result.fail("Invalid storage path");
            }
            String fileName = setFileUUID(file);
            Path target = realDirectory.resolve(fileName).normalize();
            if (!target.startsWith(realDirectory)
                    || Files.exists(target, LinkOption.NOFOLLOW_LINKS)) {
                return Result.fail("Invalid storage path");
            }
            Files.write(target, file.getBytes());
            log.info("File stored successfully");
            return Result.ok("File stored successfully").data("fileName", fileName);
        } catch (IOException | RuntimeException exception) {
            log.warn("File storage failed");
            return Result.fail("File storage failed");
        }
    }

    private static void rejectLinkedParents(Path root, Path directory) throws IOException {
        Path current = root;
        for (Path part : root.relativize(directory)) {
            current = current.resolve(part);
            if (Files.exists(current, LinkOption.NOFOLLOW_LINKS)) {
                if (Files.isSymbolicLink(current) || !current.toRealPath().startsWith(root)) {
                    throw new IOException("Unsafe storage path");
                }
            }
        }
    }

    private static String setFileUUID(MultipartFile file) {
        String original = file.getOriginalFilename();
        String name = original == null ? "file" : Paths.get(original).getFileName().toString();
        String safeName = name.replaceAll("[^A-Za-z0-9._-]", "_");
        return UUID.randomUUID() + "-" + safeName;
    }
}

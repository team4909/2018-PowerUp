package org.team4909.bionicframework.hardware.core;

import edu.wpi.first.wpilibj.DriverStation;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.rmi.server.ExportException;

public class RioFS {
    public static final Path rootPath = Paths.get("home", "lvuser");

    public static void makeDir(String directory){
        Path path = rootPath.resolve(directory);

        try {
            Files.createDirectory(path);
        } catch (Exception e) {
            DriverStation.reportError(e.getMessage(), e.getStackTrace());
        }
    }

    public static void writeFile(String directory, String name, String content){
        Path path = rootPath.resolve(directory).resolve(name);

        try {
            Files.write(path, content.getBytes(), StandardOpenOption.CREATE);
        } catch (Exception e) {
            DriverStation.reportError(e.getMessage(), e.getStackTrace());
        }
    }

    public static void appendFile(String directory, String name, String content){
        Path path = rootPath.resolve(directory).resolve(name);

        try {
            Files.write(path, content.getBytes(), StandardOpenOption.APPEND);
        } catch (Exception e) {
            DriverStation.reportError(e.getMessage(), e.getStackTrace());
        }
    }
}

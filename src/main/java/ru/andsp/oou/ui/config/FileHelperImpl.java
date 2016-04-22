package ru.andsp.oou.ui.config;


import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.TRUNCATE_EXISTING;

public class FileHelperImpl implements FileHelper {
    @Override
    public String readFile(String fileName) throws IOException {
        File file = new File(fileName);
        if (!file.exists()) {
            return null;
        }
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        StringBuilder sb = new StringBuilder();
        String line = reader.readLine();

        while (line != null) {
            sb.append(line).append(System.lineSeparator());
            line = reader.readLine();
        }
        return sb.toString();
    }

    @Override
    public void writeFile(String fileName, String source) throws IOException {

        try (OutputStream writer = Files.newOutputStream(Paths.get(fileName), CREATE,TRUNCATE_EXISTING)) {
            writer.write(source.getBytes());
        }
    }
}

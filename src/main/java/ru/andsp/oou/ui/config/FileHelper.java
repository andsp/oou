package ru.andsp.oou.ui.config;


import java.io.IOException;

interface FileHelper {

    String readFile(String fileName) throws IOException;

    void writeFile(String fileName, String source) throws IOException;
}

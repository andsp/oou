package ru.andsp.oou.service;


import ru.andsp.oou.helper.ExtensionHelper;
import ru.andsp.oou.type.OracleObject;

import java.io.*;

public class Storage {


    static private void saveFile(File parent, OracleObject object) {
        if (object != null) {
            if (object.getSource() != null && object.getName() != null) {
                try (Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(parent, String.format("%s.%s", object.getName(), ExtensionHelper.getExtension(object.getTypeObject())))), "UTF-8"))) {
                    out.write(object.getSource());
                } catch (IOException ex) {
                    System.err.println(ex);
                }
            }
        }
    }

    static public synchronized void save(OracleObject object, String directory) {
        File subParent = new File(directory, object.getTypeObject().name());
        if (!subParent.exists()) {
            subParent.mkdirs();
        }
        saveFile(subParent, object);
    }
}


package ru.andsp.oou.ui.config;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import ru.andsp.oou.ui.Instance;

import java.io.IOException;
import java.util.HashMap;

public class ConfigHelper {

    private static ConfigHelper configHelper;


    private static final String FILE_CONFIG = "config.json";

    private final FileHelper fileHelper;

    private HashMap<String, Instance> instanceList;

    private HashMap<String, Instance> loadFromFile() throws IOException {
        String source = fileHelper.readFile(FILE_CONFIG);
        Gson gson = new Gson();
        HashMap<String, Instance> instances = gson.fromJson(source, new TypeToken<HashMap<String, Instance>>() {
        }.getType());
        if (instances == null) {
            instances = new HashMap<>();
        }
        return instances;
    }

    private ConfigHelper(FileHelper fileHelper) {
        this.fileHelper = fileHelper;
    }

    public HashMap<String, Instance> getInstances() {
        if (instanceList == null) {
            try {
                instanceList = this.loadFromFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return instanceList;
    }

    private void saveConfig() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String source = gson.toJson(instanceList);
        try {
            fileHelper.writeFile(FILE_CONFIG, source);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ConfigHelper getInstance() {
        if (configHelper == null) {
            configHelper = new ConfigHelper(new FileHelperImpl());
        }
        return configHelper;
    }

    public void remove(Instance instance) {
        getInstances().remove(instance.getId());
        saveConfig();
    }

    public void save(Instance instance) {
        if (instance.isNew()) {
            String key = this.generateKey();
            instance.setId(key);
            getInstances().put(key, instance);
        } else {
            getInstances().put(instance.getId(), instance);
        }
        saveConfig();
    }

    private String generateKey() {
        return java.util.UUID.randomUUID().toString();
    }
}

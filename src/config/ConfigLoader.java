package config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.Map;
import java.util.HashMap;

public class ConfigLoader {
    private Properties properties = new Properties();
    private Map<Integer, String[]> filePairs = new HashMap<>();

    public ConfigLoader(String configFile) throws IOException {
        properties.load(new FileInputStream(configFile));

        // Dynamically load file pairs from config
        int i = 1;
        while (properties.containsKey("files." + i + ".source")) {
            String[] pair = new String[2];
            pair[0] = properties.getProperty("files." + i + ".source");
            pair[1] = properties.getProperty("files." + i + ".target");
            filePairs.put(i, pair);
            i++;
        }
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    public Map<Integer, String[]> getFilePairs() {
        return filePairs;
    }
}

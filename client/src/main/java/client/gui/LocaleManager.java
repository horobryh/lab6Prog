package client.gui;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.Scanner;

public class LocaleManager {
    private static LocaleManager instance = null;
    private static ResourceBundle currentBundle;
    private HashMap<String, ResourceBundle> bundlesMap = new HashMap<>();

    public static LocaleManager getInstance(ResourceBundle bundleRU, ResourceBundle bundleEN, ResourceBundle bundleDA, ResourceBundle bundleTR) {
        if (instance == null) {
            instance = new LocaleManager(bundleRU, bundleEN, bundleDA, bundleTR);
            instance.changeCurrentLanguage("ru");
        }
        return instance;
    }

    private LocaleManager( ResourceBundle ru, ResourceBundle en, ResourceBundle da, ResourceBundle tr) {
        bundlesMap.put("ru", ru);
        bundlesMap.put("en", en);
        bundlesMap.put("da", da);
        bundlesMap.put("tr", tr);
    }

    public void changeCurrentLanguage(String code) {
        if (bundlesMap.containsKey(code)) {
            currentBundle = bundlesMap.get(code);
        }
    }

    public String getName(String key) {
        return currentBundle.getString(key);
    }
}

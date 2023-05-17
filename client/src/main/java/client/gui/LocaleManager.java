package client.gui;

import java.util.HashMap;

public class LocaleManager {
    private LocaleManager instance = null;
//    private HashMap<>

    public LocaleManager getInstance() {
        if (instance == null) {
            instance = new LocaleManager();
        }
        return instance;
    }

    private LocaleManager() {
    }

}

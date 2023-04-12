package client.utils;

import java.util.List;

public class BeautfilListPrint<T> {
    private final List<T> listToPrint;

    public BeautfilListPrint (List<T> listToPrint) {
        this.listToPrint = listToPrint;
    }

    public String getBeautifulPrint() {
        StringBuilder res = new StringBuilder();
        int i = 0;
        for (T element : listToPrint) {
            res.append(++i).append(". ").append(element.toString()).append("\n");
        }
        return res.toString();
    }
}

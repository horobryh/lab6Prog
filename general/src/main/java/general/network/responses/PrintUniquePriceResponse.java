package general.network.responses;

import general.network.Response;

import java.io.Serializable;
import java.util.List;

public class PrintUniquePriceResponse extends Response implements Serializable {
    private final List<Integer> resultList;

    public PrintUniquePriceResponse(Boolean result, List<Integer> resultList) {
        super(result);
        this.resultList = resultList;
    }

    public PrintUniquePriceResponse(Boolean result, List<Integer> resultList, String message) {
        super(result, message);
        this.resultList = resultList;
    }

    public List<Integer> getResultList() {
        return resultList;
    }
}

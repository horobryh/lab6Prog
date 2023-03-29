package general.network.responses;

import general.network.Response;

import java.io.Serializable;
import java.util.List;

public class PrintFieldAscendingDiscountResponse extends Response implements Serializable {
    private final List<Long> resultList;
    public PrintFieldAscendingDiscountResponse(Boolean result, List<Long> resultList) {
        super(result);
        this.resultList = resultList;
    }

    public List<Long> getResultList() {
        return this.resultList;
    }
}

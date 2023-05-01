package general.network.responses;

import general.network.Response;

import java.io.Serializable;

public class CountLessThanDiscountResponse extends Response implements Serializable {
    private final Long count;
    public CountLessThanDiscountResponse(Boolean result, Long count) {
        super(result);
        this.count = count;
    }

    public CountLessThanDiscountResponse(Boolean result, Long count, String message) {
        super(result, message);
        this.count = count;
    }

    public Long getCount() {
        return this.count;
    }
}

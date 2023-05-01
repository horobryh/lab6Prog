package general.network.requests;

import general.network.Request;

import java.io.Serializable;

public class CountLessThanDiscountRequest extends Request implements Serializable {
    private final Long discount;

    public CountLessThanDiscountRequest(Long discount) {
        this.discount = discount;
    }

    public Long getDiscount() {
        return this.discount;
    }
}

package general.network.requests;

import general.network.Request;

import java.io.Serializable;

public class CheckIDInCollectionRequest extends Request implements Serializable {
    private Integer id;

    public CheckIDInCollectionRequest(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return this.id;
    }
}

package general.network;

import java.io.*;

public abstract class Request implements Serializable {
    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}

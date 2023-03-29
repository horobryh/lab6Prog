package general.models;

import com.opencsv.bean.CsvBindByName;
import general.validators.baseValidators.NotNull;

import java.util.List;

/**
 * Coordinates model class
 */
public class Coordinates extends BaseModelWithValidators {
    public void setValidators() {
        validators.put("x", List.of(new NotNull()));
        validators.put("y", List.of(new NotNull()));
    }

    @CsvBindByName(column = "CoordinatesX", required = true)
    private Integer x;

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public Coordinates(Integer x, Float y) {
        super();
        this.x = x;
        this.y = y;
    }

    public Float getY() {
        return y;
    }

    public void setY(Float y) {
        this.y = y;
    }

    @CsvBindByName(column = "CoordinatesY", required = true)
    private Float y;

    public Coordinates() {
        super();
    }

    ;

    @Override
    public String toString() {
        return "Coordinates{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    public String beautifulString() {
        return "x: " + getX() + ", y: " + getY();
    }
}
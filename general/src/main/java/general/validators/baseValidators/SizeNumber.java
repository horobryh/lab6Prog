package general.validators.baseValidators;

import general.validators.Verifiable;
import general.validators.exceptions.SizeLimitedException;
import general.validators.exceptions.ValidatorException;

/**
 * Number value constraint validator class
 * @param <N> Type of Number
 */
public class SizeNumber<N extends Number> implements Verifiable {
    private final N minValue;
    private final N maxValue;
    public SizeNumber(N minValue, N maxValue) {
        this.minValue = minValue;
        this.maxValue = maxValue;
    }
    @Override
    public void checkCondition(String object) throws ValidatorException {
        double checkedObject = Double.parseDouble(object);
        if (checkedObject < minValue.doubleValue() || checkedObject > maxValue.doubleValue()) {
            throw new SizeLimitedException("Значение аргумента не помещается в установленные пределы");
        }
    }
}

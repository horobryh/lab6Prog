package general.validators.baseValidators;

import general.validators.Verifiable;
import general.validators.exceptions.SizeLimitedException;
import general.validators.exceptions.ValidatorException;

/**
 * String length constraint validator class
 */
public class SizeString implements Verifiable {
    private final int minLength;
    private final int maxLength;

    public SizeString(int minLength, int maxLength) {
        this.minLength = minLength;
        this.maxLength = maxLength;
    }

    @Override
    public void checkCondition(String object) throws ValidatorException {
        if (object.length() < minLength || object.length() > maxLength) {
            throw new SizeLimitedException("Значение аргумента не помещается в установленные пределы");
        }
    }
}

package general.validators.baseValidators;

import general.validators.exceptions.ValidatorException;
import general.validators.Verifiable;

/**
 * Null value validator class
 */
public class NotNull implements Verifiable {
    @Override
    public void checkCondition(String object) throws ValidatorException {
        if (object == null) {
            throw new NullPointerException("Аргумент не может быть null");
        }
    }
}

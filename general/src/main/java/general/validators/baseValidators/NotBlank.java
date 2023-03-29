package general.validators.baseValidators;

import general.validators.exceptions.ValidatorException;
import general.validators.Verifiable;
import general.validators.exceptions.BlankPointerException;

/**
 * Empty string validator class
 */
public class NotBlank implements Verifiable {
    @Override
    public void checkCondition(String object) throws ValidatorException {
        if (object.isBlank()) {
            throw new BlankPointerException("Аргумент не может быть пустым.");
        }
    }
}

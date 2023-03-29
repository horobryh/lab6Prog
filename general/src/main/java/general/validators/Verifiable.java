package general.validators;

import general.validators.exceptions.ValidatorException;

/**
 * Label interface of objects that are validators
 */
public interface Verifiable {
    void checkCondition(String object) throws ValidatorException;
}

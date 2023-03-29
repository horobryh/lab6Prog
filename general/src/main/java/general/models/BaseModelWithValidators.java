package general.models;

import general.validators.Verifiable;
import general.validators.exceptions.ValidatorException;

import java.util.HashMap;
import java.util.List;

/**
 * An abstract class of a base model that has field validation
 */
public abstract class BaseModelWithValidators {
    static HashMap<String, List<Verifiable>> validators = new HashMap<>();
    public static boolean checkAllConditionsByKey(String object, String key) {
        for (Verifiable validator : getValidatorsByKey(key)) {
            try {
                validator.checkCondition(object);
            } catch (ValidatorException e){
                System.out.println(e.getMessage());
                return false;
            };
        };
        return true;
    };

    public static List<Verifiable> getValidatorsByKey(String key) {
        return validators.get(key);
    }

    public abstract void setValidators();

    public BaseModelWithValidators () {
        setValidators();
    }

    {
        setValidators();
    }

}

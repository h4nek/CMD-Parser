package options;

import java.util.Arrays;
import java.util.List;

/**
 * An option accepting a {@code Boolean} argument. The representation
 * A no-parameter version is supported - {@code true} if the option is present, {@code false} otherwise.
 * Enforcing no parameter can be achieved by providing empty lists of true/false representations.
 */
public class BooleanOption extends Option<Boolean> {
    List<String> trueRepresentations;
    List<String> falseRepresentations;

    public BooleanOption(List<String> aliases, String description, boolean mandatory) {
        super(aliases, description, mandatory);
        trueRepresentations = Arrays.asList("yes", "true", "1", "on");
        falseRepresentations = Arrays.asList("no", "false", "0", "off");
        argument = false;
    }
    
    public BooleanOption(List<String> aliases, String description, boolean mandatory, Boolean defaultValue) {
        super(aliases, description, mandatory, defaultValue);
        trueRepresentations = Arrays.asList("yes", "true", "1", "on");
        falseRepresentations = Arrays.asList("no", "false", "0", "off");
        if (defaultValue == null) { // works the same as if no defaultValue was supplied
            argument = false;
        }
    }

    public void setTrueRepresentations(List<String> trueRepresentations) {
        if (trueRepresentations == null)
            throw new IllegalArgumentException("The argument can't be null.");
        this.trueRepresentations = trueRepresentations;
    }

    public void setFalseRepresentations(List<String> falseRepresentations) {
        if (falseRepresentations == null)
            throw new IllegalArgumentException("The argument can't be null.");
        this.falseRepresentations = falseRepresentations;
    }

    @Override
    public Boolean parse(String parameter) {
        if (trueRepresentations.contains(parameter)) {
            return true;
        }
        else if (falseRepresentations.contains(parameter)) {
            return false;
        }
        else if (parameter != null) {
            invalidArgument();
        }
        return true;    // a call without an argument
    }
}

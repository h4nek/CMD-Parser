package options;

import java.util.List;

/**
 * Represents a generic option.
 * @param <T> the type of the option (what type of arguments it accepts)
 */
public abstract class Option<T> {
    List<String> aliases;
    String description;
    boolean mandatory;
    T defaultValue;

    Option(List<String> aliases, String description, boolean mandatory, T defaultValue) {
        this.aliases = aliases;
        this.description = description;
        this.mandatory = mandatory;
        this.defaultValue = defaultValue;
    }

    public List<String> getAliases() {
        return aliases;
    }

    public String getDescription() {
        return description;
    }

    public boolean isMandatory() {
        return mandatory;
    }

    public T getDefaultValue() {
        return defaultValue;
    }

    public abstract T parse();
}

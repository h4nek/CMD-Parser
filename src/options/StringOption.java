package options;

import java.util.List;

/**
 * An option accepting a {@code String} argument.
 */
public class StringOption extends Option<String> {

    public StringOption(List<String> aliases, String description, boolean mandatory) {
        super(aliases, description, mandatory);
    }
    
    public StringOption(List<String> aliases, String description, boolean mandatory, String defaultValue) {
        super(aliases, description, mandatory, defaultValue);
    }

    @Override
    public String parse(String parameter) {
        return parameter;
    }

}

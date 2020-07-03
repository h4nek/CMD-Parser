package main.options;

import java.util.List;

/**
 * An option accepting a {@code String} argument.
 */
public class StringOption extends Option<String> {

    public StringOption(List<String> aliases, String description, boolean mandatory) {
        super(aliases, description, mandatory);
    }

    @Override
    protected String parse(String parameter) {
        return parameter;
    }

}

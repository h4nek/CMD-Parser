package options;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Prints the list of all the defined options (represented by aliases) and their descriptions.
 * (The {@link StringOption} superclass was chosen for its simplicity, otherwise it doesn't matter.)
 */
public class HelpOption extends StringOption {
    /**
     * Store all available options.
     */
    Collection<Option<?>> options;
    
    private HelpOption(List<String> aliases, String description, boolean mandatory, Collection<Option<?>> options) {
        super(aliases, description, mandatory);
        this.options = options;
        setParameterOptional(true);
    }

    /**
     * The default help implementation.
     * @param options a collection of all available options, used for printing
     */
    public HelpOption(Collection<Option<?>> options) {
        this(Arrays.asList("-h", "--help"), "Displays all the present " + "options and their descriptions.", 
                false, options);
    }

    @Override
    public void parse(String parameter) {
        if (parameter != null) {
            throw new IllegalArgumentException("The help option doesn't accept a parameter!");
        }
        for (Option<?> option : options) {
            System.out.println(option.toString() + "\t" + option.getDescription());
        }
    }
}

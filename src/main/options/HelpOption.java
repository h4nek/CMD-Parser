package main.options;

import java.util.*;

/**
 * Prints the list of all the defined options (represented by aliases) and their descriptions.
 * (The {@link StringOption} superclass was chosen for its simplicity, otherwise it doesn't matter.)
 */
public class HelpOption extends StringOption {
    /**
     * Store all available options.
     */
    private final Collection<Option<?>> options;
    
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
        this(Arrays.asList("-h", "--help"), "Displays all existing options and their descriptions.", 
                false, options);
    }

    @Override
    protected String parse(String parameter) {
        if (parameter != null) {
            throw new IllegalArgumentException("The help option doesn't accept a parameter!");
        }
        
        Set<Option<?>> optionSet = new HashSet<>(options);  // get a sub collection of distinct options
        for (Option<?> option : optionSet) {
            System.out.println(String.join(", ", option.getAliases()) + "\t" + option.getDescription());
        }
        System.exit(0); // we don't need to parse anything else
        return null;
    }
}

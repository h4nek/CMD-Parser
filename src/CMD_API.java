import options.HelpOption;
import options.Option;

import java.util.*;

/**
 * The API for command line options/arguments parsing.
 * The predefined Option types are Integer, String, Boolean, Enum. A custom option type can be created by extending the 
 * Option class.
 * <br><br>
 * A reserved option -> help (-h, --help) is defined by default and prints the list of all defined options.
 *  We can overwrite it by supplying a different option that uses the same aliases to {@link #addOption}.
 *  To modify it, we would typically create and pass a different instance of the {@link HelpOption} class, or instance 
 *  of its subclass.
 */
public class CMD_API {
    /** A collection of all defined options, using their aliases as keys. */
    private final HashMap<String, Option<?>> options;
    private static CMD_API cmdApi;
    private final List<Option<?>> parsedOptions;

    private CMD_API() {
        options = new HashMap<>();
        parsedOptions = new ArrayList<>();
        
        // a reserved option, modifiable by overwriting
        HelpOption help = new HelpOption(options.values());
        addOption(help);
    }

    /**
     * Return the CMD_API object. (Realizing the Singleton pattern.)
     */
    public static CMD_API getInstance() {
        if (cmdApi == null) {
            cmdApi = new CMD_API();
        }
        return cmdApi;
    }
    
    /**
     * Add a new option.
     * If an option with the same alias as an already existing option is added, that existing option is overridden! 
     * (e.g. help option)
     * @param option an Option object to be added.
     */
    public <T> void addOption(Option<T> option) {
        if (option == null) // do nothing if null is given
            return;
        for (String alias : option.getAliases()) {
            options.put(alias, option); // put the same option under all of its aliases
        }
    }
    
    public <T> void removeOption(String alias) {
        //TODO?? search get aliases, remove all instances...
    }

    /**
     * Start the CMD option-parsing app.
     */
    public void startApp() {
        Scanner sc = new Scanner(System.in);
        Option<?> option = null;  // stores the last read option; nullified if the last read token was an argument
        while (sc.hasNext()) {  // process all CMD options and their arguments
            String token = sc.next();   // we expect an option or an argument to the last option
            
            if (option == null) {   // we expect an option now
                option = getOption(token);
            }
            else {
                if (token.startsWith("-")) {    // the last read option had no argument
                    option.evaluate(null);
                    option = getOption(token);  // get the currently read option
                }
                else {  // the current token is an argument to the stored option
                    option.evaluate(token);
                    option = null;  // nullify the option variable to signify that the next token has to be an Option
                }
            }
        }
        // check the mandatory property compliance
        for (Option<?> opt : options.values()) {
            if (opt.isMandatory() && !parsedOptions.contains(opt)) {
                throw new IllegalArgumentException("A mandatory option (" + String.join(", ", opt.getAliases())
                 + ") was not present in the CMD input.");
            }
        }
    }

    /**
     * Get the Option using the token, and raise any relevant exception.
     * @param token the token holding the option string (one of its aliases)
     * @return the associated Option
     */
    private Option<?> getOption(String token) {
        if (!token.startsWith("-")) {   // not a valid option alias
            throw new IllegalArgumentException("Expected a long option (preceded by \"--\") or short option (preceded" +
                    " by \"-\"), but " + token + " was given.");
        }
        Option<?> option = options.get(token);
        if (option == null) {
            throw new IllegalArgumentException("The option \"" + token + "\" is not defined!");
        }
        else {  // a valid option has been parsed
            parsedOptions.add(option);
        }
        return option;
    }
}

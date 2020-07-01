import options.HelpOption;
import options.Option;
import options.StringOption;

import java.util.*;
import java.util.function.Function;

/**
 * The API for command line options/arguments parsing.
 * The predefined Option types are Integer, String, Boolean, Enum and Custom (useful for defining any other data type).
 * <br><br>
 * A reserved option -> help (-h, --help) is defined by default and prints the list of all defined options.
 *  We can overwrite it by supplying a different option that uses the same aliases to {@link #addOption}.
 *  To modify it, we would typically create and pass a different instance of the {@link HelpOption} class, or instance 
 *  of its subclass.
 */
public class CMD_API {
    /** A collection of all defined options, using their aliases as keys. */
    private HashMap<String, Option> options;
    private CMD_API cmdApi;
    private List<Option> parsedOptions;

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
    public CMD_API getCMD_API() {
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
        boolean lastWasOption = false;  // if the previously read token was an option (an option argument might be valid)
        Option option = null;  // stores the last read option
        while (sc.hasNext()) {
            String token = sc.next();   // we expect an option or an argument to the last option
            
            if (option == null) {   // we expect an option now
                String optionString = optionExtract(token);
                option = options.get(optionString);
                if (option == null) {
                    throw new IllegalArgumentException("The option \"" + optionString + "\" is not defined!");
                }
            }
            
            while (token.startsWith("-")) {
                token = sc.next();
                if (token.startsWith("-")) {    // the previous option had no argument
                    option.evaluate(null);
                }
                else {  // the current token is an argument to the stored option
                    option.evaluate(token);
                }
            }
        }
    }

    /**
     * Extract the option string from the token, and raise any relevant exception.
     * @param token the token holding the option string
     * @return the option string (one of its aliases)
     */
    private String optionExtract(String token) {
        String option;
        if (token.startsWith("--")) {   // long option
            option = token.substring(2);
        }
        else if (token.startsWith("-")) {   // short option
            option = token.substring(1);
            if (option.length() > 1) {
                throw new IllegalArgumentException("A single character was expected for the short option, but " + 
                        option + " was given.");
            }
        }
        else {
            throw new IllegalArgumentException("Expected a long option (preceded by \"--\") or short option (preceded " +
                    "by \"-\"), but " + token + " was given.");
        }

        if (option.length() == 0) {
            throw new IllegalArgumentException("At least one character expected, but an empty option was given.");
        }
        
        return option;
    }
}

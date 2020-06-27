import options.Option;

import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

/**
 * The API for command line options/arguments parsing.
 * The predefined Option types are Integer, String, Boolean, Enum and Custom (useful for defining any other data type).
 */
public class CMD_API {
    /** A collection of all options, using their aliases as keys. */
    HashMap<String, Option> options;

    /**
     * Add a new option.
     * @param option an Option object to be added.
     */
    public <T> void addOption(Option<T> option) {
        for (String alias : option.getAliases()) {
            options.put(alias, option); // put the same option under all of its aliases
        }
    }

    /**
     * Start the CMD option-parsing app.
     */
    public void startApp() {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            String option = sc.next();
        }
    }

    /**
     * Parse (evaluate) a single CMD option.
     * @param option an option to parse
     * @param <T> option type
     * @return
     */
    private <T> T parseOption(Option<T> option) {
        return option.parse();
    }
}

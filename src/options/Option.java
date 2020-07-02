package options;

import java.util.List;

/**
 * Represents a generic option.
 * @param <T> the type of the option (what type of arguments it accepts)
 */
public abstract class Option<T> {
    /** Each alias represents the option in command line. It starts with "-" (short, single-character option) or "--" 
     * (long option, with one or more characters).*/
    List<String> aliases;
    /** A text description of the option. Used when showing help. */
    String description;
    /** Signifies if the option is mandatory - has to be part of every CMD input. Applicable only for parametric options. */
    boolean mandatory;
    /** Signifies if the option doesn't require a parameter. In such case it should have a {@code #parse} method that 
     * works with its argument being {@code null}.
     * If a parameter is to be <i>forbidden</i>, one can define this by throwing an exception upon receiving one..*/
    boolean parameterOptional;
    /** The parsed option parameter, stored after processing the CMD input (using {@code CMD_API.startApp()}). Can be 
     * retrieved using {@link #getArgument} and further worked with. */
    T argument;

    /**
     * Define a CMD option.
     * @param aliases option aliases (names), prefixed with - (short option) or -- (long option)
     * @param description an option description
     * @param mandatory if the option has to be part of every CMD input; applicable only for options with a parameter
     */
    public Option(List<String> aliases, String description, boolean mandatory) {
        this.aliases = aliases;
        this.description = description;
        this.mandatory = mandatory;
        argumentsCheck();
    }

    /**
     * Accepts a default value. See {@link #Option(List, String, boolean)}.
     * @param defaultValue default value of the option parameter, used when the option is not present in the CMD input.
     *                     applicable only for options with a parameter
     */
    public Option(List<String> aliases, String description, boolean mandatory, T defaultValue) {
        this(aliases, description, mandatory);
        if (!mandatory && !parameterOptional)
            this.argument = defaultValue;
    }

    /**
     * By default, the option requires a parameter. We can allow for it to receive no parameters using this method.
     * @param parameterOptional true if optional, false if required
     */
    public void setParameterOptional(boolean parameterOptional) {
        this.parameterOptional = parameterOptional;
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

    public boolean isParameterOptional() {
        return parameterOptional;
    }

    /**
     * @return the option argument (parameter). If the option was not present, return the default value.
     */
    public T getArgument() {
        return argument;
    }

    /**
     * Evaluate the option - execute any of its logic, check the parameter and parse it. Custom option logic should be 
     * implemented in {@link #parse(String)}.
     * @param parameter
     */
    public final void evaluate(String parameter) {
        if (parameter == null && !parameterOptional) {
            throw new IllegalArgumentException("The option " + this + " requires a parameter.");
        }
        parse(parameter);
        restrictionCheck();
    }
    
    /**
     * Parse the CMD option parameter, converting it to the option type.
     * @param parameter the option parameter to be parsed; null if no parameter is supplied (valid in the case of an 
     *                  optional parameter)
     */
    abstract void parse(String parameter);

    /**
     * Check if the current argument passes the defined restrictions. A violation of some restriction triggers an 
     * {@link IllegalArgumentException}. By default, no restrictions are assumed ({@link #restrictionsSatisfaction} has 
     * to be overridden).
     */
    private void restrictionCheck() {
        if (!restrictionsSatisfaction())
            throw new IllegalArgumentException("The argument " + argument + " doesn't conform to the option restrictions.");
    }

    /**
     * Evaluate the argument's conformity to all restrictions. No restrictions by default.
     * @return {@code true} if the argument passes all restrictions, {@code false} if a restriction is violated
     */
    boolean restrictionsSatisfaction() {
        return true;
    }

    /**
     * A convenience method to raise an exception in the case of an invalid option argument.
     */
    final void invalidArgument() {
        throw new IllegalArgumentException("The received argument for option " + this + " is invalid!");
    }

    private void argumentsCheck() {
        // check the booleans
        if (parameterOptional && mandatory) {
            throw new IllegalArgumentException("A mandatory option has to require a parameter.");
        }
        
        // check the aliases
        for (String alias : aliases) {
            if (alias.startsWith("--")) {
                if (alias.length() == 2) {  // empty string
                    throw new IllegalArgumentException("One of the (long option) aliases is an empty string.");
                }
            }
            else if (alias.startsWith("-")) {
                if (alias.length() != 2) {  // short option of wrong length
                    throw new IllegalArgumentException("A short option alias has be defined by exactly one character.\n\""
                            + alias + "\" was given instead.");
                }
            }
            else {  // option not starting with - or --
                throw new IllegalArgumentException("Every option alias has to start with - or --.\n\"" + alias + "\" " +
                        "was given instead.");
            }
        }
    }

    @Override
    public String toString() {
        return "(" + String.join(", ", aliases) + ")";
    }
}

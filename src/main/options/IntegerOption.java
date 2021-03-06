package main.options;

import java.util.List;

/**
 * An option accepting an {@code Integer} argument. A minimum and maximum accepted value of the argument can be defined.
 */
public class IntegerOption extends Option<Integer> {
    // used as restrictions
    private final Integer minValue;
    private final Integer maxValue;

    public IntegerOption(List<String> aliases, String description, boolean mandatory) {
        super(aliases, description, mandatory);
        minValue = Integer.MIN_VALUE;
        maxValue = Integer.MAX_VALUE;

    }

    /**
     * An {@link Option} with {@code Integer} argument and restrictions imposed on its value.
     * @param minValue minimum accepted argument value
     * @param maxValue maximum accepted argument value
     */
    public IntegerOption(List<String> aliases, String description, boolean mandatory, Integer minValue, Integer maxValue) {
        super(aliases, description, mandatory);
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    @Override
    protected Integer parse(String parameter) {
        return Integer.valueOf(parameter);
    }

    @Override
    protected boolean restrictionsSatisfaction() {
        // check if the argument lies within the bounds
        return argument >= minValue && argument <= maxValue;
    }

}

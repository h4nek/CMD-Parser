package main.options;

import java.util.List;

/**
 * An option accepting an {@code Enum} argument. Requires the concrete Enum type to correctly parse the parameter.
 */
public class EnumOption<E extends Enum<E>> extends Option<Enum<E>>{
    private final Class<E> enumType;

    public EnumOption(List<String> aliases, String description, boolean mandatory, Class<E> enumType) {
        super(aliases, description, mandatory);
        this.enumType = enumType;
    }

    @Override
    protected Enum<E> parse(String parameter) {
        return Enum.valueOf(enumType, parameter);
    }
}

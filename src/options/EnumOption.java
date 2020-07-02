package options;

import java.util.List;

/**
 * An option accepting an {@code Enum} argument. Requires the concrete Enum type to correctly parse the parameter.
 */
public class EnumOption<E extends Enum<E>> extends Option<Enum<E>>{
    Class<E> enumType;

    public EnumOption(List<String> aliases, String description, boolean mandatory, Class<E> enumType) {
        super(aliases, description, mandatory);
        this.enumType = enumType;
    }

    public EnumOption(List<String> aliases, String description, boolean mandatory, Enum<E> defaultValue, Class<E> enumType) {
        super(aliases, description, mandatory, defaultValue);
        this.enumType = enumType;
    }

    @Override
    Enum<E> parse(String parameter) {
//        this.getClass().getGenericSuperclass()
//        for (Enum<E> constant : argument.getClass().getEnumConstants()) {
//            if (constant.toString().equals(parameter)) {
//                argument = constant;
//            }
//        }
        return Enum.valueOf(enumType, parameter);
    }
}

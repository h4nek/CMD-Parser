import options.BooleanOption;
import options.EnumOption;
import options.IntegerOption;
import options.Option;

import java.util.Collections;

/**
 * An example console application demonstrating the usage of library.
 */
public class Calculator {
    public static void main(String[] args) {
        IntegerOption leftOperandOption = new IntegerOption(Collections.singletonList("-l"), "(left operand) " +
                "Integer, mandatory", true);
        IntegerOption rightOperandOption = new IntegerOption(Collections.singletonList("-r"), "(right operand) " +
                "Integer, mandatory", true);
        EnumOption<Operator> operatorOption = new EnumOption<Operator>(Collections.singletonList("-o"), "(operator)" +
                " Enum {PLUS, MINUS, MULTIPLY, DIVIDE}, mandatory", true, Operator.class);
        BooleanOption verboseOption = new BooleanOption(Collections.singletonList("-v"), "Boolean, no parameter. true – the " +
                "expression is printed (<-l><-o><-r>=<result>), false – only the result is printed", false);
        // prohibit parameters for the verbose option
        verboseOption.setParameterOptional(true);
        verboseOption.setTrueRepresentations(Collections.emptyList());
        verboseOption.setFalseRepresentations(Collections.emptyList());
        
        CMD_API cmdApi = CMD_API.getInstance();
        cmdApi.addOptions(leftOperandOption, rightOperandOption, operatorOption, verboseOption);
        
        cmdApi.startApp();
        
        int left = leftOperandOption.getArgument();
        int right = rightOperandOption.getArgument();
        Operator operator = (Operator) operatorOption.getArgument();
        int result = operator.apply(left, right);
        if (verboseOption.getArgument()) {
            System.out.println("" + left + operator + right + "=" + result);
        }
        else {
            System.out.println(result);
        }
    }
    
    enum Operator{
        PLUS("+") {
            int apply(int left, int right) {
                return left + right;
            }
        }, 
        MINUS("-") {
            int apply(int left, int right) {
                return left - right;
            }
        }, 
        MULTIPLY("*") {
            int apply(int left, int right) {
                return left * right;
            }
        }, 
        DIVIDE("/") {
            int apply(int left, int right) {
                int result;
                try {
                    result = left / right;
                }
                catch (ArithmeticException e) {
                    System.err.println("The right operator was 0 ... can't divide by zero.");
                }
                return left / right;
            }
        };

        String string;
        Operator(String stringRep) {
            this.string = stringRep;
        }

        abstract int apply(int left, int right);

        @Override
        public String toString() {
            return string;
        }
    }
}

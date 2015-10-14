package coffee;

import coffee.datatypes.Value;

import java.util.Map;
import java.util.TreeMap;

// token keyword, operator, digit, id(unique number),

/**
 * Holds all the variables with unique names.
 * All variable initializations and assignments should use {@code addVariable} method.
 * Created by ft on 10/2/15.
 */
public class IdentifierList {
    private static int mVarCount = 0;
    private static Map<String,Value> mVariableList = new TreeMap<String, Value>();

    /**
     * Initializes a variable with null value.
     * @param name variable's name
     * @return variable's name in variable list.
     */
    public String addVariable(String name) {
        String varName = mVarCount+"_"+name;
        ++mVarCount;
        mVariableList.put(varName,null);
        return varName;
    }

    /**
     * Initializes a variable with value {@code variable}
     * @param name variable name
     * @param value variable's value
     * @return variable's name in variable list.
     */
    public String addVariable(String name, Value value) {
        String varName = mVarCount+"_"+name;
        ++mVarCount;
        mVariableList.put(varName, value);
        return varName;
    }
}

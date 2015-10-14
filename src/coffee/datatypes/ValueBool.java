package coffee.datatypes;

/**
 * Created by ft on 10/2/15.
 */
public class ValueBool implements Value {
    private Boolean mValue = null;

    @Override
    public Type getType() {
        return Type.BOOLEAN;
    }

    @Override
    public Boolean getValue() {
        return mValue;
    }

    @Override
    public String getTokenName() {
        return "VALUE_BOOL";
    }

    @Override
    public Token.Type getTokenType() {
        return Token.Type.VALUE;
    }
}

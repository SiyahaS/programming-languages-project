package coffee.project;

import coffee.datatypes.Token;

/**
 * Created by SiyahaS on 12/25/2016.
 */
public interface State {
    State next(Input word);

    boolean isFinal();

    Token currentToken(Input word);
}

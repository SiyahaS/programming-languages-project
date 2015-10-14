package coffee;

import coffee.datatypes.Token;

import java.util.ArrayList;
import java.util.List;

/**
 * Singleton token list.
 * Created by ft on 10/8/15.
 */
public class TokenList {
    private List<Token> mCurrentLine = new ArrayList<Token>();
    private List< List<Token> > mTokens = new ArrayList< List<Token> >();

    private static final TokenList INSTANCE = new TokenList();

    private TokenList() {}

    public static TokenList getInstance(){
        return INSTANCE;
    }

    /**
     * Adds a token to the current line.
     * @param token
     */
    public void addToken(Token token) {
        mCurrentLine.add(token);
    }

    /**
     * Returns the tokens of the current line
     * @return
     */
    public List<Token> getCurrentTokens() {
        return mCurrentLine;
    }

    /**
     * Adds current line's tokens to the tokens list and
     * initializes a new token list for new line.
     */
    public void newLine() {
        mTokens.add(mCurrentLine);
        mCurrentLine = new ArrayList<Token>();
    }

    public List< List<Token> > getAllTokens() {
        return mTokens;
    }

}

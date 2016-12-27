package coffee.project;

        import coffee.IdentifierList;
        import coffee.REPL;
        import coffee.TokenList;
        import coffee.datatypes.Identifier;
        import coffee.datatypes.Token;

/**
 * Created by ft on 10/14/15.
 */
public class Lexer implements REPL.LineInputCallback {
    @Override
    public String lineInput(String line) throws REPL.InvalidSyntaxException {
        TokenList tokenList = TokenList.getInstance();
        IdentifierList identifierList = IdentifierList.getInstance();

        State current = States.EMPTY;
        Token token;
        Input input = new Input(line);

        while(current != States.END){
            if(current == States.FAIL)
                throw new REPL.InvalidSyntaxException();
            if(current.isFinal()){
                token = current.currentToken(input);
                if(token != null){
                    if(token.getTokenType() == Token.Type.IDENTIFIER)
                        identifierList.addIdentifier(((Identifier)token).getName());
                    tokenList.addToken(token);
                    //System.err.println(token);
                }
            }
            current = current.next(input);
        }

        return null;
    }
}
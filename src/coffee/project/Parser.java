package coffee.project;

import coffee.IdentifierList;
import coffee.TokenList;
import coffee.datatypes.Keyword;
import coffee.datatypes.Operator;
import coffee.datatypes.Token;
import coffee.syntax.Keywords;
import coffee.syntax.Operators;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Created by ftektas on 12/12/16.
 */
public class Parser {
    // Parses the lexer result and prints *ONLY* the parsing result.
    public void parse() {
        List<String> identifierList = IdentifierList.getInstance().getIdentifiers();
        List<Token> tokenList = TokenList.getInstance().getAllTokens();

        Stack<String> output = new Stack<String>();
        List<String> tokens = new ArrayList<String>();

        for (Token t : tokenList) {
            switch (t.getTokenType()) {
                case OPERATOR:
                    tokens.add(((Operator) t).getOperator());
                    break;
                case KEYWORD:
                    tokens.add(((Keyword) t).getKeyword());
                    break;
                default:
                    tokens.add(t.getTokenName());
                    break;
            }
        }

        while (!tokens.isEmpty()) {
            for (int i = 0; i < tokens.size(); ++i) {
                if (tokens.get(i).equals("IDENTIFIER") || tokens.get(i).equals("VALUE_INT")) {
                    tokens.set(i, "EXPI");
                    i = tokens.size();
                } else if (tokens.get(i).equals("EXPI")) {
                    if (i - 2 >= 0 && i + 2 <= tokens.size()) {
                        if (tokens.get(i - 2).equals(Operators.LEFT_PARENTHESIS)) {
                            if (tokens.get(i - 1).equals(Operators.PLUS) || tokens.get(i - 1).equals(Operators.MINUS) || tokens.get(i - 1).equals(Operators.SLASH) || tokens.get(i - 1).equals(Operators.ASTERISK)) {
                                if (tokens.get(i + 1).equals("EXPI") && tokens.get(i + 2).equals(Operators.RIGHT_PARENTHESIS)) {
                                    tokens.remove(i + 2);
                                    tokens.remove(i + 1);
                                    tokens.remove(i - 1);
                                    tokens.remove(i - 2);
                                    i = tokens.size();
                                }
                            } else if (tokens.get(i - 1).equals(Keywords.EQUAL) && tokens.get(i + 1).equals("EXPI") && tokens.get(i + 2).equals(Operators.RIGHT_PARENTHESIS)) {
                                tokens.set(i, "EXPB");
                                tokens.remove(i + 2);
                                tokens.remove(i + 1);
                                tokens.remove(i - 1);
                                tokens.remove(i - 2);
                                i = tokens.size();
                            }
                        }
                    } else {
                        tokens.set(i, "INPUT");
                        i = tokens.size();
                    }
                } else if (tokens.get(i).equals("INPUT")) {
                    i = tokens.size();
                    tokens.remove(0);
                } else if (tokens.get(i).equals("VALUE_BINARY")) {
                    tokens.set(i, "EXPB");
                    i = tokens.size();
                } else if (tokens.get(i).equals("EXPB")) {
                    if (i - 2 >= 0 && i + 2 <= tokens.size()) {
                        if (tokens.get(i - 2).equals(Operators.LEFT_PARENTHESIS)) {
                            if (tokens.get(i - 1).equals(Keywords.AND) || tokens.get(i - 1).equals(Keywords.OR) || tokens.get(i - 1).equals(Keywords.NOT) || tokens.get(i - 1).equals(Keywords.EQUAL)) {
                                if (tokens.get(i + 1).equals("EXPB") && tokens.get(i + 2).equals(Operators.RIGHT_PARENTHESIS)) {
                                    tokens.remove(i + 2);
                                    tokens.remove(i + 1);
                                    tokens.remove(i - 1);
                                    tokens.remove(i - 2);
                                    i = tokens.size();
                                }
                            }
                        }
                    } else {
                        tokens.set(i, "INPUT");
                        i = tokens.size();
                    }
                }
            }

            StringBuilder sb = new StringBuilder();
            for (String s : tokens) {
                sb.append(s);
                sb.append(' ');
            }
            output.add(sb.toString());
        }
        output.pop();
        System.out.println("START -> " + output.pop());
        while (!output.empty()) {
            System.out.println("      -> " + output.pop());
        }
    }
}
package coffee.project;

import coffee.datatypes.*;
import coffee.syntax.Keywords;
import coffee.syntax.Operators;

/**
 * Created by SiyahaS on 12/25/2016.
 */
public enum States implements State {
    EMPTY {
        @Override
        public State next(Input word) {
            char c = word.read();
            String cs = Character.toString(c);
            if (c >= 'a' && c <= 'z') {
                word.consume();
                return States.KEYDENTIFIER;
            } else if (c >= 'A' && c <= 'Z') {
                word.consume();
                return States.KEYDENTIFIER;
            } else if (c >= '0' && c <= '9') {
                word.consume();
                return States.NUMBER;
            } else if (c == '\'') {
                word.consume();
                return States.APOSTROPHE;
            } else if (Character.isWhitespace(c)) {
                word.skip();
                return EMPTY;
            } else if (c == '\0') {
                return States.END;
            } else if (cs.equals(Operators.PLUS) || cs.equals(Operators.MINUS) || cs.equals(Operators.SLASH) || cs.equals(Operators.ASTERISK) || cs.equals(Operators.LEFT_PARENTHESIS) || cs.equals(Operators.RIGHT_PARENTHESIS)) {
                word.consume();
                return States.OPERATOR;
            }
            return States.FAIL;
        }

        @Override
        public boolean isFinal() {
            return false;
        }

        @Override
        public Token currentToken(Input word) {
            return null;
        }


    }, KEYDENTIFIER {
        @Override
        public State next(Input word) {
            char c = word.read();
            String cs = Character.toString(c);
            if (c >= 'a' && c <= 'z') {
                word.consume();
                return States.KEYDENTIFIER;
            } else if (c >= 'A' && c <= 'Z') {
                word.consume();
                return States.KEYDENTIFIER;
            } else if (c == '\'' || Character.isWhitespace(c) || c == '\0' || cs.equals(Operators.PLUS) || cs.equals(Operators.MINUS) || cs.equals(Operators.SLASH) || cs.equals(Operators.ASTERISK) || cs.equals(Operators.LEFT_PARENTHESIS) || cs.equals(Operators.RIGHT_PARENTHESIS)) {
                return States.KEYDENTIFIER_FINAL;
            }
            return States.FAIL;
        }

        @Override
        public boolean isFinal() {
            return false;
        }

        @Override
        public Token currentToken(Input word) {
            return null;
        }
    }, KEYDENTIFIER_FINAL {
        @Override
        public State next(Input word) {
            word.ready();
            char c = word.read();
            String cs = Character.toString(c);
            if (c == '\'') {
                word.consume();
                return States.APOSTROPHE;
            } else if (Character.isWhitespace(c)) {
                word.skip();
                return States.EMPTY;
            } else if (c == '\0') {
                return States.END;
            } else if (cs.equals(Operators.PLUS) || cs.equals(Operators.MINUS) || cs.equals(Operators.SLASH) || cs.equals(Operators.ASTERISK) || cs.equals(Operators.LEFT_PARENTHESIS) || cs.equals(Operators.RIGHT_PARENTHESIS)) {
                word.consume();
                return States.OPERATOR;
            }
            return States.FAIL;
        }

        @Override
        public boolean isFinal() {
            return true;
        }

        @Override
        public Token currentToken(Input word) {
            String accumulated = word.getValue();

            if (accumulated.equals(Keywords.AND)) return new Keyword(Keywords.AND);
            else if (accumulated.equalsIgnoreCase(Keywords.OR)) return new Keyword(Keywords.OR);
            else if (accumulated.equalsIgnoreCase(Keywords.NOT)) return new Keyword(Keywords.NOT);
            else if (accumulated.equalsIgnoreCase(Keywords.EQUAL)) return new Keyword(Keywords.EQUAL);
            else if (accumulated.equalsIgnoreCase(Keywords.APPEND)) return new Keyword(Keywords.APPEND);
            else if (accumulated.equalsIgnoreCase(Keywords.CONCAT)) return new Keyword(Keywords.CONCAT);
            else if (accumulated.equalsIgnoreCase(Keywords.SET)) return new Keyword(Keywords.SET);
            else if (accumulated.equalsIgnoreCase(Keywords.DEFFUN)) return new Keyword(Keywords.DEFFUN);
            else if (accumulated.equalsIgnoreCase(Keywords.FOR)) return new Keyword(Keywords.FOR);
            else if (accumulated.equalsIgnoreCase(Keywords.WHILE)) return new Keyword(Keywords.WHILE);
            else if (accumulated.equalsIgnoreCase(Keywords.IF)) return new Keyword(Keywords.IF);
            else if (accumulated.equalsIgnoreCase(Keywords.THEN)) return new Keyword(Keywords.THEN);
            else if (accumulated.equalsIgnoreCase(Keywords.ELSE)) return new Keyword(Keywords.ELSE);
            else if (accumulated.equalsIgnoreCase(Keywords.TRUE)) return new ValueBinary(true);
            else if (accumulated.equalsIgnoreCase(Keywords.FALSE)) return new ValueBinary(false);

            return new Identifier(accumulated);
        }
    }, OPERATOR {
        @Override
        public State next(Input word) {
            word.ready();
            char c = word.read();
            String cs = Character.toString(c);
            if (c >= 'a' && c <= 'z') {
                word.consume();
                return States.KEYDENTIFIER;
            } else if (c >= 'A' && c <= 'Z') {
                word.consume();
                return States.KEYDENTIFIER;
            } else if (c >= '0' && c <= '9') {
                word.consume();
                return States.NUMBER;
            } else if (c == '\'') {
                word.consume();
                return States.APOSTROPHE;
            } else if (Character.isWhitespace(c)) {
                word.skip();
                return States.EMPTY;
            } else if (c == '\0') {
                return States.END;
            } else if (cs.equals(Operators.PLUS) || cs.equals(Operators.MINUS) || cs.equals(Operators.SLASH) || cs.equals(Operators.ASTERISK) || cs.equals(Operators.LEFT_PARENTHESIS) || cs.equals(Operators.RIGHT_PARENTHESIS)) {
                word.consume();
                return States.OPERATOR;
            }
            return States.FAIL;
        }

        @Override
        public boolean isFinal() {
            return true;
        }

        @Override
        public Token currentToken(Input word) {
            String accumulated = word.getValue();
            if (accumulated.equals(Operators.PLUS)) return new Operator(Operators.PLUS);
            else if (accumulated.equals(Operators.MINUS)) return new Operator(Operators.MINUS);
            else if (accumulated.equals(Operators.SLASH)) return new Operator(Operators.SLASH);
            else if (accumulated.equals(Operators.ASTERISK)) return new Operator(Operators.ASTERISK);
            else if (accumulated.equals(Operators.LEFT_PARENTHESIS)) return new Operator(Operators.LEFT_PARENTHESIS);
            else if (accumulated.equals(Operators.RIGHT_PARENTHESIS)) return new Operator(Operators.RIGHT_PARENTHESIS);
            else if (accumulated.equals(Operators.CONS)) return new Operator(Operators.CONS);

            return null;
        }
    }, APOSTROPHE {
        @Override
        public State next(Input word) {
            char c = word.read();
            if (c == '(') {
                word.consume();
                return States.OPERATOR;
            }
            return States.FAIL;
        }

        @Override
        public boolean isFinal() {
            return false;
        }

        @Override
        public Token currentToken(Input word) {
            return null;
        }
    }, NUMBER {
        @Override
        public State next(Input word) {
            char c = word.read();
            String cs = Character.toString(c);
            if (c >= '0' && c <= '9') {
                word.consume();
                return States.NUMBER;
            } else if (c == '\'' || c == '\0' || Character.isWhitespace(c) || cs.equals(Operators.PLUS) || cs.equals(Operators.MINUS) || cs.equals(Operators.SLASH) || cs.equals(Operators.ASTERISK) || cs.equals(Operators.LEFT_PARENTHESIS) || cs.equals(Operators.RIGHT_PARENTHESIS)) {
                return States.NUMBER_FINAL;
            }

            return States.FAIL;
        }

        @Override
        public boolean isFinal() {
            return false;
        }

        @Override
        public Token currentToken(Input word) {
            return null;
        }
    }, NUMBER_FINAL {
        @Override
        public State next(Input word) {
            word.ready();
            char c = word.read();
            String cs = Character.toString(c);
           if (c == '\'') {
                word.consume();
                return States.APOSTROPHE;
            } else if (Character.isWhitespace(c)) {
                word.skip();
                return States.EMPTY;
            } else if (c == '\0') {
                return States.END;
            } else if (cs.equals(Operators.PLUS) || cs.equals(Operators.MINUS) || cs.equals(Operators.SLASH) || cs.equals(Operators.ASTERISK) || cs.equals(Operators.LEFT_PARENTHESIS) || cs.equals(Operators.RIGHT_PARENTHESIS)) {
                word.consume();
                return States.OPERATOR;
            }
            return States.FAIL;
        }

        @Override
        public boolean isFinal() {
            return true;
        }

        @Override
        public Token currentToken(Input word) {
            return new ValueInt(Integer.parseInt(word.getValue()));
        }
    }, FAIL {
        @Override
        public State next(Input word) {
            return FAIL;
        }

        @Override
        public boolean isFinal() {
            return true;
        }

        @Override
        public Token currentToken(Input word) {
            return null;
        }
    }, END {
        @Override
        public State next(Input word) {
            return States.END;
        }

        @Override
        public boolean isFinal() {
            return true;
        }

        @Override
        public Token currentToken(Input word) {
            return null;
        }
    };

    public abstract State next(Input word);

    public abstract boolean isFinal();

    public abstract Token currentToken(Input word);
}

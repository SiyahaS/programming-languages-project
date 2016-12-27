package coffee.project;

/**
 * Created by SiyahaS on 12/25/2016.
 */
public class Input {
    private char[] input;
    private int old;
    private int current;

    public Input(String input) {
        this.input = input.toCharArray();
        this.old = 0;
        this.current = 0;
    }

    public char read() {
        if (current < input.length)
            return input[current];
        return '\0';
    }

    public void consume() {
        ++current;
    }

    public void skip() {
        old = ++current;
    }

    public String getValue() {
        return new String(input, old, current - old);
    }

    public void ready(){
        old = current;
    }
}

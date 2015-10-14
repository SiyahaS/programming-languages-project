package coffee;

/**
 * Created by ft on 10/2/15.
 */
public class Main {

    public static void main(String[] args) {
        if(args.length != 0) { // file input provided

        }
        REPL.repl(new REPL.LineInputCallback() {
            @Override
            public String lineInput(String line) {
                String[] tokens = line.split(" ");
                StringBuilder builder = new StringBuilder();
                for(String token : tokens) {
                    builder.append("|"+token+"|\t");
                }
                return builder.toString();
            }
        });
    }


}

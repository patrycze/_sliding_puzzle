import sac.State;
import sac.StateFunction;
import static jdk.nashorn.internal.runtime.JSType.toInteger;

public class HFunctionManhattan extends StateFunction {

  @Override
    public double calculate(State state){
        Puzzle P1 = (Puzzle) state;
        double h = 0.0;
        for (int i = 0; i < P1.n; i++) {
            for (int j = 0; j < P1.n; j++) {
                byte w = P1.board[i][j];
                if(w == 0)
                    continue;
                int x = w % P1.n;
                int y = w / P1.n;
                h += Math.abs(x-i) + Math.abs(y-j);
            }
        }
        //System.out.println(h);
        return h;
    }

    protected int manhattan(Puzzle P1, int index) {
        int n = Puzzle.n;
        int i1 = P1.board[index] / n;
        int j1 = P1.board[index] % n;
        int i2 = index / n;
        int j2 = index % n;
        return Math.abs(i1 - i2) + Math.abs(j1 - j2);
    }
}

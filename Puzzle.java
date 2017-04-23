    /**
 * Created by Pazura on 23.04.2017.
 */

import sac.graph.AStar;
import sac.graph.GraphState;
import sac.graph.GraphStateImpl;

import java.util.List;

    public class Puzzle extends GraphStateImpl {


    public static final int n = 3;
    public static final int n2 = n * n;
    public byte[][] board = null;

    public Puzzle() {
        this.board = new byte[3][3];
        byte k = 0;
        for(int i = 0; i < n; ++i) {
            for(int j = 0; j < n; ++j) {
                this.board[i][j] = ++k;
            }
        }
    }

    public String toString() {
        String result = new String();
        for(int i = 0; i < n; ++i) {
            for(int j = 0; j < n; ++j) {
                result = result + this.board[i][j] + " ";
            }
            result = result + "\n";
        }
        return result;
    }

        public static void main(String[] arg)
        {
            Puzzle P1 = new Puzzle();
            System.out.println(P1.toString());
        }


    @Override
    public List<GraphState> generateChildren() {
        return null;
    }

    @Override
    public boolean isSolution() {
        return false;
    }
}


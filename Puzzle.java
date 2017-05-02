import sac.graph.*;

import java.util.*;

class Point
{
    byte x;
    byte y;
    public Point()
    {
        x = 0;
        y = 0;
    }
}

enum Ruch
{
    UP,
    DOWN,
    RIGHT,
    LEFT
}

public class Puzzle extends GraphStateImpl {

    public static final int n = 3;
    public static final int n2 = n * n;
    public byte[][] board = null;
    private Point zero = null;  //EMPTY POINT LOCATION
    private int Pojnt = 0;
    public Puzzle() {
        zero = new Point();
        this.board = new byte[3][3];
        byte k = 0;
        for(int i = 0; i < n; ++i) {
            for(int j = 0; j < n; ++j) {
                this.board[i][j] = k++;
            }
        }
    }

    public Puzzle(Puzzle parent)
    {
        //konstruktor kopiujacy
        this.board = new byte[3][3];
        this.zero = new Point();
        this.zero.x = parent.zero.x;
        this.zero.y = parent.zero.y;

        for(int i = 0; i < n; ++i)
            for(int j = 0; j < n; ++j)
                this.board[i][j] = parent.board[i][j];
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
        String PuzzleAsString = "125487306";
        Puzzle P1 = new Puzzle();
        P1.FromString(PuzzleAsString);
        System.out.println(P1.toString());
        P1.SearchZero();
        System.out.print(P1.zero.x);
        System.out.println(P1.zero.y);
        P1.SearchZero();
        GraphSearchAlgorithm algorithm = new AStar(P1);
        algorithm.execute();
        Puzzle solution = (Puzzle)algorithm.getSolutions().get(0);

        System.out.println( "SOLUTION: \n" + solution);
        System.out.println( "PATH LENGTH:" + solution.getPath().size());

    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(board);
    }

    public void FromString(String txt) {

        int k = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = Byte.valueOf(txt.substring(k, k + 1));
                k++;
            }

        }
    }

    public void ZeroPosition()
    {
        List<Byte> Position = new ArrayList<Byte>();
        Position.add((zero.x));
        Position.add(zero.y);
        System.out.println(Position);

    }
    public void move(int r)
    {
        //To samo co mow, ale podajemy kierunek int'em
        switch(r)
        {
            case 0:		//RIGHT
                if(zero.y != n-1)
                {
                    byte temp = board[zero.x][zero.y];
                    board[zero.x][zero.y] = board[zero.x][zero.y+1];
                    board[zero.x][zero.y+1] = temp;
                    zero.y++;
                    ZeroPosition();
                }
                break;
            case 1:		//LEFT
                if(zero.y != 0)
                {
                    byte temp = board[zero.x][zero.y];
                    board[zero.x][zero.y] = board[zero.x][zero.y-1];
                    board[zero.x][zero.y-1] = temp;
                    zero.y--;
                    ZeroPosition();
                }
                break;
            case 2:		//DOWN
                if(zero.x != n-1)
                {
                    byte temp = board[zero.x][zero.y];
                    board[zero.x][zero.y] = board[zero.x+1][zero.y];
                    board[zero.x+1][zero.y] = temp;
                    zero.x++;
                    ZeroPosition();
                }
                break;
            case 3:		//UP
                if(zero.x != 0)
                {
                    byte temp = board[zero.x][zero.y];
                    board[zero.x][zero.y] = board[zero.x-1][zero.y];
                    board[zero.x-1][zero.y] = temp;
                    zero.x--;
                    ZeroPosition();
                }
                break;
        }
    }

    private boolean CanMove(int r)
    {
        //zwraca true jesli mozliwy jest ruch
        //w zadanym kierunku

        switch(r)
        {
            case 0:		//RIGHT
                if(zero.y != n-1)
                    return true;
                break;
            case 1:		//LEFT
                if(zero.y != 0)
                    return true;
                break;
            case 2:		//DOWN
                if(zero.x != n-1)
                    return true;
                break;
            case 3:		//UP
                if(zero.x != 0)
                    return true;
                break;
        }
        return false;
    }

    public void MixUp(int h_many)
    {
        //losowanie h_many ruchow
        Random generator = new Random();

        for(int i=0; i<h_many; i++)
        {
            move(generator.nextInt(4));
        }
    }
    public void SearchZero()
    {
      for(int i=0;i<3;i++)
          for(int j=0;j<3;j++)
              if(board[i][j] == 0) {
                  zero.x = (byte)i;
                  zero.y = (byte)j;
              }
    }
    private void makeMove(byte x, byte y)
    {
        int k = 0;
        board[x][y] = board[zero.x][zero.y];
        board[zero.x][zero.y] = (byte)k;
    }

    /*public LinkedList<Byte> getPossibleMoves() {

    }*/
    //@Override
    public LinkedList<GraphState> generateChildren()
    {
        //generujemy 4 potomkow powstalych w wyniku
        //przesuniec w kazdym mozliwym kierunku
        //
        //jesli potomek nie moze wykonac przesuniecia
        //nie dodajemy go do listy potomkow

        LinkedList<GraphState> children = new LinkedList<GraphState>();

        for(int i=0;i<4;i++)	//4 to liczba kierunkow
        {
            Puzzle child = new Puzzle(this);
            //child.SearchZero();
            byte k = 1;
            if (child.CanMove(i))
            {

                if(i == 0) {
                    child.zero.x = (byte)(zero.x);
                    child.zero.y = (byte)(zero.y+k);
                    child.makeMove(zero.x,zero.y);
                }
                if(i == 1) {
                    child.zero.x = (byte)(zero.x);
                    child.zero.y = (byte)(zero.y-k);
                    child.makeMove(zero.x,zero.y);
                }
                if(i == 2) {
                    child.zero.x = (byte)(zero.x+k);
                    child.zero.y = (byte)(zero.y);
                    child.makeMove(zero.x,zero.y);
                }
                if(i == 3) {
                    child.zero.x = (byte)(zero.x-k);
                    child.zero.y = (byte)(zero.y);
                    child.makeMove(zero.x,zero.y);
                }
                children.add(child);
            }
        }
        return children;
    }

    //@Override
    public boolean isSolution()
    {
        //sprawdzam czy wartosci board sa poukladane rosnaco
        int k=0;
        for (int i=0; i<n; i++)
            for (int j=0; j<n; j++)
                if (k++ != board[i][j])
                    return false;
        return true;
    }
}


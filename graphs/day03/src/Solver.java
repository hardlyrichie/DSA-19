/**
 * Solver definition for the 8 Puzzle challenge
 * Construct a tree of board states using A* to find a path to the goal
 */

import java.util.*;

public class Solver {

    public int minMoves = -1;
    private State solutionState;
    private boolean solved = false;
    private Board initial;

    /**
     * State class to make the cost calculations simple
     * This class holds a board state and all of its attributes
     */
    private class State implements Comparable<State>{
        // Each state needs to keep track of its cost and the previous state
        private Board board;
        private int moves; // equal to g-cost in A*; cost of path from start to current vertex
        public int cost; // equal to f-cost in A*; the rank
        private State prev;

        public State(Board board, int moves, State prev) {
            this.board = board;
            this.moves = moves;
            this.prev = prev;
            cost = moves + board.manhattan();
        }

        public int compareTo(State s) {
            return cost - s.cost;
        }

        @Override
        public boolean equals(Object s) {
            if (s == this) return true;
            if (s == null) return false;
            if (!(s instanceof State)) return false;
            return ((State) s).board.equals(this.board);
        }
    }

    /*
     * Return the root state of a given state.
     */
    private State root(State state) {
        State current = state;
        while(current.prev != null) {
            current = current.prev;
        }
        return current;
    }

    /*
     * A* Solver
     * Find a solution to the initial board using A* to generate the state tree
     * and a identify the shortest path to the the goal state
     */
    public Solver(Board initial) {
        this.initial = initial;
        if (!isSolvable()) return;

        PriorityQueue<State> frontier = new PriorityQueue<State>();
        HashMap<Board, Integer> movesAtEachNode = new HashMap<>();

        State init = new State(initial,0 ,null);
        frontier.add(init);
        movesAtEachNode.put(initial, 0);

        while (!frontier.isEmpty()) {
            State current = frontier.poll();

            // Shortest path found
            if (current.board.isGoal()) {
                solutionState = current;
                solved = true;
                minMoves = current.moves;
                break;
            }

            for (Board n : current.board.neighbors()) {
                if (n.equals(current.board)) continue;

                if (!movesAtEachNode.containsKey(n) || current.moves + 1 < movesAtEachNode.get(n)) {
                    movesAtEachNode.replace(n, current.moves + 1);
                    frontier.add(new State(n, current.moves + 1, current));
                }
            }
        }

    }

    /*
     * Is the input board a solvable state
     */
    public boolean isSolvable() {
        return initial.solvable();
    }

    /*
     * Return the sequence of boards in a shortest solution, null if unsolvable
     */
    public Iterable<Board> solution() {
        if (!isSolvable()) return null;

        List<Board> solution = new ArrayList<>();
        State current = solutionState;
        while(current.prev != null) {
            solution.add(current.board);
            current = current.prev;
        }
        solution.add(current.board);
        Collections.reverse(solution);
        return solution;
    }

    public State find(Iterable<State> iter, Board b) {
        for (State s : iter) {
            if (s.board.equals(b)) {
                return s;
            }
        }
        return null;
    }

    /*
     * Debugging space
     */
    public static void main(String[] args) {
        int[][] initState = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
        Board initial = new Board(initState);

        Solver solver = new Solver(initial);
    }


}

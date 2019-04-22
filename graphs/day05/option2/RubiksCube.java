import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.io.*;


// this is our implementation of a rubiks cube. It is your job to use A* or some other search algorithm to write a
// solve() function
public class RubiksCube {

    private BitSet cube;
    private static HashMap<BitSet, Integer> h;
    private char rotations[] = {'u', 'U', 'r', 'R', 'f', 'F'};
    private HashMap<Integer, HashMap<Integer, Integer>> distances;

    // initialize a solved rubiks cube
    public RubiksCube() {
        // 24 colors to store, each takes 3 bits
        cube = new BitSet(24 * 5);
        for (int side = 0; side < 6; side++) {
            for (int i = 0; i < 4; i++) {
                setColor(side * 4 + i, side * 4 + i);
            }
        }
    }

    public void populate_hm(){
        {0,19,22,}

        distances = new HashMap<>();
        for(int i = 0; i < 24; i++){
            HashMap<Integer, Integer> curr_distances = new HashMap<>()
            for(int j = 0; j < 24;j++){

            }
        distances.put(i, curr_distances);

        }
    }

    // initialize a rubiks cube with the input bitset
    private RubiksCube(BitSet s) {
        cube = (BitSet) s.clone();
    }

    // creates a copy of the rubics cube
    public RubiksCube(RubiksCube r) {
        cube = (BitSet) r.cube.clone();
    }

    // return true if this rubik's cube is equal to the other rubik's cube
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof RubiksCube))
            return false;
        RubiksCube other = (RubiksCube) obj;
        return other.cube.equals(cube);
    }

    /**
     * return a hashCode for this rubik's cube.
     *
     * Your hashCode must follow this specification:
     *   if A.equals(B), then A.hashCode() == B.hashCode()
     *
     * Note that this does NOT mean:
     *   if A.hashCode() == B.hashCode(), then A.equals(B)
     */
    @Override
    public int hashCode() {
        return cube.hashCode();
    }

    public boolean isSolved() {
        return this.equals(new RubiksCube());
    }


    // takes in 3 bits where bitset.get(0) is the MSB, returns the corresponding int
    private static int bitsetToInt(BitSet s) {
        int i = 0;
        if (s.get(0)) i |= 16;
        if (s.get(1)) i |= 8;
        if (s.get(2)) i |= 4;
        if (s.get(3)) i |= 2;
        if (s.get(4)) i |= 1;
        return i;
    }

    // takes in a number 0-5, returns a length-3 bitset, where bitset.get(0) is the MSB
    private static BitSet intToBitset(int i) {
        BitSet s = new BitSet(5);
        if (i % 2 == 1) s.set(4, true);
        i /= 2;
        if (i % 2 == 1) s.set(3, true);
        i /= 2;
        if (i % 2 == 1) s.set(2, true);
        i /= 2;
        if (i % 2 == 1) s.set(1, true);
        i /= 2;
        if (i % 2 == 1) s.set(0, true);
        return s;
    }

    // index from 0-23, color from 0-5
    private void setColor(int index, int number) {
        BitSet colorBitset = intToBitset(number);
        for (int i = 0; i < 5; i++)
            cube.set(index * 5 + i, colorBitset.get(i));
    }


    // index from 0-23, returns a number from 0-5
    private int getColor(int index) {
        return bitsetToInt(cube.get(index * 5, (index + 1) * 5));
    }

    // given a list of rotations, return a rubik's cube with the rotations applied
    public RubiksCube rotate(List<Character> c) {
        RubiksCube rub = this;
        for (char r : c) {
            rub = rub.rotate(r);
        }
        return rub;
    }


    // Given a character in ['u', 'U', 'r', 'R', 'f', 'F'], return a new rubik's cube with the rotation applied
    // Do not modify this rubik's cube.
    public RubiksCube rotate(char c) {
        int[] faceFrom = null;
        int[] faceTo = null;
        int[] sidesFrom = null;
        int[] sidesTo = null;
        // colors move from the 'from' variable to the 'to' variable
        switch (c) {
            case 'u': // clockwise
            case 'U': // counterclockwise
                // I feel like faceFrom and faceTo here is a counter-clockwise rotation?
                faceFrom = new int[]{0, 1, 2, 3};
                faceTo = new int[]{1, 2, 3, 0};
                sidesFrom = new int[]{4, 5, 8, 9, 17, 16, 21, 20};
                sidesTo = new int[]{21, 20, 4, 5, 8, 9, 17, 16};
                break;
            case 'r':
            case 'R':
                faceFrom = new int[]{8, 9, 10, 11};
                faceTo = new int[]{9, 10, 11, 8};
                sidesFrom = new int[]{6, 5, 2, 1, 17, 18, 13, 14};
                sidesTo = new int[]{2, 1, 17, 18, 13, 14, 6, 5};
                break;
            case 'f':
            case 'F':
                faceFrom = new int[]{4, 5, 6, 7};
                faceTo = new int[]{5, 6, 7, 4};
                sidesFrom = new int[]{3, 2, 8, 11, 14, 15, 23, 20};
                sidesTo = new int[]{8, 11, 14, 15, 23, 20, 3, 2};
                break;
            default:
                System.out.println(c);
                assert false;
        }
        // if performing a counter-clockwise rotation, swap from and to
        if (Character.isUpperCase(c)) {
            int[] temp;
            temp = faceFrom;
            faceFrom = faceTo;
            faceTo = temp;
            temp = sidesFrom;
            sidesFrom = sidesTo;
            sidesTo = temp;
        }
        RubiksCube res = new RubiksCube(cube);
        // Rotates by swapping colors in from and to positions so that indexes remain the same for positions
        for (int i = 0; i < faceFrom.length; i++) res.setColor(faceTo[i], this.getColor(faceFrom[i]));
        for (int i = 0; i < sidesFrom.length; i++) res.setColor(sidesTo[i], this.getColor(sidesFrom[i]));
        return res;
    }

    // returns a random scrambled rubik's cube by applying random rotations
    public static RubiksCube scrambledCube(int numTurns) {
        RubiksCube r = new RubiksCube();
        char[] listTurns = getScramble(numTurns);
        for (int i = 0; i < numTurns; i++) {
            r= r.rotate(listTurns[i]);
        }
        return r;
    }

    public static char[] getScramble(int size){
        char[] listTurns = new char[size];
        for (int i = 0; i < size; i++) {
            switch (ThreadLocalRandom.current().nextInt(0, 6)) {
                case 0:
                    listTurns[i] = 'u';
                    break;
                case 1:
                    listTurns[i] = 'U';
                    break;
                case 2:
                    listTurns[i] = 'r';
                    break;
                case 3:
                    listTurns[i] = 'R';
                    break;
                case 4:
                    listTurns[i] = 'f';
                    break;
                case 5:
                    listTurns[i] = 'F';
                    break;
            }
        }
        return listTurns;
    }

    /**
     * State class to make the cost calculations simple
     * This class holds a cube state and all of its attributes
     */
    private class State implements Comparable<State>{
        // Each state needs to keep track of its cost and the previous state
        private RubiksCube cube;
        private int moves; // equal to g-cost in A*; cost of path from start to current vertex
        public int cost; // equal to f-cost in A*; the rank
        private State prev;
        private char move;

        public State(RubiksCube cube, int moves, State prev, char move) {
            this.cube = cube;
            this.moves = moves;
            this.prev = prev;
            this.move = move;
            cost = moves + h.get(cube.cube);
        }

        public int compareTo(State s) {
            return cost - s.cost;
        }

        @Override
        public boolean equals(Object s) {
            if (s == this) return true;
            if (s == null) return false;
            if (!(s instanceof State)) return false;
            return ((State) s).cube.equals(this.cube);
        }
    }






    // return the list of rotations needed to solve a rubik's cube
    public List<Character> solve() {
        if (h == null) h = getHeuristic();

        PriorityQueue<State> frontier = new PriorityQueue<>();
        HashMap<RubiksCube, Integer> movesAtEachNode = new HashMap<>();

        State init = new State(this,0 ,null, '\u0000');
        frontier.add(init);
        movesAtEachNode.put(this, 0);

        State solutionState = null;
        int minMoves = -1;

        while (!frontier.isEmpty()) {
            State current = frontier.poll();

            // Shortest path found
            if (current.cube.isSolved()) {
                solutionState = current;
                minMoves = current.moves;
                break;
            }

            int newCost = current.moves + 1;
            List<RubiksCube> neighbors = current.cube.getNeighbors();
            for (int i = 0; i < neighbors.size(); i++) {
                RubiksCube n = neighbors.get(i);
                if (n.equals(current.cube)) continue;

                if (!movesAtEachNode.containsKey(n) ||  newCost < movesAtEachNode.get(n)) {
                    movesAtEachNode.replace(n, newCost);
                    frontier.add(new State(n, newCost, current, rotations[i]));
                }
            }
        }

        // Build solution char list: which rotations to apply in order to solve cube
        List<Character> solution = new ArrayList<>();
        State current = solutionState;
        while(current.prev != null) {
            solution.add(current.move);
            current = current.prev;
        }
        Collections.reverse(solution);
        return solution;
    }

    // return list of cubes that is one rotation away from the current cube
    private List<RubiksCube> getNeighbors() {
        List<RubiksCube> neighbors = new ArrayList<>();
        for (char rotation: rotations) {
            neighbors.add(this.rotate(rotation));
        }
        return neighbors;
    }

    // Performs BFS backwards on the search space starting from the goal to create a pattern database (lookup table)
    // Maps each state to the number of moves/distance from goal
    public void calculateHeuristic() {
        // Solved rubik's cube state
        RubiksCube r = new RubiksCube();

        // Queue for Breadth First Search
        Queue<RubiksCube> q = new LinkedList<>();
        // Pattern database to write to file and use to retrieve heuristic. Maps Bitset representation of rubik's cube to moves from goal
        HashMap<BitSet, Integer> h = new HashMap<>();
        int moves = 0;
        int states = 0;

        q.add(r);
        h.put(r.cube, moves);

        // Perform BFS. Terminates loop when all possible permutations are already visited (exist in hashmap p)
        while (!q.isEmpty()) {
            RubiksCube current = q.poll();
            moves = h.get(current.cube) + 1;
            for (RubiksCube c : current.getNeighbors()) {
                if (!h.containsKey(c.cube)) {
                    h.put(c.cube, moves);
                    q.add(c);
                    states++;
                }
            }
        }

        // System.out.println(states);

        // Serialize pattern database and write to file
        try {
            FileOutputStream fileOut = new FileOutputStream("pattern.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(h);
            out.close();
            fileOut.close();
            System.out.printf("Serialized pattern database is saved in /pattern.ser");
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    public static HashMap<BitSet, Integer> getHeuristic() {
        try {
            FileInputStream fileIn = new FileInputStream("pattern.ser");
            BufferedInputStream bin = new BufferedInputStream(fileIn);
            ObjectInputStream in = new ObjectInputStream(bin);
            h = (HashMap<BitSet, Integer>) in.readObject();
            in.close();
            bin.close();
            fileIn.close();
        } catch (IOException i) {
            System.out.println("Could not find file");
            i.printStackTrace();
            return null;
        } catch (ClassNotFoundException c) {
            c.printStackTrace();
            return null;
        }
        return h;
    }


}
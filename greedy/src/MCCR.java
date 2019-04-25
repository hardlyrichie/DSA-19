public class MCCR {
    public static int MCCR(EdgeWeightedGraph G) {
        // Shortest edges; Contains the minimum spanning tree
        Edge[] edges = new Edge[G.numberOfV()];
        // Weights (price for road) of shortest edges
        int[] keys = new int[G.numberOfV()];
        // Flags for vertices that are not in minimum spanning tree
        boolean[] found = new boolean[G.numberOfV()];
        // Priority queue of vertices. Priority is the weight of the edge to that vertex. K is vertex index, V is key of vertex (weights)
        // Ensures travel in path of least cost
        IndexPQ<Integer> pq = new IndexPQ<Integer>(G.numberOfE());

        for (int i = 0; i < G.numberOfV(); i++) {
            // Set initial distances to infinity so that shorter paths takes priority
            keys[i] = Integer.MAX_VALUE;
        }

        // Iterate through all verticies
        for (int i = 0; i < G.numberOfV(); i++) {
            if (!found[i]) {
                // Initial weight of vertex is 0
                keys[i] = 0;
                pq.insert(i, keys[i]);

                while (!pq.isEmpty()) {
                    // Vertex with smallest key, smallest price
                    int v = pq.delMin();
                    found[v] = true;

                    // Update weights for all neighboring verticies
                    for (Edge e : G.edges(v)) {
                        int v2 = e.other(v);

                        if (found[v2]) continue;

                        // Update key if current cost to path (e.weight()) is less
                        if (e.weight() < keys[v2]) {
                            keys[v2] = e.weight();
                            edges[v2] = e;

                            if (pq.contains(v2)) {
                                pq.changeKey(v2, keys[v2]);
                            }
                            else {
                                pq.insert(v2, keys[v2]);
                            }
                        }
                    }
                }
            }
        }

        // Sum up weights in MST
        int minCost = 0;
        Edge e;

        for (int i = 0; i < edges.length; i++){
            e = edges[i];
            if (e != null) {
                minCost += e.weight();
            }
        }

        return minCost;
    }
}


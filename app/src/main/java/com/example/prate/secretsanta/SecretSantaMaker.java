package com.example.prate.secretsanta;

import java.util.*;

/* Java program for solution of Hamiltonian Cycle problem
   using backtracking */
// This code is contributed by Abhishek Shankhadhar, modified by David Prater
class SecretSantaMaker {

    //Initialize an ArrayList of person objects called people
    ArrayList<Person> people;

    SecretSantaMaker(ArrayList<Person> people) {
        this.people = people;
    }

    /* A utility function to check if the vertex v can be
       added at index 'pos'in the Hamiltonian Cycle
       constructed so far (stored in 'path[]') */
    private boolean isSafe(int v, int graph[][], int path[], int pos) {
        /* Check if this vertex is an adjacent vertex of
           the previously added vertex. */
        if (graph[path[pos - 1]][v] == 0)
            return false;

        /* Check if the vertex has already been included.
           This step can be optimized by creating an array
           of size V */
        for (int i = 0; i < pos; i++)
            if (path[i] == v)
                return false;

        return true;
    }

    /* A recursive utility function to solve hamiltonian
       cycle problem */
    private boolean hamCycleUtil(int graph[][], int path[], int pos) {
        /* base case: If all vertices are included in
           Hamiltonian Cycle */
        if (pos == people.size()) {
            // And if there is an edge from the last included
            // vertex to the first vertex
            if (graph[path[pos - 1]][path[0]] == 1)
                return true;
            else
                return false;
        }

        // Try different vertices as a next candidate in
        // Hamiltonian Cycle. We don't try for 0 as we
        // included 0 as starting point in in hamCycle()
        for (int i = 1; i < people.size(); i++) {
            /* Check if this vertex can be added to Hamiltonian
               Cycle */
            if (isSafe(i, graph, path, pos)) {
                path[pos] = i;

                /* recur to construct rest of the path */
                if (hamCycleUtil(graph, path, pos + 1))
                    return true;

                /* If adding vertex v doesn't lead to a solution,
                   then remove it */
                path[pos] = -1;
            }
        }

        /* If no vertex can be added to Hamiltonian Cycle
           constructed so far, then return false */
        return false;
    }

    /* This function solves the Hamiltonian Cycle problem using
       Backtracking. It mainly uses hamCycleUtil() to solve the
       problem. It returns false if there is no Hamiltonian Cycle
       possible, otherwise return true and prints the path.
       Please note that there may be more than one solutions,
       this function prints one of the feasible solutions. */
    private int[] hamCycle(int graph[][]) {
        int path[] = new int[people.size()];
        for (int i = 0; i < people.size(); i++)
            path[i] = -1;

        /* Let us put vertex 0 as the first vertex in the path.
           If there is a Hamiltonian Cycle, then the path can be
           started from any point of the cycle as the graph is
           undirected */
        path[0] = 0;
        if (!hamCycleUtil(graph, path, 1)) {
            System.out.println("\nSolution does not exist");
        }

        return path;
    }

    // This method is used create a matrix that stores the excluded people for that person
    private int[][] createAdjacencyMatrix() {
        //Matrix size of number of people by number of people
        int matrix[][] = new int[people.size()][people.size()];
        //Set everyone as included as possible match to start.
        for (int row[] : matrix) {
            Arrays.fill(row, 1);
        }

        //Iterate through each person to create a matrix to store excluded people
        for (Person person : people) {
            int index = people.indexOf(person);
            for (Person excludedPerson : person.excludedPeople) {
                int subIndex = people.indexOf(excludedPerson);
                //this corresponds to e.g. for matrix[0][0], the first person is excluded from the first person
                // e.g. matrix[0][1] first person cant get the second person
                matrix[index][subIndex] = 0;

            }
        }

        return matrix;
    }

    public HashMap<Person, Person> createSecretSantas() {
        // This matrix is made from exclusions it is a 2 dimensional array of 1s and 0s.
        int matrix[][] = createAdjacencyMatrix();
        // Finds the cycle and returns an array of ints representing the index of the person in the people array list
        int path[] = hamCycle(matrix);
        // Initialize a HashMap of person objects called secretSantaMap
        HashMap<Person, Person> secretSantaMap = new HashMap<>();

        // Create a for loop
        for (int i = 0; i < path.length; i++) {
            // If this is the last iteration the next index points back to the first person that is
            // used, completes the loop otherwise its just the next index.
            int nextI = i == path.length - 1 ? 0 : i + 1;
            // If index of path i is less than zero or the value at index of nextI is less than zero
            // there is no solution
            if (path[i] < 0 || path[nextI] < 0) return secretSantaMap;
            // The final result is a pair of people "Gifters and Giftees" in the format where the
            // first name is the gifter and the second names is the giftee (Gifter --> Giftee)
            secretSantaMap.put(people.get(path[i]), people.get(path[nextI]));

        }

        // Return the secret santa HashMap
        return secretSantaMap;
    }


}
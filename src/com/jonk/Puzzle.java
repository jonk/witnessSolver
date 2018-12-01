package com.jonk;

import java.util.*;

public class Puzzle {

    private String[][] grid;
    private List<Node> solution;
    private Node start;

    private Puzzle() {
    }

    private Puzzle(String[][] grid, Node start) {
        this.grid = grid;
        this.start = start;
    }

    /** Expects grid to be a rectangle, all heights and widths match. 'S' denotes
     * start, 'E' denotes end, '.' is empty space, '_' is missing space. */
    public static Puzzle fromString(String input) {
        String[] rows = input.split("\n");
        int height = rows.length;
        int width = rows[0].length();
        String[][] grid = new String[height][width];
        Node start = null;
        for (int i = 0; i < rows.length; i++) {
            String row = rows[i];
            grid[i] = row.split(" ");
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j].equals("S")) {
                    start = new Node(i, j);
                }
            }
        }
        return new Puzzle(grid, start);
    }

    public void printSolution() {
        if (solution == null) {
            solveBfs();
        }
        for (int i = 1; i < solution.size(); i++) {
            Node p = solution.get(i);
            grid[p.y][p.x] = "x";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                sb.append(grid[i][j]);
                sb.append(" ");
            }
            sb.append("\n");
        }
        System.out.println(sb.toString());
    }

    private void solveBfs() {
        Queue<Node> queue = new LinkedList<>();
        queue.add(start);
        Set<Node> visited = new HashSet<>();
        while (!queue.isEmpty()) {
            Node cur = queue.poll();
            visited.add(cur);
            if (grid[cur.y][cur.x].equals("E")) {
                solution = getSolution(cur);
                break;
            } else {
                queue.addAll(findChildren(cur, visited));
            }
        }
    }

    private List<Node> getSolution(Node cur) {
        List<Node> path = new ArrayList<>();
        while (cur.parent != null) {
            path.add(cur);
            cur = cur.parent;
        }
        return path;
    }

    private List<Node> findChildren(Node cur, Set<Node> visited) {
        List<Node> children = new ArrayList<>();
        int[] search = new int[]{-1, 1};
        Node newX, newY;
        for (int incr : search) {
            newX = new Node(cur.x + incr, cur.y, cur);
            if (isValid(newX, visited)) {
                children.add(newX);
            }
            newY = new Node(cur.x, cur.y + incr, cur);
            if (isValid(newY, visited)) {
                children.add(newY);
            }
        }
        return children;
    }

    private boolean isValid(Node n, Set<Node> visited) {
        return n.x >= 0 && n.x < grid[0].length &&
                n.y >= 0 && n.y < grid.length &&
                !visited.contains(n) &&
                !grid[n.y][n.x].equals("_");
    }

    private static class Node {
        int x;
        int y;
        Node parent;

        public Node(int x, int y, Node parent) {
            this.x = x;
            this.y = y;
            this.parent = parent;
        }

        public Node(int x, int y) {
            this.x = x;
            this.y = y;
            this.parent = null;
        }

        @Override
        public String toString() {
            return String.format("(%d, %d)", this.x, this.y);
        }
    }
}

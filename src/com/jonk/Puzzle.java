package com.jonk;

import java.util.*;
import java.util.stream.Collectors;

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

    /**
     * Expects grid to be a rectangle, height and width is uniform across rows and cols. 'S' denotes
     * start, 'E' denotes end, '.' is a valid space, '_' is an invalid space.
     */
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
                if (grid[i][j].contains("S")) {
                    start = new Node(j, i);
                }
            }
        }
        if (start == null) {
            throw new IllegalArgumentException("Puzzle requires start point denoted by 'S'");
        }
        return new Puzzle(grid, start);
    }

    public void printSolution() {
        if (solution == null) {
            solveBfs();
        }

        StringBuilder sb = new StringBuilder();
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[row].length; col++) {
                if (grid[row][col].contains("E")) {
                    sb.append("E");
                } else if (solution.contains(new Node(row, col))) {
                    sb.append(directionSymbol(solution.indexOf(new Node(row, col))));
                } else {
                    sb.append(".");
                }
                sb.append(" ");
            }
            sb.append("\n");
        }
        System.out.println(sb.toString());
    }

    private String directionSymbol(int n) {
        Node cur = solution.get(n);
        Node prev = solution.get(n - 1);
        if (cur.row < prev.row) {
            return "v";
        } else if (cur.row > prev.row) {
            return "^";
        } else if (cur.col > prev.col) {
            return "<";
        } else {
            return ">";
        }
    }

    private void solveBfs() {
        Queue<Node> queue = new LinkedList<>();
        queue.add(start);
        Set<Node> visited = new HashSet<>();
        while (!queue.isEmpty()) {
            Node cur = queue.poll();
            visited.add(cur);
            if (grid[cur.row][cur.col].contains("E")) {
                solution = traverseParents(cur);
                break;
            } else {
                queue.addAll(findChildren(cur, visited));
            }
        }
    }

    private List<Node> traverseParents(Node cur) {
        List<Node> path = new ArrayList<>();
        while (cur.parent != null) {
            path.add(cur);
            cur = cur.parent;
        }
        path.add(cur); // For the start node
        return path;
    }

    private List<Node> findChildren(Node cur, Set<Node> visited) {
        Map<String, Node> exploreMap = initExploreMap(cur);
        return exploreMap.entrySet()
                .stream()
                .filter(e -> this.isValid(e, visited))
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
    }

    private Map<String, Node> initExploreMap(Node n) {
        Map<String, Node> resultMap = new HashMap<>();
        resultMap.put("<", new Node(n.row, n.col - 1, n));
        resultMap.put(">", new Node(n.row, n.col + 1, n));
        resultMap.put("^", new Node(n.row - 1, n.col, n));
        resultMap.put("v", new Node(n.row + 1, n.col, n));
        return resultMap;
    }

    private boolean isValid(Map.Entry<String, Node> e, Set<Node> visited) {
        String key = e.getKey();
        Node n = e.getValue();
        return n.col >= 0 && n.col < grid[0].length &&
                n.row >= 0 && n.row < grid.length &&
                !visited.contains(n) &&
                !grid[n.row][n.col].equals("_") &&
                (grid[n.parent.row][n.parent.col].equals(".") || grid[n.parent.row][n.parent.col].contains(key));
    }

    private static class Node {
        int row;
        int col;
        Node parent;

        public Node(int row, int col, Node parent) {
            this.row = row;
            this.col = col;
            this.parent = parent;
        }

        public Node(int row, int col) {
            this.row = row;
            this.col = col;
            this.parent = null;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Node node = (Node) o;

            return col == node.col && row == node.row;
        }

        @Override
        public int hashCode() {
            int result = col;
            result = 31 * result + row;
            return result;
        }

        @Override
        public String toString() {
            return String.format("(row: %d, col: %d)", this.row, this.col);
        }
    }
}

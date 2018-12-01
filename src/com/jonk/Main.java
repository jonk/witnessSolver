package com.jonk;

public class Main {

    /*
    input example:

    . . E
    ^ ^>v .
    S> . .

     */

    private static Puzzle helloWorld = Puzzle.fromString("S . E");
    private static Puzzle oneByOne = Puzzle.fromString("E .\n_ S");

    public static void main(String[] args) {
        helloWorld.printSolution();
        oneByOne.printSolution();
    }
}

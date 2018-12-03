package com.jonk;

public class Main {

    /*
    input example:

    . . E
    ^ ^>v .
    S> . .

     */

    private static Puzzle helloWorld = Puzzle.fromString("S> . <E");
    private static Puzzle oneByOne = Puzzle.fromString("E> .\n_ ^S");
    private static Puzzle directional = Puzzle.fromString("Sv _ vE\n^> <> <^");
    private static Puzzle lotsOfDeadEnds = Puzzle.fromString(
            "v> <> E<v >v <v >v <v\n" +
            "^v v ^> <^ ^> <^ ^v\n" +
            "^v ^>v <v > <v> <> <^v\n" +
            "^ ^v ^v> <> <^ v ^v\n" +
            "> <^v ^> <v> S<>v <^v ^\n" +
            "v ^ v ^> <^ ^> <v\n" +
            "^> <> <^> <> <> <> <^\n");


    public static void main(String[] args) {
        helloWorld.printSolution();
        oneByOne.printSolution();
        directional.printSolution();
        lotsOfDeadEnds.printSolution();
    }
}

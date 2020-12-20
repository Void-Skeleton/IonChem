package org.skeleton.ionchem.tests;

import org.skeleton.ionchem.io.Executer;
import org.skeleton.ionchem.io.Terminal;
import org.skeleton.ionchem.states.ChemContainer;

import java.util.Scanner;

public class Test1 {
    public static void main(String[] args) {
        ChemContainer container = new ChemContainer();
        Executer exec = new Executer(container, DefaultConstants.reactionList);
        Terminal terminal = new Terminal(new Scanner(System.in), exec);
        while (true) {
            terminal.execute();
        }
    }
}

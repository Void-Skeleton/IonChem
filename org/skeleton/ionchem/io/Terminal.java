package org.skeleton.ionchem.io;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Scanner;

public class Terminal {
    private Scanner input;
    private Object executer;

    public Terminal(Scanner input, Object executer) {
        this.input = input;
        this.executer = executer;
    }

    public void execute() {
        String command = input.nextLine();
        String[] substrings = command.split(" ");
        String commandName = substrings[0];
        String[] args = Arrays.copyOfRange(substrings, 1, substrings.length);
        try {
            Method m = executer.getClass().getDeclaredMethod(commandName, String[].class);
            m.invoke(executer, (Object) args); // non-varargs call
        } catch (NoSuchMethodException e) {
            System.out.printf("Illegal Command: %s!\n", commandName);
            e.printStackTrace();
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}

package client.scannerManager;

import java.util.Stack;

/**
 * Stack class to prevent recursion in file commands
 */
public class StackManager {
    private static Stack<String> stack = new Stack<>();

    public static void addFilename(String filename) {
        stack.add(filename);
    }

    public static void pop() {
        stack.pop();
    }

    public static boolean checkOnRecursion(String filename) {
        return stack.contains(filename);
    }
}

// This program reads through user input of desired arithmetic operations in the form of a string.
// It parses that string into arithmetic operators and numbers, adds each to a queue, then operates
// on everything by reading from that queue sequentially. It is incapable of processing division. It
// does not solve accurately with regards to PEMDAS, as multiplication is not prioritized over addition
// or subtraction. Everything is processed as it is read in.

import java.util.*;

public class QueueUtilsPrompt {
    private static Queue<String> queue = new LinkedList<>();

    public static void main(String[] args) {
        Integer potentialOutcome = -1;

        Scanner keyboard = new Scanner(System.in);

        while (potentialOutcome != 0) {
            System.out.println("Enter an expression in prefix form (operator comes first)");
            String userInput = keyboard.nextLine();

            String[] splitLine = userInput.split("\\s+");
            int length = splitLine.length;

            for (String loopVar : splitLine) {
                queue.add(loopVar);
            }

            String sign = "";
            Integer prevInt = null;
            Integer currentInt = null;

            if (queue.size() > 1) {
                for (String current = queue.poll(); current != null; current = queue.poll()) {
                    if (prevInt == null) {
                        try {
                            prevInt = Integer.parseInt(current);
                        } catch (NumberFormatException e) {
                            if (current.equals("+") || current.equals("-") || current.equals("*")) {
                                if (!sign.equals("")) {
                                    queue.add(sign);
                                    sign = current;
                                } else {
                                    sign = current;
                                }
                            } else {
                                System.out.println("Invalid input!");
                                break;
                            }
                        }
                    } else {
                        try {
                            currentInt = Integer.parseInt(current);

                            if (sign.equals("+")) {
                                Integer result = prevInt + currentInt;
                                potentialOutcome = result;
                                queue.add(result + "");
                            } else if (sign.equals("-")) {
                                Integer result = prevInt - currentInt;
                                potentialOutcome = result;
                                queue.add(result + "");
                            } else if (sign.equals("*")) {
                                Integer result = prevInt * currentInt;
                                potentialOutcome = result;
                                queue.add(result + "");
                            } else {
                                queue.add(prevInt + "");
                                queue.add(currentInt + "");
                                prevInt = null;
                                currentInt = null;
                            }

                            sign = "";
                            prevInt = null;
                            currentInt = null;
                        } catch (NumberFormatException e) {
                            if (current.equals("+") || current.equals("-") || current.equals("*")) {
                                if (!sign.equals("")) {
                                    queue.add(sign);
                                    sign = current;
                                    queue.add(prevInt + "");
                                    prevInt = null;
                                } else {
                                    sign = current;
                                    queue.add(prevInt + "");
                                    prevInt = null;
                                }
                            } else {
                                System.out.println("Invalid input!");
                                break;
                            }
                        }
                    }
                }
                System.out.println(potentialOutcome);
            }
            else {
                potentialOutcome = Integer.parseInt(queue.poll());
                System.out.println(potentialOutcome);
            }
        }
        System.out.println("Exiting");
    }
}
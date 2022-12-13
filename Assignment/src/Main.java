import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.Scanner;

import implement.ArrayList;
import implement.Queue;
import implement.Stack;

public class Main {
    public static void main(String[] args) {
        Queue<String> queue = new Queue<String>();
        Stack<String> stack = new Stack<String>();
        Queue<String> tempSpam = new Queue<String>();
        ArrayList<String> spam = new ArrayList<String>();
        ArrayList<String> history = new ArrayList<String>();
        boolean isRunning = true;

        while (isRunning) {
            String[] sentences = input();
            long startTime = System.currentTimeMillis();
            transportMessage(sentences, queue, history, spam);
            long stopTime = System.currentTimeMillis();
            System.out.println("Your messages sent in " + (stopTime - startTime) + "ms\n");
            storageMessage(sentences, stack, queue);
            showMessage(stack, tempSpam);
            isRunning = handle(history, tempSpam, spam);
        }
    }

    private static boolean handle(ArrayList<String> history, Queue<String> tempSpam, ArrayList<String> spam) {
        Scanner sc = new Scanner(System.in);
        System.out.println("0. Skip         1. Mark as spam         2. History          3. Quit");
        System.out.print("Your choice: ");
        String choice = sc.nextLine();
        while (!choice.equals("0")&& !choice.equals("1")&& !choice.equals("2")&& !choice.equals("3")) {
            System.out.println("Wrong ! Please input from 0 to 3");
            choice = sc.nextLine();
        }
        switch (choice) {
            case "1":
                spam.add(tempSpam.poll());
                return true;
            case "2":
                System.out.println("History:\n" + history);
                return true;
            case "3":
                return false;
            default:
                return true;
        }
    }

    private static boolean checkSpam(String s , ArrayList<String> spam) {
        return spam.contains(s);
    }

    private static void showMessage(Stack<String> stack, Queue<String> tempSpam) {
        while (!stack.isEmpty()) {
            tempSpam.offer(stack.peek());
            System.out.println("You received a new message: " + stack.pop());
        }
        System.out.println();
    }

    private static void storageMessage(String[] sentences, Stack<String> stack, Queue<String> queue) {
        while (!queue.isEmpty()) {
            stack.push(queue.poll());
        }
    }

    private static void transportMessage(String[] sentences, Queue<String> queue, ArrayList<String> history,
                                         ArrayList<String> spam) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        for (int i =0; i< sentences.length; i++) {
            if (checkSpam(sentences[i], spam)) {
                System.out.println("Your message is considered spam");
            } else {
                queue.offer(sentences[i]);
                System.out.println("Your message has been sent successfully");

                Date date = new Date();
                history.add(dateFormat.format(date) + ": " + sentences[i]);
            }
        }
    }

    private static String[] input() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Message: ");
        String message = sc.nextLine();
        String[] sentences =  message.split("\\.");
        for (int i = 0; i<sentences.length; i++) {
            sentences[i] = sentences[i].trim();
        }
        return sentences;
    }
}
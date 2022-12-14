import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import implement.ArrayList;
import implement.Queue;
import implement.Stack;

public class Main {
    static Scanner sc = new Scanner(System.in);
    public static void main(String[] args) {
        Queue<String> queue = new Queue<String>();
        Stack<String> stack = new Stack<String>();
        Queue<String> tempSpam = new Queue<String>();
        Stack<String> spam = new Stack<String>();
        Queue<String> historyDisplay = new Queue<String>();
        Queue<String> historyStorage = new Queue<String>();
        boolean isRunning = true;

        while (isRunning) {
            String[] sentences = input();
            long startTime = System.currentTimeMillis();
            transportMessage(sentences, queue, historyDisplay,historyStorage, spam);
            long stopTime = System.currentTimeMillis();
            System.out.println("Your messages sent in " + (stopTime - startTime) + "ms\n");
            storageMessage(sentences, stack, queue);
            showMessage(stack, tempSpam);
            isRunning = handle(historyDisplay, historyStorage, tempSpam, spam);
        }
    }

    private static boolean handle(Queue<String> historyDisplay, Queue<String> historyStorage, Queue<String> tempSpam, Stack<String> spam) {
        System.out.println("""
                0. Handle with spam
                1. History
                2. Quit;
                Others to skip""");
        System.out.print("Your choice: ");
        String choice = sc.nextLine();
        switch (choice) {
            case "0":
                handleSpam(tempSpam, spam);
                return true;
            case "1":
                handleHistory(historyDisplay, historyStorage);
                return true;
            case "2":
                return false;
            default:
                return true;
        }
    }

    private static void handleHistory(Queue<String> historyDisplay, Queue<String> historyStorage) {
        System.out.println("History:\n" + historyDisplay);
        System.out.println("""
                        0. Remove specified sentence
                        1. Delete all history;
                        2. Find history
                        Others to skip;""");
        System.out.print("Your choice: ");
        String choiceHistory = sc.nextLine();
        if (choiceHistory.equals("0")) {
            System.out.print("Sentence: ");
            String sentence = sc.nextLine();
            if (historyStorage.contains(sentence)) {
                int indexRemove = historyStorage.getIndex(sentence);
                historyStorage.remove(sentence);
                historyDisplay.remove(indexRemove);
                System.out.println("That sentence has been delete in spam list");
            } else {
                System.out.println("That sentence is not on the spam list");
            }
        } else if (choiceHistory.equals("1")) {
            historyDisplay.pollAll();
            historyStorage.pollAll();
            System.out.println("Delete all history successfully");
        } else if (choiceHistory.equals("2")) {

        }
    }

    private static void handleSpam(Queue<String> tempSpam, Stack<String> spam) {
        System.out.println("""
                0. Mark previously message as spam
                1. Remove specified sentence
                2. Delete spam list
                3. View spam list
                Others to skip;""");
        System.out.print("Your choice: ");
        String choiceSpam = sc.nextLine();
        if (choiceSpam.equals("0")) {
            while (!tempSpam.isEmpty()) {
                spam.push(tempSpam.poll());
            }
            System.out.println("Add message to spam list successfully");
        }else if (choiceSpam.equals("1")) {
            System.out.print("Sentence: ");
            String sentence = sc.nextLine();
            if (spam.contains(sentence)) {
                spam.remove(sentence);
                System.out.println("That sentence has been delete in spam list");
            } else {
                System.out.println("That sentence is not on the spam list");
            }
        } else if(choiceSpam.equals("2")) {
            spam.popAll();
            System.out.println("Delete all spam list successfully");
        } else if(choiceSpam.equals("3")) {
            System.out.println("Spam List:");
            System.out.println(spam);
        }
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

    private static void transportMessage(String[] sentences, Queue<String> queue, Queue<String> historyDisplay,
                                         Queue<String> historyStorage, Stack<String> spam) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        for (int i =0; i< sentences.length; i++) {
            if (spam.contains(sentences[i])) {
                System.out.println("Your message is considered spam");
            } else {
                queue.offer(sentences[i]);
                System.out.println("Your message has been sent successfully");

                Date date = new Date();
                historyDisplay.offer(dateFormat.format(date) + ": " + sentences[i]);
                historyStorage.offer(sentences[i]);
            }
        }
    }

    private static String[] input() {
        System.out.print("Message: ");
        String message = sc.nextLine();
        String[] sentences =  message.split("\\.");
        for (int i = 0; i<sentences.length; i++) {
            sentences[i] = sentences[i].trim();
        }
        return sentences;
    }
}
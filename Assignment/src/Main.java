import java.io.IOException;
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
            try {
                String[] sentences = input();
                long startTime = System.currentTimeMillis();
                transportMessage(sentences, queue, historyDisplay, historyStorage, spam);
                long stopTime = System.currentTimeMillis();
                System.out.println("Your process takes " + (stopTime - startTime) + "ms\n");
                storageMessage(sentences, stack, queue);
                showMessage(stack, tempSpam);
                isRunning = handle(historyDisplay, historyStorage, tempSpam, spam);
            } catch (IndexOutOfBoundsException ie) {
                System.out.println("Error: The message has exceeded 250 characters");
            } catch (IOException io) {
                System.out.println("Error: You haven't typed anything yet");
            } catch (Exception e) {
                System.out.println("Error: An error occurred, please try again.");
            }
        }
    }

    private static boolean handle(Queue<String> historyDisplay, Queue<String> historyStorage, Queue<String> tempSpam, Stack<String> spam) {
        System.out.println("""
                0. Handle with spam
                1. History
                2. Quit;
                Press enter to skip""");
        System.out.print("Your choice: ");
        String choice = sc.nextLine();
        choice=choice.trim();
        while (!choice.equals("0") && !choice.equals("1") && !choice.equals("2") && !choice.equals("")) {
            System.out.print("Your choice: ");
            choice = sc.nextLine();
            choice=choice.trim();
        }
        System.out.println();
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

    private static void handleSpam(Queue<String> tempSpam, Stack<String> spam) {
        while (true) {
            System.out.println("""
                0. Mark previously message as spam
                1. Remove specified sentence
                2. Delete spam list
                3. View spam list
                Press enter to skip""");
            System.out.print("Your choice: ");
            String choiceSpam = sc.nextLine();
            choiceSpam=choiceSpam.trim();
            while (!choiceSpam.equals("0") && !choiceSpam.equals("1") &&
                    !choiceSpam.equals("2") && !choiceSpam.equals("3") &&!choiceSpam.equals("")) {
                System.out.print("Your choice: ");
                choiceSpam = sc.nextLine();
                choiceSpam=choiceSpam.trim();
            }
            if (choiceSpam.equals("0")) {
                if (tempSpam.isEmpty()) {
                    System.out.println("Nothing to add");
                } else {
                    while (!tempSpam.isEmpty()) {
                        spam.push(tempSpam.poll());
                        System.out.println("Add message to spam list successfully");
                    }
                }
                System.out.println();
            }else if (choiceSpam.equals("1")) {
                System.out.print("Sentence: ");
                String sentence = sc.nextLine();
                if (spam.contains(sentence)) {
                    spam.remove(sentence);
                    System.out.println("That sentence has been delete in spam list");
                } else {
                    System.out.println("That sentence is not on the spam list");
                }
                System.out.println();
            } else if(choiceSpam.equals("2")) {
                spam.popAll();
                System.out.println("Delete all spam list successfully");
                System.out.println();
            } else if(choiceSpam.equals("3")) {
                if (spam.isEmpty()) {
                    System.out.println("No records found");
                    System.out.println();
                } else {
                    System.out.println("Spam List:");
                    System.out.println(spam);
                }
            } else {
                break;
            }
        }
    }

    private static void handleHistory(Queue<String> historyDisplay, Queue<String> historyStorage) {
        while (true) {
            if (historyDisplay.isEmpty()) {
                System.out.println("History: No records found");
                break;
            }
            else {
                System.out.println("------------------------------------------");
                System.out.println("History:\n" + historyDisplay);
                System.out.println("------------------------------------------");
                System.out.println("""
                        0. Remove specified sentence
                        1. Delete all history;
                        2. Find history
                        Press enter to skip""");
                System.out.print("Your choice: ");
                String choiceHistory = sc.nextLine();
                choiceHistory=choiceHistory.trim();
                while (!choiceHistory.equals("0") && !choiceHistory.equals("1") &&
                        !choiceHistory.equals("2") && !choiceHistory.equals("")) {
                    System.out.print("Your choice: ");
                    choiceHistory = sc.nextLine();
                    choiceHistory=choiceHistory.trim();
                }
                System.out.println();
                if (choiceHistory.equals("0")) {
                    System.out.print("Sentence: ");
                    String sentence = sc.nextLine();
                    if (historyStorage.contains(sentence)) {
                        int indexRemove = historyStorage.getIndex(sentence);
                        historyStorage.remove(sentence);
                        historyDisplay.remove(indexRemove);
                        System.out.println("That sentence has been delete in history");
                    } else {
                        System.out.println("That sentence is not on the history");
                    }
                    System.out.println();
                } else if (choiceHistory.equals("1")) {
                    historyDisplay.pollAll();
                    historyStorage.pollAll();
                    System.out.println("Delete all history successfully");
                    System.out.println();
                } else if (choiceHistory.equals("2")) {
                    System.out.print("Sentence: ");
                    String sentence = sc.nextLine();
                    ArrayList<Integer> indexesFind = historyStorage.getMultipleIndex(sentence);
                    if (indexesFind.isEmpty()) {
                        System.out.println("No records found");
                    } else {
                        System.out.println("All records match: ");
                        for (int i =0; i< indexesFind.size(); i++) {
                            String result = historyDisplay.getItemByIndex(indexesFind.get(i));
                            System.out.println(result);
                        }
                    }
                    System.out.println();
                } else {
                    break;
                }
            }
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
                                         Queue<String> historyStorage, Stack<String> spam) throws Exception {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        for (int i =0; i< sentences.length; i++) {
            if (sentences[i].length()> 250) {
                throw new IndexOutOfBoundsException();
            } else if(sentences[i].equals("")) {
                throw new IOException();
            }
            else if (spam.contains(sentences[i])) {
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
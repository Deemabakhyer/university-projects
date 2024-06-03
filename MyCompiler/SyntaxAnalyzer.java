package MyCompiler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.Stack;

public class SyntaxAnalyzer {

    private String line;
    private String ans;

    Scanner scanner = new Scanner(System.in);

    /*
     * its an interactive method that act with the user and asked for writing
     * sentence
     * if user write no it will closed the program, if user write yes it will make
     * the user to choose ethir if or for statement
     * then it will store the written statemnt in "review" file.  
     */

    public void opinionsListMethod() {
        System.out.println("Do you want to write your sentence(String)?");
        String answer = scanner.next();
        if (answer.equalsIgnoreCase("YES")) {
            try {
                PrintWriter outputStream = new PrintWriter(new FileOutputStream("review.txt", true));
                System.out.println("*\n");
                Scanner keyboard = new Scanner(System.in);
                System.out.println("\n*\n"
                        + "Before you write your sentence(String), do you want to write If Statements or For Loop?");
                String ans = keyboard.nextLine();
                if (ans.equalsIgnoreCase("if")) {
                    System.out.println("\n*\n" + "Write your sentence(String) in one line:");
                    line = keyboard.nextLine();
                    outputStream.println(line);
                    outputStream.close();
                    displayFileIF();
                } else {
                    System.out.println("\n*\n" + "Write your sentence(String) in one line:");
                    line = keyboard.nextLine();
                    outputStream.println(line);
                    outputStream.close();
                    displayFileFOR();
                }
            } catch (FileNotFoundException e) {
                System.out.println("File not found");
                System.exit(0);
            }
        } else {
            System.out.println("We are sorry to hear that. :(");
        }
    }

    /*
     * this method will read the sentence from "review" file line by line
     * by using the while loop that helps to store the reading line in "line"
     * variable
     * then calling the IFString() method to check if there are any syntax errors. 
     */

    public void displayFileIF() {
        try {
            File file = new File("review.txt");
            Scanner scanner = new Scanner(file);

            System.out.println("\nsentence(String):\n");
            while (scanner.hasNextLine()) {
                line = scanner.nextLine();
                System.out.println(line);
                IFString();
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            System.exit(0);
        }
    }

    /*
     * this method will read the sentence from "review" file line by line
     * by using the while loop that helps to store the reading line in "line"
     * variable
     * then calling the FORString() method to check if there are any syntax errors. 
     */

    public void displayFileFOR() {
        try {
            File file = new File("review.txt");
            Scanner scanner = new Scanner(file);

            System.out.println("\nsentence(String):\n");
            while (scanner.hasNextLine()) {
                line = scanner.nextLine();
                System.out.println(line);
                FORString();
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            System.exit(0);
        }
    }

    /*
     * in this method we use a stack then we create an Iteration by using for each
     * loop
     * and if statment that check if the paranthisis open type then push it else it
     * will check again
     * by nested if, ethir stack empty or have another paranthisis but != open
     * paranthisis
     * it will pop it and return false 
     *     
     */

    private boolean areParenthesesBalanced(String line) {
        Stack<Character> stack = new Stack<>();
        for (char ch : line.toCharArray()) {
            if (ch == '(') {
                stack.push(ch);
            } else if (ch == ')') {
                if (stack.isEmpty() || stack.pop() != '(') {
                    return false;
                }
            }
        }
        return stack.isEmpty();
    }

    /*
     * in this method we use a stack then we create an Iteration by using for each
     * loop
     * and if statment that check if the braces open type then push it else it will
     * check again
     * by nested if, ethir stack empty or have another braces but != open braces
     * it will pop it and return false 
     *     
     */

    private boolean areBracesBalanced(String line) {
        Stack<Character> stack = new Stack<>();
        for (char ch : line.toCharArray()) {
            if (ch == '{') {
                stack.push(ch);
            } else if (ch == '}') {
                if (stack.isEmpty() || stack.pop() != '{') {
                    return false;
                }
            }
        }
        return stack.isEmpty();
    }

    /*
     * this method will works as a basic syntax analysis.
     * we use if/else statement to check if statement doesn't contains if keyword
     * it will print error meassage and the same thing with the else keyword.
     * and it will check if the sentenc have balanced paranthisis and barces or not,
     * if not it will print an error message.
     * if the sentence was correct it will print a correction message
     *     
     */

    public void IFString() {
        if (!line.contains("if")) {
            System.out.println("Oops, you are missing the 'if' keyword. Please rewrite the statement correctly.");
        } else if (!line.contains("else")) {
            System.out.println("Oops, you are missing the 'else' keyword. Please rewrite the statement correctly.");
        } else if (!areParenthesesBalanced(line)) {
            System.out.println("Oops, your parentheses are not balanced. Please rewrite the statement correctly.");
        } else if (!areBracesBalanced(line)) {
            System.out.println("Oops, your braces are not balanced. Please rewrite the statement correctly.");
        } else {
            System.out.println("COOL! The syntax analysis was successful.");
        }
    }

    /*
     * this method will works as a basic syntax analysis.
     * we use if/else statement to check if statement doesn't contains for keyword
     * it will print error meassage and the same thing with the int keyword.
     * its also check for variable names that should be letter followed by letters
     * or digits.
     * checks if the statement contains semicolons or not, and checks if there is
     * ncrement or decrement or not
     * and it will check if the sentenc have balanced paranthisis and barces or not
     *     
     */

    public void FORString() {

        if (!line.contains("for")) {
            System.out.println("Oops, you are missing the 'for' keyword. Please rewrite the statement correctly.");
        } else if (!line.contains("int")) {
            System.out.println("Oops, you are missing the 'int' keyword. Please rewrite the statement correctly.");
        } else if (!line.matches(".*\\b[a-zA-Z][a-zA-Z0-9]*\\b.*")) {
            System.out.println("Oops, you are missing a variable. Please rewrite the statement correctly.");
        } else if (!line.contains(";")) {
            System.out.println("Oops, you are missing the semicolon ';'. Please rewrite the statement correctly.");
        } else if (!line.matches(".*[a-zA-Z0-9].*\\s*(\\+\\+|--).*")) {
            System.out.println(
                    "Oops, you are missing the expression that increments/decrements the loop variable by some value. Please rewrite the statement correctly.");
        } else if (!areParenthesesBalanced(line)) {
            System.out.println("Oops, your parentheses are not balanced. Please rewrite the statement correctly.");
        } else if (!areBracesBalanced(line)) {
            System.out.println("Oops, your braces are not balanced. Please rewrite the statement correctly.");
        } else {
            System.out.println("COOL! The syntax analysis was successful.");
        }

    }
}

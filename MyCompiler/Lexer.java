package MyCompiler;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Lexer {

    static String identifierRegularExpression = "[a-zA-Z][a-zA-Z0-9]*";
    static String digit = "[0-9]+";
    static String comment = "/\\*.*?\\*/";
    static String output = "'[^']*'";

    static String token;
    static Scanner inputCode;
    static String filename = "C:\\Users\\deema\\VisualStudio\\myJavaProjects\\src\\MyCompiler\\review.txt";

    static List<String> keywords = new ArrayList<>();
    static List<String> symbols = new ArrayList<>();
    static List<String> operations = new ArrayList<>();
    static List<String> logicalOps = new ArrayList<>();

    public static void initializeLists() {
        keywords.add("if");
        keywords.add("else");
        keywords.add("elseif");
        keywords.add("for");
        keywords.add("while");
        keywords.add("do");
        keywords.add("return");
        keywords.add("break");
        keywords.add("continue");
        keywords.add("byte");
        keywords.add("class");
        keywords.add("default");
        keywords.add("extends");
        keywords.add("implements");
        keywords.add("instanceof");
        keywords.add("interface");
        keywords.add("native");
        keywords.add("short");
        keywords.add("strictfp");
        keywords.add("super");
        keywords.add("const");
        keywords.add("goto");
        keywords.add("switch");
        keywords.add("synchronized");
        keywords.add("throw");
        keywords.add("throws");
        keywords.add("volatile");
        keywords.add("transient");
        keywords.add("final");
        keywords.add("import");
        keywords.add("finally");
        keywords.add("public");
        keywords.add("private");
        keywords.add("protected");
        keywords.add("package");
        keywords.add("abstract");
        keywords.add("assert");
        keywords.add("case");
        keywords.add("new");
        keywords.add("int");
        keywords.add("double");
        keywords.add("float");
        keywords.add("static");
        keywords.add("char");
        keywords.add("boolean");
        keywords.add("long");
        keywords.add("void");
        keywords.add("this");
        keywords.add("try");
        keywords.add("catch");
        keywords.add("true");
        keywords.add("false");
        keywords.add("enum");

        symbols.add("!");
        symbols.add("&");
        symbols.add("^");
        symbols.add("%");
        symbols.add("$");
        symbols.add("#");
        symbols.add("@");
        symbols.add("?");
        symbols.add("~");
        symbols.add("("); // begin the condition
        symbols.add(")"); // end the condition
        symbols.add("{"); // begin the loop
        symbols.add("}"); // end for loop
        symbols.add(";"); // end statement
        symbols.add("'"); // use to print

        operations.add("+"); // addition
        operations.add("-"); // subtraction
        operations.add("*"); // multiplication
        operations.add("/"); // division
        operations.add("="); // assignment
        operations.add("%"); // mod

        logicalOps.add("||");
        logicalOps.add("&&");
        logicalOps.add("==");
        logicalOps.add("<=");
        logicalOps.add(">=");
        logicalOps.add(">");
        logicalOps.add("<");
    }

    public static void scanFile() {
        System.out.println("- - - - - - - - - - - - - - - - - - -");
        System.out.printf("| %-10s | %-10s | %-5s |%n", "Lexeme", "Token", "Index");
        System.out.println("- - - - - - - - - - - - - - - - - - -");

        try {
            File file = new File(filename);

            inputCode = new Scanner(file);
            StringBuilder content = new StringBuilder();
            while (inputCode.hasNextLine()) {
                content.append(inputCode.nextLine()).append("\n");
            }

            // Preprocessing the input to add spaces around operators and semicolons
            String processedInput = content.toString().replaceAll("([=;+-])", " $1 ");

            Scanner scanner = new Scanner(processedInput);
            int index = 1;
            while (scanner.hasNext()) {
                token = scanner.next();
                String tokenCategory;
                if (keywords.contains(token)) {
                    tokenCategory = "Keyword";
                } else if (symbols.contains(token)) {
                    tokenCategory = "Symbol";
                } else if (operations.contains(token)) {
                    tokenCategory = "Operation";
                } else if (logicalOps.contains(token)) {
                    tokenCategory = "LogicalOp";
                } else if (Pattern.matches(identifierRegularExpression, token)) {
                    tokenCategory = "Identifier";
                } else if (Pattern.matches(comment, token)) {
                    tokenCategory = "Comment";
                } else if (Pattern.matches(digit, token)) {
                    tokenCategory = "Digit";
                } else if (Pattern.matches(output, token)) {
                    tokenCategory = "Output";
                } else {
                    tokenCategory = "Error";
                }
                if (!tokenCategory.equals("Error")) {
                    System.out.printf("| %-10s | %-10s | %-5d |%n", token, tokenCategory, index++);
                } else {
                    System.out.printf("| %-10s | %-10s | %-5s |%n", token, "ERROR !!!", index);
                }
            }
        } catch (FileNotFoundException ex) {
            System.out.println("File not found: " + filename);
        }
        System.out.println("- - - - - - - - - - - - - - - - - - -");
    }

    public static void main(String[] args) {
        initializeLists();
        scanFile();
    }
}

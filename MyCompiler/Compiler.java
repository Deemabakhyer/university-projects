
package MyCompiler;

import java.util.Scanner;

public class Compiler {

    public static void main(String[] args) {
        System.out.print("Do you want writ to Lexer analyzer or Syntax analyzer\n ");
        String Answerr;
        Scanner scann = new Scanner(System.in);
        Answerr = scann.nextLine();
        SyntaxAnalyzer compiler = new SyntaxAnalyzer();
        if (Answerr.equalsIgnoreCase("Lexer analyzer")) {
            Lexer.initializeLists();
            Lexer.scanFile();
        }

        else {
            compiler.opinionsListMethod();

        }
    }
}

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Stack;

public class Main {

    private static int priority(char symbol) {
        return switch (symbol) {
            case '(' -> 0;
            case '+' -> 1;
            case '*' -> 2;
            default -> 0;
        };
    }

    private static int getVal(int a, int b, char op){
        return switch (op) {
            case '+' -> a + b;
            case '*' -> a * b;
            default -> 0;
        };
    }

    private static boolean evalpost(String infix, String s) {
        HashMap<Character, Integer> mp = new HashMap<>();
        int j = 0;
        for(int i = 0; i < infix.length(); i++) {
            if(Character.isLetter(infix.charAt(i))) {
                if(!mp.containsKey(infix.charAt(i))) {
                    mp.put(infix.charAt(i), s.charAt(j++)-'0');
                }
            }
        }

        Stack<Integer> val = new Stack<>();
        Stack<Character> ops = new Stack<>();

        for(int i = 0; i < infix.length(); i++) {
            if(infix.charAt(i) == ' ' || infix.charAt(i) == '~') {
                continue;
            }
            else if(infix.charAt(i) == '(') {
                ops.push(infix.charAt(i));
            }
            else if(Character.isLetter(infix.charAt(i))) {
                if(i > 0 && infix.charAt(i-1) == '~') {
                    val.push(1 - mp.get(infix.charAt(i)));
                }
                else {
                    val.push(mp.get(infix.charAt(i)));
                }
            }
            else if(infix.charAt(i) == ')') {
                while(!ops.isEmpty() && ops.peek() != '(') {
                    int a = val.pop();
                    int b = val.pop();

                    char op = ops.pop();

                    val.push(getVal(b, a, op));
                }

                if(!ops.isEmpty()) {
                    ops.pop();
                }
            }
            else {
                while(!ops.isEmpty() && priority(ops.peek()) >= priority(infix.charAt(i))) {
                    int a = val.pop();

                    int b = val.pop();

                    char op = ops.pop();

                    val.push(getVal(b, a, op));
                }
                ops.push(infix.charAt(i));
            }
        }

        while(!ops.empty()) {
            int a=val.pop();

            int b=val.pop();

            char op=ops.pop();

            val.push(getVal(b, a, op));
        }

        return val.peek() != 0;
    }

    private static boolean printTable(String s, ArrayList<String> posb) {
        int c = 0;
        for(int i = 0; i < posb.size(); i++) {
            for(int j = 0; j < posb.get(i).length(); j++) {
                if(posb.get(i).charAt(j) == '0') {
                    System.out.print("False\t");
                }
                else {
                    System.out.print("True\t");
                }
            }

            if(evalpost(s, posb.get(i))) {
                c++;
                System.out.println("True");
            }
            else {
                System.out.println("False");
            }
        }
        return c != 0;
    }

    private static void gen(StringBuilder t, int i, int n, ArrayList<String> posb) {
        if(i == n) {
            posb.add(t.toString());
            return;
        }
        t.append('0');
        gen(t, i+1, n, posb);
        t.deleteCharAt(t.length()-1);

        t.append('1');
        gen(t, i+1, n, posb);
        t.deleteCharAt(t.length()-1);
    }

    public static void main(String[] args) {
        int n;
        boolean result;

        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter number of variables: ");
        n = scanner.nextInt();

        StringBuilder t = new StringBuilder();

        ArrayList<String> posb = new ArrayList<>();

        gen(t, 0, n, posb);

        System.out.println("(Use + for OR, * for AND, ~for NOT");
        System.out.print("Enter the wff: ");

        String infix = scanner.next();

        System.out.println(infix);

        result = printTable(infix, posb);

        if(result) {
            System.out.print("\n=>The wff is valid.");
        }
        else {
            System.out.print("\n=>The wff is satisfiable.");
        }
    }

    // (a+~b)*c
}

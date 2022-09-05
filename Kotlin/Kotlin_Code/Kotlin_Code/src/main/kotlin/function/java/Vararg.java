package function.java;

public class Vararg {

    public static void main(String[] args) {
        printNumbers(1, 2, 3);
        printNumbers(1, 2, 3, 4);
    }

    public static void printNumbers(int ... numbers) {
        for (int number : numbers) {
            System.out.print(number + " ");
        }
        System.out.println();
    }
}

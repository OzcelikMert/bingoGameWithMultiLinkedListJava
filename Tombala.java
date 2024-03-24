import java.util.*;

public class Tombala {
    public static void main(String[] args) {
        Random rand = new Random();
        int[] randomPermutation = generatePermutation(90);

        MultiLinkedList player1Card = new MultiLinkedList(1);
        MultiLinkedList player2Card = new MultiLinkedList(2);

        player1Card.init();
        player2Card.init();

        player1Card.print();
        player2Card.print();

        System.out.println("Kartlar dağıtıldı ve oyunumuz başladı!");
        while (player1Card.filledRowCount < 3 && player2Card.filledRowCount < 3) {
            int index = rand.nextInt(randomPermutation.length);
            int drawnNumber = randomPermutation[index];
            System.out.println("Torbadan çekilen sayı: " + drawnNumber);

            randomPermutation = updatePermutation(randomPermutation, drawnNumber);

            boolean isMarked1 = player1Card.fillNumber(drawnNumber);
            boolean isMarked2 = player2Card.fillNumber(drawnNumber);

            if (isMarked1) {
                player1Card.isRowFilled();
            }

            if (isMarked2) {
                player2Card.isRowFilled();
            }

            player1Card.print();
            player2Card.print();
        }
    }

    public static int[] generatePermutation(int n) {
        int[] permutation = new int[n];
        for (int i = 0; i < n; i++) {
            permutation[i] = i + 1;
        }
        Random rand = new Random();
        for (int i = 0; i < n; i++) {
            int randIndex = rand.nextInt(n);
            int temp = permutation[i];
            permutation[i] = permutation[randIndex];
            permutation[randIndex] = temp;
        }
        return permutation;
    }

    public static int[] updatePermutation(int[] randomPermutation, int drawnNumber) {
        int[] newPermutation = new int[randomPermutation.length - 1];
        for (int i = 0, j = 0; i < randomPermutation.length; i++) {
            if (randomPermutation[i] != drawnNumber) {
                newPermutation[j] = randomPermutation[i];
                j++;
            }
        }
        return newPermutation;
    }
}

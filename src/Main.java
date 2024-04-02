import java.util.Scanner;

public class Main {
    public static void printPlayTable(int x, String[][] plaingFields) {
        int max = x * x;
        int test = 0;
        String separator;
        String before = "";

        for (int j = 0; j < plaingFields[0].length; j++) {

            for (int i = 0; i < plaingFields.length; i++)
            {
                test++;
                if ((i+1) % x != 0) {
                    separator = " | ";
                } else {
                    separator = "";
                }
                if (test< 10){
                    before = " ";
                } else {
                    before = "";
                }

                if (plaingFields[i][j] != null){
                    System.out.print(plaingFields[i][j] + separator);
                } else {
                    System.out.print(before + test + separator) ;
                }

            }
            System.out.println();
        }
    }
    
    public static boolean checkWin(int row, int col, String player, int size, String[][] plaingFields) {

        int winCount = 5;
        if(row < 5){
            winCount = 3;
        }
        if (row < 0 || col < 0 || row >= size || col >= size) {
            throw new IllegalArgumentException("Pozice mimo herní pole!");
        }

        // Kontrola horizontální řady
        if (checkLine(row, col, 0, 1, player, size, plaingFields) + checkLine(row, col, 0, -1, player, size, plaingFields) + 1 >= winCount) {
            return true;
        }
        // Kontrola vertikální řady
        if (checkLine(row, col, 1, 0, player, size, plaingFields) + checkLine(row, col, -1, 0, player, size, plaingFields) + 1 >= winCount) {
            return true;
        }
        // Kontrola hlavní diagonály
        if (checkLine(row, col, 1, 1, player, size, plaingFields) + checkLine(row, col, -1, -1, player, size, plaingFields) + 1 >= winCount) {
            return true;
        }
        // Kontrola vedlejší diagonály
        if (checkLine(row, col, 1, -1, player, size, plaingFields) + checkLine(row, col, -1, 1, player, size, plaingFields) + 1 >= winCount) {
            return true;
        }

        return false;
    }

    private static int checkLine(int row, int col, int dRow, int dCol, String player, int size, String[][] plaingFields) {
        int count = 0;
        int r = row + dRow;
        int c = col + dCol;
        while (r >= 0 && r < size && c >= 0 && c < size && plaingFields[r][c] == player) {
            count++;
            r += dRow;
            c += dCol;
        }
        return count;
    }

    public static void InsertData(int row, int col,  String[][] plaingFields, String player){
          plaingFields[row][col] = player;
    }

    public static void main(String[] args) {
        int maxField;
        int playerMmove;
        String player = "X";
        boolean wins = false;
        Scanner scanner = new Scanner(System.in);

        while (true){
             try{
                System.out.println("Zadej velikost pole: ");
                maxField = scanner.nextInt();
                break;
            } catch (Exception e){
                System.out.println("Zadej prosím číslo!");
                scanner.nextLine();
            }
        }

        String[][] plaingFields = new String[maxField][maxField];


        printPlayTable(maxField, plaingFields);


        while (!wins){
            System.out.println("Hraje hráč "+ player);

            System.out.println("Zadej vybrané pole. Hodnota musí být menší než " +  (int) Math.pow(maxField, 2));
            playerMmove = scanner.nextInt();

            if (playerMmove > (int) Math.pow(maxField, 2)){
                System.out.println("Zadal jste velké číslo, opakujte!");
                playerMmove = scanner.nextInt();
            }

            //převod pozice na souradnice
            int row;
            int col;

            if (playerMmove % maxField == 0){
                col = (playerMmove / maxField) - 1;
            } else {
                col = (playerMmove / maxField);
            }

            if (playerMmove <= maxField){
                row = playerMmove - 1;
            } else {
                if (playerMmove % maxField == 0){
                    row = maxField - 1;
                } else {
                    row = (playerMmove % maxField) - 1;
                }

            }
            InsertData(row, col, plaingFields, player);

            wins = checkWin(row, col, player, maxField, plaingFields);
            if (wins){
                System.out.println("Gratulujeme hráči " + player + " k výhře");
            } else {
                printPlayTable(maxField, plaingFields);
                player = player.equals("X") ? "0" : "X";
            }

        }

    }
}
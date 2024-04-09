import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        int maxField;
        int playerMove = 0;
        int row;
        int col;
        boolean wins = false;
        String player = "X";

        final int maxValue = 10;

        Scanner scanner = new Scanner(System.in);

        while (true) {
            try {
                System.out.println("Zadej velikost pole (max " + maxValue + "): ");
                maxField = scanner.nextInt();
                while (maxField > maxValue) {
                    System.out.println("Říkal jsem maximálně " + maxValue + "! Zadej znova prosím");
                    maxField = scanner.nextInt();
                }
                break;
            } catch (Exception e) {
                System.out.println("Zadej prosím číslo!");
                scanner.nextLine();
            }
        }

        String[][] plaingFields = new String[maxField][maxField];


        printPlayTable(maxField, plaingFields);


        while (!wins) {

            try {
                System.out.println("Hraje hráč " + player);

                System.out.println("Zadej vybrané pole. Hodnota musí být menší než " + (int) Math.pow(maxField, 2));
                playerMove = scanner.nextInt();

                if (playerMove > (int) Math.pow(maxField, 2)) {
                    System.out.println("Zadal jste velké číslo, opakujte!");
                    playerMove = scanner.nextInt();
                }

                col = getCol(playerMove, maxField);
                row = getRow(playerMove, maxField);

                while (!InsertData(row, col, plaingFields, player)){
                    System.out.println("Vybral jsi již zadané pole, opakuj!");
                    playerMove = scanner.nextInt();
                    col = getCol(playerMove, maxField);
                    row = getRow(playerMove, maxField);
                };


                wins = checkWin(row, col, player, maxField, plaingFields);

                if (wins) {
                    printPlayTable(maxField, plaingFields);
                    System.out.println("Gratulujeme hráči " + player + " k výhře");
                } else {
                    printPlayTable(maxField, plaingFields);
                    player = player.equals("X") ? "0" : "X";
                }
            } catch (Exception e) {
                System.out.println("Zadej prosím číslo!");
                scanner.nextLine();
            }
        }
    }

    public static int getCol(int playerMove, int maxField) {
        int col;

        if (playerMove % maxField == 0) {
            col = (playerMove / maxField) - 1;
        } else {
            col = (playerMove / maxField);
        }

        return col;
    }

    public static int getRow(int playerMove, int maxField) {
        int row;
        if (playerMove <= maxField) {
            row = playerMove - 1;
        } else {
            if (playerMove % maxField == 0) {
                row = maxField - 1;
            } else {
                row = (playerMove % maxField) - 1;
            }

        }
        return row;
    }


    public static void printPlayTable(int x, String[][] plaingFields) {
        int test = 0;
        String separator;
        String before = "";

        for (int j = 0; j < plaingFields[0].length; j++) {

            for (int i = 0; i < plaingFields.length; i++) {
                test++;
                if ((i + 1) % x != 0) {
                    separator = " | ";
                } else {
                    separator = "";
                }
                if (test < 10) {
                    before = " ";
                } else {
                    before = "";
                }

                if (plaingFields[i][j] != null) {
                    System.out.print(plaingFields[i][j] + separator);
                } else {
                    System.out.print(before + test + separator);
                }

            }
            System.out.println();
        }
    }

    public static boolean checkWin(int row, int col, String player, int size, String[][] plaingFields) {

        int winCount = 5;
        if (row < 5) {
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
        return checkLine(row, col, 1, -1, player, size, plaingFields) + checkLine(row, col, -1, 1, player, size, plaingFields) + 1 >= winCount;
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

    public static void Play (int playerMove, String player){

    }

    public static boolean InsertData(int row, int col, String[][] plaingFields, String player) {
        if (plaingFields[row][col] != "X" && plaingFields[row][col] != "0") {
            plaingFields[row][col] = player;
            return true;
        } else {
            return false;
        }
    }
}
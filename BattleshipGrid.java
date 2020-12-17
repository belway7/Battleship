import java.util.Scanner;

//Team NUFAN: Bruce French, Braden Kadlec

public class BattleshipGrid {

    static Scanner scan = new Scanner(System.in);
    static int playersRemaining = 0;
    static String hOrV;
    static int attackYAxis;
    static int attackXAxis;
    static int xAxis;
    static int yAxis;
    static int playerWin;
    static int gridSelector;
    static int gridSize;
    static int playerCount; 
    static int[][][] playerGrids = new int[playerCount][gridSize][gridSize];
    static int[] playerLives = new int [4];

    // allows player count to be set by user
    public static void PlayerCreator() {
        System.out.println("How many players are playing? (Limit 4)");
        playerCount = scan.nextInt();
        while (playerCount > 4) {
            System.out.println("Try again. Your player count must be between 1 and 4");
            playerCount = scan.nextInt();
        }
        for (int i = 0; i < playerCount; i++) {
            playerLives[i] = 17;
            playersRemaining++;
        }
    }

    // allows the grid to be sized dynamically based on user input
    public static void DynamicGrid() {
        System.out.println("How large would you like the grid? (min 8, max 20)");
        gridSize = scan.nextInt();
        while ((gridSize < 8) || (gridSize > 20)) {
            System.out.println("Try again. The grid size must be between 8 and 20");
            gridSize = scan.nextInt();
        }
        playerGrids = new int[playerCount * 2][gridSize][gridSize];
        for (int i = 0; i < playerCount * 2; i++) {
            playerGrids[i][0][0] = i;
            for (int x = 0; x < gridSize; x++) {
                playerGrids[i][x][0] = 0;
                for (int y = 0; y < gridSize; y++) {
                    playerGrids[i][x][y] = 0;
                }
            }
        }
    }

    // outputs grids to give visual interface to users
    public static void GridOutput(int gridSelector) throws InterruptedException {
        char topBoard = 'A';
        System.out.print("     ");
        for (int y = 0; y < gridSize; y++) {
            System.out.printf("%-5s", topBoard);
            topBoard++;
        }
        System.out.printf("\n\n");
        for (int i = 0; i < gridSize; i++) {
            System.out.printf("%-5s", i + 1);
            for (int x = 0; x < gridSize; x++) {
                System.out.printf("%-5s", playerGrids[gridSelector][i][x]);
            }
            System.out.println("");
        }
        Thread.sleep(2000);
    }    

    // method that sets values for ShipPlacement 
    public static void ShipSelector(int shipSize) {
        Scanner scan = new Scanner(System.in);
        String carrierPlacement = scan.nextLine();

        xAxis = carrierPlacement.charAt(0);
        yAxis = carrierPlacement.charAt(1);

        while (xAxis > 84) {
            System.out.println("Try again. Your input must be a capital letter");
            carrierPlacement = scan.nextLine();
            xAxis = carrierPlacement.charAt(0);
        }

        yAxis = yAxis - '0';

        if (carrierPlacement.length() > 2) {
            int yAxisTemp = carrierPlacement.charAt(2);
            yAxisTemp = yAxisTemp - '0';
            yAxis = yAxis * 10;
            yAxis = yAxis + yAxisTemp;
        }

        System.out.println("Would you like it to be placed horizontally or vertically. (Type h or v)");
        hOrV = scan.next();
        while ((!hOrV.equals("h")) && (!hOrV.equals("v"))) {
            System.out.println("Try again. Your input must be an h or a v");
            hOrV = scan.next();
        }

    }

    // places ships within each grid
    public static void ShipPlacement(int size, String hOrV, int xAxis, int yAxis, int gridSelector) {
        xAxis = xAxis - 'A';
        if (hOrV.equals("h")) {
            for (int i = 0; i < size; i++) {
                playerGrids[gridSelector][yAxis - 1][xAxis + i] = 1;
            }
        }
        else {
            for (int i = 0; i < size; i++) {
                playerGrids[gridSelector][yAxis + i - 1][xAxis] = 1;
            }
        }

    }

    // handles turn by turn attacks for each player
    public static void AttackTurn(int playerTurn) throws InterruptedException {
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n"); 
        System.out.println("Player " + playerTurn + " it is now your turn. Who would you like to attack? (enter the number of the player you wish to attack)");

        int playerSelection = scan.nextInt();

        while (playerSelection == playerTurn) {
            System.out.println("You can't attack yourself silly.");
            playerSelection = scan.nextInt();
        }

        System.out.println("Player " + playerTurn + ", where would you like to attack? (Ex. A3 Use Caps!!)");
        String attackPlacement = scan.next();

        attackXAxis = attackPlacement.charAt(0);
        attackYAxis = attackPlacement.charAt(1);

        while (attackXAxis > 84) {
            System.out.println("Try again. Your input must be a capital letter");
            attackPlacement = scan.nextLine();
            attackXAxis = attackPlacement.charAt(0);
        }

        attackYAxis = attackYAxis - '0';
        attackXAxis = attackXAxis - 'A';

        if (attackPlacement.length() > 2) {
            int yAxisTemp = attackPlacement.charAt(2);
            yAxisTemp = yAxisTemp - '0';
            attackYAxis = attackYAxis * 10;
            attackYAxis = attackYAxis + yAxisTemp;
        }

        int boardSelection = 0;
        if (playerSelection == 1) {
            boardSelection = 0;
        }
        else if (playerSelection == 2) {
            boardSelection = 2;
        }
        else if (playerSelection == 3) {
            boardSelection = 4;
        }
        else if (playerSelection == 4) {
            boardSelection = 6;
        }

        // error checking for already hit location
        while (playerGrids[boardSelection + 1][attackYAxis - 1][attackXAxis] == 1) {
            System.out.println("That position has already been hit.");
            attackPlacement = scan.next();

            attackXAxis = attackPlacement.charAt(0);
            attackYAxis = attackPlacement.charAt(1);

            while (attackXAxis > 84) {
                System.out.println("Try again. Your input must be a capital letter");
                attackPlacement = scan.nextLine();
                attackXAxis = attackPlacement.charAt(0);
            }

            attackYAxis = attackYAxis - '0';
            attackXAxis = attackXAxis - 'A';

            if (attackPlacement.length() > 2) {
                int yAxisTemp = attackPlacement.charAt(2);
                yAxisTemp = yAxisTemp - '0';
                attackYAxis = attackYAxis * 10;
                attackYAxis = attackYAxis + yAxisTemp;
            }

            boardSelection = 0;
            if (playerSelection == 1) {
                boardSelection = 0;
            }
            else if (playerSelection == 2) {
                boardSelection = 2;
            }
            else if (playerSelection == 3) {
                boardSelection = 4;
            }
            else if (playerSelection == 4) {
                boardSelection = 6;
            }
        }
        
        //error checking for already missed location
        while (playerGrids[boardSelection + 1][attackYAxis - 1][attackXAxis] == 5) {
            System.out.println("That position has already been hit.");
            attackPlacement = scan.next();

            attackXAxis = attackPlacement.charAt(0);
            attackYAxis = attackPlacement.charAt(1);

            while (attackXAxis > 84) {
                System.out.println("Try again. Your input must be a capital letter");
                attackPlacement = scan.nextLine();
                attackXAxis = attackPlacement.charAt(0);
            }

            attackYAxis = attackYAxis - '0';
            attackXAxis = attackXAxis - 'A';

            if (attackPlacement.length() > 2) {
                int yAxisTemp = attackPlacement.charAt(2);
                yAxisTemp = yAxisTemp - '0';
                attackYAxis = attackYAxis * 10;
                attackYAxis = attackYAxis + yAxisTemp;
            }

            boardSelection = 0;
            if (playerSelection == 1) {
                boardSelection = 0;
            }
            else if (playerSelection == 2) {
                boardSelection = 2;
            }
            else if (playerSelection == 3) {
                boardSelection = 4;
            }
            else if (playerSelection == 4) {
                boardSelection = 6;
            }
        }

        // performs hit and outputs user hit/miss board
        if (playerGrids[boardSelection][attackYAxis - 1][attackXAxis] == 1) {
            System.out.println("Congrats. You got a hit.");
            playerGrids[boardSelection + 1][attackYAxis - 1][attackXAxis] = 1;
            GridOutput(boardSelection + 1);
            playerLives[playerSelection - 1] -= 1;
            if (playerLives[playerSelection - 1] == 0) {
                playersRemaining--;
            }
        }
        // performs miss and outputs user hit/miss board
        else {
            System.out.println("Oh no you missed!");
            playerGrids[boardSelection + 1][attackYAxis - 1][attackXAxis] = 5;
            GridOutput(boardSelection + 1);
        }
    }

    //reads out winner
    public static void EndGame() {
        if (playerLives[0] > 0) {
            System.out.println("Congrats player 1! You won.");
        }
        else if (playerLives[1] > 0) {
            System.out.println("Congrats player 2! You won.");
        }
        else if (playerLives[2] > 0) {
            System.out.println("Congrats player 3! You won.");
        }
        else if (playerLives[3] > 0) {
            System.out.println("Congrats player 4! You won.");
        }
    }

    // recursive method that continues to checks for 1 remaining player while initiating the turn
    public static void TurnTaker() throws InterruptedException {
        if (playersRemaining == 1) { //base case
            EndGame();
        }

        else {
            for (int i = 0; i < playerCount; i++) {
                if (playerLives[i] != 0) {
                    AttackTurn(i + 1);
                }
            }
            TurnTaker();
        }  
    }

    
    //performs set up for turns to begin
    public static void main(String [] args) throws InterruptedException {
        PlayerCreator();
        DynamicGrid();

        for (int i = 0, j = 1; i < playerCount * 2; i += 2, j++) {
            System.out.println("Player " + j + " please get ready to place your ships!");
            GridOutput(i);
            System.out.println("Where would you like to place the carrier (5 spaces)? (Ex. A3 Use Caps!!)");
            ShipSelector(5);
            ShipPlacement(5, hOrV, xAxis, yAxis, i);
            GridOutput(i);
            System.out.println("Where would you like to place the battleship (4 spaces)? (Ex. A3 Use Caps!!)");
            ShipSelector(4);
            ShipPlacement(4, hOrV, xAxis, yAxis, i);
            GridOutput(i);
            System.out.println("Where would you like to place the destroyer (3 spaces)? (Ex. A3. Use Caps!!)");
            ShipSelector(3);
            ShipPlacement(3, hOrV, xAxis, yAxis, i);
            GridOutput(i);
            System.out.println("Where would you like to place the submarine (3 spaces)? (Ex. A3 Use Caps!!)");
            ShipSelector(3);
            ShipPlacement(3, hOrV, xAxis, yAxis, i);
            GridOutput(i);
            System.out.println("Where would you like to place the patrol boat (2 spaces)? (Ex. A3 Use Caps!!)");
            ShipSelector(2);
            ShipPlacement(2, hOrV, xAxis, yAxis, i);
            GridOutput(i);
            System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        }

        // initiates recursive method
        TurnTaker();
    }
}

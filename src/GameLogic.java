import javax.swing.JButton;
import java.awt.Image;
import javax.swing.ImageIcon;

/**
 * The GameLogic class represents the state and behavior of a Tic Tac Toe game.
 * It keeps track of the game board, the current player, and the game statistics
 * (number of wins and ties for each player).
 * 
 * It also provides methods for checking if the game has ended, setting the
 * label of a button on the board, clearing the board for a new game, and
 * checking if there is a winner.
 * 
 * This class uses constants for the values of the players and the empty cell.
 * It
 * also uses the JButton class to represent the cells of the game board.
 * 
 * @author Arun Karki
 * @version April 2, 2023
 */

public class GameLogic {

    public static final String PLAYER_X = "X"; // player using "X"
    public static final String PLAYER_O = "O"; // player using "O"
    public static final String EMPTY = " "; // empty cell
    public static final String TIE = "T"; // game ended in a tie
    public static String DEFAULT_PLAYER = PLAYER_X; // starting player

    // game stats; X wins, O wins, Ties
    private int plrXWins;
    private int plrOWins;
    private int plrTie;

    // current player (PLAYER_X or PLAYER_O)
    private String player;

    // winner: PLAYER_X, PLAYER_O, TIE, EMPTY = in progress
    private String winner;

    // true when the game is done, false otherwise
    private boolean gameEnded;

    // number of squares still free
    private int numFreeSquares;

    // 2d array of JButtons to represent the board
    private JButton[][] board;

    /**
     * The GameLogic constructor method for constructing the GameLogic.
     * Reset all stats to default values and other settings.
     */
    public GameLogic() {
        this.gameEnded = false;
        this.plrXWins = 0;
        this.plrOWins = 0;
        this.plrTie = 0;
        System.out.println("GameLogic.java compiled");
    }

    /**
     * Set a reference to a 2D array of JButton board.
     * 
     * @param board A 2d array board, retrive its reference for use within the class
     */
    public void setBoard(JButton[][] board) {
        this.board = board;
    }

    /**
     * Return true if a winner is established; the game has ended.
     * Return true if a tie has occured too. False in other cases.
     * 
     * @param row The 'x' coordinate of where the JButton is stored
     * @param col The 'y' coordinate of where the JButton is stored
     * @return true if the same has ended; false otherwise.
     */
    public boolean gameWinner(int row, int col) {
        // see if the game is over
        if (haveWinner(row, col)) {
            // must be the player who just went
            winner = player;
            if (winner.equals(PLAYER_X))
                // stats for X wins go up
                plrXWins++;
            else
                // stats for O win go up
                plrOWins++;
            gameEnded = true;
        }
        // if no one has won yet, and no spaces are left in the board then game tied.
        else if (numFreeSquares == 0) {
            winner = TIE; // board is full so it's a tie
            gameEnded = true;
            plrTie++;
        }
        return gameEnded;
    }

    /**
     * Return number of wins for player X.
     * 
     * @return Return the number of total wins player X has.
     */
    public int getPlrXWins() {
        return this.plrXWins;
    }

    /**
     * Return number of wins for player O.
     * 
     * @return Return the number of total wins player O has.
     */
    public int getPlrOWins() {
        return this.plrOWins;
    }

    /**
     * Return number of ties that has occured.
     * 
     * @return Return the number of total ties.
     */
    public int getPlrTies() {
        return this.plrTie;
    }

    /**
     * Reset all stats in the game to default. Used to clearing the game; a new
     * game.
     */
    public void resetStats() {
        this.plrXWins = 0;
        this.plrOWins = 0;
        this.plrTie = 0;
    }

    /**
     * Return if the game has ended or not.
     * 
     * @return true if the game has ended; false otherwise.
     */
    public boolean getGameEnded() {
        return this.gameEnded;
    }

    /**
     * Place an X or O in the board. Resize the icons images to fit the JButton.
     * Decrement the number of spaces, and rotatte between players.
     * 
     * @param row The 'x' coordinate of where the JButton is stored
     * @param col The 'y' coordinate of where the JButton is stored
     */
    public void setLabel(int row, int col) {

        // identify which button was clicked
        JButton button = board[row][col];

        // if the button place isn't taken
        if (button.getText().equals(EMPTY)) {

            // place on the board
            numFreeSquares--;
            button.setText(player);
            // set the image on the board
            if (player.equals(PLAYER_X)) {
                ImageIcon resizedIcon = new ImageIcon(UI.X_ICON.getImage().getScaledInstance(button.getWidth(),
                        button.getHeight(), Image.SCALE_SMOOTH));
                button.setIcon(resizedIcon);
            } else {
                ImageIcon resizedIcon = new ImageIcon(UI.O_ICON.getImage().getScaledInstance(button.getWidth(),
                        button.getHeight(), Image.SCALE_SMOOTH));
                button.setIcon(resizedIcon);
            }

            // check to see if the game has ended; set properties for other checks
            gameWinner(row, col); // game ended? no?

            // alternate between player turns
            if (player.equals(PLAYER_X))
                player = PLAYER_O;
            else
                player = PLAYER_X;

        }
    }

    /**
     * Return the current player's turn.
     * 
     * @return The current player's turn.
     */
    public String getPlayer() {
        // will be the opposite as player contains the player that just had their turn
        if (this.player.equals(PLAYER_X))
            return PLAYER_O;
        return PLAYER_X;
    }

    /**
     * Return the player that won.
     * 
     * @return The player that won the game.
     */
    public String getWinner() {
        return this.winner;
    }

    /**
     * Swap the default/starting player of the game.
     */
    public void swapPlayer() {
        if (DEFAULT_PLAYER.equals(PLAYER_X))
            DEFAULT_PLAYER = PLAYER_O;
        else
            DEFAULT_PLAYER = PLAYER_X;
    }

    /**
     * Sets everything up for a new game. Marks all squares in the Tic Tac Toe board
     * as empty,
     * and indicates no winner yet, 9 free squares and the current player is player
     * X.
     */
    public void clearBoard() {
        // set all board elements to default; empty
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j].setText(EMPTY);
                board[i][j].setIcon(UI.BLANK_ICON);
            }
        }
        winner = EMPTY;
        numFreeSquares = 9;
        player = DEFAULT_PLAYER; // Player X always has the first turn.
        gameEnded = false;
    }

    /**
     * Returns true if filling the given square gives us a winner, and false
     * otherwise.
     *
     * @param int row of square just set
     * @param int col of square just set
     * 
     * @return true if we have a winner, false otherwise
     */
    public boolean haveWinner(int row, int col) {
        // unless at least 5 squares have been filled, we don't need to go any further
        // (the earliest we can have a winner is after player X's 3rd move).

        if (numFreeSquares > 4)
            return false;

        // Note: We don't need to check all rows, columns, and diagonals, only those
        // that contain the latest filled square. We know that we have a winner
        // if all 3 squares are the same, as they can't all be blank (as the latest
        // filled square is one of them).

        // check row "row"
        if (board[row][0].getText().equals(board[row][1].getText()) &&
                board[row][0].getText().equals(board[row][2].getText()))
            return true;

        // check column "col"
        if (board[0][col].getText().equals(board[1][col].getText()) &&
                board[0][col].getText().equals(board[2][col].getText()))
            return true;

        // if row=col check one diagonal
        if (row == col)
            if (board[0][0].getText().equals(board[1][1].getText()) &&
                    board[0][0].getText().equals(board[2][2].getText()))
                return true;

        // if row=2-col check other diagonal
        if (row == 2 - col)
            if (board[0][2].getText().equals(board[1][1].getText()) &&
                    board[0][2].getText().equals(board[2][0].getText()))
                return true;

        // no winner yet
        return false;
    }
}

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;

/**
 * The UI class represents the user interface for the Tic-Tac-Toe game. It
 * provides methods for building and updating
 * the graphical interface, including the game board, status and statistics
 * labels, menu bar items, and button enablement.
 * 
 * It also contains constants for the game name, window dimensions, and image
 * icons for the blank, X, and O cells.
 * 
 * @author Arun Karki
 * @version April 2, 2023
 */

public class UI {

    // propertis of the game; dimensions; stat spacings; game name
    public static int WIDTH = 500;
    public static int HEIGHT = 500;
    public static String GAME_NAME = "Tic-Tac-Toe";
    public static String STATS_SPACING = "              ";

    // fields for UI components
    private JFrame frame;
    private JPanel panel;
    private Container contentPanel;
    private JMenuBar menuBar;

    // menu bar items
    private JMenuItem newGame;
    private JMenuItem firstPlayer;
    private JMenuItem resetStats;
    private JMenuItem quit;

    // labels for status and statistics
    private JLabel gameStatus;
    private JLabel gameStatistics;

    // 2d array of JButtons to represent the board
    private JButton[][] board = new JButton[3][3];

    // image icons for empty button and X & O placements
    public static ImageIcon BLANK_ICON = new ImageIcon("images/white.png");
    public static ImageIcon X_ICON = new ImageIcon("images/X.png");
    public static ImageIcon O_ICON = new ImageIcon("images/O.png");

    /**
     * The UI constructor method for constructing the UI.
     * Build the main Frame and the panels and buttons. This will essentially build
     * everything
     * that is visible in the UI.
     */
    public UI() {

        frame = new JFrame(GAME_NAME);

        frame.setResizable(false);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(WIDTH, HEIGHT);

        contentPanel = frame.getContentPane();

        buildPanel();
        buildButtons();
        // a 3x3 grid to place the 2d array of buttons
        panel.setLayout(new GridLayout(3, 3));
        buildStatusLabel();
        buildStatsLabel();
        // build board
        System.out.println("UI.java compiled");
    }

    /**
     * Build the status label that shows the game's status.
     * Shows player's turn and if the game is in progress or ended or is just
     * starting.
     */
    public void buildStatusLabel() {
        gameStatus = new JLabel();
        gameStatus.setText(" Game Starting: " + GameLogic.DEFAULT_PLAYER + "'s turn");
        // place it at the bottom of the frame
        frame.add(gameStatus, BorderLayout.SOUTH);
    }

    /**
     * Build the statistics label that shows all stats.
     * Show the number of players each player has and the total ties that occured.
     */
    public void buildStatsLabel() {
        gameStatistics = new JLabel();
        gameStatistics.setText(
                " Statistics:" + STATS_SPACING + "X Wins: 0" + STATS_SPACING + "O Wins: 0" + STATS_SPACING + "Ties: 0");
        // place it at the top of the frame
        frame.add(gameStatistics, BorderLayout.NORTH);
    }

    /**
     * Build the panel that will hold the 2d array of JButtons
     * Will have a 3x3 grid layout.
     */
    public void buildPanel() {
        panel = new JPanel();
        panel.setOpaque(false);
        contentPanel.add(panel);
    }

    /**
     * Set the text of the label to be the game's current status.
     * 
     * @param status String representation of the game's current status
     */
    public void updateStatus(String status) {
        gameStatus.setText(status);
    }

    /**
     * Set the text of the label to be the game's current stats.
     * 
     * @param stats String representation of the game's current stats
     */
    public void updateStatistics(String stats) {
        gameStatistics.setText(stats);
    }

    /**
     * Construct the Menu and place the items in the menu.
     * Return all menu items in an array form.
     * 
     * @return An array of JMenuItem that contains all menu items.
     */
    public JMenuItem[] buildMenu() {
        menuBar = new JMenuBar();
        JMenu menu = new JMenu("Menu");

        newGame = new JMenuItem("New Game");
        firstPlayer = new JMenuItem("Swap First Player");
        resetStats = new JMenuItem("Reset Stats");
        quit = new JMenuItem("Quit");

        menu.add(newGame);
        menu.add(firstPlayer);
        menu.add(resetStats);
        menu.add(quit);

        menuBar.add(menu);
        frame.setJMenuBar(menuBar);
        return new JMenuItem[] { newGame, firstPlayer, resetStats, quit };
    }

    /**
     * Return the 2d array of JButtons. Used to access from other classes for
     * various uses.
     *
     * @return The 2d array of JButtons
     */
    public JButton[][] getBoard() {
        return this.board;
    }

    /**
     * Build a 2D array of JButton to represent the game board.
     * 
     * @return The 2d array of JButtons that was created
     */
    public JButton[][] buildButtons() {
        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                JButton button = new JButton();
                button.setName("[" + x + ", " + y + "]");
                panel.add(button);
                board[x][y] = button; // add to
                button.setIcon(BLANK_ICON);
            }
        }
        return board;
    }

    /**
     * Enable or disable every single button on the board.
     * Turn off when the game has concluded.
     * Turn on when a new game begins.
     * 
     * @param enable A boolean to indicate whether to turn on or off the buttons;
     *               true for on.
     */
    public void buttonEnable(boolean enable) { // turn buttons on or off
        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                board[x][y].setEnabled(enable);
            }
        }
    }

    /**
     * Destroy the Frame of the game
     */
    public void terminate() {
        frame.dispose();
    }
}

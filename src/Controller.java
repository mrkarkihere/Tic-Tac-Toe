// asd
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JMenuItem;

/**
 * The Controller class acts as a mediator between the View and Model classes in
 * a Tic-Tac-Toe game.
 * It listens for user actions, such as button clicks and menu selections, and
 * updates the game's state accordingly.
 * 
 * The class sets up and initializes the game board and menu items, and
 * implements the ActionListener interface to handle
 * user interactions. When a button is clicked, the corresponding coordinates
 * are passed to the Model class to place an X or O
 * on the board.
 * 
 * The class also checks for game ending conditions and updates the UI
 * accordingly. Menu items such as "New Game",
 * "Swap 1st Player", "Reset Stats", and "Quit" are also implemented in this
 * class.
 * 
 * The getCoordinatesFromString method is a helper method that parses a string
 * in the format "[x, y]" and returns the
 * corresponding integer coordinates as an array.
 * 
 * @author Arun Karki
 * @version April 2, 2023
 */

public class Controller implements ActionListener {

    // main calling stuff for now
    public static void main(String[] args) {
        Controller controller = new Controller(new View(), new Model());
        System.out.println("up and running...");
    }

    // 2d array of JButtons to represent the board
    private JButton[][] board;

    /* [0] = new game, [1] = swap 1st player, [2] = reset stats, [3] = quit */
    private JMenuItem[] menuItems; // menu items

    // the View class instance for communication
    private View view;

    // the Model class instance for communication
    private Model model;

    /**
     * The Controller constructor method for constructing the MVC framework.
     * Initalize the game and set up the board.
     */
    public Controller(View view, Model model) {

        this.view = view;
        this.model = model;
        this.board = view.getBoard();
        this.menuItems = view.buildMenu();

        model.setBoard(board);
        setButtonListener();
        setMenuItemListener();
        model.clearBoard();

        System.out.println("Controller.java compiled");
    }

    /**
     * Take all the buttons on the board and assign them an ActionListener to detect
     * clicks.
     */
    public void setButtonListener() {
        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                board[x][y].addActionListener(this); // add action listener to each button on board
            }
        }
    }

    /**
     * Take all Menu items and assign ActionListeners to them for click detection.
     */
    public void setMenuItemListener() {
        for (int i = 0; i < menuItems.length; i++) {
            menuItems[i].addActionListener(this);
        }
    }

    /**
     * Method called when a Menu item or JButton is clicked.
     * Check to see game status; if a winner exists, placing a label, and all other
     * Menu items.
     * Update the game statistics and show player turns.
     * 
     * @param event The event that caused the method to trigger, used to detect
     *              which button or menu item
     */
    public void actionPerformed(ActionEvent event) {

        // all buttons; so all placements on board for our purposes
        if (event.getSource().getClass() == JButton.class) {
            // get button clicked
            JButton button = (JButton) event.getSource();

            // retrieve the coordinate of the button on the board as a [x,y] array
            int[] buttonCoordinate = getCoordinatesFromString(button.getName());
            int x = buttonCoordinate[0];
            int y = buttonCoordinate[1];

            // update the status of the game, whose turn it is
            view.updateStatus(" Game in progress: " + model.getPlayer() + "'s turn");
            // set an X or O in the board
            model.setLabel(x, y);

            // has the game ended yet or not
            boolean result = model.getGameEnded();

            if (result) { // check to see if game ended
                view.buttonEnable(false); // disable all buttons

                // lets update game status as we play
                if (model.getWinner().equals(model.TIE))
                    view.updateStatus(" Game over: TIE!");
                else
                    view.updateStatus(" Game over: " + model.getWinner() + " won!");

                // since the game ended, update the statistics of the game.
                view.updateStatistics(" Statistics:" + View.STATS_SPACING + "X Wins: " + model.getPlrXWins()
                        + View.STATS_SPACING + "O Wins: " + model.getPlrOWins() + View.STATS_SPACING + "Ties: "
                        + model.getPlrTies());
            }

            // all the menu bar stuff, lets do each case now
        } else if (event.getSource().getClass() == JMenuItem.class) {

            /* [0] = new game, [1] = swap 1st player, [2] = reset stats, [3] = quit */
            if (event.getSource() == menuItems[0]) { // new game
                // clear the board, reset everything, start from scratch
                model.clearBoard();
                // turn the buttons on again in case they are off
                view.buttonEnable(true);
                // update the status of the game and statistics
                view.updateStatus(" Game Starting: " + Model.DEFAULT_PLAYER + "'s turn");
                view.updateStatistics(" Statistics:" + View.STATS_SPACING + "X Wins: " + model.getPlrXWins()
                        + View.STATS_SPACING + "O Wins: " + model.getPlrOWins() + View.STATS_SPACING + "Ties: "
                        + model.getPlrTies());
            } else if (event.getSource() == menuItems[1]) { // swap 1st player
                model.swapPlayer();
            } else if (event.getSource() == menuItems[2]) { // reset stats
                // reset game stats and update JLabel
                model.resetStats();
                view.updateStatistics(" Statistics:" + View.STATS_SPACING + "X Wins: " + model.getPlrXWins()
                        + View.STATS_SPACING + "O Wins: " + model.getPlrOWins() + View.STATS_SPACING + "Ties: "
                        + model.getPlrTies());
            } else if (event.getSource() == menuItems[3]) { // quit
                // kill the ui and free the memory
                view.terminate();
                view = null;
                model = null;
                System.exit(0); // terminate any systems
            }
        }
    }

    /**
     * Take a string "[x, y]" and output it in an array form [x, y].
     * Used to find the coordinate of a JButton.
     * By default the JButton names are set to be their coordinates in the board.
     * 
     * @param str The name of the JButton in the form "[x, y]"
     * @return An array of [x, y] which represents the coordinate of the button
     */
    public static int[] getCoordinatesFromString(String str) {

        // remove the square brackets and split the string at the comma
        String[] parts = str.substring(1, str.length() - 1).split(", ");

        // get the [0] and [1] indexes store them in an array.
        int x = Integer.parseInt(parts[0]);
        int y = Integer.parseInt(parts[1]);
        return new int[] { x, y };
    }

}

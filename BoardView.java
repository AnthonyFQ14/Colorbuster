import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.HashSet;

public class BoardView extends JPanel {

    GridBagLayout gridBagLayout = new GridBagLayout();
    Board board;
    TileView [][] tileViews = new TileView[6][6];
    ActionListener tileTouchedListener;
    public ScoreManager scoreManager;
    public JPopupMenu popupMenu = new JPopupMenu();
    public Scoreboard scoreboard;
    public GameTimer gameTimer;

    public BoardView(ActionListener tileTouchedListener, ScoreManager scoreManager, Scoreboard scoreboard, GameTimer gameTimer) throws SQLException {
        this.scoreManager = scoreManager;
        this.tileTouchedListener = tileTouchedListener;
        this.scoreboard = scoreboard;
        this.gameTimer = gameTimer;

        board = Board.getInstance(scoreManager);


        createBoardView(tileTouchedListener, scoreManager);

    }

    public void createBoardView(ActionListener tileTouchedListener, ScoreManager scoreManager){
        this.scoreManager = scoreManager;
        setBackground(Color.WHITE);
        tileViews = new TileView[6][6];
        setPreferredSize(new Dimension(900, 900));
        int gridx = 0;
        int gridy = 0;
        GridBagConstraints c = new GridBagConstraints();

        setLayout(gridBagLayout);
        for (int rows = 0; rows < 6; rows++) {
            for (int cols = 0; cols < 6; cols++) {

                c.gridx = gridx;
                c.gridy = gridy;
                TileView tileView = new TileView(board.tileAt(rows, cols));
                tileView.addListener(tileTouchedListener);
                tileViews[rows][cols] = tileView;
                add(tileView, c);
                gridx++;
            }
            gridx = 0;
            gridy++;
        }
    }

    public void tileTouched(TileView tileView) throws SQLException {
        HashSet<Tile> matches = new HashSet<Tile>();
        board.locateNeighbors(tileView.getRow(), tileView.getCol(), tileView.getColor(), matches);

        System.out.println();
        if(matches.size() >= 3){
            board.removeMatches(matches);
            board.fixColumns();
            updateBoardView();

            board.printBoard();
            repaint();
            revalidate();
        }
        if(!board.isMoveAvailable()){

            JLabel gameOver = new JLabel("No moves remaining, Please start a new game");
            gameOver.setFont(new Font("Tsuki Typeface", Font.PLAIN, 30));
            popupMenu.setLocation(500,200);
            popupMenu.add(gameOver);
            add(popupMenu);
            popupMenu.setVisible(true);
            for (int rows = 0; rows < 6; rows++) {
                for (int cols = 0; cols < 6; cols++) {
                    tileViews[rows][cols].removeActionListener(tileTouchedListener);
                }
            }
            scoreboard.gameOver(scoreManager.getScore());

        }

    }

    public void timesUp() throws SQLException {
        JLabel gameOver = new JLabel("Time's Up, Please start a new game");
        gameOver.setFont(new Font("Tsuki Typeface", Font.PLAIN, 30));
        popupMenu.setLocation(550,200);
        popupMenu.add(gameOver);
        add(popupMenu);
        popupMenu.setVisible(true);
        for (int rows = 0; rows < 6; rows++) {
            for (int cols = 0; cols < 6; cols++) {
                tileViews[rows][cols].removeActionListener(tileTouchedListener);
            }
        }
        scoreboard.gameOver(scoreManager.getScore());
    }

    public void newGame(){
        board.newBoard();
        popupMenu.setVisible(false);
        createBoardView(tileTouchedListener, scoreManager);
    }
    public void updateBoardView(){

        removeAll();
        repaint();
        revalidate();

        createBoardView(tileTouchedListener, scoreManager);

    }


}
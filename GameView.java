
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class GameView {

    private JFrame frame;
    private JPanel panel;
    private JLabel label;
    private JLabel timerLabel;
    public BoardView boardView;
    private NewGame newGame;
    private ScoreBoardButton scoreBoardButton;
    private ActionListener newGameListener;
    private ActionListener tileTouchedListener;
    private ActionListener scoreBoardListener;
    private ScoreManager scoreManager = new ScoreManager();
    private Scoreboard scoreboard;
    private RulesButton rulesButton;
    private ActionListener rulesListener;
    private GameTimer gameTimer;
    private TimerObserver timerObserver;


    public GameView(ActionListener newGameListener, ActionListener tileTouchedListener, ActionListener scoreBoardListener, Scoreboard scoreboard, ActionListener rulesListener, GameTimer gameTimer) throws SQLException{

        this.newGameListener = newGameListener;
        this.tileTouchedListener = tileTouchedListener;
        this.scoreBoardListener = scoreBoardListener;
        this.scoreboard = scoreboard;
        this.rulesListener = rulesListener;
        this.gameTimer = gameTimer;

        frame = new JFrame("Color Buster");
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));


        label = new JLabel("Score: " + scoreManager.getScore());
        label.setFont(new Font("Helvetica", Font.PLAIN, 65));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(label);

        timerObserver = new TimerObserver(this.gameTimer, this);
        gameTimer.startTimer();

        timerLabel = new JLabel();
        timerLabel.setFont(new Font("Helvetica", Font.PLAIN, 30));
        timerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(timerLabel);

        boardView = new BoardView(tileTouchedListener, scoreManager, scoreboard, this.gameTimer);
        if(!boardView.board.isMoveAvailable()){
            newGame();
        }

        boardView.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(boardView);

        newGame = new NewGame(newGameListener);
        newGame.setAlignmentX(Component.CENTER_ALIGNMENT);

        scoreBoardButton = new ScoreBoardButton(scoreBoardListener);
        scoreBoardButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        rulesButton = new RulesButton(rulesListener);
        rulesButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(newGame);
        panel.add(new JLabel(" "));
        panel.add(new JLabel(" "));

        panel.add(scoreBoardButton);
        panel.add(new JLabel(" "));
        panel.add(new JLabel(" "));

        panel.add(rulesButton);

        panel.setBackground(Color.WHITE);
        frame.setLocation(400,75);
        frame.add(panel);
        frame.setPreferredSize(new Dimension( 800, 950) );
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    }

    public void newGame() throws SQLException {
        gameTimer.setSecondsLeft(120);
        gameTimer.startTimer();
        boardView.newGame();
        if(!boardView.board.isMoveAvailable()){
            newGame();
        }
        panel.removeAll();
        frame.getContentPane().removeAll();
        frame.remove(boardView.popupMenu);
        panel.add(label);
        panel.add(timerLabel);
        boardView = new BoardView(tileTouchedListener, scoreManager,scoreboard, gameTimer);
        panel.add(boardView);
        panel.add(newGame);
        panel.add(new JLabel(" "));
        panel.add(new JLabel(" "));
        panel.add(scoreBoardButton);
        panel.add(new JLabel(" "));
        panel.add(new JLabel(" "));
        panel.add(rulesButton);
        scoreManager.setScore(0);
        label.setText("Score: " + scoreManager.getScore());
        frame.add(panel);
        frame.getContentPane().revalidate();
        frame.getContentPane().repaint();

    }
    public void showScoreBoard() throws SQLException {
        UIManager.put("OptionPane.messageFont", new Font("Arial", Font.BOLD, 25));
        JOptionPane.showMessageDialog(frame, "These are the 10 highest scores of all time, maybe if you work hard enough you can be immortalized in the scoreboard! \n\n" + scoreboard.showScoreBoard());

    }
    public void showRules(){
        UIManager.put("OptionPane.messageFont", new Font("Arial", Font.BOLD, 20));
        JOptionPane.showMessageDialog(frame, "The rules of color buster are simple, match 3 or more of the same color tiles that are touching each other to pop them. " +
                "\nNew tiles fill in from the top after tiles are removed and you will be given score for every tile popped. " +
                "\nThere are special rare tiles that spawn." +
                "\nThere is the color wipe tile which if matched will remove all tiles on the board of that color. " +
                "\nThere is also the elusive SUPER TILE which if matched removes all the tiles on the board. " +
                "\nTry to get the highest score possible in two minutes and be remembered forever in the scoreboard!");

    }


    public void tileTouched(TileView tileView) throws SQLException {

        boardView.tileTouched(tileView);
        if(boardView.board.isMoveAvailable()){
            label.setText("Score: " + scoreManager.getScore());
        }
        else{
            endGameView();
        }
    }
    public void endGameView(){
        if(scoreManager.getScore() > 100000){
            label.setFont(new Font("Helvetica", Font.PLAIN, 45));
        }
        else{
            label.setFont(new Font("Helvetica", Font.PLAIN, 45));
        }
        label.setText("GAME OVER, Final Score: " + scoreManager.getScore());
        timerLabel.setText("Time Remaining: " + gameTimer.getSecondsLeft() + " seconds");
    }
    public void setLabel(String newMessage){
        timerLabel.setText(newMessage);
    }

}

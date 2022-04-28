import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class GameManager {

    public GameView gameView;
    private GameTimer gameTimer = new GameTimer(120);
    public Scoreboard scoreboard = new Scoreboard(gameTimer);;
    public GameManager() throws SQLException {

        gameView = new GameView(new NewGameButton(), new TileTouched(), new ScoreBoardButton(), scoreboard, new RulesButton(), gameTimer);
    }
    class RulesButton implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            gameView.showRules();
        }
    }
    class ScoreBoardButton implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                gameView.showScoreBoard();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
    class NewGameButton implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            System.out.println("\nNEW GAME\n");
            try {
                gameView.newGame();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }

    }
    class TileTouched implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            TileView tv = (TileView)event.getSource();

            try {
                gameView.tileTouched(tv);
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }

    }
}

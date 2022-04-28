import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.SQLException;

public class TimerObserver implements PropertyChangeListener {
    int secondsLeft;
    GameTimer gameTimer;
    GameView gameView;
    public TimerObserver(GameTimer gameTimer, GameView gameView){
        this.gameTimer = gameTimer;
        this.gameView = gameView;
        gameTimer.addListener(this);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        setSecondsLeft((int)evt.getNewValue());
        gameView.setLabel("Time Remaining: " + evt.getNewValue() + " seconds");
        if((int)evt.getNewValue() == 0){
            gameView.endGameView();
            try {
                gameTimer.stopTimer();
                gameView.boardView.timesUp();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void setSecondsLeft(int secondsLeft) {
        this.secondsLeft = secondsLeft;
    }

}

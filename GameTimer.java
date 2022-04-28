import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

public class GameTimer {

    private int secondsLeft;
    private javax.swing.Timer timer;
    private ArrayList<PropertyChangeListener> listeners = new ArrayList();

    public GameTimer(int seconds){
        this.secondsLeft = seconds;
        timer = new javax.swing.Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setSecondsLeft(--secondsLeft);
            }
        });
    }

    public void startTimer(){
        timer.start();
    }
    public void stopTimer(){
        timer.stop();
    }
    public void setSecondsLeft(int newSeconds){
        notifyListeners(
                this,
                "secondsLeft",
                secondsLeft,
                this.secondsLeft = newSeconds
        );
        this.secondsLeft = newSeconds;
    }
    public int getSecondsLeft(){
        return secondsLeft;
    }

    private void notifyListeners(Object object, String property, int oldValue, int newValue){
        for( PropertyChangeListener listener: listeners){
            listener.propertyChange(new PropertyChangeEvent(this, property, oldValue, newValue));
        }
    }

    public void addListener( PropertyChangeListener newListener ){
        listeners.add(newListener);
    }
}

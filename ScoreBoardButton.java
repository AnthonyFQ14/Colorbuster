import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ScoreBoardButton extends JButton{
    JLabel label;
    public ScoreBoardButton(ActionListener newGameListener){
        setPreferredSize(new Dimension(50,50));
        label = new JLabel("Scoreboard");
        add(label);
        addActionListener(newGameListener);
    }
}

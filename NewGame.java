import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class NewGame extends JButton {
    JLabel label;
    public NewGame(ActionListener newGameListener){
        setPreferredSize(new Dimension(50,50));
        label = new JLabel("New Game");
        add(label);
        addActionListener(newGameListener);
    }

}

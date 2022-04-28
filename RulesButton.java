import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class RulesButton extends JButton {
    JLabel label;
    public RulesButton(ActionListener newGameListener){
        setPreferredSize(new Dimension(50,50));
        label = new JLabel("How To Play");
        add(label);
        addActionListener(newGameListener);
    }
}

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.IOException;

public class TileView extends JButton {

        JLabel superLabel = new JLabel(" SUPER TILE");
        JLabel colorLabel = new JLabel("COLOR WIPE");
        public String color;
        public int row;
        public int col;
        ActionListener tileTouched;

        public TileView(Tile tile) {

            this.row = tile.getRow();
            this.col = tile.getCol();
            this.color = tile.getColor();

            superLabel.setFont(new Font("Verdana", Font.BOLD, 13));
            colorLabel.setFont(new Font("Verdana", Font.BOLD, 13));

            setPreferredSize(new Dimension(100,100));
            if(tile.getColor().equals("GREEN")){
                setBackground(Color.GREEN);
            }
            else if(tile.getColor().equals("BLUE")){
                setBackground(Color.BLUE);
            }
            else if(tile.getColor().equals("RED")){
                setBackground(Color.RED);
            }
            else if(tile.getColor().equals("YELLOW")){
                setBackground(Color.YELLOW);
            }
            if(tile.isSuperTile){
                add(superLabel);

            }
            if(tile.isColorWipeTile){
                add(colorLabel);
            }
            setOpaque(true);
            setBorder(BorderFactory.createLineBorder(Color.black));

        }

//    public TileView(int row, int col) {
//
//            Tile newTile = new Tile(row, col);
//
//            this.row = newTile.getRow();
//            this.col = newTile.getRow();
//            this.color = newTile.getColor();
//
//            setPreferredSize(new Dimension(100,100));
//            if(newTile.getColor().equals("GREEN")){
//                setBackground(Color.GREEN);
//            }
//            else if(newTile.getColor().equals("BLUE")){
//                setBackground(Color.BLUE);
//            }
//            else if(newTile.getColor().equals("RED")){
//                setBackground(Color.RED);
//            }
//            else if(newTile.getColor().equals("YELLOW")){
//                setBackground(Color.YELLOW);
//            }
//            setOpaque(true);
//            setBorder(BorderFactory.createLineBorder(Color.black));
//    }

    public String getColor() {
        return this.color;
    }
    public void setColor(String color){
            this.color = color;
    }
    public int getRow() {
        return this.row;
    }
    public int getCol(){
        return this.col;
    }
    public void addListener(ActionListener touched) {
        tileTouched = touched;
        addActionListener(tileTouched);
    }

    public void updateTileView(Board board, int row, int col){
        setColor(board.tileAt(row,col).getColor());
        if(board.tileAt(row, col).getColor().equals("GREEN")){
            setBackground(Color.GREEN);
        }
        else if(board.tileAt(row, col).getColor().equals("BLUE")){
            setBackground(Color.BLUE);
        }
        else if(board.tileAt(row, col).getColor().equals("RED")){
            setBackground(Color.RED);
        }
        else if(board.tileAt(row, col).getColor().equals("YELLOW")){
            setBackground(Color.YELLOW);
        }
        if(board.tileAt(row, col).isSuperTile){
            add(superLabel);

        }
        if(board.tileAt(row, col).isColorWipeTile){
            add(colorLabel);
        }
    }

}

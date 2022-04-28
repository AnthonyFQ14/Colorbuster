import java.awt.*;
import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

public class Tile {

    String color;
    int row;
    int col;
    boolean isSuperTile;
    boolean isColorWipeTile;

    public static String [] colors = {
            "GREEN",
            "BLUE",
            "RED",
            "YELLOW",
    };

    public Tile(int row, int col) {
        this.row = row;
        this.col = col;
        color = colors[randNum()];
        isSuperTile = makeSuperTile();
        isColorWipeTile = makeColorWipe();

    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public boolean isSuperTile() {
        return isSuperTile;
    }

    public void setSuperTile(boolean superTile) {
        isSuperTile = superTile;
    }

    public boolean isColorWipeTile() {
        return isColorWipeTile;
    }

    public void setColorWipeTile(boolean colorWipeTile) {
        isColorWipeTile = colorWipeTile;
    }

    public int randNum(){

        return ThreadLocalRandom.current().nextInt(0, 4);

    }

    public boolean makeSuperTile(){
        boolean isSuper = false;

        int num = ThreadLocalRandom.current().nextInt(1,300);
        if(num == 1){
            isSuper = true;
        }

        return isSuper;

    }

    public boolean makeColorWipe(){
        boolean isColorWipe = false;

        int num = ThreadLocalRandom.current().nextInt(1,75);
        if(num == 1){
            isColorWipe = true;
        }

        return isColorWipe;

    }

    @Override
    public String toString() {
        return "Tile{" +
                "color='" + color + '\'' +
                ", row=" + row +
                ", col=" + col +
                '}';
    }
}





import java.awt.*;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Queue;

public class Board {


    private static Board board = null;
    private Tile [][] tiles;
    public ScoreManager scoreManager;

    private Board(ScoreManager scoreManager) {
        this.scoreManager = scoreManager;
        tiles = new Tile[6][6];
        createBoard();
        printBoard();

    }
    public static Board getInstance(ScoreManager scoreManager){
        if(board == null)
            board = new Board(scoreManager);

        return board;
    }


    public void createBoard() {
        for(int row = 0; row < 6; row++){
            for(int col = 0; col < 6; col++){
                tiles[row][col] = new Tile(row, col);
            }
        }
    }
    public void newBoard(){
        for(int row = 0; row < 6; row++){
            for(int col = 0; col < 6; col++){
                tiles[row][col] = null;
                tiles[row][col] = new Tile(row, col);
            }
        }
    }
    public boolean hasTileAt(int row, int col){
        boolean hasTile;

        if(tiles[row][col] != null){
            hasTile = true;
        }
        else{
            hasTile = false;
        }

        return hasTile;
    }

    public Tile tileAt( int row, int col ){

        return tiles[row][col];
    }

    public void removeTileAt(int row, int col) {

        tiles[row][col] = null;
    }

    public void printBoard(){
        for(int row = 0; row < 6; row++){

            for( int col = 0; col < 6; col++ ) {
                System.out.print(tiles[row][col].getRow() + " " + tiles[row][col].getCol() + " " + tiles[row][col].getColor() + "   ");
            }
            System.out.println();
        }
    }

    public void locateNeighbors(int row, int col, String color, HashSet<Tile> matches){
        Tile t = tiles[row][col];
        if(matches.contains(t)){
            return;
        }
        else{
            matches.add(t);
        }

        if(northMatches(row,col,color)){
            locateNeighbors(row - 1,col,color,matches);
        }
        if(southMatches(row,col,color)){
            locateNeighbors(row + 1,col,color,matches);
        }
        if(eastMatches(row,col,color)){
            locateNeighbors(row,col + 1,color,matches);
        }
        if(westMatches(row,col,color)){
            locateNeighbors(row,col - 1,color,matches);
        }
    }

    boolean northMatches(int row, int col, String color){
        if(row <= 5 && row >= 1 && tiles[row - 1][col] != null && tiles[row-1][col].getColor().equals(color)){
            return true;
        }
        else{
            return false;
        }
    }
    boolean southMatches(int row, int col, String color){
        if(row <= 4 && row >= 0 && tiles[row + 1][col] != null && tiles[row + 1][col].getColor().equals(color)){
            return true;
        }
        else{
            return false;
        }
    }
    boolean eastMatches(int row, int col, String color){
        if(col <= 4 && col >= 0 && tiles[row][col + 1] != null && tiles[row][col + 1].getColor().equals(color)){
            return true;
        }
        else{
            return false;
        }
    }
    boolean westMatches(int row, int col, String color){
        if(col >= 1 && col <= 5 && tiles[row][col - 1] != null && tiles[row][col-1].getColor().equals(color)){
            return true;
        }
        else{
            return false;
        }
    }
    public void fixColumn(int column){
        ArrayList<Tile> tilesInCol = new ArrayList<>();

        for(int row = 5; row >= 0; row--){
            if(hasTileAt(row,column)){
                tilesInCol.add(tiles[row][column]);
            }
        }

        int newTiles = 6 - tilesInCol.size();

        for(int i = 1; i <= newTiles; i++){
            tilesInCol.add(new Tile(tilesInCol.size()+i , column));
        }
        Collections.reverse(tilesInCol);
        if(tilesInCol.size() > 0){
            for (int i = 0; i < 6; i++){

                tiles[i][column] = tilesInCol.get(i);
                tiles[i][column].setRow(i);
                tiles[i][column].setCol(column);
            }
        }

    }
    public void fixColumns(){
        for(int cols = 0; cols < 6; cols++){
            fixColumn(cols);
        }
    }

    public void removeMatches( HashSet<Tile> matches ){
        HashSet<Tile> superTiles = new HashSet<>();
        HashSet<Tile> colorWipes = new HashSet<>();
        HashSet<Tile> normalTiles = new HashSet<>();
        HashSet<Tile> tilesToRemove = new HashSet<>();

        for(Tile t: matches){

            if(t.isColorWipeTile()){
                for (int rows = 0; rows < 6; rows++) {
                    for (int cols = 0; cols < 6; cols++) {
                        if(t.getColor().equals(tiles[rows][cols].getColor()))
                            colorWipes.add(tiles[rows][cols]);
                    }
                }
            }
            else if(t.isSuperTile()){
                for (int rows = 0; rows < 6; rows++) {
                    for (int cols = 0; cols < 6; cols++) {
                        superTiles.add(tiles[rows][cols]);
                    }
                }
            }
            else{
                normalTiles.add(t);
            }
        }

        if(colorWipes.size() > 0) {
            for (Tile t : colorWipes) {
                tilesToRemove.add(t);
            }
        }
        if(superTiles.size() > 0) {
            for (Tile t : superTiles) {
                tilesToRemove.add(t);
            }
        }
        for (Tile t : normalTiles) {
            tilesToRemove.add(t);
        }
        System.out.println(tilesToRemove.size() + " MATCHES ARE: ");

        for(Tile t: tilesToRemove){
            scoreManager.updateScore(tilesToRemove);
            System.out.print("Row: " + t.getRow() + " Col: " + t.getCol() + " Color: + " + t.getColor() + " , ");
            removeTileAt(t.getRow(), t.getCol());
        }
        System.out.println("\n");
    }

    public boolean isMoveAvailable(){

        for(int row = 0; row < 6; row++){
            for(int col = 0; col < 6; col++){
                HashSet<Tile> matches = new HashSet<>();
                locateNeighbors(row,col,tileAt(row,col).getColor(), matches);
                if(matches.size() >= 3){
                    return true;
                }
            }
        }
        System.out.println("NO MORE MOVES");
        return false;

    }

    @Override
    public String toString() {
        return "Board{" +
                "tiles=" + Arrays.toString(tiles) +
                '}';
    }
}

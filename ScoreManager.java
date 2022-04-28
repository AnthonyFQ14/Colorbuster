import java.util.HashSet;

public class ScoreManager{
    public int score = 0;

    public ScoreManager(){

    }
    public void updateScore(HashSet<Tile> matches){
        for(Tile t: matches){
            if(t.isSuperTile()){
                score += 300;
            }
            else if(t.isColorWipeTile()){
                score += 200;
            }
            else if(!t.isColorWipeTile() && !t.isSuperTile()){
                score += 100;
            }
        }
    }

    public int getScore(){
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}

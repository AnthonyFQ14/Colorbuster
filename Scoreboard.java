import javax.swing.*;
import java.sql.*;

public class Scoreboard {

    String url;
    String user;
    String password;
    Connection connection;
    private String name;
    GameTimer gameTimer;

    public Scoreboard(GameTimer gameTimer) throws SQLException {
        this.gameTimer = gameTimer;
        url = "jdbc:mysql://localhost:3306/Colorbuster";
        user = "root";
        password = "YourPassword";

        try {
            connection = DriverManager.getConnection(url,user,password);
            System.out.println("Connected to sql server");

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Nope didnt work");
        }

    }
    public void gameOver(int score) throws SQLException {
        try{
            gameTimer.stopTimer();
            Statement statement = connection.createStatement();

            if(isTopScore(score)){
                name = JOptionPane.showInputDialog("Congratulations you scored a top 10 score! Please enter your name to be saved in eternal glory!");
                if(name == null){
                    name = "Anonymous";
                }
                if(name == ""){
                    name = "Anonymous";
                }
                statement.executeUpdate("INSERT INTO highscores (name,score) " + "VALUES ('"+name+"', '"+score+"')");
                System.out.println("Score going into database: " + name + " " + score);

            }
        }
        catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Game Over didnt work");
        }


    }


    public boolean isTopScore(int score) throws SQLException {
        String sql = ("SELECT name, score FROM highscores\n" +
                "ORDER BY score DESC\n" +
                "LIMIT 10;");

        Statement statement = connection.createStatement();

        ResultSet rs = statement.executeQuery(sql);
        while(rs.next()){
           if(score > rs.getInt("score")){
               return true;
           }
        }
        return false;
    }

    public String showScoreBoard() throws SQLException {
        String top10Scores = "";
        String sql = ("SELECT name, score FROM highscores\n" +
                      "ORDER BY score DESC\n" +
                      "LIMIT 10;");

        Statement statement = connection.createStatement();

        ResultSet rs = statement.executeQuery(sql);

        while(rs.next()){
            top10Scores += rs.getString("name")+ ": " + rs.getInt("score") + "\n";
        }

        System.out.println(top10Scores);

        return top10Scores;

    }

}

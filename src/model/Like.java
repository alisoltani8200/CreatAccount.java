package model;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Like {

    public Like(String liker){

    }

    public Like(boolean isPost,String postId,String commentId,String liker){
        if(isPost){
            final String DB_url = "jdbc:mysql://localhost/users?serverTimezone=UTC";
            final String username = "root";
            final String Password = "ali12345678";
            try {
                Connection conn = DriverManager.getConnection(DB_url, username, Password);

                Statement statement = conn.createStatement();
                String sql = "INSERT INTO likes (isPost,postId,liker,likeTime,likeDate) VALUES (?,?,?,?,?)";
                PreparedStatement preparedStatement = conn.prepareStatement(sql);
                preparedStatement.setString(1, "yes");
                preparedStatement.setInt(2, Integer.parseInt(postId));
                preparedStatement.setString(3, liker);
                preparedStatement.setString(4, LocalTime.now().format(Formatter1()) );
                preparedStatement.setString(5, LocalDate.now().toString());
                int x = preparedStatement.executeUpdate();
                if (x==3) {
                    System.out.println("Your comment send successfully :) ");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else {
            final String DB_url = "jdbc:mysql://localhost/users?serverTimezone=UTC";
            final String username = "root";
            final String Password = "ali12345678";
            try {
                Connection conn = DriverManager.getConnection(DB_url, username, Password);

                Statement statement = conn.createStatement();
                String sql = "INSERT INTO likes (isPost,commentId,liker,commentTime,commentDate) VALUES (?,?,?,?,?)";
                PreparedStatement preparedStatement = conn.prepareStatement(sql);
                preparedStatement.setString(1, "no");
                preparedStatement.setInt(2, Integer.parseInt(commentId));
                preparedStatement.setString(3, liker);
                preparedStatement.setString(4, LocalDate.now().toString());
                preparedStatement.setString(5, LocalTime.now().format(Formatter1()));
                int x = preparedStatement.executeUpdate();
                if (x==3) {
                    System.out.println("Your comment send successfully :) ");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private DateTimeFormatter Formatter1(){
        return DateTimeFormatter.ofPattern("HH:mm");
    }
}

package model;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Post {
    private String postContent;
    private String sender;

    public Post(String postContent,String sender){
        final String DB_url = "jdbc:mysql://localhost/users?serverTimezone=UTC";
        final String username = "root";
        final String Password = "ali12345678";
        try {
            Connection conn = DriverManager.getConnection(DB_url, username, Password);

            Statement statement = conn.createStatement();
            String sql = "INSERT INTO posts (postContent,sender,postTime,postDate) VALUES (?,?,?,?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, postContent);
            preparedStatement.setString(2, sender);
            preparedStatement.setString(3,LocalTime.now().format(Formatter1()) );
            preparedStatement.setString(4, LocalDate.now().toString());
            int x = preparedStatement.executeUpdate();
            if (x==4) {
                System.out.println("You have successfully post :) ");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private DateTimeFormatter Formatter1(){
        return DateTimeFormatter.ofPattern("HH:mm");
    }
}
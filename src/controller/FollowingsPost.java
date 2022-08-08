package controller;

import model.Comment;
import model.Like;
import view.UserPage;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class FollowingsPost {
    public static String userName;
    private Scanner scanner=new Scanner(System.in);

    private final String DB_url = "jdbc:mysql://localhost/users?serverTimezone=UTC";
    private final String username = "root";
    private final String Password = "ali12345678";
    public void all(){
        try {
            Connection conn = DriverManager.getConnection(DB_url, username, Password);
            Statement followingStatement=conn.createStatement();
            String followingSql="SELECT * FROM followings";
            ResultSet followingResultSet=followingStatement.executeQuery(followingSql);
            ArrayList<String> followingList=new ArrayList<>();
            while (followingResultSet.next()){
                if(followingResultSet.getString(userName)!=null){
                    followingList.add(followingResultSet.getString(userName));
                }
            }
            Statement firstStatement = conn.createStatement();
            String firstSql = "SELECT * FROM posts WHERE sender NOT LIKE '"+userName+"'";
            for (String s : followingList) {
                if(s!=null){
                    firstSql=firstSql+" AND sender LIKE '"+s+"'";
                }
            }
            ResultSet resultSet = firstStatement.executeQuery(firstSql);
            while (resultSet.next()){
                System.out.println("model.Post sender : " + resultSet.getString("sender"));
                System.out.println("model.Post content : " + resultSet.getString("postContent"));
                System.out.println("Time of posting : "+resultSet.getString("postTime") + "   " + resultSet.getString("postDate"));
                System.out.println("---------------------------------------------------------------------------------------------");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void certain(){
        try {
            Connection conn = DriverManager.getConnection(DB_url, username, Password);
            Statement followingStatement=conn.createStatement();
            String followingSql="SELECT * FROM followings";
            ResultSet followingResultSet=followingStatement.executeQuery(followingSql);
            ArrayList<String> followingList=new ArrayList<>();
            while (followingResultSet.next()){
                if(followingResultSet.getString(userName)!=null){
                    followingList.add(followingResultSet.getString(userName));
                }
            }
            Statement firstStatement = conn.createStatement();
            System.out.println("These are your following username : ");
            for (String s : followingList) {
                if(s!=null){
                    System.out.println(s);
                }
            }
            System.out.print("Now please enter your desire following : ");
            String desireFollowing=scanner.nextLine().trim();
            String sql="SELECT * FROM posts WHERE sender LIKE '"+desireFollowing+"'";
            ResultSet resultSet = firstStatement.executeQuery(sql);
            boolean isEmpty=true;
            while (resultSet.next()){
                System.out.println("model.Post sender : " + resultSet.getString("sender"));
                System.out.println("model.Post content : " + resultSet.getString("postContent"));
                System.out.println("Time of posting : "+resultSet.getString("postTime") + "   " + resultSet.getString("postDate"));
                System.out.println("---------------------------------------------------------------------------------------------");
                isEmpty=false;
            }
            if(isEmpty){
                System.out.println(desireFollowing+" has no post!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void recent(){
        try {
            Connection conn = DriverManager.getConnection(DB_url, username, Password);
            Statement followingStatement=conn.createStatement();
            String followingSql="SELECT * FROM followings";
            ResultSet followingResultSet=followingStatement.executeQuery(followingSql);
            ArrayList<String> followingList=new ArrayList<>();
            while (followingResultSet.next()){
                if(followingResultSet.getString(userName)!=null){
                    followingList.add(followingResultSet.getString(userName));
                }
            }

            ArrayList<String > sqls=new ArrayList<>();
//            Statement firstStatement = conn.createStatement();
            String firstSql = "SELECT * FROM posts WHERE sender NOT LIKE '"+userName+"'";
            for (String s : followingList) {
                if(s!=null){
                    sqls.add(firstSql+" AND sender LIKE '"+s+"'");
                }
            }


            Statement viewStatement=conn.createStatement();
            String lastView="SELECT * FROM  views WHERE viewer LIKE '"+userName+"'";
            ResultSet viewResultSet=viewStatement.executeQuery(lastView);
            ArrayList<Integer> view=new ArrayList<>();
            while (viewResultSet.next()){
                view.add(viewResultSet.getInt("postId"));
            }
            int count=0;
            for (String sql : sqls) {
                for (Integer integer : view) {
                    sql=sql+" AND id!="+integer;
                }
                sqls.set(count,sql);
                count++;
            }

            Statement statement=conn.createStatement();
            ArrayList<Integer> ids=new ArrayList<>();
            ArrayList<String> sender=new ArrayList<>();
            ArrayList<String> postContent=new ArrayList<>();
            ArrayList<String> postTime=new ArrayList<>();
            ArrayList<String> postDate=new ArrayList<>();
            boolean isEmpty=true;
            for (String sql : sqls) {
                ResultSet resultSet1=statement.executeQuery(sql);
                while (resultSet1.next()){
                    ids.add(resultSet1.getInt("id"));
                    sender.add(resultSet1.getString("sender"));
                    postContent.add(resultSet1.getString("postContent"));
                    postTime.add(resultSet1.getString("postTime"));
                    postDate.add(resultSet1.getString("postDate"));
                    isEmpty=false;
                }
            }


            if(!isEmpty){
                int id=0;
                //first post :
                System.out.println("model.Post sender : " +sender.get(id) );
                System.out.println("model.Post content : " + postContent.get(id));
                System.out.println("Time of posting : "+postTime.get(id) + "   " + postDate.get(id));
                System.out.println("model.Post id is : "+ids.get(id));
                System.out.println("---------------------------------------------------------------------------------------------");
                view(ids.get(id));
                //end of first post :
                boolean run=true;
                while (run){
                    System.out.println("If ypu want to wee next post enter \"NEXT\" : ");
                    System.out.println("If you want to see previous post enter \"PREVIOUS\" : ");
                    System.out.println("If you want to create comment for this post enter \"CREATE COMMENT FOR THIS POST\" : ");
                    System.out.println("If you want to like this post enter \"LIKE THIS POST\" : ");
                    System.out.println("If you want to back enter \"BACK\" : ");
                    System.out.print("What is your command ? ");
                    String command=scanner.nextLine().trim();
                    if(command.equals("NEXT")){
                        if(id<sender.size()-1) {
                            System.out.println("model.Post sender : " + sender.get(++id));
                            System.out.println("model.Post content : " + postContent.get(id));
                            System.out.println("Time of posting : " + postTime.get(id) + "   " + postDate.get(id));
                            System.out.println("model.Post id is : "+ids.get(id));
                            System.out.println("---------------------------------------------------------------------------------------------");
                            view(ids.get(id));
                        }
                        else {
                            System.out.println("End of posts :) ");
                        }
                    }
                    else if(command.equals("PREVIOUS")){
                        if(id>0) {
                            System.out.println("model.Post sender : " + sender.get(--id));
                            System.out.println("model.Post content : " + postContent.get(id));
                            System.out.println("Time of posting : " + postTime.get(id) + "   " + postDate.get(id));
                            System.out.println("model.Post id is : "+ids.get(id));
                            System.out.println("---------------------------------------------------------------------------------------------");
                            view(ids.get(id));
                        }
                        else {
                            System.out.println("This is first post!");
                        }
                    }
                    else if(command.equals("CREATE COMMENT FOR THIS POST")){

//                        System.out.print("Please enter post's ID : ");
//                        String postId=scanner.nextLine().trim();
                        System.out.print("Please enter your comment content : ");
                        String commentContent=scanner.nextLine().trim();
                        Comment comment=new Comment(true,ids.get(id).toString(),null,commentContent,userName);
                    }
                    else if(command.equals("LIKE THIS POST")){
//                        System.out.print("Please enter post's ID : ");
//                        String postId=scanner.nextLine().trim();
                        Like like=new Like(true,ids.get(id).toString(),null,userName);
                    }
                    else if(command.equals("BACK")){
                        run=false;
                        UserPage userPage=new UserPage();
                        userPage.run();
                    }

                }
//            LocalDate localDateNow=LocalDate.now();
//            LocalDate localDateYesterday=localDateNow.minusDays(1);
//            String sql1=firstSql+" AND postDate LIKE '"+localDateYesterday+"'";
//            ResultSet resultSet1 = firstStatement.executeQuery(sql1);
//            System.out.println("Posts of "+localDateYesterday+" : ");
//            while (resultSet1.next()){
//                System.out.println("model.Post sender : " + resultSet1.getString("sender"));
//                System.out.println("model.Post content : " + resultSet1.getString("postContent"));
//                System.out.println("Time of posting : "+resultSet1.getString("postTime") + "   " + resultSet1.getString("postDate"));
//                System.out.println("---------------------------------------------------------------------------------------------");
//            }
//            String sql2=firstSql+" AND postDate LIKE '"+localDateNow+"'";
//            ResultSet resultSet2 = firstStatement.executeQuery(sql2);
//            System.out.println("Posts of "+localDateNow+" : ");
//            while (resultSet2.next()){
//                System.out.println("model.Post sender : " + resultSet2.getString("sender"));
//                System.out.println("model.Post content : " + resultSet2.getString("postContent"));
//                System.out.println("Time of posting : "+resultSet2.getString("postTime") + "   " + resultSet2.getString("postDate"));
//                System.out.println("---------------------------------------------------------------------------------------------");
//            }
            }
            else{
                System.out.println("No recent post :) ");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void view(int postId) throws SQLException {
        Connection conn = DriverManager.getConnection(DB_url, username, Password);
        Statement statement1=conn.createStatement();
        Statement statement2=conn.createStatement();
        String lastView="SELECT * FROM  views WHERE postId='"+postId+"'"+" AND viewer LIKE '"+userName+"'";
        ResultSet resultSet=statement1.executeQuery(lastView);
        if(!resultSet.next()){
            String viewSql="INSERT INTO views (postId,viewer,viewTime,viewDate) VALUES ('"+postId+"','"+userName+"','"+ LocalTime.now().format(Formatter1()).toString() +"','"+ LocalDate.now().toString()+"')";
            statement2.executeUpdate(viewSql);
        }
    }


    private DateTimeFormatter Formatter1(){
        return DateTimeFormatter.ofPattern("HH:mm");
    }
}

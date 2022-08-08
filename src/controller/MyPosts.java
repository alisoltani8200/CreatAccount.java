package controller;

import view.UserPage;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class MyPosts {
    Scanner scanner = new Scanner(System.in);

    ArrayList<String> lastCommentSenders = new ArrayList<>();
    ArrayList<String> lastComments = new ArrayList<>();
    private final String DB_url = "jdbc:mysql://localhost/users?serverTimezone=UTC";
    private final String username = "root";
    private final String Password = "ali12345678";

    public void run() {

        try {
            Connection conn = DriverManager.getConnection(DB_url, username, Password);
            Statement statement = conn.createStatement();
            String sql = "SELECT * FROM posts WHERE sender LIKE '" + UserPage.userName + "'";
            ResultSet resultSet = statement.executeQuery(sql);
            ArrayList<Integer> ids = new ArrayList<>();
            ArrayList<String> postContents = new ArrayList<>();
            ArrayList<Integer> postCountLike = new ArrayList<>();
            ArrayList<Integer> postCountComment = new ArrayList<>();
            while (resultSet.next()) {
                ids.add(resultSet.getInt("id"));
                postContents.add(resultSet.getString("postContent"));
                String sql1 = "SELECT COUNT(*) FROM comments WHERE postId = " + resultSet.getInt("id");
                Statement statement1 = conn.createStatement();
                ResultSet resultSet1 = statement1.executeQuery(sql1);
                if (resultSet1.next()) {
                    postCountComment.add(resultSet1.getInt(1));
                }
                String sql2 = "SELECT COUNT(*) FROM likes WHERE postId = " + resultSet.getInt("id");
                Statement statement2 = conn.createStatement();
                ResultSet resultSet2 = statement2.executeQuery(sql2);
                if (resultSet2.next()) {
                    postCountLike.add(resultSet2.getInt(1));
                }
            }
            System.out.println("You have " + postContents.size() +  " post ");
            System.out.println("All your posts : ");
            int id=0;
            System.out.println("****************");
            System.out.println(postContents.get(id) + "  |||||||  count of comment : " + postCountComment.get(id) + " |||||||  count of likes : " + postCountLike.get(id));
            System.out.println("****************");
            boolean wrongFormat = false;
            while (!wrongFormat) {
                System.out.println("if you want to see next post enter \"NEXT\" ");
                System.out.println("if you want to see previous post enter \"PREVIOUS\" ");
                System.out.println("if you want to edit this post enter \"EDIT\"");
                System.out.println("if you want to see this post comments enter \"SHOW COMMENTS\"");
                System.out.println("if you want to see this post likes enter \"SHOW LIKES\"");
                if(UserPage.businessAccount!=null){
                    System.out.println("If you want to see your post data enter \"SHOW STATS\" : ");
                }
                System.out.println("If you want to back enter \"BACK\" : ");

                String temp = scanner.nextLine().trim();
                if (temp.equals("NEXT")) {
                    if(id==ids.size()-1){
                        System.out.println("End of post :) ");
                    }
                    else{
                        showPost(++id,postContents,postCountComment,postCountLike);
                    }
                }
                else if(temp.equals("PREVIOUS")){
                    if(id==0){
                        System.out.println("This post is first post!");
                    }
                    else {
                        showPost(--id,postContents,postCountComment,postCountLike);
                    }
                }
                else if (temp.equals("SHOW COMMENTS")) {
                    ArrayList<String> commentSenders = new ArrayList<>();
                    ArrayList<String> comments = new ArrayList<>();
                    ArrayList<Integer> commentsId = new ArrayList<>();
                    try {
                        Statement statement1 = conn.createStatement();
                        String sql1 = "SELECT * FROM comments WHERE postId = " + id;
                        ResultSet resultSet1 = statement1.executeQuery(sql1);
                        while (resultSet1.next()) {
                            String senderName = resultSet1.getString("sender");
                            String content = resultSet1.getString("commentContent");
                            int commentId = resultSet1.getInt("id");
                            comments.add(content);
                            commentSenders.add(senderName);
                            commentsId.add(commentId);
                            //for comments of comment
                            int commentsOfComment = countCommentsOfComment(commentId);
                            //for likes of comment
                            int likesOfComment = countLikesOfComment(commentId);
                            System.out.println("ID : " + commentId + "  ||||  " + senderName + " : " + content + "   |||||     this comment has " + commentsOfComment + " comments and " + likesOfComment + " likes");
                        }
                    } catch (SQLException e) {

                    }
                    boolean endComment = false;
                    while (!endComment) {
                        System.out.println("If you want to see comment of another comment  enter \"COMMENTS OF COMMENT\"");
                        System.out.println("If you want to see who likes comment  enter \"LIKES COMMENT\"");
                        System.out.println("If you want to end seeing this post comments enter \"ENOUGH\"");
                        String allComments = scanner.nextLine().trim();
                        if (allComments.equals("COMMENTS OF COMMENT")) {
                            boolean format = false;
                            while (!format) {
                                System.out.println("enter id of comment you want to see its comments");
                                System.out.println("if you want to end seeing this post comments enter \"ENOUGH\"");
                                String temp3 = scanner.nextLine().trim();
                                if (temp3.equals("ENOUGH")) {
                                    System.out.println("done :)");
                                    endComment = true;
                                }
                                else {
                                    try {
                                        if (Integer.parseInt(temp3) == Double.parseDouble(temp3)) {
                                            try {
                                                String sql1 = "SELECT * FROM comments WHERE commentId = " + Integer.parseInt(temp3);
                                                Statement stmt = conn.createStatement();
                                                ResultSet resultSet1 = stmt.executeQuery(sql1);
                                                //format = true;
                                                if (resultSet1.next()) {
                                                    format = true;
                                                    int commentId = resultSet1.getInt("id");
                                                    String senderName = resultSet1.getString("sender");
                                                    String content = resultSet1.getString("commentContent");
                                                    int commentsOfComment = countCommentsOfComment(commentId);
                                                    int likesOfComment = countLikesOfComment(commentId);
                                                    System.out.println("ID : " + commentId + "  ||||  " + senderName + " : " + content + "   |||||     this comment has " + commentsOfComment + " comments and " + likesOfComment + " likes");
                                                    while (resultSet.next()) {
                                                        int commentId2 = resultSet1.getInt("id");
                                                        String senderName2 = resultSet1.getString("sender");
                                                        String content2 = resultSet1.getString("commentContent");
                                                        int commentsOfComment2 = countCommentsOfComment(commentId2);
                                                        int likesOfComment2 = countLikesOfComment(commentId2);
                                                        System.out.println("ID : " + commentId2 + "  ||||  " + senderName2 + " : " + content2 + "   |||||     this comment has " + commentsOfComment2 + " comments and " + likesOfComment2 + " likes");
                                                    }
                                                }
                                                else {
                                                    System.out.println("we don't have comment with this id , try again ");
                                                }
                                            }
                                            catch (SQLException e) {
                                                e.printStackTrace();
                                            }
                                        } else {
                                            System.out.println("your number should be an integer !");
                                        }
                                    } catch (NumberFormatException e) {
                                        System.out.println("wrong input , please pay attention ");
                                    }
                                }
                            }
                        }
                        else if (allComments.equals("LIKES COMMENT")) {
                            boolean format = false;
                            while (!format) {
                                System.out.println("enter id of comment you want to see its liker");
                                System.out.println("if you want to end seeing this post comments enter \"ENOUGH\"");
                                String temp3 = scanner.nextLine().trim();
                                if (temp3.equals("ENOUGH")) {
                                    System.out.println("done :)");
                                    format = true;
                                    endComment = true;
                                }
                                else {
                                    try {
                                        if (Integer.parseInt(temp3) == Double.parseDouble(temp3)) {
                                            format = true;
                                            ArrayList<String> likers = new ArrayList<>();
                                            ArrayList<Integer> likerId = new ArrayList<>();
                                            System.out.println("liker usernames are : ");
                                            try {
                                                Statement statement1 = conn.createStatement();
                                                String sql1 = "SELECT * FROM likes WHERE commentId = " + Integer.parseInt(temp3);
                                                ResultSet resultSet1 = statement1.executeQuery(sql1);
                                                while (resultSet1.next()) {
                                                    String likerName = resultSet1.getString("liker");
                                                    int likerID = resultSet1.getInt("id");
                                                    likers.add(likerName);
                                                    likerId.add(likerID);
                                                    System.out.println(likerName);
                                                    System.out.println("----------------------------");
                                                }
                                            } catch (SQLException e) {

                                            }
                                        }
                                        else {
                                            System.out.println("your number format is wrong ,try again");
                                        }
                                    }
                                    catch (NumberFormatException e) {
                                        System.out.println("wrong input , please pay attention ");
                                    }
                                }
                            }
                        }
                        else if (allComments.equals("ENOUGH")) {
                            System.out.println("done :)");
                            endComment = true;
                        }
                        else {
                            System.out.println("wrong order , please pay attention ");
                        }
                    }
                }
                else if (temp.equals("EDIT")) {
                    System.out.println("insert your new post content : ");
                    String convert = scanner.nextLine().trim();
                    try {
                        Statement statement1 = conn.createStatement();
                        String sql1 = "UPDATE posts SET postContent = '" + convert + "' WHERE id = " + id;
                        int resultSet1 = statement1.executeUpdate(sql1);

                        if (resultSet1 == 1) {
                            sql1 = "SELECT postContent FROM posts WHERE id = " + id;
                            ResultSet tempp = statement1.executeQuery(sql1);

                            System.out.println("Done :) \n" +
                                    "your edited post now is :");
                            if (tempp.next()) {
                                System.out.println(tempp.getString("postContent"));
                                postContents.set(ids.indexOf(id), tempp.getString("postContent"));
                            }
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                else if (temp.equals("SHOW LIKES")) {
                    ArrayList<String> likers = new ArrayList<>();
                    ArrayList<Integer> likerId = new ArrayList<>();
                    System.out.println("liker usernames are : ");
                    try {
                        Statement statement1 = conn.createStatement();
                        String sql1 = "SELECT * FROM likes WHERE postId = " + id;
                        ResultSet resultSet1 = statement1.executeQuery(sql1);
                        while (resultSet1.next()) {
                            String likerName = resultSet1.getString("liker");
                            int likerID = resultSet1.getInt("id");
                            likers.add(likerName);
                            likerId.add(likerID);
                            System.out.println(likerName);
                            System.out.println("----------------------------");
                        }
                    } catch (SQLException e) {

                    }
                }
                else if(UserPage.businessAccount!=null&&temp.equals("SHOW STATS")){
                    showStats(ids.get(id));
                }
                else if(temp.equals("BACK")){
                    wrongFormat=true;
                    UserPage userPage=new UserPage();
                    userPage.run();
                }
                else {
                    System.out.println("wrong order , please pay attention ");
                }
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }


    private int countCommentsOfComment (int id){
        int ans =0;

        try {
            Connection conn = DriverManager.getConnection(DB_url, username, Password);

            Statement statement = conn.createStatement();
            String sql = "SELECT * FROM comments WHERE commentId = " + id;

            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()){ans++;}


        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }

        return ans;
    }


    private int countLikesOfComment (int id){
        int ans =0;

        try {
            Connection conn = DriverManager.getConnection(DB_url, username, Password);

            Statement statement = conn.createStatement();
            String sql = "SELECT * FROM likes WHERE commentId = " + id;

            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()){ans++;}


        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }

        return ans;
    }


    private void showStats(int id){
        ArrayList<LocalDate> viewLocalDateArrayList=new ArrayList<>();
        ArrayList<LocalDate> likeLocalDateArrayList=new ArrayList<>();
        try {
            Connection conn = DriverManager.getConnection(DB_url, username, Password);
            Statement statement1 = conn.createStatement();
            Statement statement2=conn.createStatement();
            String sql = "SELECT * FROM views WHERE postId=" + id;
            String sql1="SELECT * FROM likes WHERE postId="+id;
            ResultSet resultSet1 = statement1.executeQuery(sql);
            ResultSet resultSet2=statement2.executeQuery(sql1);
            while (resultSet1.next()){
                viewLocalDateArrayList.add(LocalDate.parse(resultSet1.getString("viewDate")));
            }
            while ((resultSet2.next())){
                likeLocalDateArrayList.add(LocalDate.parse(resultSet2.getString("likeDate")));
            }
            int viewCount=0,likeCount=0,count1=0;
            for (LocalDate viewLocalDate : viewLocalDateArrayList) {
                viewCount++;
                if(count1+1!=viewLocalDateArrayList.size()){
                    if(viewLocalDate.compareTo(viewLocalDateArrayList.get(count1+1))==1){
                        if(likeLocalDateArrayList.size()!=0){
                            for (LocalDate likeLocalDate : likeLocalDateArrayList) {
                                if(viewLocalDate.compareTo(likeLocalDate)==0){
                                    likeCount++;
                                }
                            }
                        }
                        System.out.println(viewLocalDate+" : ");
                        System.out.println("Your post view number is : "+viewCount);
                        System.out.println("Your post like number is : "+likeCount);
                        viewCount=0;
                        likeCount=0;
                    }
                }
                else {
                    if(likeLocalDateArrayList.size()!=0){
                        for (LocalDate likeLocalDate : likeLocalDateArrayList) {
                            if(viewLocalDate.compareTo(likeLocalDate)==0){
                                likeCount++;
                            }
                        }
                    }
                    System.out.println(viewLocalDate+" : ");
                    System.out.println("Your post view number is : "+viewCount);
                    System.out.println("Your post like number is : "+likeCount);
                    viewCount=0;
                    likeCount=0;
                }
                count1++;
            }
        }
        catch (SQLException e){
            System.out.println(":)");
        }
    }

    private void showPost(int id,ArrayList<String> postContents,ArrayList<Integer> postCountComment,ArrayList<Integer> postCountLike){
        System.out.println("****************");
        System.out.println(postContents.get(id) + "  |||||||  count of comment : " + postCountComment.get(id) + " |||||||  count of likes : " + postCountLike.get(id));
        System.out.println("****************");
    }
}

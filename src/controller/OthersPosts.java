package controller;

import model.Comment;
import model.Like;
import view.Menu;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class OthersPosts {
    private Scanner scanner=new Scanner(System.in);
    public static String userName;
    public static String name;

    private final String DB_url = "jdbc:mysql://localhost/users?serverTimezone=UTC";
    private final String username = "root";
    private final String Password = "ali12345678";

    public void run () throws SQLException {
        boolean isrun=true;
        int whichIdIs=0;
        int whichIdStart=0;
        String sql="";

        Connection conn = DriverManager.getConnection(DB_url, username, Password);

        try {
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
                    firstSql=firstSql+" AND sender NOT LIKE '"+s+"'";
                }
            }
            sql=firstSql;
            ResultSet resultSet = firstStatement.executeQuery(firstSql);
            int counter=0;
            while (resultSet.next()&&counter<15){
                System.out.println("model.Post sender : " + resultSet.getString("sender"));
                System.out.println("model.Post content : " + resultSet.getString("postContent"));
                System.out.println("Time of posting : "+resultSet.getString("postTime") + "   " + resultSet.getString("postDate"));
                System.out.println("model.Post id is : "+resultSet.getInt("id"));
                System.out.println("---------------------------------------------------------------------------------------------");
                whichIdIs= resultSet.getInt("id");
                counter++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        while (isrun){
            System.out.println("If you want to see next posts enter \"NEXT POSTS\" : ");
            System.out.println("If you want to see previous posts enter \"PREVIOUS POSTS\" : ");
            System.out.println("If you want to see each post , please enter \"SEE POST\" : ");
            System.out.println("If you want to see someone's profile enter \"SEE PROFILE\" : ");
            System.out.println("If you want to back to menu enter \"BACK\" : ");
            System.out.print("What is your command ? ");
            String command=scanner.nextLine().trim();
            if(command.equals("NEXT POSTS")){
                int counter1=0;
                Statement Statement1=conn.createStatement();
                String thisScopSql=sql+" AND id>"+whichIdIs;
                whichIdStart=whichIdIs;
                ResultSet resultSet1 = Statement1.executeQuery(thisScopSql);
                while (resultSet1.next()&&counter1<15){
                    System.out.println("model.Post sender : " + resultSet1.getString("sender"));
                    System.out.println("model.Post content : " + resultSet1.getString("postContent"));
                    System.out.println("Time of posting : "+resultSet1.getString("postTime") + "   " + resultSet1.getString("postDate"));
                    System.out.println("model.Post id is : "+resultSet1.getInt("id"));
                    System.out.println("---------------------------------------------------------------------------------------------");
                    whichIdIs=resultSet1.getInt("id");
                    counter1++;
                }
            }
            else if(command.equals("PREVIOUS POSTS")){
                int counter2=0;
                Statement Statement2=conn.createStatement();
                String thisScopSql=sql+" AND id<="+whichIdStart+" AND id>"+(whichIdStart-15);
                ResultSet resultSet2 = Statement2.executeQuery(thisScopSql);
                while (resultSet2.next()&&counter2<15){
                    System.out.println("model.Post sender : " + resultSet2.getString("sender"));
                    System.out.println("model.Post content : " + resultSet2.getString("postContent"));
                    System.out.println("Time of posting : "+resultSet2.getString("postTime") + "   " + resultSet2.getString("postDate"));
                    System.out.println("model.Post id is : "+resultSet2.getInt("id"));
                    System.out.println("---------------------------------------------------------------------------------------------");
                    whichIdIs=resultSet2.getInt("id");
                    counter2++;
                }
            }
            else if(command.equals("SEE POST")){
                System.out.print("Please enter your desire post id : ");
                String postId=scanner.nextLine().trim();
                Statement Statement3=conn.createStatement();
                String thisScopSql=sql+" AND id="+postId;
                ResultSet resultSet3 = Statement3.executeQuery(thisScopSql);
                if (resultSet3.next()){
                    System.out.println("model.Post sender : " + resultSet3.getString("sender"));
                    System.out.println("model.Post content : " + resultSet3.getString("postContent"));
                    System.out.println("Time of posting : "+resultSet3.getString("postTime") + "   " + resultSet3.getString("postDate"));
                    System.out.println("model.Post id is : "+resultSet3.getInt("id"));
                    System.out.println("---------------------------------------------------------------------------------------------");
                }
                view(Integer.parseInt(postId));
            }
            else if(command.equals("SEE PROFILE")){
                seeProfile();
            }
            else if(command.equals("BACK")){
                isrun=false;
                Menu menu=new Menu();
                menu.run();
            }
            else {
                System.out.println("You have enter incorrect command!");
            }
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

//    private void seeProfile(){
//        System.out.print("Please enter your desire username : ");
//        String desireUserName=scanner.nextLine().trim();
//
//        try {
//            Connection connUserName = DriverManager.getConnection(DB_url, username, Password);
//            Statement statementUserName = connUserName.createStatement();
//            String desireUserSql = "SELECT * FROM personalInformation WHERE username LIKE '"+desireUserName+"'";
//            ResultSet resultSet = statementUserName.executeQuery(desireUserSql);
//            if(resultSet.next()){
//                String desireAccountType=resultSet.getString("accounttype");
//                String desireBusinessType=resultSet.getString("businessType");
//                if(desireBusinessType==null){
//                    System.out.println("Person's name is : "+resultSet.getString("name"));
//                    System.out.println("model.Account username is : "+desireUserName);
//                    System.out.println("model.Account Type is : "+desireAccountType);
//                }
//                else{
//                    System.out.println("Business account name is : "+resultSet.getString("name"));
//                    System.out.println("Username is : "+desireUserName);
//                    System.out.println("model.Account Type is : "+desireAccountType);
//                    System.out.println("Business Type is : "+desireBusinessType);
//                }
//                boolean isFollow=false;
//                while (!isFollow){
//                    System.out.println("If you want to follow "+desireUserName+" enter \"FOLLOW\" :");
//                    System.out.println("If you want to back enter \"BACK\" : ");
//                    System.out.print("What is your command ? ");
//                    String followCommand=scanner.nextLine().trim();
//                    if(followCommand.equals("FOLLOW")){
//                        follow(userName,desireUserName);
//                        isFollow=true;
//                    }
//                    else if(followCommand.equals("BACK")){
//                        isFollow=true;
//                    }
//                    else{
//                        System.out.println("You have enter incorrect command!");
//                    }
//                }
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }

    private void seeProfile(){

        System.out.print("Please enter your desire username : ");
        String desireUserName=scanner.nextLine().trim();
        try {
            Connection connUserName = DriverManager.getConnection(DB_url, username, Password);
            Statement statement = connUserName.createStatement();
            String desireUserSql = "SELECT * FROM personalInformation WHERE username LIKE '"+desireUserName+"'";
            ResultSet resultSet = statement.executeQuery(desireUserSql);
            if(resultSet.next()){
                String desireAccountType=resultSet.getString("accounttype");
                String desireBusinessType=resultSet.getString("businessType");

                if(desireBusinessType==null){
                    System.out.println("Person's name is : "+resultSet.getString("name"));
                    System.out.println("model.Account username is : "+desireUserName);
                    System.out.println("model.Account Type is : "+desireAccountType);
                }
                else{
                    System.out.println("Business account name is : "+resultSet.getString("name"));
                    System.out.println("Username is : "+desireUserName);
                    System.out.println("model.Account Type is : "+desireAccountType);
                    System.out.println("Business Type is : "+desireBusinessType);
                }


                String sqlPost="SELECT * FROM posts WHERE sender LIKE '"+desireUserName+"'";
                ResultSet resultSetPost=statement.executeQuery(sqlPost);
                int postNum=0;
                while(resultSetPost.next()){
                    postNum++;
                }
                System.out.println("Person's post number is : "+postNum);
                String sql = "SELECT " + desireUserName +" FROM followers";

                ResultSet resultSetFollowers = statement.executeQuery(sql);
                System.out.println("Person's followers :");

                while (resultSetFollowers.next()) {
                    if(resultSetFollowers.getString(desireUserName)!=null){
                        System.out.println(resultSetFollowers.getString(desireUserName));
                    }
                }

                String sql2 = "SELECT " + desireUserName +" FROM followings";
                ResultSet resultSetFollowings = statement.executeQuery(sql2);

                System.out.println("Person's following :");
                while (resultSetFollowings.next()) {
                    if(resultSetFollowings.getString(desireUserName)!=null){
                        System.out.println(resultSetFollowings.getString(desireUserName));
                    }
                }

                boolean isGo=true;
                while (isGo){
                    String checkFollowSql = "SELECT * FROM followings WHERE "+userName+" LIKE '"+desireUserName+"'";
                    ResultSet checkFollowResultSet = statement.executeQuery(checkFollowSql);
                    boolean checkFollow=false;
                    if(checkFollowResultSet.next()) {
                        checkFollow = true;
                    }
                    if(checkFollow){
                        System.out.println("Tou are following "+desireUserName);
                        System.out.println("If you want to unfollow "+desireUserName+" enter \"UNFOLLOW\" :");
                    }
                    else {
                        System.out.println("If you want to follow "+desireUserName+" enter \"FOLLOW\" :");
                    }
                    System.out.println("If you want to see "+desireUserName+"'s post enter \"SEE POSTS\" : ");
                    System.out.println("If you want to back enter \"BACK\" : ");
                    System.out.print("What is your command ? ");
                    String followCommand=scanner.nextLine().trim();
                    if(followCommand.equals("FOLLOW")){
                        follow(userName,desireUserName);
                    }
                    else if(followCommand.equals("UNFOLLOW")){
                        unFollow(userName,desireUserName);
                    }
                    else if(followCommand.equals("SEE POSTS")){
                        ArrayList<Integer> ids=new ArrayList<>();
                        ArrayList<String> sender=new ArrayList<>();
                        ArrayList<String> postContent=new ArrayList<>();
                        ArrayList<String> postTime=new ArrayList<>();
                        ArrayList<String> postDate=new ArrayList<>();

                        String postSql="SELECT * FROM posts WHERE sender LIKE '"+desireUserName+"'";
                        ResultSet postResultSet = statement.executeQuery(postSql);
                        boolean isEnpty=true;
                        while (postResultSet.next()){
                            ids.add(postResultSet.getInt("id"));
                            sender.add(postResultSet.getString("sender"));
                            postContent.add(postResultSet.getString("postContent"));
                            postTime.add(postResultSet.getString("postTime"));
                            postDate.add(postResultSet.getString("postDate"));
                            isEnpty=false;
                        }

                        if(!isEnpty){
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
                                    System.out.print("Please enter your comment content : ");
                                    String commentContent=scanner.nextLine().trim();
                                    Comment comment=new Comment(true,ids.get(id).toString(),null,commentContent,userName);
                                }
                                else if(command.equals("LIKE THIS POST")){
                                    Like like=new Like(true,ids.get(id).toString(),null,userName);
                                }
                                else if(command.equals("BACK")){
                                    run=false;
                                }
                            }
                        }
                        else{
                            System.out.println("No post :) ");
                        }
                    }
                    else if(followCommand.equals("BACK")){
                        isGo=false;
                    }
                    else{
                        System.out.println("You have enter incorrect command!");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void follow(String follower,String following){
        try {
            Connection conn = DriverManager.getConnection(DB_url, username, Password);
            Statement statement = conn.createStatement();
            String followingSql = "INSERT INTO followers ("+following+") VALUES ('"+follower+"')";
            String followerSql = "INSERT INTO followings ("+follower+") VALUES ('"+following+"')";
            int b =statement.executeUpdate(followingSql);
            int a = statement.executeUpdate(followerSql);
            if(a==1&&b==1){
                System.out.println("You follow "+following+" successfully :)");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void unFollow(String follower,String following){
        try {
            Connection conn = DriverManager.getConnection(DB_url, username, Password);
            Statement followingStatement = conn.createStatement();
            Statement followerStatement=conn.createStatement();
            String followingSql = "DELETE FROM followers WHERE "+following+" LIKE '"+follower+"'";
            String followerSql = "DELETE FROM followings WHERE "+follower+" LIKE '"+following+"'";
            int a=followingStatement.executeUpdate(followingSql);
            int b=followerStatement.executeUpdate(followerSql);
            if(a==1&&b==1){
                System.out.println("You unfollow "+following+" successfully :)");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}

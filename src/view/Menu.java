package view;

import controller.FollowingsPost;
import controller.OthersPosts;
import controller.RecommendUsers;
import model.*;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class Menu {
    private Scanner scanner=new Scanner(System.in);
    public static String name;
    public static String userName;
    public static NormalAccount normalAccount;
    public static BusinessAccount businessAccount;

    private final String DB_url = "jdbc:mysql://localhost/users?serverTimezone=UTC";
    private final String username = "root";
    private final String Password = "ali12345678";

    public void run() throws SQLException {
        boolean isMenu=true;
        while (isMenu){
            System.out.println("If you want to go to your profile enter \"VIEW PROFILE\" : ");
            System.out.println("If you want to chat enter \"CHAT\" : ");
            System.out.println("If you want to see other's posts enter \"OTHER'S POSTS\" : ");
            System.out.println("If you want to see Your following's posts enter \"FOLLOWING'S POSTS\" : ");
            System.out.println("If you want to search other's username enter \"SEARCH USERNAME\" :");
            System.out.println("If you want to create new post enter \"CREATE POST\" : ");
            System.out.println("If you want to create new post enter \"CREATE COMMENT\" : ");
            System.out.println("If you want to like one post enter \"LIKE\" : ");
            System.out.println("If you want to see recommended account enter \"RECOMMENDED ACCOUNT\" : ");
            System.out.println("If you want to logout enter \"LOGOUT\" : ");
            System.out.print("What is your command ? ");
            String command=scanner.nextLine().trim();
            if(command.equals("VIEW PROFILE")){
                if(normalAccount==null){
                    UserPage.businessAccount=businessAccount;
                    UserPage.normalAccount=null;
                    UserPage.userName= userName;
                    UserPage.name=name;
                    UserPage userPage=new UserPage();
                    userPage.run();
                }
                else {
                    UserPage.normalAccount=normalAccount;
                    UserPage.businessAccount=null;
                    UserPage.userName= userName;
                    UserPage.name=name;
                    UserPage userPage=new UserPage();
                    userPage.run();
                }
            }
            else if (command.equals("CHAT")) {
                boolean chatting = true;
                Chat myChat=new Chat();
                String chatType = "";
                while (chatting) {
                    System.out.println("Who do you want to chat with ? (Enter group name or username)\n(or type 'BACK' to go to personal page)");
                    String receiver = scanner.nextLine().trim();
                    if (receiver.equals("BACK")) {
                        chatting = false;
                    } else {
                        System.out.println("Is it a group or a person ? (Type 'GROUP' or 'PERSON' or 'BACK') ");
                        chatType = scanner.nextLine().trim();
                        if (chatType.equals("GROUP")) {
                            myChat.startChat(scanner, userName, receiver);
                        } else if (chatType.equals("PERSON")) {
                            myChat.startChat(userName, receiver, scanner);
                        } else if (chatType.equals("BACK")) {
                            continue;
                        } else {
                            System.out.println("Invalid command !");
                            continue;
                        }
                    }
                }
            }
            else if(command.equals("OTHER'S POSTS")){
                OthersPosts othersPosts=new OthersPosts();
                OthersPosts.userName=userName;
                OthersPosts.name=name;
                othersPosts.run();
            }
            else if (command.equals("FOLLOWING'S POSTS")) {
                boolean show=true;
                while (show){
                    System.out.println("If you want to see all posts of your following enter \"ALL\" : ");
                    System.out.println("If you want to see certain following's post enter \"CERTAIN FOLLOWING\" : ");
                    System.out.println("If you want to see recent post of your following's post enter \"RECENT POSTS\" : ");
                    System.out.println("If you want to back enter \"BACK\" : ");
                    System.out.print("What is your command ? ");
                    String command1=scanner.nextLine().trim();
                    if(command1.equals("ALL")){
                        FollowingsPost followingsPost=new FollowingsPost();
                        FollowingsPost.userName=userName;
                        followingsPost.all();
                    }
                    else if(command1.equals("CERTAIN FOLLOWING")){
                        FollowingsPost followingsPost=new FollowingsPost();
                        FollowingsPost.userName=userName;
                        followingsPost.certain();
                    }
                    else if(command1.equals("RECENT POSTS")){
                        FollowingsPost followingsPost=new FollowingsPost();
                        FollowingsPost.userName=userName;
                        followingsPost.recent();
                    }
                    else if(command1.equals("BACK")){
                        show=false;
                    }
                }

            }
            else if(command.equals("SEARCH USERNAME")){
                seeProfile();
            }
            else if(command.equals("CREATE POST")){
                boolean addTextBoolean=false;
                while (!addTextBoolean){
                    System.out.print("If you want to enter your post content enter \"ADD TEXT\" else enter \"BACK\" : ");
                    String addText=scanner.nextLine().trim();
                    if(addText.equals("ADD TEXT")){
                        System.out.print("Please enter post content : ");
                        String postContent=scanner.nextLine().trim();
                        Post post=new Post(postContent,userName);
                        addTextBoolean=true;
                    }
                    else if(addText.equals("BACK")){
                        addTextBoolean=true;
                    }
                    else {
                            System.out.println("You have enter incorrect command!");
                        }
                }

            }
            else if(command.equals("CREATE COMMENT")){
                System.out.println("If you want to comment for post enter \"POST\" .");
                System.out.println("If you want to comment for comment enter \"COMMENT\" .");
                String postOrComment=scanner.nextLine().trim();
                if(postOrComment.equals("POST")){
                    System.out.print("Please enter post's ID : ");
                    String postId=scanner.nextLine().trim();
                    System.out.print("Please enter your comment content : ");
                    String commentContent=scanner.nextLine().trim();
                    Comment comment=new Comment(true,postId,null,commentContent,userName);
                }
                else if(postOrComment.equals("COMMENT")){
                    System.out.print("Please enter comment's ID : ");
                    String commandId=scanner.nextLine().trim();
                    System.out.print("Please enter your comment content : ");
                    String commentContent=scanner.nextLine().trim();
                    Comment comment=new Comment(false,null,commandId,commentContent,userName);
                }
                else {
                    System.out.println("You enter incorrect command!");
                }
            }
            else if(command.equals("LIKE")){
                System.out.println("If you want to like post enter \"POST\" .");
                System.out.println("If you want to like comment enter \"COMMENT\" .");
                String postOrComment=scanner.nextLine().trim();
                if(postOrComment.equals("POST")){
                    System.out.print("Please enter post's ID : ");
                    String postId=scanner.nextLine().trim();
                    Like like=new Like(true,postId,null,userName);
                }
                else if(postOrComment.equals("COMMENT")){
                    System.out.print("Please enter comment's ID : ");
                    String commandId=scanner.nextLine().trim();
                    Like like=new Like(false,null,commandId,userName);
                }
                else {
                    System.out.println("You enter incorrect command!");
                }
            }
            else if(command.equals("RECOMMENDED ACCOUNT")){
                RecommendUsers recommendUsers=new RecommendUsers();
                RecommendUsers.userName=userName;
                recommendUsers.run();
            }
            else if(command.equals("LOGOUT")){
                isMenu=false;
                Login login=new Login();
                login.run();
            }
            else{
                System.out.println("You have entered incorrect command!");
            }
        }
    }

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
}

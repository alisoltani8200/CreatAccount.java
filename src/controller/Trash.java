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

public class Trash {

//    public void run(){
//
//        final String DB_url = "jdbc:mysql://localhost/users?serverTimezone=UTC";
//        final String username = "root";
//        final String Password = "ali12345678";
//
//
//
//        try {
//
//            ArrayList<Integer> ids = new ArrayList<>();
//            ArrayList<String> postContents = new ArrayList<>();
//
//            Connection conn = DriverManager.getConnection(DB_url, username, Password);
//            //connect to sql successfully
//
//            Statement statement = conn.createStatement();
//            String sql = "SELECT * FROM posts WHERE sender LIKE '"  +  view.UserPage.userName + "'";
//
//            ResultSet resultSet = statement.executeQuery(sql);
//
//
//            while (resultSet.next()){
//                ids.add(resultSet.getInt("id"));
//                postContents.add(resultSet.getString("postContent"));
//            }
//
//            System.out.println("All your posts :");
//
//            for (String i : postContents){
//                System.out.println(i);
//                System.out.println("if you want to see next post enter \"NEXT\"");
//                System.out.println("if you want to see this post comments enter \"SHOW COMMENTS\"");
//                System.out.println("if you want to see this post likes enter \"SHOW LIKES\"");
//                String temp = scanner.nextLine();
//                if (temp.equals("NEXT")){
//                }
//
//                else if (temp.equals("SHOW COMMENTS")){
//                    try {
//                        String sql2 = "SELECT * FROM comments WHERE postId = " + ids.get(postContents.indexOf(i));
//                        ResultSet resultSet1 = statement.executeQuery(sql2);
//                        while (resultSet1.next()){
//                            String content = resultSet1.getString("commentContent");
//                            int sender = resultSet1.getInt("id");
//                            System.out.print(content);
//
//                            try {
//                                String sql3 = "SELECT * FROM comments WHERE commentId = " + sender ;
//                                ResultSet resultSet2 = statement.executeQuery(sql3);
//                                int commentsOnComment = 0;
//                                while (resultSet2.next()){
//                                    commentsOnComment++;
//                                }
//                                System.out.println("   have " + commentsOnComment);
//
//                            }catch (SQLException e){}
//
//
//                        }
//                    }catch (SQLException e){}
//
//                }
//
//
//            }
//
//
//            boolean isBack = false;
//            while (!isBack) {
//
//                System.out.println("If you want to go to edit your profile enter \"EDIT PROFILE\" : ");
//                System.out.println("If you want to see your posts enter \"MY POSTS\" : ");
//                System.out.println("If you want to see other's posts enter \"FOLLOWING'S POSTS\" : ");
//                System.out.println("If you want to end the program enter \"BACK USER PAGE\" : ");
//
//
//                String temp1 = scanner.nextLine().trim();
//
//                if (temp1.equals("EDIT PROFILE")) {
//
//                }
//                else if (temp1.equals("MY POSTS")) {
//
//                }
//                else if (temp1.equals("FOLLOWING'S POSTS")) {
//
//                }
//                else if (temp1.equals("BACK USER PAGE")) {
//                    view.UserPage userPage=new view.UserPage();
//                    userPage.run();
//                    isBack = true;
//                }
//                else {
//                    System.out.println("wrong order , please pay attention ");
//                }
//            }
//
//        }
//        catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }


//    else if(command.equals("FOLLOWING'S POST")){
//        controller.FollowingsPost followingsPost=new controller.FollowingsPost();
//        followingsPost.userName=userName;
//        followingsPost.all();
//    }




    // recommended account :
//    package controller;
//
//import model.Comment;
//import model.Like;
//import view.Menu;
//import view.UserPage;
//
//import java.sql.*;
//import java.time.LocalDate;
//import java.time.LocalTime;
//import java.time.format.DateTimeFormatter;
//import java.util.ArrayList;
//import java.util.Scanner;
//
//    public class RecommendUsers {
//        Scanner scanner = new Scanner(System.in);
//        public static String userName;
//
//        private final String DB_url = "jdbc:mysql://localhost/users?serverTimezone=UTC";
//        private final String username = "root";
//        private final String Password = "ali12345678";
//
//        private ArrayList<String> usersName = new ArrayList<>();
//        private ArrayList<Integer> usersScore = new ArrayList<>();
//        private ArrayList<String> otherUser = new ArrayList<>();
//
//        public void run() throws SQLException {
//            Rating();
//            selectionSort(usersScore);
//
//            int count1 =0 ;
//            int count2 = 3;
//            System.out.println(" our recommend user for you to follow : ");
////        for (int i = usersName.size()-1 ; i>-1 ; i--){
////            if (count1 < count2 && i >0 ){
////                System.out.println("********");
////                System.out.println(usersName.get(i));
////                System.out.println("********");
////                System.out.println();
////                count1++;
////            }
////            else {
////
////                boolean command = false;
////                while (!command){
////                    if (count2 == count1 ){
////                        i++;
////                    }
////                    if (i != 0) {
////                        System.out.println("if you wanna see another recommended users enter \"NEXT\" ");
////                    }
////                    else {
////                        System.out.println("there is no user exist for recommend ");
////                    }
////                    if (count2 > 3) {
////                        System.out.println("if you wanna see  recommended users before this ones enter \"PREVIOUS\" ");
////                    }
////                    System.out.println("if you wanna see profile of a user enter \"SEE PROFILE\" ");
////                    System.out.println("if you wanna back to menu enter \"BACK\" ");
////
////                    String order = scanner.nextLine().trim();
////
////                    if (order.equals("NEXT")){
////                        count2 += 3;
////                        command = true;
////                    }
////                    else if (order.equals("PREVIOUS")){
////                        count2 -= 3;
////                        count1 -= 6;
////                        i += 6;
////                        command = true;
////                    }
////
////                    else if (order.equals("SEE PROFILE")){}
////
////                    else if (order.equals("BACK")){
////                        command = true;
////                        i = -1;
////                    }
////
////                    else{
////                        System.out.println("wrong order , please pay attention ");
////                    }
////
////
////                }
////            }
////
////        }
//            int id=0;
//            if(id<usersName.size()-1){
//                System.out.println(usersName.get(++id));
//            }
//            if(id<usersName.size()-1){
//                System.out.println(usersName.get(++id));
//            }
//            if(id<usersName.size()-1){
//                System.out.println(usersName.get(++id));
//            }
//            boolean run=true;
//            while (run){
//                System.out.println("If you want to see next 3 recommended username enter \"NEXT\" : ");
//                System.out.println("If you want to see previous 3 recommended username enter \"PREVIOUS\" : ");
//                System.out.println("If you want to see profile enter \"SEE PROFILE\" : ");
//                System.out.println("If you want to back enter \"BACK\" : ");
//                String command=scanner.nextLine().trim();
//                if(command.equals("NEXT")){
//                    if(id+2>=usersName.size()-1){
//                        System.out.println("End of recommended account!");
//                    }
//                    else {
//                        if(id<usersName.size()-1){
//                            System.out.println(usersName.get(++id));
//                        }
//                        if(id<usersName.size()-1){
//                            System.out.println(usersName.get(++id));
//                        }
//                        if(id<usersName.size()-1){
//                            System.out.println(usersName.get(++id));
//                        }
//                    }
//                }
//                else if(command.equals("PREVIOUS")){
//                    if(id-6<=0){
//                        if (id%3==2) {
//                            id=id-5;
//                        }
//                        else if(id%3==1){
//                            id=id-4;
//                            System.out.println(usersName.get(id));
//                            System.out.println(usersName.get(++id));
//                            System.out.println(usersName.get(++id));
//                        }
//                        else{
//                            System.out.println(usersName.get(id));
//                            System.out.println(usersName.get(++id));
//                            System.out.println(usersName.get(++id));
//                        }
//                    }
//                    else{
//                        System.out.println("These are first recommended account! ");
//                    }
//
//                }
//                else if(command.equals("SEE PROFILE")){
//                    seeProfile();
//                }
//                else if(command.equals("BACK")){
//                    run=false;
//                    Menu menu=new Menu();
//                    menu.run();
//                }
//                else{
//                    System.out.println("You have entered incorrect command!");
//                }
//            }
//
//        }
//
//
//
//
//
//        private void Rating (){
//
//            ArrayList<String> followers = new ArrayList<>();
//            ArrayList<String> followings = new ArrayList<>();
//            try {
//                Connection conn = DriverManager.getConnection(DB_url, username, Password);
//                Statement statement1 = conn.createStatement();
//                String sql1 = "SELECT * FROM followings";
//                ResultSet resultSet1 = statement1.executeQuery(sql1);
//
//                int temp  =1 ;
//                while (resultSet1.next()){
//                    if (resultSet1.getString(userName) != null){
////                    System.out.println(resultSet1.getString(userName));
//                        followings.add(resultSet1.getString(userName));
//                    }
//                    temp++;
//                }
//                System.out.println("*******");
//
//
//                Statement statement2 = conn.createStatement();
//                String sql2 = "SELECT * FROM followers";
//                ResultSet resultSet2 = statement2.executeQuery(sql2);
//
//                temp = 1;
//                while (resultSet2.next()){
//                    if (resultSet2.getString(userName) != null){
////                    System.out.println(resultSet2.getString(userName));
//                        followers.add(resultSet2.getString(userName));
//                        usersName.add(resultSet2.getString(userName));
//                        usersScore.add(3);
//                    }
//                    temp++;
//                }
//                System.out.println("*******");
//
//
//            }
//            catch (SQLException e) {
//                e.printStackTrace();
//            }
//
//            for (String i : followings) {
//
//                try {
//                    Connection conn = DriverManager.getConnection(DB_url, username, Password);
//                    Statement statement1 = conn.createStatement();
//                    String sql1 = "SELECT * FROM followings";
//                    ResultSet resultSet1 = statement1.executeQuery(sql1);
//
//
//                    while (resultSet1.next()){
//                        if (resultSet1.getString(i) != null && !followings.contains(resultSet1.getString(i)) && !resultSet1.getString(i).equals(userName)){
////                        System.out.println(resultSet1.getString(i));
//                            if (usersName.contains(resultSet1.getString(i))){
//                                usersScore.set(usersName.indexOf(resultSet1.getString(i)) , usersScore.get(usersName.indexOf(resultSet1.getString(i))) + 5) ;
//                            }
//                            else {
//                                usersName.add(resultSet1.getString(i));
//                                usersScore.add(5);
//                            }
//                        }
//                    }
//                    System.out.println("*******");
//
//
//                    Statement statement2 = conn.createStatement();
//                    String sql2 = "SELECT * FROM followers";
//                    ResultSet resultSet2 = statement2.executeQuery(sql2);
//
//                    while (resultSet2.next()){
//                        if (resultSet2.getString(i) != null && !followings.contains(resultSet2.getString(i)) && !resultSet2.getString(i).equals(userName)) {
////                        System.out.println(resultSet2.getString(i));
//                            if (usersName.contains(resultSet2.getString(i))) {
//                                System.out.println(resultSet2.getString(i));
//                                usersScore.set(usersName.indexOf(resultSet2.getString(i)), usersScore.get(usersName.indexOf(resultSet2.getString(i))) + 4);
//                            }
//                            else {
//                                usersName.add(resultSet2.getString(i));
//                                usersScore.add(4);
//                            }
//                        }
//                    }
//                    System.out.println("*******");
//
//
//                }
//                catch (SQLException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            for (String i : followers) {
//
//                try {
//                    Connection conn = DriverManager.getConnection(DB_url, username, Password);
//                    Statement statement1 = conn.createStatement();
//                    String sql1 = "SELECT * FROM followings";
//                    ResultSet resultSet1 = statement1.executeQuery(sql1);
//
//
//                    while (resultSet1.next()){
//                        if (resultSet1.getString(i) != null  && !followings.contains(resultSet1.getString(i)) && !resultSet1.getString(i).equals(userName)){
////                        System.out.println(resultSet1.getString(i));
//                            if (usersName.contains(resultSet1.getString(i))){
//                                usersScore.set(usersName.indexOf(resultSet1.getString(i)) , usersScore.get(usersName.indexOf(resultSet1.getString(i))) + 2) ;
//                            }
//                            else {
//                                usersName.add(resultSet1.getString(i));
//                                usersScore.add(2);
//                            }
//                        }
//                    }
//                    System.out.println("*******");
//
//
//                    Statement statement2 = conn.createStatement();
//                    String sql2 = "SELECT * FROM followers";
//                    ResultSet resultSet2 = statement2.executeQuery(sql2);
//
//                    while (resultSet2.next()){
//                        if (resultSet2.getString(i) != null  && !followings.contains(resultSet2.getString(i)) && !resultSet2.getString(i).equals(userName)) {
////                        System.out.println(resultSet2.getString(i));
//
//                            if (usersName.contains(resultSet2.getString(i))) {
//                                usersScore.set(usersName.indexOf(resultSet2.getString(i)), usersScore.get(usersName.indexOf(resultSet2.getString(i))) + 1);
//                            }
//                            else {
//                                usersName.add(resultSet2.getString(i));
//                                usersScore.add(1);
//                            }
//                        }
//                    }
//                    System.out.println("*******");
//
//
//                }
//                catch (SQLException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            try {
//                Connection conn = DriverManager.getConnection(DB_url, username, Password);
//                Statement statement1 = conn.createStatement();
//                String sql1 = "SELECT * FROM personalInformation";
//                ResultSet resultSet1 = statement1.executeQuery(sql1);
//
//                while (resultSet1.next()){
//                    if (!followings.contains(resultSet1.getString("username")) && !followers.contains(resultSet1.getString("username")) && !usersName.contains(resultSet1.getString("username"))){
//                        otherUser.add(resultSet1.getString(userName));
//                    }
//                }
//            }
//            catch (SQLException e){}
//
//        }
//
//        private void selectionSort(ArrayList<Integer> arr) {
//            int pos;
//            int temp1;
//            String temp2;
//            for (int i = 0; i < arr.size(); i++)
//            {
//                pos = i;
//                for (int j = i+1; j < arr.size(); j++)
//                {
//                    if (arr.get(j) < arr.get(pos))                  //find the index of the minimum element
//                    {
//                        pos = j;
//                    }
//                }
//
//                temp1 = arr.get(pos);            //swap the current element with the minimum element
//                arr.set(pos , arr.get(i));
//                arr.set(i  ,temp1 );
//
//
//                temp2 = usersName.get(pos);            //swap the current element with the minimum element
//                usersName.set(pos , usersName.get(i));
//                usersName.set(i  ,temp2 );
//
//
//            }
//        }
//
//        private void seeProfile(){
//
//            System.out.print("Please enter your desire username : ");
//            String desireUserName=scanner.nextLine().trim();
//            try {
//                Connection connUserName = DriverManager.getConnection(DB_url, username, Password);
//                Statement statement = connUserName.createStatement();
//                String desireUserSql = "SELECT * FROM personalInformation WHERE username LIKE '"+desireUserName+"'";
//                ResultSet resultSet = statement.executeQuery(desireUserSql);
//                if(resultSet.next()){
//                    String desireAccountType=resultSet.getString("accounttype");
//                    String desireBusinessType=resultSet.getString("businessType");
//
//                    if(desireBusinessType==null){
//                        System.out.println("Person's name is : "+resultSet.getString("name"));
//                        System.out.println("model.Account username is : "+desireUserName);
//                        System.out.println("model.Account Type is : "+desireAccountType);
//                    }
//                    else{
//                        System.out.println("Business account name is : "+resultSet.getString("name"));
//                        System.out.println("Username is : "+desireUserName);
//                        System.out.println("model.Account Type is : "+desireAccountType);
//                        System.out.println("Business Type is : "+desireBusinessType);
//                    }
//
//
//                    String sqlPost="SELECT * FROM posts WHERE sender LIKE '"+desireUserName+"'";
//                    ResultSet resultSetPost=statement.executeQuery(sqlPost);
//                    int postNum=0;
//                    while(resultSetPost.next()){
//                        postNum++;
//                    }
//                    System.out.println("Person's post number is : "+postNum);
//                    String sql = "SELECT " + desireUserName +" FROM followers";
//
//                    ResultSet resultSetFollowers = statement.executeQuery(sql);
//                    System.out.println("Person's followers :");
//
//                    while (resultSetFollowers.next()) {
//                        if(resultSetFollowers.getString(desireUserName)!=null){
//                            System.out.println(resultSetFollowers.getString(desireUserName));
//                        }
//                    }
//
//                    String sql2 = "SELECT " + desireUserName +" FROM followings";
//                    ResultSet resultSetFollowings = statement.executeQuery(sql2);
//
//                    System.out.println("Person's following :");
//                    while (resultSetFollowings.next()) {
//                        if(resultSetFollowings.getString(desireUserName)!=null){
//                            System.out.println(resultSetFollowings.getString(desireUserName));
//                        }
//                    }
//
//                    boolean isGo=true;
//                    while (isGo){
//                        String checkFollowSql = "SELECT * FROM followings WHERE "+userName+" LIKE '"+desireUserName+"'";
//                        ResultSet checkFollowResultSet = statement.executeQuery(checkFollowSql);
//                        boolean checkFollow=false;
//                        if(checkFollowResultSet.next()) {
//                            checkFollow = true;
//                        }
//                        if(checkFollow){
//                            System.out.println("Tou are following "+desireUserName);
//                            System.out.println("If you want to unfollow "+desireUserName+" enter \"UNFOLLOW\" :");
//                        }
//                        else {
//                            System.out.println("If you want to follow "+desireUserName+" enter \"FOLLOW\" :");
//                        }
//                        System.out.println("If you want to see "+desireUserName+"'s post enter \"SEE POSTS\" : ");
//                        System.out.println("If you want to back enter \"BACK\" : ");
//                        System.out.print("What is your command ? ");
//                        String followCommand=scanner.nextLine().trim();
//                        if(followCommand.equals("FOLLOW")){
//                            follow(userName,desireUserName);
//                        }
//                        else if(followCommand.equals("UNFOLLOW")){
//                            unFollow(userName,desireUserName);
//                        }
//                        else if(followCommand.equals("SEE POSTS")){
//                            ArrayList<Integer> ids=new ArrayList<>();
//                            ArrayList<String> sender=new ArrayList<>();
//                            ArrayList<String> postContent=new ArrayList<>();
//                            ArrayList<String> postTime=new ArrayList<>();
//                            ArrayList<String> postDate=new ArrayList<>();
//
//                            String postSql="SELECT * FROM posts WHERE sender LIKE '"+desireUserName+"'";
//                            ResultSet postResultSet = statement.executeQuery(postSql);
//                            boolean isEnpty=true;
//                            while (postResultSet.next()){
//                                ids.add(postResultSet.getInt("id"));
//                                sender.add(postResultSet.getString("sender"));
//                                postContent.add(postResultSet.getString("postContent"));
//                                postTime.add(postResultSet.getString("postTime"));
//                                postDate.add(postResultSet.getString("postDate"));
//                                isEnpty=false;
//                            }
//
//                            if(!isEnpty){
//                                int id=0;
//                                //first post :
//                                System.out.println("model.Post sender : " +sender.get(id) );
//                                System.out.println("model.Post content : " + postContent.get(id));
//                                System.out.println("Time of posting : "+postTime.get(id) + "   " + postDate.get(id));
//                                System.out.println("model.Post id is : "+ids.get(id));
//                                System.out.println("---------------------------------------------------------------------------------------------");
//                                view(ids.get(id));
//                                //end of first post :
//                                boolean run=true;
//                                while (run){
//                                    System.out.println("If ypu want to wee next post enter \"NEXT\" : ");
//                                    System.out.println("If you want to see previous post enter \"PREVIOUS\" : ");
//                                    System.out.println("If you want to create comment for this post enter \"CREATE COMMENT FOR THIS POST\" : ");
//                                    System.out.println("If you want to like this post enter \"LIKE THIS POST\" : ");
//                                    System.out.println("If you want to back enter \"BACK\" : ");
//                                    System.out.print("What is your command ? ");
//                                    String command=scanner.nextLine().trim();
//                                    if(command.equals("NEXT")){
//                                        if(id<sender.size()-1) {
//                                            System.out.println("model.Post sender : " + sender.get(++id));
//                                            System.out.println("model.Post content : " + postContent.get(id));
//                                            System.out.println("Time of posting : " + postTime.get(id) + "   " + postDate.get(id));
//                                            System.out.println("model.Post id is : "+ids.get(id));
//                                            System.out.println("---------------------------------------------------------------------------------------------");
//                                            view(ids.get(id));
//                                        }
//                                        else {
//                                            System.out.println("End of posts :) ");
//                                        }
//                                    }
//                                    else if(command.equals("PREVIOUS")){
//                                        if(id>0) {
//                                            System.out.println("model.Post sender : " + sender.get(--id));
//                                            System.out.println("model.Post content : " + postContent.get(id));
//                                            System.out.println("Time of posting : " + postTime.get(id) + "   " + postDate.get(id));
//                                            System.out.println("model.Post id is : "+ids.get(id));
//                                            System.out.println("---------------------------------------------------------------------------------------------");
//                                            view(ids.get(id));
//                                        }
//                                        else {
//                                            System.out.println("This is first post!");
//                                        }
//                                    }
//                                    else if(command.equals("CREATE COMMENT FOR THIS POST")){
//                                        System.out.print("Please enter your comment content : ");
//                                        String commentContent=scanner.nextLine().trim();
//                                        Comment comment=new Comment(true,ids.get(id).toString(),null,commentContent,userName);
//                                    }
//                                    else if(command.equals("LIKE THIS POST")){
//                                        Like like=new Like(true,ids.get(id).toString(),null,userName);
//                                    }
//                                    else if(command.equals("BACK")){
//                                        run=false;
//                                    }
//                                }
//                            }
//                            else{
//                                System.out.println("No post :) ");
//                            }
//                        }
//                        else if(followCommand.equals("BACK")){
//                            isGo=false;
//                        }
//                        else{
//                            System.out.println("You have enter incorrect command!");
//                        }
//                    }
//                }
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//
//
//        private void view(int postId) throws SQLException {
//            Connection conn = DriverManager.getConnection(DB_url, username, Password);
//            Statement statement1=conn.createStatement();
//            Statement statement2=conn.createStatement();
//            String lastView="SELECT * FROM  views WHERE postId='"+postId+"'"+" AND viewer LIKE '"+userName+"'";
//            ResultSet resultSet=statement1.executeQuery(lastView);
//            if(!resultSet.next()){
//                String viewSql="INSERT INTO views (postId,viewer,viewTime,viewDate) VALUES ('"+postId+"','"+userName+"','"+ LocalTime.now().format(Formatter1()).toString() +"','"+ LocalDate.now().toString()+"')";
//                statement2.executeUpdate(viewSql);
//            }
//        }
//
//        private DateTimeFormatter Formatter1(){
//            return DateTimeFormatter.ofPattern("HH:mm");
//        }
//
//        private void unFollow(String follower,String following){
//            try {
//                Connection conn = DriverManager.getConnection(DB_url, username, Password);
//                Statement followingStatement = conn.createStatement();
//                Statement followerStatement=conn.createStatement();
//                String followingSql = "DELETE FROM followers WHERE "+following+" LIKE '"+follower+"'";
//                String followerSql = "DELETE FROM followings WHERE "+follower+" LIKE '"+following+"'";
//                int a=followingStatement.executeUpdate(followingSql);
//                int b=followerStatement.executeUpdate(followerSql);
//                if(a==1&&b==1){
//                    System.out.println("You unfollow "+following+" successfully :)");
//                }
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//
//        private void follow(String follower,String following){
//            try {
//                Connection conn = DriverManager.getConnection(DB_url, username, Password);
//                Statement statement = conn.createStatement();
//                String followingSql = "INSERT INTO followers ("+following+") VALUES ('"+follower+"')";
//                String followerSql = "INSERT INTO followings ("+follower+") VALUES ('"+following+"')";
//                int b =statement.executeUpdate(followingSql);
//                int a = statement.executeUpdate(followerSql);
//                if(a==1&&b==1){
//                    System.out.println("You follow "+following+" successfully :)");
//                }
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//
//    }


}

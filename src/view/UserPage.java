package view;

import controller.FollowingsPost;
import controller.MyPosts;
import model.BusinessAccount;
import model.NormalAccount;

import java.sql.*;
import java.util.Scanner;




public class UserPage {

    Scanner scanner = new Scanner(System.in);

    public static BusinessAccount businessAccount;
    public static NormalAccount normalAccount;
    public static String userName;
    public static String name;

    private final String DB_url = "jdbc:mysql://localhost/users?serverTimezone=UTC";
    private final String username = "root";
    private final String Password = "ali12345678";

//    public static int id;
//    public static String userName;
//    public static String password;
//    public static model.AccountType accountType;
//    public static LocalDate birthDay;
//    public static model.BusinessType businessType;
//



    public void run() throws SQLException{

        Connection conn = DriverManager.getConnection(DB_url, username, Password);
        Statement statement = conn.createStatement();

        System.out.println("welcome " +name);
        if(businessAccount!=null){
            System.out.println("Your model.BusinessType is : "+businessAccount.getBusinessType().toString());
        }
        else {
            System.out.println("Your birth day is : "+normalAccount.getBirth());
        }
        String sqlPost="SELECT * FROM posts WHERE sender LIKE '"+userName+"'";
        ResultSet resultSetPost=statement.executeQuery(sqlPost);
        int postNum=0;
        while(resultSetPost.next()){
            postNum++;
        }
        System.out.println("Your post number is : "+postNum);
        try {
            String sql = "SELECT " + userName +" FROM followers";

            ResultSet resultSet = statement.executeQuery(sql);
            System.out.println("your followers :");

            while (resultSet.next()) {
                if(resultSet.getString(userName)!=null){
                    System.out.println(resultSet.getString(userName));
                }
            }

            String sql2 = "SELECT " + userName +" FROM followings";
            ResultSet resultSet1 = statement.executeQuery(sql2);

            System.out.println("your following :");
            while (resultSet1.next()) {
                if(resultSet1.getString(userName)!=null){
                    System.out.println(resultSet1.getString(userName));
                }
            }


            boolean ifBack = false;
            while (!ifBack) {
                System.out.println("If you want to go to edit your profile enter \"EDIT PROFILE\" : ");
                System.out.println("If you want to see your posts enter \"MY POSTS\" : ");
                System.out.println("If you want to see other's posts enter \"FOLLOWING'S POSTS\" : ");
                System.out.println("If you want to end the program enter \"BACK MENU\" : ");

                String temp1 = scanner.nextLine().trim();
                if (temp1.equals("EDIT PROFILE")) {

                }
                else if (temp1.equals("MY POSTS")) {
                    MyPosts myPosts = new MyPosts();
                    myPosts.run();
                }
                else if (temp1.equals("FOLLOWING'S POSTS")) {
                    boolean show=true;
                    while (show){
                        System.out.println("If you want to see all posts of your following enter \"ALL\" : ");
                        System.out.println("If you want to see certain following's post enter \"CERTAIN FOLLOWING\" : ");
                        System.out.println("If you want to see recent post of your following's post enter \"RECENT POSTS\" : ");
                        System.out.println("If you want to back enter \"BACK\" : ");
                        System.out.print("What is your command ? ");
                        String command=scanner.nextLine().trim();
                        if(command.equals("ALL")){
                            FollowingsPost followingsPost=new FollowingsPost();
                            FollowingsPost.userName=userName;
                            followingsPost.all();
                        }
                        else if(command.equals("CERTAIN FOLLOWING")){
                            FollowingsPost followingsPost=new FollowingsPost();
                            FollowingsPost.userName=userName;
                            followingsPost.certain();
                        }
                        else if(command.equals("RECENT POSTS")){
                            FollowingsPost followingsPost=new FollowingsPost();
                            FollowingsPost.userName=userName;
                            followingsPost.recent();
                        }
                        else if(command.equals("BACK")){
                            show=false;
                        }
                        else {
                            System.out.println("You have enter incorrect command ! ");
                        }
                    }

                }
                else if (temp1.equals("BACK MENU")) {
                    ifBack = true;
                }
                else {
                    System.out.println("wrong order , please pay attention ");
                }
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

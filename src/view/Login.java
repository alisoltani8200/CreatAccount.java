package view;

import model.AccountType;
import model.BusinessAccount;
import model.NormalAccount;
import view.Menu;

import java.sql.*;
import java.time.LocalDate;
import java.util.Scanner;

public class Login {
    Scanner scanner = new Scanner(System.in);

    private final String DB_url = "jdbc:mysql://localhost/users?serverTimezone=UTC";
    private final String username = "root";
    private final String Password = "ali12345678";



    public void  run(){
        String command;
        String command2;
        String userName;
        String password;
        String security = "";
        System.out.println("Welcome to our \"tweeter\" simulation  ");
        boolean isLogin = false;
        boolean enterPassWord=false;

        while (! isLogin) {
            System.out.println("If you have an account enter \"LOGIN\" ");
            System.out.println("If you don't have an account enter \"CREATE ACCOUNT\" " );
            System.out.print("what is your command?  :  ");
            command = scanner.nextLine().trim();

            if (command.equals("CREATE ACCOUNT")){
                CreatAccount creatAccount=new CreatAccount();
                creatAccount.run();
            }
            else if(command.equals("LOGIN")){
                System.out.print("what is your username?  :  ");
                userName = scanner.nextLine().trim();
                enterPassWord=false;
                while (!enterPassWord){
                    System.out.print("If you forgot your password enter \"FORGOT PASSWORD\" else enter \"PASSWORD\" or for back enter \"BACK\" : " );
                    command = scanner.nextLine().trim();
                    if (command.equals("FORGOT PASSWORD")){
                        int userID = 0;
                        boolean ifTrue = false;
                        boolean backToLogin = false;


                        while (!ifTrue && !backToLogin ) {

                            System.out.println("If you want to back in login mode enter \"BACK TO LOGIN\" in the username part ");
                            System.out.print("what is your username?  :  ");
                            userName = scanner.nextLine().trim();
                            if (userName.equals("BACK TO LOGIN")) {
                                backToLogin = true;
                            }
                            else {
                                System.out.print("what is you're favorite famous person?  :  ");
                                security = scanner.nextLine().trim();

                                try {
                                    Connection conn = DriverManager.getConnection(DB_url, username, Password);

                                    String sql = "SELECT * FROM personalInformation WHERE username=? AND securityQ=?";

                                    PreparedStatement preparedStatement = conn.prepareStatement(sql);
                                    preparedStatement.setString(1, userName);
                                    preparedStatement.setString(2, security);

                                    ResultSet resultSet = preparedStatement.executeQuery();

                                    if (resultSet.next()) {
                                        userID = resultSet.getInt("id");
                                        System.out.println(userID);
                                        ifTrue = true;
                                    }
                                    else {
                                        System.out.println("wrong username or security answer question ");
                                        ifTrue = false;
                                    }

                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                        backToLogin = false;

                        if (ifTrue) {
                            boolean confirmPassword = false;

                            while (!confirmPassword) {
                                System.out.println("insert your new password  :  ");
                                password = scanner.nextLine().trim();

                                System.out.println("insert your  password again for confirmation  :  ");
                                String temp = scanner.nextLine().trim();

                                if (format(password)) {
                                    if (password.equals(temp)) {

                                        try {
                                            Connection conn = DriverManager.getConnection(DB_url, username, Password);

                                            String sql = "UPDATE personalInformation SET password=? WHERE id=?";
                                            System.out.println(userID);

                                            PreparedStatement preparedStatement = conn.prepareStatement(sql);
                                            preparedStatement.setString(1, password);
                                            preparedStatement.setInt(2, userID);

                                            int resultSet = preparedStatement.executeUpdate();

                                            if (resultSet == 1  ){
                                                System.out.println("your password changed successfully :) \n " +
                                                        "now go and log in :)");
                                            }
                                            confirmPassword = true;
                                            enterPassWord=true;

                                        } catch (SQLException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                    else {
                                        System.out.println("your password and confirm password don't match \n" +
                                                "please re enter them ");
                                    }
                                }

                                else {
                                    System.out.println("you're password format is wrong , please pay attention");
                                }
                            }
                        }
                    }
                    else if(command.equals("PASSWORD")) {
                        System.out.print("what is your password? (you're password should have at least  8 digits and at least one letter and one number and one character between (# \\ @ ! % $ & ) and maximum 16 digits)    :  ");
                        password = scanner.nextLine().trim();


                        if (format(password)) {
                            try {
                                Connection conn = DriverManager.getConnection(DB_url, username, Password);

                                String sql = "SELECT * FROM personalInformation ";
                                isLogin = isLogin(userName, password, isLogin, conn, sql);
                                if(isLogin){
                                    enterPassWord=true;
                                }
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }
                        else {
                            System.out.println("you're password format is wrong , please pay attention");
                        }
                    }
                    else if(command.equals("BACK")){
                        enterPassWord=true;
                    }
                    else{
                        System.out.println("You have enter incorrect command!");
                    }
                }
            }
        }
    }

    private boolean isLogin(String userName, String security, boolean isLogin, Connection conn, String sql) throws SQLException {
//        PreparedStatement preparedStatement = conn.prepareStatement(sql);
//        preparedStatement.setString(1, userName);
//        preparedStatement.setString(2, security);
        Statement statement= conn.createStatement();
        sql=sql+" WHERE username LIKE '"+userName+ "' AND password LIKE '"+security+"'";
//        ResultSet resultSet = preparedStatement.executeQuery();
        ResultSet resultSet=statement.executeQuery(sql);
        if (resultSet.next()) {
            System.out.println("you login successfully :) ");
            System.out.println("Welcome "+resultSet.getString("name"));
            isLogin = true;

            if(resultSet.getString("accounttype").equals(AccountType.BusinessAccount.toString())){
                BusinessAccount businessAccount=new BusinessAccount(resultSet.getString("name"),resultSet.getString("username"),
                        resultSet.getString("password"),resultSet.getString("password"),
                        resultSet.getString("businessType"),resultSet.getInt("id"));
                Menu.businessAccount=businessAccount;
                Menu.normalAccount=null;
                Menu.name=resultSet.getString("name");
            }
            else{
                NormalAccount normalAccount=new NormalAccount(resultSet.getString("name"),resultSet.getString("username"),
                        resultSet.getString("password"),resultSet.getString("password"),
                        LocalDate.parse(resultSet.getString("birthday")),resultSet.getInt("id"));
                Menu.normalAccount=normalAccount;
                Menu.businessAccount=null;
                Menu.name=resultSet.getString("name");
            }
//            try {
//                Connection conn1 = DriverManager.getConnection(DB_url, username, Password);
//
//                Statement statement1 = conn1.createStatement();
//                String sql1 = "SELECT * FROM personalInformation WHERE username LIKE '"+userName+"'";
//                ResultSet resultSet1 = statement1.executeQuery(sql1);
//                if (resultSet1.next()) {
//
//                }
//                else {
//                    System.out.println("Error!");
//                }
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
            Menu menu=new Menu();
            Menu.userName=userName;
            menu.run();
        } else {
            System.out.println("wrong username or password");
        }
        return isLogin;
    }

    private boolean format(String password){
        if (!password.contains("#") && !password.contains("\\") && !password.contains("@") && !password.contains("!") && !password.contains("%") && !password.contains("$") && !password.contains("&")) {
            return false;
        }

        else if (password.length() < 8 || password.length() > 16) {
            return false;
        }

        else {
            boolean number = false;
            boolean letter = false;
            for (int i = 0; i < password.length(); i++) {
                char temp = password.charAt(i);
                if ((temp >= 65 && temp <= 90) || (temp >= 97 && temp <= 122)) {
                    letter = true;
                } else if ((temp >= 48 && temp <= 57)) {
                    number = true;
                }
                if (letter && number) {
                    return true;
                }
            }
            return false;
        }
    }

    private void changePassword(String password,int userID, Connection conn , String sql ) throws SQLException {


    }
}

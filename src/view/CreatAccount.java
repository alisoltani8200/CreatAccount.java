package view;

import model.AccountType;
import model.BusinessType;

import java.sql.*;
import java.time.LocalDate;
import java.util.Scanner;

public class CreatAccount {
    private Scanner scanner=new Scanner(System.in);

    private final String DB_url = "jdbc:mysql://localhost/users?serverTimezone=UTC";
    private final String username = "root";
    private final String Password = "ali12345678";

    public void run(){
        String name;
        String userName;
        String passWord;
        String securityQuestion;
        AccountType accountType;
        accountType=accountType();
        if(accountType.equals(AccountType.NormalAccount)){
            name=name(true);
            userName=userName();
            passWord=passWord();
            checkPassWord(passWord);
            securityQuestion=securityQuestion();
            LocalDate birthDay=BirthDay();

            if(accountType!=null){
                try {
                    Connection conn = DriverManager.getConnection(DB_url, username, Password);

                    String sql = "INSERT INTO personalInformation (name,username,password,securityQ,accounttype,birthday) VALUES (?,?,?,?,?,?)";
                    PreparedStatement preparedStatement = conn.prepareStatement(sql);
                    preparedStatement.setString(1,name);
                    preparedStatement.setString(2, userName);
                    preparedStatement.setString(3, passWord);
                    preparedStatement.setString(4, securityQuestion);
                    preparedStatement.setString(5, accountType.toString());
                    preparedStatement.setString(6, birthDay.toString());
                    int x = preparedStatement.executeUpdate();

                    Statement statement = conn.createStatement();
                    String sql1="ALTER TABLE followers ADD "+userName+" VARCHAR(255)";
                    statement.executeUpdate(sql1);
                    Statement statement1 = conn.createStatement();
                    String sql2="ALTER TABLE followings ADD "+userName+" VARCHAR(255)";
                    statement1.executeUpdate(sql2);
                    Statement statement2=conn.createStatement();
                    String sql3="ALTER TABLE blocktable ADD "+userName+" VARCHAR(255)";
                    statement2.executeUpdate(sql3);

                    if (x==5) {
                        System.out.println("Your model.Account created successfully :) ");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }
        else{
            name=name(false);
            userName=userName();
            passWord=passWord();
            checkPassWord(passWord);
            securityQuestion=securityQuestion();
            BusinessType businessType=businessType();

            if(accountType!=null){
                try {
                    Connection conn = DriverManager.getConnection(DB_url, username, Password);

                    String sql = "INSERT INTO personalInformation (name,username,password,securityQ,accounttype,businessType) VALUES (?,?,?,?,?,?)";
                    PreparedStatement preparedStatement = conn.prepareStatement(sql);
                    preparedStatement.setString(1,name);
                    preparedStatement.setString(2, userName);
                    preparedStatement.setString(3, passWord);
                    preparedStatement.setString(4, securityQuestion);
                    preparedStatement.setString(5, accountType.toString());
                    preparedStatement.setString(6, businessType.toString());
                    int x = preparedStatement.executeUpdate();

                    Statement statement = conn.createStatement();
                    String sql1="ALTER TABLE followers ADD "+userName+" VARCHAR(255)";
                    statement.executeUpdate(sql1);
                    Statement statement1 = conn.createStatement();
                    String sql2="ALTER TABLE followings ADD "+userName+" VARCHAR(255)";
                    statement1.executeUpdate(sql2);
                    Statement statement2=conn.createStatement();
                    String sql3="ALTER TABLE blocktable ADD "+userName+" VARCHAR(255)";
                    statement2.executeUpdate(sql3);

                    if (x==5) {
                        System.out.println("Your model.Account created successfully :) ");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }
    }
    private String name(boolean normalAccount){
        if(normalAccount){
            System.out.print("Please enter your full name : ");
            return scanner.nextLine().trim();
        }
        else{
            System.out.print("Please enter your business name : ");
            return scanner.nextLine().trim();
        }
    }
    private String userName(){
        boolean isCreated=false;
        while (!isCreated){
            System.out.print("Please enter your desire username : ");
            String userName= scanner.nextLine().trim();


            try {
                Connection conn = DriverManager.getConnection(DB_url, username, Password);
                //connect to sql successfully

                Statement statement = conn.createStatement();
                String sql = "SELECT * FROM personalInformation WHERE username=?";
                PreparedStatement preparedStatement = conn.prepareStatement(sql);
                preparedStatement.setString(1,userName);
                ResultSet resultSet = preparedStatement.executeQuery();
                if(resultSet.next()){
                    System.out.println("This user name is already existed!");
                }
                else{
                    System.out.println("You set your userName successfully :) ");
                    isCreated=true;
                    return userName;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    private String passWord(){
        System.out.print("Please enter your desire password : ");
        String passWord= scanner.nextLine().trim();
        if(format(passWord)){
            return passWord;
        }
        else{
            System.out.println("you're password format is wrong , please pay attention");
            return passWord();
        }
    }
    private void checkPassWord(String passWord){
        System.out.print("Please enter your desire password again : ");
        String checkPassWord= scanner.nextLine().trim();
        if(checkPassWord.equals(passWord)){
            System.out.println("Your password set successfully :)");
        }
        else {
            System.out.println("Your checkPassWord is not equal with your password!");
            checkPassWord(passWord);
        }
    }
    private String securityQuestion(){
        System.out.println("Please answer your securityQuestion :");
        System.out.print("What is your favorite famous person : ");
        String securityQuastion=scanner.nextLine().trim();
        return securityQuastion;
    }
    private AccountType accountType(){
        System.out.print("Choose your account type (BusinessAccount or NormalAccount) : ");
        String accountType=scanner.nextLine().trim();
        if(accountType.equals("BusinessAccount")){
            return AccountType.BusinessAccount;
        }
        else if(accountType.equals("NormalAccount")){
            return AccountType.NormalAccount;
        }
        else{
            System.out.println("You have entered incorrect account type!");
            return accountType();
        }
    }
    private BusinessType businessType(){
        System.out.print("Please enter business account type  \"Artist,Blogger,Gamer,Photographer,Writer,Musician,Education\" : ");
        String businessType= scanner.nextLine().trim();
        if(businessType.equals("Artist")||businessType.equals("Blogger")||businessType.equals("Gamer")||businessType.equals("Photographer")||
                businessType.equals("Writer")||businessType.equals("Musician")||businessType.equals("Education")){
            if(businessType.equals("Artist")){
                return BusinessType.Artist;
            }
            else if(businessType.equals("Blogger")){
                return BusinessType.Blogger;
            }
            else if(businessType.equals("Gamer")){
                return BusinessType.Gamer;
            }
            else if(businessType.equals("Photographer")){
                return BusinessType.Photographer;
            }
            else if(businessType.equals("Writer")){
                return BusinessType.Writer;
            }
            else if(businessType.equals("Musician")){
                return BusinessType.Musician;
            }
            else {
                return BusinessType.Education;
            }
        }
        else{
            System.out.println("You enter incorrect command!");
            return businessType();
        }
    }
    private LocalDate BirthDay(){
        System.out.println("Please enter your birthday : ");
        System.out.print("Year : ");
        String year=scanner.nextLine().trim();
        System.out.print("Month : ");
        String month=scanner.nextLine().trim();
        System.out.print("Day : ");
        String day= scanner.nextLine().trim();
        String birthDay= year+"-"+month+"-"+day;
        return LocalDate.parse(birthDay);
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
}

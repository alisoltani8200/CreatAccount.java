package controller;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class RecommendUsers {
    Scanner scanner = new Scanner(System.in);
    public static String userName;

    final String DB_url = "jdbc:mysql://localhost/users?serverTimezone=UTC";
    final String username = "root";
    final String Password = "ali12345678";

    ArrayList<String> usersName = new ArrayList<>();
    ArrayList<Integer> usersScore = new ArrayList<>();
    ArrayList<String> otherUser = new ArrayList<>();

    public void run(){
        Rating();

        selectionSort(usersScore);


        int count1 =0 ;
        int count2 = 3;
        int i=usersName.size() -1 ;
        boolean showUsers = true;
        System.out.println(" our recommend user for you to follow : ");
        while (showUsers){

            if (i>1) {
                System.out.println("********");
                System.out.println(usersName.get(i));
                System.out.println("********");
                System.out.println();


                System.out.println("********");
                System.out.println(usersName.get(i - 1));
                System.out.println("********");
                System.out.println();

                System.out.println("********");
                System.out.println(usersName.get(i - 2));
                System.out.println("********");
                System.out.println();
            }

            else {
                if (i == 1){
                    System.out.println("********");
                    System.out.println(usersName.get(i));
                    System.out.println("********");
                    System.out.println();


                    System.out.println("********");
                    System.out.println(usersName.get(i - 1));
                    System.out.println("********");
                    System.out.println();

                }

                else if (i == 0){
                    System.out.println("********");
                    System.out.println(usersName.get(i));
                    System.out.println("********");
                    System.out.println();

                }

            }



            boolean command = false;
            while (!command){


                if (i > 1 &&  i< usersName.size() - 3 ) {
                    System.out.println("if you wanna see another recommended users enter \"NEXT\" ");
                    System.out.println("if you wanna see  recommended users before this ones enter \"PREVIOUS\" ");

                    System.out.println("if you wanna see profile of a user enter \"SEE PROFILE\" ");
                    System.out.println("if you wanna back to menu enter \"BACK\" ");

                    String order = scanner.nextLine().trim();

                    if (order.equals("NEXT")){
                        i -= 3;
                        command = true;
                    }

                    else if (order.equals("PREVIOUS")){
                        i += 3;
                        command = true;
                    }

                    else if (order.equals("SEE PROFILE")){}

                    else if (order.equals("BACK")){
                        command = true;
                        showUsers = false;
                    }
                    else {
                        System.out.println("wrong order , please pay attention ");
                    }


                }

                else if (i < 1 && i< usersName.size() - 3  ){
                    System.out.println("there is no user exist for recommend ");
                    System.out.println("if you wanna see  recommended users before this ones enter \"PREVIOUS\" ");

                    System.out.println("if you wanna see profile of a user enter \"SEE PROFILE\" ");
                    System.out.println("if you wanna back to menu enter \"BACK\" ");

                    String order = scanner.nextLine().trim();

                    if (order.equals("PREVIOUS")){
                        i += 3;
                        command = true;
                    }

                    else if (order.equals("SEE PROFILE")){}

                    else if (order.equals("BACK")){
                        command = true;
                        showUsers = false;
                    }
                    else {
                        System.out.println("wrong order , please pay attention ");
                    }


                }

                else if ( i > 1 && i  >= usersName.size() - 3){
                    System.out.println("if you wanna see another recommended users enter \"NEXT\" ");

                    System.out.println("if you wanna see profile of a user enter \"SEE PROFILE\" ");
                    System.out.println("if you wanna back to menu enter \"BACK\" ");

                    String order = scanner.nextLine().trim();

                    if (order.equals("NEXT")){
                        i -= 3;
                        command = true;
                    }


                    else if (order.equals("SEE PROFILE")){}

                    else if (order.equals("BACK")){
                        command = true;
                        showUsers = false;
                    }

                    else {
                        System.out.println("wrong order , please pay attention ");
                    }                }

                else if (i< 1 && i  >= usersName.size() - 3){
                    System.out.println("there is no user exist for recommend ");

                    System.out.println("if you wanna see profile of a user enter \"SEE PROFILE\" ");
                    System.out.println("if you wanna back to menu enter \"BACK\" ");

                    String order = scanner.nextLine().trim();


                    if (order.equals("SEE PROFILE")){}

                    else if (order.equals("BACK")){
                        command = true;
                        showUsers = false;
                    }
                    else {
                        System.out.println("wrong order , please pay attention ");
                    }

                }


            }

        }

    }





    private void Rating (){

        ArrayList<String> followers = new ArrayList<>();
        ArrayList<String> followings = new ArrayList<>();
        try {
            Connection conn = DriverManager.getConnection(DB_url, username, Password);
            Statement statement1 = conn.createStatement();
            String sql1 = "SELECT * FROM followings";
            ResultSet resultSet1 = statement1.executeQuery(sql1);

            while (resultSet1.next()){
                if (resultSet1.getString(userName) != null){
                    followings.add(resultSet1.getString(userName));
                }
            }


            Statement statement2 = conn.createStatement();
            String sql2 = "SELECT * FROM followers";
            ResultSet resultSet2 = statement2.executeQuery(sql2);

            while (resultSet2.next()){
                if (resultSet2.getString(userName) != null){
                    followers.add(resultSet2.getString(userName));
                    usersName.add(resultSet2.getString(userName));
                    usersScore.add(3);
                }
            }


        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        for (String i : followings) {

            try {
                Connection conn = DriverManager.getConnection(DB_url, username, Password);
                Statement statement1 = conn.createStatement();
                String sql1 = "SELECT * FROM followings";
                ResultSet resultSet1 = statement1.executeQuery(sql1);


                while (resultSet1.next()){
                    if (resultSet1.getString(i) != null && !followings.contains(resultSet1.getString(i)) && !resultSet1.getString(i).equals(userName)){
                        if (usersName.contains(resultSet1.getString(i))){
                            usersScore.set(usersName.indexOf(resultSet1.getString(i)) , usersScore.get(usersName.indexOf(resultSet1.getString(i))) + 5) ;
                        }
                        else {
                            usersName.add(resultSet1.getString(i));
                            usersScore.add(5);
                        }
                    }
                }


                Statement statement2 = conn.createStatement();
                String sql2 = "SELECT * FROM followers";
                ResultSet resultSet2 = statement2.executeQuery(sql2);

                while (resultSet2.next()){
                    if (resultSet2.getString(i) != null && !followings.contains(resultSet2.getString(i)) && !resultSet2.getString(i).equals(userName)) {
                        if (usersName.contains(resultSet2.getString(i))) {
                            usersScore.set(usersName.indexOf(resultSet2.getString(i)), usersScore.get(usersName.indexOf(resultSet2.getString(i))) + 4);
                        }
                        else {
                            usersName.add(resultSet2.getString(i));
                            usersScore.add(4);
                        }
                    }
                }


            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }

        for (String i : followers) {

            try {
                Connection conn = DriverManager.getConnection(DB_url, username, Password);
                Statement statement1 = conn.createStatement();
                String sql1 = "SELECT * FROM followings";
                ResultSet resultSet1 = statement1.executeQuery(sql1);


                while (resultSet1.next()){
                    if (resultSet1.getString(i) != null  && !followings.contains(resultSet1.getString(i)) && !resultSet1.getString(i).equals(userName)){
                        if (usersName.contains(resultSet1.getString(i))){
                            usersScore.set(usersName.indexOf(resultSet1.getString(i)) , usersScore.get(usersName.indexOf(resultSet1.getString(i))) + 2) ;
                        }
                        else {
                            usersName.add(resultSet1.getString(i));
                            usersScore.add(2);
                        }
                    }
                }


                Statement statement2 = conn.createStatement();
                String sql2 = "SELECT * FROM followers";
                ResultSet resultSet2 = statement2.executeQuery(sql2);

                while (resultSet2.next()){
                    if (resultSet2.getString(i) != null  && !followings.contains(resultSet2.getString(i)) && !resultSet2.getString(i).equals(userName)) {

                        if (usersName.contains(resultSet2.getString(i))) {
                            usersScore.set(usersName.indexOf(resultSet2.getString(i)), usersScore.get(usersName.indexOf(resultSet2.getString(i))) + 1);
                        }
                        else {
                            usersName.add(resultSet2.getString(i));
                            usersScore.add(1);
                        }
                    }
                }


            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }


    }

    private void selectionSort(ArrayList<Integer> arr)
    {
        int pos;
        int temp1;
        String temp2;
        for (int i = 0; i < arr.size(); i++)
        {
            pos = i;
            for (int j = i+1; j < arr.size(); j++)
            {
                if (arr.get(j) < arr.get(pos))                  //find the index of the minimum element
                {
                    pos = j;
                }
            }

            temp1 = arr.get(pos);            //swap the current element with the minimum element
            arr.set(pos , arr.get(i));
            arr.set(i  ,temp1 );


            temp2 = usersName.get(pos);            //swap the current element with the minimum element
            usersName.set(pos , usersName.get(i));
            usersName.set(i  ,temp2 );


        }
    }
}

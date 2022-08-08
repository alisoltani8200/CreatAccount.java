package view;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class Chat {
    final String DB_url = "jdbc:mysql://localhost/users?serverTimezone=UTC";
    final String username = "root";
    final String Password = "ali12345678";
    public void startChat(String senderName , String receiverName , Scanner scanner) throws SQLException {
        String chatName;
        String sql;
        boolean haveBlocked ;
        boolean isBlocked ;
        int number = 0 ;
        int showedMessages = 0 ;
        ResultSet resultSet;
        ResultSet resultSet1;
        Connection conn = DriverManager.getConnection(DB_url, username, Password);
        Statement statement = conn.createStatement();
        Statement statement1 = conn.createStatement();
        sql = "SELECT * FROM personalinformation WHERE username LIKE '" + receiverName + "'";
        resultSet = statement.executeQuery(sql);
        if (!resultSet.next()) {
            System.out.println("No username with name " + receiverName + " is available !");
        }
        else {
            String name1 = senderName + "_" + receiverName;
            String name2 = receiverName + "_" + senderName;
            sql = "SELECT * FROM chatTable WHERE name LIKE '" + name1 + "' or name LIKE '" + name2 + "'";
            resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                chatName = resultSet.getString("name");
            }
            else {
                chatName = senderName + "_" + receiverName;
                sql = "CREATE TABLE " + chatName + " ( id int not null auto_increment , senderName varchar(255) not null , receiverName varchar(255) not null , message varchar (255) not null , replyTo int not null , seen varchar (255) not null , date varchar (255) not null , time varchar (255) not null , forwarded varchar (255) not null , primary key (id))";
                statement.executeUpdate(sql);
                sql = "insert into chatTable (name,type)" + "values ('" + chatName + "' , \"pv\")";
                statement.executeUpdate(sql);
            }
            haveBlocked = false;
            isBlocked = false ;
            sql = "SELECT " + senderName + " FROM blockTable";
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()){
                if (resultSet.getString(senderName) != null) {
                    if (resultSet.getString(senderName).equals(receiverName)) {
                        haveBlocked = true;
                        break;
                    }
                }
            }
            sql = "SELECT " + receiverName + " FROM blockTable";
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()){
                if (resultSet.getString(receiverName) != null){
                    if (resultSet.getString(receiverName).equals(senderName)) {
                        isBlocked = true;
                        break;
                    }
                }
            }

            resetTable (chatName,resultSet,statement);
            resetTable("grouptable",resultSet,statement);
            resetTable("chattable",resultSet,statement);
            resetTable("blocktable",resultSet,statement);

            System.out.println("Enter your message \nor type 'END' to stop chatting \nor type 'REPLY' to reply to a message \nor type 'FORWARD' to forward a message \n" +
                    "or type 'EDIT' to edit a message \nor type 'DELETE' to delete a message \nor type 'REFRESH' to refresh your chat screen \nor type 'BLOCK' to block the user \nor type 'UNBLOCK' to unblock the user\nor type 'SEARCH' to search for a message : ");
            sql = "SELECT * FROM " + chatName + " ORDER BY id DESC";
            resultSet = statement.executeQuery(sql);
            ArrayList<String> lastMessages = new ArrayList<>();
            String message ;
            while (resultSet.next()) {
                message = resultSet.getString("id") + " ) " + resultSet.getString("senderName") + " : ";
                if (!resultSet.getString("replyTo").equals("0")){
                    sql = "SELECT * FROM " + chatName + " WHERE id LIKE " + resultSet.getString("replyTo");
                    resultSet1 = statement1.executeQuery(sql);
                    if (resultSet1.next()){
                        message = message + "Reply to --> " + resultSet1.getString("message").substring(0,Integer.min(7,resultSet1.getString("message").length())) + "... : ";
                    }
                }
                else if (!resultSet.getString("forwarded").equals("-")){
                    message = message + "Forwarded! from " + resultSet.getString("forwarded") + " : ";
                }
                message = message + resultSet.getString("message");
                lastMessages.add(message);
            }
            for (int i = Integer.min(14 , lastMessages.size() - 1) ; i >= 0 ; i--) {
                System.out.println(lastMessages.get(i));
                showedMessages += 1;
            }
            number += showedMessages ;
            boolean keepChatting = true;
            String id ;
            String sender;
            String receiver;
            String newChatName;
            String chatType ;
            boolean editable ;
            boolean searched ;
            if (isBlocked){
                System.out.println("You have been blocked by this user !");
            }
            else {
                while (keepChatting) {
                    if (!haveBlocked) {
                        message = scanner.nextLine().trim();
                        if (message.isEmpty()) {
                            continue;
                        }
                        else if (message.equals("END")) {
                            keepChatting = false;
                        }
                        else if (message.equals("REPLY")) {
                            System.out.println("Please enter the id of the message you want to reply to it : ");
                            id = scanner.nextLine();
                            sql = "SELECT * FROM " + chatName + " WHERE id LIKE " + id;
                            resultSet = statement.executeQuery(sql);
                            if (resultSet.next()) {
                                System.out.println("Enter the message : ");
                                message = scanner.nextLine().trim();
                                sql = "insert into " + chatName + " (senderName , receiverName , message , replyTo , seen , date , time , forwarded) values ('" + senderName + "' , '" + receiverName + "' , '" + message + "' , '" + id + "' , \"no\" , " +
                                        LocalDate.now() + " , '" + LocalTime.now().format(Formatter1()) + "' , \"-\")";
                                statement.executeUpdate(sql);
                            } else {
                                System.out.println("No message with id " + id + " !");
                            }
                        }
                        else if (message.equals("FORWARD")) {
                            System.out.println("Please enter the id of the message you want to forward it : ");
                            id = scanner.nextLine().trim();
                            sql = "SELECT * FROM " + chatName + " WHERE id LIKE " + id ;
                            resultSet = statement.executeQuery(sql);
                            if (resultSet.next()) {
                                sender = resultSet.getString("senderName");
                                message = resultSet.getString("message");
                                System.out.println("Who do you want to forward the message to ? ");
                                receiver = scanner.nextLine().trim();
                                System.out.println("Is it a 'GROUP' or a 'PERSON' ? ");
                                chatType = scanner.nextLine().trim();
                                if (chatType.equals("PERSON")) {
                                    sql = "SELECT * FROM personalinformation WHERE username LIKE '" + receiver + "'";
                                    resultSet = statement.executeQuery(sql);
                                    if (resultSet.next()) {
                                        name1 = senderName + "_" + receiver;
                                        name2 = receiver + "_" + senderName;
                                        sql = "SELECT name FROM chatTable WHERE name LIKE '" + name1 + "' or name LIKE '" + name2 + "'";
                                        resultSet = statement.executeQuery(sql);
                                        if (resultSet.next()) {
                                            newChatName = resultSet.getString("name");
                                        } else {
                                            newChatName = senderName + "_" + receiver;
                                            sql = "CREATE TABLE " + newChatName + " ( id int not null auto_increment , senderName varchar(255) not null , receiverName varchar(255) not null , message varchar (255) not null , replyTo int not null , seen varchar (255) not null , date varchar (255) not null , time varchar (255) not null , forwarded varchar (255) not null , primary key (id))";
                                            statement.executeUpdate(sql);
                                            sql = "insert into chatTable (name,type)" + "values ('" + newChatName + "' , \"pv\")";
                                            statement.executeUpdate(sql);
                                        }
                                        sql = "insert into " + newChatName + " (senderName , receiverName , message , replyTo , seen , date , time , forwarded) values ('" + senderName + "' , '" + receiver + "' , '" + message + "' , '" + "0" + "' , \"no\" ,  " +
                                                LocalDate.now() + " , '" + LocalTime.now().format(Formatter1()) + "' , '" + sender + "')";
                                        statement.executeUpdate(sql);
                                        System.out.println("Forwarded successfully :) ");
                                    } else {
                                        System.out.println("No username with name " + receiver + " is available !");
                                    }
                                }
                                else if (chatType.equals("GROUP")){
                                    sql = "SELECT * FROM chatTable WHERE name LIKE '" + receiver + "' AND type LIKE \"group\"";
                                    resultSet = statement.executeQuery(sql);
                                    if (resultSet.next()){
                                        newChatName = resultSet.getString("name");
                                        sql = "insert into " + newChatName + " (senderName  , message , replyTo , date , time , forwarded) values ('" + senderName + "' , '" + message + "' , '" + "0" + "' , " +
                                                LocalDate.now() + " , '" + LocalTime.now().format(Formatter1()) + "' , '" + sender + "')";
                                        statement.executeUpdate(sql);
                                        System.out.println("Forwarded successfully :) ");
                                    }
                                    else {
                                        System.out.println("No group with name " + receiver + " is available !");
                                    }
                                }
                            } else {
                                System.out.println("No message with id " + id + " !");
                            }
                        }
                        else if (message.equals("EDIT")) {
                            System.out.println("Please enter the id of the message you want to edit it : ");
                            id = scanner.nextLine().trim();
                            sql = "SELECT * FROM " + chatName + " WHERE id LIKE " + id;
                            resultSet = statement.executeQuery(sql);
                            if (resultSet.next()) {
                                if (!resultSet.getString("forwarded").equals("-")) {
                                    System.out.println("You can't edit forwarded messages ! ");
                                } else {
                                    message = resultSet.getString("senderName");
                                    if (message.equals(senderName)) {
                                        sql = "SELECT * FROM " + chatName + " WHERE senderName LIKE '" + senderName + "' ORDER BY id DESC LIMIT 10 ";
                                        resultSet = statement.executeQuery(sql);
                                        editable = false;
                                        while (resultSet.next()) {
                                            if (resultSet.getString("id").equals(id)) {
                                                editable = true;
                                                break;
                                            }
                                        }
                                        if (editable) {
                                            System.out.println("Enter the message to replace with the old one : ");
                                            message = scanner.nextLine().trim();
                                            sql = "UPDATE " + chatName + " SET message = '" + message + "' WHERE id LIKE " + id;
                                            statement.executeUpdate(sql);
                                            System.out.println("Edited successfully ");
                                        } else {
                                            System.out.println("You can only edit your last 10 messages !");
                                        }
                                    } else {
                                        System.out.println("You can't edit other's messages ! ");
                                    }
                                }
                            } else {
                                System.out.println("No message with id " + id + " !");
                            }
                        }
                        else if (message.equals("DELETE")) {
                            System.out.println("Please enter the id of the message you want to delete it : ");
                            id = scanner.nextLine().trim();
                            sql = "SELECT * FROM " + chatName + " WHERE id LIKE " + id;
                            resultSet = statement.executeQuery(sql);
                            if (resultSet.next()) {
                                message = resultSet.getString("senderName");
                                if (message.equals(senderName)) {
                                    sql = "SELECT * FROM " + chatName + " WHERE senderName LIKE '" + senderName + "' ORDER BY id DESC LIMIT 10 ";
                                    resultSet = statement.executeQuery(sql);
                                    editable = false;
                                    while (resultSet.next()) {
                                        if (resultSet.getString("id").equals(id)) {
                                            editable = true;
                                            break;
                                        }
                                    }
                                    if (editable) {
                                        sql = "DELETE FROM " + chatName + " WHERE id LIKE " + id;
                                        statement.executeUpdate(sql);
                                        System.out.println("Deleted successfully ");
                                        sql = "UPDATE " + chatName + " SET replyTo = 0 WHERE replyTo LIKE " + id;
                                        statement.executeUpdate(sql);
                                    } else {
                                        System.out.println("You can only delete your last 10 messages !");
                                    }
                                } else {
                                    System.out.println("You can't delete other's messages ! ");
                                }
                            } else {
                                System.out.println("No message with id " + id + " !");
                            }
                        }
                        else if (message.equals("REFRESH")) {
                            keepChatting = false;
                            startChat(senderName, receiverName, scanner);
                        }
                        else if (message.equals("BLOCK")) {
                            sql = "insert into blockTable (" + senderName + ") values (\"" + receiverName + "\")";
                            statement.executeUpdate(sql);
                            haveBlocked = true;
                            System.out.println("Blocked successfully ");
                        }
                        else if (message.equals("UNBLOCK")) {
                            System.out.println("This user is not blocked ! ");
                        }
                        else if (message.equals("SEARCH")){
                            System.out.println("Type what do you want to search : ");
                            message = scanner.nextLine();
                            sql = "SELECT * FROM " + chatName + " WHERE message LIKE '" + "%" + message + "%'";
                            resultSet = statement.executeQuery(sql);
                            searched = false;
                            while (resultSet.next()){
                                message = resultSet.getString("id") + " ) Date = " + resultSet.getString("date") + " & Time = " + resultSet.getString("time") +
                                        " : " + resultSet.getString("message").substring(0,Integer.min(7,resultSet.getString("message").length()));
                                System.out.println(message);
                                searched = true;
                            }
                            if (searched){
                                System.out.println("Enter the id of the message which you want to see completely : ");
                                message = scanner.nextLine().trim();
                                sql = "SELECT * FROM " + chatName + " WHERE id LIKE " + message ;
                                resultSet = statement.executeQuery(sql);
                                if (resultSet.next()){
                                    System.out.println(resultSet.getString("message"));
                                }
                            }
                            else {
                                System.out.println("No message found which machs " + message);
                            }
                        }
                        else if (message.equals("PREVIOUS")){
                            showedMessages = 0;
                            for (int i = number + 14 ; i >= number && i >= 0; i--) {
                                try {
                                    System.out.println(lastMessages.get(i));
                                    showedMessages += 1;
                                }
                                catch (Exception e){
                                    continue;
                                }
                            }
                            if (showedMessages != 0) {
                                number += showedMessages;
                            }
                            else {
                                System.out.println("No more chat before !");
                            }
                        }
                        else if (message.equals("NEXT")){
                            showedMessages = 0;
                            for (int i = number - 16 ; i >= number - 30 && i >= 0; i--) {
                                try {
                                    System.out.println(lastMessages.get(i));
                                    showedMessages += 1;
                                }
                                catch (Exception e){
                                    continue;
                                }
                            }
                            if (showedMessages != 0) {
                                number -= showedMessages;
                            }
                            else {
                                System.out.println("Here is the end of your chat !");
                            }
                        }
                        else {
                            sql = "insert into " + chatName + " (senderName , receiverName , message , replyTo , seen , date , time , forwarded) values ('" + senderName + "' , '" + receiverName + "' , '" + message + "' , '" + "0" + "' , \"no\" , " +
                                    LocalDate.now() + " , '" + LocalTime.now().format(Formatter1()) + "' , \"-\" )";
                            statement.executeUpdate(sql);
                        }
                    }
                    else {
                        System.out.println("You have blocked this user !");
                        message = scanner.nextLine().trim();
                        if (message.isEmpty()) {
                            continue;
                        }
                        else if (message.equals("END")) {
                            keepChatting = false;
                        }
                        else if (message.equals("FORWARD")) {
                            System.out.println("Please enter the id of the message you want to forward it : ");
                            id = scanner.nextLine().trim();
                            sql = "SELECT * FROM " + chatName + " WHERE id LIKE " + id ;
                            resultSet = statement.executeQuery(sql);
                            if (resultSet.next()) {
                                sender = resultSet.getString("senderName");
                                message = resultSet.getString("message");
                                System.out.println("Who do you want to forward the message to ? ");
                                receiver = scanner.nextLine().trim();
                                System.out.println("Is it a 'GROUP' or a 'PERSON' ? ");
                                chatType = scanner.nextLine().trim();
                                if (chatType.equals("PERSON")) {
                                    sql = "SELECT * FROM personalinformation WHERE username LIKE '" + receiver + "'";
                                    resultSet = statement.executeQuery(sql);
                                    if (resultSet.next()) {
                                        name1 = senderName + "_" + receiver;
                                        name2 = receiver + "_" + senderName;
                                        sql = "SELECT name FROM chatTable WHERE name LIKE '" + name1 + "' or name LIKE '" + name2 + "'";
                                        resultSet = statement.executeQuery(sql);
                                        if (resultSet.next()) {
                                            newChatName = resultSet.getString("name");
                                        } else {
                                            newChatName = senderName + "_" + receiver;
                                            sql = "CREATE TABLE " + newChatName + " ( id int not null auto_increment , senderName varchar(255) not null , receiverName varchar(255) not null , message varchar (255) not null , replyTo int not null , seen varchar (255) not null , date varchar (255) not null , time varchar (255) not null , forwarded varchar (255) not null , primary key (id))";
                                            statement.executeUpdate(sql);
                                            sql = "insert into chatTable (name,type)" + "values ('" + newChatName + "' , \"pv\")";
                                            statement.executeUpdate(sql);
                                        }
                                        sql = "insert into " + newChatName + " (senderName , receiverName , message , replyTo , seen , date , time , forwarded) values ('" + senderName + "' , '" + receiver + "' , '" + message + "' , '" + "0" + "' , \"no\" ,  " +
                                                LocalDate.now() + " , '" + LocalTime.now().format(Formatter1()) + "' , '" + sender + "')";
                                        statement.executeUpdate(sql);
                                        System.out.println("Forwarded successfully :) ");
                                    } else {
                                        System.out.println("No username with name " + receiver + " is available !");
                                    }
                                }
                                else if (chatType.equals("GROUP")){
                                    sql = "SELECT * FROM chatTable WHERE name LIKE '" + receiver + "' AND type LIKE \"group\"";
                                    resultSet = statement.executeQuery(sql);
                                    if (resultSet.next()){
                                        newChatName = resultSet.getString("name");
                                        sql = "insert into " + newChatName + " (senderName  , message , replyTo , date , time , forwarded) values ('" + senderName + "' , '" + message + "' , '" + "0" + "' , " +
                                                LocalDate.now() + " , '" + LocalTime.now().format(Formatter1()) + "' , '" + sender + "')";
                                        statement.executeUpdate(sql);
                                        System.out.println("Forwarded successfully :) ");
                                    }
                                    else {
                                        System.out.println("No group with name " + receiver + " is available !");
                                    }
                                }
                            } else {
                                System.out.println("No message with id " + id + " !");
                            }
                        }
                        else if (message.equals("REFRESH")) {
                            keepChatting = false;
                            startChat(senderName, receiverName, scanner);
                        }
                        else if (message.equals("BLOCK")) {
                            System.out.println("This user is already blocked ! ");
                        }
                        else if (message.equals("UNBLOCK")) {
                            sql = "DELETE FROM blockTable WHERE " + senderName + " LIKE \"" + receiverName + "\"";
                            statement.executeUpdate(sql);
                            System.out.println("Unblocked successfully ");
                            haveBlocked = false;
                        }
                        else if (message.equals("SEARCH")){
                            System.out.println("Type what do you want to search : ");
                            message = scanner.nextLine();
                            sql = "SELECT * FROM " + chatName + " WHERE message LIKE '" + "%" + message + "%'";
                            resultSet = statement.executeQuery(sql);
                            searched = false;
                            while (resultSet.next()){
                                message = resultSet.getString("id") + " ) Date = " + resultSet.getString("date") + " & Time = " + resultSet.getString("time") +
                                        " : " + resultSet.getString("message").substring(0,Integer.min(7,resultSet.getString("message").length()));
                                System.out.println(message);
                                searched = true;
                            }
                            if (searched){
                                System.out.println("Enter the id of the message which you want to see completely : ");
                                message = scanner.nextLine().trim();
                                sql = "SELECT * FROM " + chatName + " WHERE id LIKE " + message ;
                                resultSet = statement.executeQuery(sql);
                                if (resultSet.next()){
                                    System.out.println(resultSet.getString("message"));
                                }
                            }
                            else {
                                System.out.println("No message found which machs " + message);
                            }
                        }
                        else if (message.equals("PREVIOUS")){
                            showedMessages = 0;
                            for (int i = number + 14 ; i >= number && i >= 0; i--) {
                                try {
                                    System.out.println(lastMessages.get(i));
                                    showedMessages += 1;
                                }
                                catch (Exception e){
                                    continue;
                                }
                            }
                            if (showedMessages != 0) {
                                number += showedMessages;
                            }
                            else {
                                System.out.println("No more chat before !");
                            }
                        }
                        else if (message.equals("NEXT")){
                            showedMessages = 0;
                            for (int i = number - 16 ; i >= number - 30 && i >= 0; i--) {
                                try {
                                    System.out.println(lastMessages.get(i));
                                    showedMessages += 1;
                                }
                                catch (Exception e){
                                    continue;
                                }
                            }
                            if (showedMessages != 0) {
                                number -= showedMessages;
                            }
                            else {
                                System.out.println("Here is the end of your chat !");
                            }
                        }
                    }
                }
            }
        }
    }


    public void startChat(Scanner scanner , String senderName , String groupName) throws SQLException {
        Connection conn = DriverManager.getConnection(DB_url, username, Password);
        Statement statement = conn.createStatement();
        Statement statement1 = conn.createStatement();
        String sql = "SELECT name FROM chatTable WHERE name LIKE '" + groupName + "' AND type LIKE \"group\"";
        ResultSet resultSet = statement.executeQuery(sql);
        ResultSet resultSet1 ;
        int number = 0 ;
        int showedMessages = 0 ;
        String message ;
        boolean fine = false ;
        boolean isMember = false ;
        String roll = "";
        ArrayList<String> members = new ArrayList<>();
        if (resultSet.next()){
            sql = "SELECT * FROM groupTable WHERE groupName LIKE '" + groupName + "'";
            resultSet1 = statement1.executeQuery(sql);
            while (resultSet1.next()){
                if (resultSet1.getString("username").equals(senderName)){
                    isMember = true ;
                    roll = resultSet1.getString("roll");
                    break;
                }
            }
            if (isMember){
                fine = true;
                sql = "SELECT * FROM groupTable WHERE groupName LIKE '" + groupName + "'";
                resultSet1 = statement1.executeQuery(sql);
                while (resultSet1.next()){
                    members.add(resultSet1.getString("username"));
                }
            }
            else {
                System.out.println("You are not a member of this group !");
            }
        }
        else {
            System.out.println("There is no group with this name ; do you want to create a group with this name ? (Type 'YES' or 'NO') ");
            message = scanner.nextLine().trim();
            if (message.equals("YES")){
                System.out.println("Please enter the name of the members one by one : \nType 'FINISH' when you were done : ");
                message = "";
                while (!message.equals("FINISH")){
                    message = scanner.nextLine().trim();
                    sql = "SELECT * FROM personalinformation WHERE username LIKE '" + message + "'";
                    resultSet = statement.executeQuery(sql);
                    if (!resultSet.next() && !message.equals("FINISH")) {
                        System.out.println("No username with name " + message + " is available !");
                    }
                    else if (!message.equals("FINISH")){
                        members.add(message);
                        System.out.println("Added successfully ");
                    }
                }
                if (members.size() == 0){
                    System.out.println("You haven't select any member ! No group is created !");
                }
                else {
                    sql = "CREATE TABLE " + groupName + " ( id int not null auto_increment , senderName varchar(255) not null , message varchar (255) not null , replyTo int not null , date varchar (255) not null , time varchar (255) not null , forwarded varchar (255) not null , primary key (id))";
                    statement.executeUpdate(sql);
                    sql = "insert into chatTable (name,type)" + "values ('" + groupName + "' , \"group\")";
                    statement.executeUpdate(sql);
                    for (String a:members) {
                        sql = "insert into groupTable (groupName , username , roll) values ('" + groupName + "' , '" + a + "' , \"member\")";
                        statement.executeUpdate(sql);
                    }
                    sql = "insert into groupTable (groupName , username , roll) values ('" + groupName + "' , '" + senderName + "' , \"admin\")";
                    statement.executeUpdate(sql);
                    System.out.println("Group created successfully ");
                    fine = true ;
                    roll = "admin";
                }
            }
            else if (message.equals("NO")){
                System.out.println("Ok :)");
            }
            else {
                System.out.println("You have entered invalid command !");
            }
        }
        if (fine){

            resetTable (groupName,resultSet,statement);
            resetTable("groupTable",resultSet,statement);
            resetTable("chatTable",resultSet,statement);
            resetTable("blockTable",resultSet,statement);

            System.out.println("Enter your message \nor type 'END' to stop chatting \nor type 'REPLY' to reply to a message \nor type 'FORWARD' to forward a message \n" +
                    "or type 'EDIT' to edit a message \nor type 'DELETE' to delete a message \nor type 'REFRESH' to refresh your chat screen \nor type 'SEARCH' to search for a message \n" +
                    "or type 'ADD' to add a member \nor type 'REMOVE' to remove a member : ");
            sql = "SELECT * FROM " + groupName + " ORDER BY id DESC";
            resultSet = statement.executeQuery(sql);
            ArrayList<String> lastMessages = new ArrayList<>();
            while (resultSet.next()) {
                message = resultSet.getString("id") + " ) " + resultSet.getString("senderName") + " : ";
                if (!resultSet.getString("replyTo").equals("0")){
                    sql = "SELECT * FROM " + groupName + " WHERE id LIKE " + resultSet.getString("replyTo");
                    resultSet1 = statement1.executeQuery(sql);
                    if (resultSet1.next()){
                        message = message + "Reply to --> " + resultSet1.getString("message").substring(0,Integer.min(7,resultSet1.getString("message").length())) + " : ";
                    }
                }
                else if (!resultSet.getString("forwarded").equals("-")){
                    message = message + "Forwarded! from " + resultSet.getString("forwarded") + " : ";
                }
                message = message + resultSet.getString("message");
                lastMessages.add(message);
            }
            for (int i = Integer.min(14 , lastMessages.size() - 1) ; i >= 0 ; i--) {
                System.out.println(lastMessages.get(i));
                showedMessages += 1;
            }
            number += showedMessages ;
            boolean keepChatting = true;
            String id ;
            String sender;
            String receiver;
            String newChatName;
            String name1 ;
            String name2 ;
            String chatType ;
            boolean editable ;
            boolean searched ;
            while (keepChatting){
                message = scanner.nextLine().trim();
                if (message.isEmpty()) {
                    continue;
                }
                else if (message.equals("END")) {
                    keepChatting = false;
                }
                else if (message.equals("REPLY")) {
                    System.out.println("Please enter the id of the message you want to reply to it : ");
                    id = scanner.nextLine();
                    sql = "SELECT * FROM " + groupName + " WHERE id LIKE " + id;
                    resultSet = statement.executeQuery(sql);
                    if (resultSet.next()) {
                        System.out.println("Enter the message : ");
                        message = scanner.nextLine().trim();
                        //sql = "CREATE TABLE " + groupName + " ( id int not null auto_increment , senderName varchar(255) not null , message varchar (255) not null , replyTo int not null , date varchar (255) not null , time varchar (255) not null , forwarded varchar (255) not null , primary key (id))";
                        sql = "insert into " + groupName + " (senderName , message , replyTo , date , time , forwarded) values ('" + senderName + "' , '" + message + "' , '" + id + "' , " +
                                LocalDate.now() + " , '" + LocalTime.now().format(Formatter1()) + "' , \"-\")";
                        statement.executeUpdate(sql);
                    } else {
                        System.out.println("No message with id " + id + " !");
                    }
                }
                else if (message.equals("FORWARD")) {
                    System.out.println("Please enter the id of the message you want to forward it : ");
                    id = scanner.nextLine().trim();
                    sql = "SELECT * FROM " + groupName + " WHERE id LIKE " + id ;
                    resultSet = statement.executeQuery(sql);
                    if (resultSet.next()) {
                        sender = resultSet.getString("senderName");
                        message = resultSet.getString("message");
                        System.out.println("Who do you want to forward the message to ? ");
                        receiver = scanner.nextLine().trim();
                        System.out.println("Is it a group or a person ? ");
                        chatType = scanner.nextLine().trim();
                        if (chatType.equals("PERSON")) {
                            sql = "SELECT * FROM personalinformation WHERE username LIKE '" + receiver + "'";
                            resultSet = statement.executeQuery(sql);
                            if (resultSet.next()) {
                                name1 = senderName + "_" + receiver;
                                name2 = receiver + "_" + senderName;
                                sql = "SELECT name FROM chatTable WHERE name LIKE '" + name1 + "' or name LIKE '" + name2 + "'";
                                resultSet = statement.executeQuery(sql);
                                if (resultSet.next()) {
                                    newChatName = resultSet.getString("name");
                                } else {
                                    newChatName = senderName + "_" + receiver;
                                    sql = "CREATE TABLE " + newChatName + " ( id int not null auto_increment , senderName varchar(255) not null , receiverName varchar(255) not null , message varchar (255) not null , replyTo int not null , seen varchar (255) not null , date varchar (255) not null , time varchar (255) not null , forwarded varchar (255) not null , primary key (id))";
                                    statement.executeUpdate(sql);
                                    sql = "insert into chatTable (name,type)" + "values ('" + newChatName + "' , \"pv\")";
                                    statement.executeUpdate(sql);
                                }
                                sql = "insert into " + newChatName + " (senderName , receiverName , message , replyTo , seen , date , time , forwarded) values ('" + senderName + "' , '" + receiver + "' , '" + message + "' , '" + "0" + "' , \"no\" ,  " +
                                        LocalDate.now() + " , '" + LocalTime.now().format(Formatter1()) + "' , '" + sender + "')";
                                statement.executeUpdate(sql);
                                System.out.println("Forwarded successfully :) ");
                            } else {
                                System.out.println("No username with name " + receiver + " is available !");
                            }
                        }
                        else if (chatType.equals("GROUP")){
                            sql = "SELECT * FROM chatTable WHERE name LIKE '" + receiver + "' AND type LIKE \"group\"";
                            resultSet = statement.executeQuery(sql);
                            if (resultSet.next()){
                                newChatName = resultSet.getString("name");
                                sql = "insert into " + newChatName + " (senderName , message , replyTo , date , time , forwarded) values ('" + senderName + "' , '" + message + "' , '" + "0" + "' , " +
                                        LocalDate.now() + " , '" + LocalTime.now().format(Formatter1()) + "' , '" + sender + "')";
                                statement.executeUpdate(sql);
                                System.out.println("Forwarded successfully :) ");
                            }
                            else {
                                System.out.println("No group with name " + receiver + " is available !");
                            }
                        }
                    } else {
                        System.out.println("No message with id " + id + " !");
                    }
                }
                else if (message.equals("EDIT")) {
                    System.out.println("Please enter the id of the message you want to edit it : ");
                    id = scanner.nextLine().trim();
                    sql = "SELECT * FROM " + groupName + " WHERE id LIKE " + id;
                    resultSet = statement.executeQuery(sql);
                    if (resultSet.next()) {
                        if (!resultSet.getString("forwarded").equals("-")) {
                            System.out.println("You can't edit forwarded messages ! ");
                        } else {
                            message = resultSet.getString("senderName");
                            if (message.equals(senderName)) {
                                sql = "SELECT * FROM " + groupName + " WHERE senderName LIKE '" + senderName + "' ORDER BY id DESC LIMIT 10 ";
                                resultSet = statement.executeQuery(sql);
                                editable = false;
                                while (resultSet.next()) {
                                    if (resultSet.getString("id").equals(id)) {
                                        editable = true;
                                        break;
                                    }
                                }
                                if (editable) {
                                    System.out.println("Enter the message to replace with the old one : ");
                                    message = scanner.nextLine().trim();
                                    sql = "UPDATE " + groupName + " SET message = '" + message + "' WHERE id LIKE " + id;
                                    statement.executeUpdate(sql);
                                    System.out.println("Edited successfully ");
                                } else {
                                    System.out.println("You can only edit your last 10 messages !");
                                }
                            } else {
                                System.out.println("You can't edit other's messages ! ");
                            }
                        }
                    } else {
                        System.out.println("No message with id " + id + " !");
                    }
                }
                else if (message.equals("DELETE")) {
                    System.out.println("Please enter the id of the message you want to delete it : ");
                    id = scanner.nextLine().trim();
                    sql = "SELECT * FROM " + groupName + " WHERE id LIKE " + id;
                    resultSet = statement.executeQuery(sql);
                    if (resultSet.next()) {
                        message = resultSet.getString("senderName");
                        if (message.equals(senderName)) {
                            sql = "SELECT * FROM " + groupName + " WHERE senderName LIKE '" + senderName + "' ORDER BY id DESC LIMIT 10 ";
                            resultSet = statement.executeQuery(sql);
                            editable = false;
                            while (resultSet.next()) {
                                if (resultSet.getString("id").equals(id)) {
                                    editable = true;
                                    break;
                                }
                            }
                            if (editable) {
                                sql = "DELETE FROM " + groupName + " WHERE id LIKE " + id;
                                statement.executeUpdate(sql);
                                System.out.println("Deleted successfully ");
                                sql = "UPDATE " + groupName + " SET replyTo = 0 WHERE replyTo LIKE " + id;
                                statement.executeUpdate(sql);
                            } else {
                                System.out.println("You can only delete your last 10 messages !");
                            }
                        } else {
                            System.out.println("You can't delete other's messages ! ");
                        }
                    } else {
                        System.out.println("No message with id " + id + " !");
                    }
                }
                else if (message.equals("REFRESH")) {
                    keepChatting = false;
                    startChat(scanner,senderName,groupName);
                }
                else if (message.equals("SEARCH")){
                    System.out.println("Type what do you want to search : ");
                    message = scanner.nextLine();
                    sql = "SELECT * FROM " + groupName + " WHERE message LIKE '" + "%" + message + "%'";
                    resultSet = statement.executeQuery(sql);
                    searched = false;
                    while (resultSet.next()){
                        message = resultSet.getString("id") + " ) Date = " + resultSet.getString("date") + " & Time = " + resultSet.getString("time") +
                                " : " + resultSet.getString("message").substring(0,Integer.min(7,resultSet.getString("message").length()));
                        System.out.println(message);
                        searched = true;
                    }
                    if (searched){
                        System.out.println("Enter the id of the message which you want to see completely : ");
                        message = scanner.nextLine().trim();
                        sql = "SELECT * FROM " + groupName + " WHERE id LIKE " + message ;
                        resultSet = statement.executeQuery(sql);
                        if (resultSet.next()){
                            System.out.println(resultSet.getString("message"));
                        }
                    }
                    else {
                        System.out.println("No message found which machs " + message);
                    }
                }
                else if (message.equals("PREVIOUS")){
                    showedMessages = 0;
                    for (int i = number + 14 ; i >= number && i >= 0; i--) {
                        try {
                            System.out.println(lastMessages.get(i));
                            showedMessages += 1;
                        }
                        catch (Exception e){
                            continue;
                        }
                    }
                    if (showedMessages != 0) {
                        number += showedMessages;
                    }
                    else {
                        System.out.println("No more chat before !");
                    }
                }
                else if (message.equals("NEXT")){
                    showedMessages = 0;
                    for (int i = number - 16 ; i >= number - 30 && i >= 0; i--) {
                        try {
                            System.out.println(lastMessages.get(i));
                            showedMessages += 1;
                        }
                        catch (Exception e){
                            continue;
                        }
                    }
                    if (showedMessages != 0) {
                        number -= showedMessages;
                    }
                    else {
                        System.out.println("Here is the end of your chat !");
                    }
                }
                else if (message.equals("ADD")){
                    System.out.println("Please enter the name of the member you want to add : ");
                    message = scanner.nextLine().trim();
                    sql = "SELECT * FROM personalinformation WHERE username LIKE '" + message + "'";
                    resultSet = statement.executeQuery(sql);
                    if (!resultSet.next()) {
                        System.out.println("No username with name " + message + " is available !");
                    }
                    else {
                        if(members.contains(message)){
                            System.out.println(message + " is already in the group !");
                        }
                        else {
                            members.add(message);
                            sql = "insert into groupTable (groupName , username , roll) values ('" + groupName + "' , '" + message + "' , \"member\")";
                            statement.executeUpdate(sql);
                            System.out.println("Added successfully ");
                        }

                    }
                }
                else if (message.equals("REMOVE")){
                    if (roll.equals("member")){
                        System.out.println("Only the admin can remove a member !");
                    }
                    else if (roll.equals("admin")){
                        System.out.println("Please enter the name of the member you want to remove : ");
                        message = scanner.nextLine().trim();
                        sql = "SELECT * FROM personalinformation WHERE username LIKE '" + message + "'";
                        resultSet = statement.executeQuery(sql);
                        if (!resultSet.next()) {
                            System.out.println("No username with name " + message + " is available !");
                        }
                        else {
                            if(!members.contains(message)){
                                System.out.println(message + " is not in the group !");
                            }
                            else {
                                members.remove(message);
                                sql = "DELETE FROM groupTable WHERE groupName LIKE '" + groupName + "' AND username LIKE '" + message + "'";
                                statement.executeUpdate(sql);
                                System.out.println("Removed successfully ");
                            }
                        }
                    }
                }
                else {
                    sql = "insert into " + groupName + " (senderName , message , replyTo , date , time , forwarded) values ('" + senderName + "' , '" + message + "' , '" + "0" + "' , " +
                            LocalDate.now() + " , '" + LocalTime.now().format(Formatter1()) + "' , \"-\")";
                    statement.executeUpdate(sql);
                }
            }
        }
    }


    private DateTimeFormatter Formatter1(){
        return DateTimeFormatter.ofPattern("HH:mm");
    }

    private void resetTable (String tableName , ResultSet resultSet , Statement statement) throws SQLException {
        int n = 1 ;
        int maxId = 0;
        String sql = "SELECT * FROM " + tableName ;
        resultSet = statement.executeQuery(sql);
        Connection conn = DriverManager.getConnection(DB_url, username, Password);
        Statement statement1=conn.createStatement();
        Statement statement2= conn.createStatement();
        while (resultSet.next()){
            if (resultSet.getInt("id") > n) {
                sql = "UPDATE " + tableName + " SET id = " + n + " WHERE id LIKE " + resultSet.getString("id");
                statement1.executeUpdate(sql);
                sql = "UPDATE " + tableName + " SET replyTo = " + n + " WHERE replyTo LIKE " + resultSet.getString("id");
                statement2.executeUpdate(sql);
            }
            n++;
        }
        sql = "SELECT MAX(id) FROM " + tableName;
        resultSet = statement.executeQuery(sql);
        if (resultSet.next()) {
            maxId = resultSet.getInt(1);
        }
        sql = "ALTER TABLE " + tableName + " AUTO_INCREMENT = " + (maxId + 1) ;
        statement.executeUpdate(sql);
    }
}

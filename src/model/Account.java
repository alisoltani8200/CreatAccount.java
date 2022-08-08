package model;

import java.util.ArrayList;

public class Account {
    private String name;
    private String userName;
    private String passWord;
    private String checkPassWord;
    private int id;




    public static ArrayList<Account> accounts=new ArrayList<>();

    public Account(){}


    public Account(String name ,String userName, String passWord, String checkPassWord, int id) {
        this.name=name;
        this.userName = userName;
        this.passWord = passWord;
        this.checkPassWord = checkPassWord;
        this.id=id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getCheckPassWord() {
        return checkPassWord;
    }

    public void setCheckPassWord(String checkPassWord) {
        this.checkPassWord = checkPassWord;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return userName ;
    }
}

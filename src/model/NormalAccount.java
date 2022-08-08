package model;

import model.Account;

import java.time.LocalDate;

public class NormalAccount extends Account {
    private LocalDate birth;

    public LocalDate getBirth() {
        return birth;
    }

    public void setBirth(LocalDate birth) {
        this.birth = birth;
    }

    public NormalAccount(String name,String userName, String passWord, String checkPassWord,LocalDate birthDay,int id) {
        super(name,userName, passWord, checkPassWord,id);
        this.birth=birthDay;
    }

}
package model;

import model.Account;
import model.BusinessType;

public class BusinessAccount extends Account {
    private BusinessType businessType;


    public BusinessType getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        if(businessType.equals("Artist")||businessType.equals("Blogger")||businessType.equals("Gamer")||businessType.equals("Photographer")||
                businessType.equals("Writer")||businessType.equals("Musician")||businessType.equals("Education")){
            if(businessType.equals("Artist")){
                this.businessType = BusinessType.Artist;
            }
            else if(businessType.equals("Blogger")){
                this.businessType = BusinessType.Blogger;
            }
            else if(businessType.equals("Gamer")){
                this.businessType = BusinessType.Gamer;
            }
            else if(businessType.equals("Photographer")){
                this.businessType = BusinessType.Photographer;
            }
            else if(businessType.equals("Writer")){
                this.businessType = BusinessType.Writer;
            }
            else if(businessType.equals("Musician")){
                this.businessType = BusinessType.Musician;
            }
            else {
                this.businessType = BusinessType.Education;
            }
        }
    }

    public BusinessAccount(String name , String userName, String passWord, String checkPassWord,String businessType,int id) {
        super(name ,userName, passWord, checkPassWord,id);
        this.setBusinessType(businessType);
    }
}

package com.targetappcraft.tictactoe.Modal;

public class users {


    String userName;
    String Number;


    public users(String userName, String Number) {
        this.userName = userName;
        this.Number = Number;
    }


    public users(){}


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNumber() {
        return Number;
    }

    public void setNumber(String number) {
        Number = number;
    }
}

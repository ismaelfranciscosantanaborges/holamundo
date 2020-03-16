package com.example.smartlist;

public class CFabrica {


    private String user;
    private String password;

    public CFabrica (String user, String password){
        this.user = user;
        this.password = password;
    }

    public boolean tipoUsuario(){
        if (user.equalsIgnoreCase("admin") && password.equalsIgnoreCase("123456")){
            return true;
        }

        return false;
    }

}

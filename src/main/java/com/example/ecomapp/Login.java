package com.example.ecomapp;

import java.sql.ResultSet;

public class Login {


    public User customerLogin(String userName, String password){

        String loginQuery="SELECT * from loginInfo where email='"+userName+"' AND password='"+password+"';";
        DbConnection conn=new DbConnection();
        ResultSet rs=conn.getQueryTable(loginQuery);

        try{
            if(rs.next()){
                return new User(rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("mobile"));
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public User adminLogin(String userName, String password){

        String loginQuery="SELECT * from admintable where email='"+userName+"' AND password='"+password+"';";
        DbConnection conn=new DbConnection();
        ResultSet rs=conn.getQueryTable(loginQuery);

        try{
            if(rs.next()){
                return new User(rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("mobile"));
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

//    for customer
    public static boolean isAlreadyRegistered(String query,String emailOrMobile){

        String loginQuery="SELECT * from loginInfo where "+emailOrMobile+"='"+query+"';";
        DbConnection conn=new DbConnection();
        ResultSet rs=conn.getQueryTable(loginQuery);

        try{
            if(rs.next()){
                return true;
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public  static boolean newCustomerRegistration(String name,String email,String mobile,String password){
        String registerUser="INSERT INTO loginInfo(name,email,mobile,password) values" +
                "('"+name+"','"+email+"','"+mobile+"','"+password+"');";
        DbConnection con=new DbConnection();

        try{
            return con.updateDb(registerUser)!=0;

        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

//    for admin
    public static boolean isAlreadyAdminRegistered(String query,String emailOrMobile){

        String loginQuery="SELECT * from admintable where "+emailOrMobile+"='"+query+"';";
        DbConnection conn=new DbConnection();
        ResultSet rs=conn.getQueryTable(loginQuery);

        try{
            if(rs.next()){
                return true;
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }
    public  static boolean newAdminRegistration(String name,String email,String password,String mobile){
        String registerUser="INSERT INTO admintable(name,email,password,mobile) values" +
                "('"+name+"','"+email+"','"+password+"','"+mobile+"');";
        DbConnection con=new DbConnection();

        try{
            if(mobile.length()==10){
                return con.updateDb(registerUser)!=0;
            }else return false;


        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

}

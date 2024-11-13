package com.example.java_project;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection
{
    public static Connection doConnect()
    {
        Connection con=null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/javaproject", "root", "Tanishque23");
        }
        catch(Exception exp)
        {
            System.out.println(exp.fillInStackTrace().toString());
        }
        return con;
    }
    public static void main(String ary[])
    {
        if(doConnect()==null)
            System.out.println("Sorryyyy");
        else
            System.out.println("Badhaiiii");

    }
}

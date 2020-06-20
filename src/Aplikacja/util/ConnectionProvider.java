package Aplikacja.util;
 
import java.sql.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
 
public class ConnectionProvider {
 
    private static DataSource dataSource;
 
    public static Connection getConnection() throws SQLException {
        return getDataSource().getConnection();
    }
 
    public static DataSource getDataSource() {
        try{
            Connection myConn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/serwis", "root", "metin2");
            Statement statement = myConn.createStatement();
            ResultSet myRs = statement.executeQuery("select * from article");
            while(myRs.next()){
                System.out.println( myRs.getString("name"));
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }

//        if (dataSource == null) {
//            try {
//                Context initialContext = new InitialContext();
//                Context envContext = (Context) initialContext
//                        .lookup("java:comp/env");
//                DataSource ds = (DataSource) envContext.lookup("jdbc/webserwis");
//                dataSource = ds;
//            } catch (NamingException e) {
//                e.printStackTrace();
//            }
//        }
         
        return dataSource;
    }
}
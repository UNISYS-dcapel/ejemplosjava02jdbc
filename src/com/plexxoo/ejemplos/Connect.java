package com.plexxoo.ejemplos;
import java.sql.*;

public class Connect
{
	static Connection CONN = null;
	static String USERNAME = "root";
    static String PASSWORD = "";
    static String URL = "jdbc:mysql://localhost:3306/sakila";
	private static long LAST_INSERT_ID;
	private static String TABLE="language";
	private static String NAME="NAME";
	private static String DATETIME="last_update";
	private static String ID="language_ID";
    public static void main (String[] args)
    {
        

        try
        {
            
            Class.forName ("com.mysql.jdbc.Driver")
            .newInstance ();
            CONN = DriverManager
            		.getConnection (URL, USERNAME, PASSWORD);
            System.out.println 
            ("Database CONNection established");
            String sql="SELECT * FROM "+TABLE;
            Statement statement = CONN.createStatement();
            ResultSet result = statement.executeQuery(sql);
            displayResults(result);
            createData();
            updateData();
            deleteData();
        }
        catch (Exception e)
        {
        	System.out.println(e.getMessage());
        	System.out.println(e.getCause());
        	System.out.println(e.getClass());
            System.out.println ("Cannot CONNect to database server");
        }
        finally
        {
            if (CONN != null)
            {
                try
                {
                    CONN.close ();
                    System.out.println ("Database Connection terminated");
                }
                catch (Exception e) { /* ignore close errors */ }
            }
        }
    }
    static void createData(){
    	 String sql = "INSERT into "+TABLE+" ("+NAME+","+DATETIME+") VALUES(?,?)";
    	  PreparedStatement prest;
		try {
			prest = CONN.prepareStatement(sql);
			prest.setString(1, "España");
			prest.setString(2,"2012-02-01 18:00:00" );
			int count = prest.executeUpdate();
			ResultSet generatedKeys = null;
	    		generatedKeys = prest.getGeneratedKeys();
	        if (generatedKeys.next()) {
	            LAST_INSERT_ID=generatedKeys.getLong(1);
	            System.out.println(LAST_INSERT_ID + " ID");
	        } else {
	            throw new SQLException("Creating user failed, no generated key obtained.");
	        }
	    	System.out.println(count + "row(s) affected");
	    	
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	  

    }
    static void updateData() throws SQLException{
    	String sql = "UPDATE "+TABLE+" SET "+NAME+" = ? WHERE "+ID+" = ?";
    	PreparedStatement prest;
		try {
			prest = CONN.prepareStatement(sql);
			prest.setString(1,"ESHpaña");
	    		prest.setLong(2,LAST_INSERT_ID);
		    	prest.executeUpdate();
		    	System.out.println("Updating Successfully!");
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	  

    }
    static void deleteData() throws SQLException{
    	String sql = "DELETE from "+TABLE+" WHERE "+ID+" = ?";
    	PreparedStatement prest;
		try {
			prest = CONN.prepareStatement(sql);
		    	prest.setLong(1,LAST_INSERT_ID);
		    	prest.executeUpdate();
		    	System.out.println("Deleting Successfully!");
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	  

    }
    static void displayResults(ResultSet r) 
    		throws SQLException {
	    	ResultSetMetaData rmeta = r.getMetaData();
	    	int numColumns=rmeta.getColumnCount();
	    	for(int i=1;i<=numColumns;++i) {
	    	 if(i<numColumns)
	    	  System.out.print(rmeta.getColumnName(i)+" | ");
	    	 else
	    	   System.out.println(rmeta.getColumnName(i));
	    	 }
	    	while(r.next()){
	    	  for(int i=1;i<=numColumns;++i) {
	    	   if(i<numColumns)
	    	    System.out.print(r.getString(i)+" | ");
	    	   else
	    	    System.out.println(r.getString(i));
	    	  }
	    	}
    }
}
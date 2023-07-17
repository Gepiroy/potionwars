package utilsPW;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SQLConnection {
	private Connection c;
	String host,port,login,pass,database,table;
	public void connect(String Host, String Username, String Password, String DatabaseName)
	{
		host = Host;port = "3306";login = Username;pass = Password;database = "gepcraft";table = "PWPlayers";
			if (c != null)
			{
			    close();
			}
			try
			{
			    c = DriverManager.getConnection("jdbc:mysql://" + host + ":3306/" + database + "?useUnicode=true&characterEncoding=utf8&autoReconnect=true" , login, pass);
			}
			catch (SQLException ex)
			{
			    ex.printStackTrace();
			}
			TryToCreateTable();
	}
	public void close()
	{
	        try {
	            c.close();
	        } catch (SQLException ex) {
	            ex.printStackTrace();
	        }
	}
	public void TryToCreateTable()
	{
		Statement state;
		
		try {
			state = c.createStatement();
			state.executeUpdate("CREATE TABLE IF NOT EXISTS " + database + "." + table +
					" (id INT NOT NULL AUTO_INCREMENT,PlayerName TEXT(20),Bonus TEXT(20),Help BOOL,Ach TEXT,Games INT NOT NULL DEFAULT 0,Wons INT NOT NULL DEFAULT 0,PRIMARY KEY(id));"
					);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void SetBonus(String PlayerName, String Bonus) throws SQLException
	{
		Statement state;
		ResultSet res;
		
		try {
			state = c.createStatement();
			res = state.executeQuery("SELECT * FROM " + database + "." + table + " WHERE PlayerName = '" + PlayerName + "';");
			
			if(!res.next()) state.executeUpdate("INSERT INTO " + database + "." + table + " (PlayerName,Bonus) VALUES('" + PlayerName + "','" + Bonus + "');");
			else state.executeUpdate("UPDATE " + database + "." + table + " SET Bonus = '" + Bonus + "' WHERE PlayerName = '" + PlayerName + "';");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void SetHelp(String PlayerName, int Help) throws SQLException
	{
		Statement state;
		ResultSet res;
		
		try {
			state = c.createStatement();
			res = state.executeQuery("SELECT * FROM " + database + "." + table + " WHERE PlayerName = '" + PlayerName + "';");
			
			if(!res.next()) state.executeUpdate("INSERT INTO " + database + "." + table + " (PlayerName,Help) VALUES('" + PlayerName + "','" + Help + "');");
			else state.executeUpdate("UPDATE " + database + "." + table + " SET Help = '" + Help + "' WHERE PlayerName = '" + PlayerName + "';");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void SetAchs(String PlayerName, String Ach) throws SQLException
	{
		Statement state;
		ResultSet res;
		
		try {
			state = c.createStatement();
			res = state.executeQuery("SELECT * FROM " + database + "." + table + " WHERE PlayerName = '" + PlayerName + "';");
			
			if(!res.next()) state.executeUpdate("INSERT INTO " + database + "." + table + " (PlayerName,Ach) VALUES('" + PlayerName + "','" + Ach + "');");
			else state.executeUpdate("UPDATE " + database + "." + table + " SET Ach = '" + Ach + "' WHERE PlayerName = '" + PlayerName + "';");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void SetGames(String PlayerName, int Ach) throws SQLException
	{
		Statement state;
		ResultSet res;
		
		try {
			state = c.createStatement();
			res = state.executeQuery("SELECT * FROM " + database + "." + table + " WHERE PlayerName = '" + PlayerName + "';");
			
			if(!res.next()) state.executeUpdate("INSERT INTO " + database + "." + table + " (PlayerName,Games) VALUES('" + PlayerName + "','" + Ach + "');");
			else state.executeUpdate("UPDATE " + database + "." + table + " SET Games = '" + Ach + "' WHERE PlayerName = '" + PlayerName + "';");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void SetWons(String PlayerName, int Ach) throws SQLException
	{
		Statement state;
		ResultSet res;
		
		try {
			state = c.createStatement();
			res = state.executeQuery("SELECT * FROM " + database + "." + table + " WHERE PlayerName = '" + PlayerName + "';");
			
			if(!res.next()) state.executeUpdate("INSERT INTO " + database + "." + table + " (PlayerName,Wons) VALUES('" + PlayerName + "','" + Ach + "');");
			else state.executeUpdate("UPDATE " + database + "." + table + " SET Wons = '" + Ach + "' WHERE PlayerName = '" + PlayerName + "';");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public List<String> GetStats(String name)
	{
		Statement state;
		ResultSet res;
		
		try {
			state = c.createStatement();
			res = state.executeQuery("SELECT * FROM " + database + "." + table + " WHERE PlayerName = '" + name + "';");
			
			if(!res.next())return null;
			List<String> list = new ArrayList<>();
			list.add(res.getString("Bonus"));
			list.add(res.getString("Help"));
			list.add(res.getString("Ach"));
			list.add(res.getString("Games"));
			list.add(res.getString("Wons"));
			return list;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}

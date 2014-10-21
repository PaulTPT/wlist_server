package database;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import wunderlist.Listw;

public class ListDatabase {

	
	

	public static boolean DBInitialized = false;
	public static Connection connec;
	public static Statement stmt;

	public static void checkDB() {
		if (!DBInitialized) {
			DBInitialized = initDatabase();
			if (DBInitialized) {
				try {
					stmt = connec.createStatement();
				} catch (SQLException e) {
					DBInitialized=false;
					e.printStackTrace();
				}
			}
		}

	}

	public static boolean initDatabase() {

		try {
			connec = DriverManager
					.getConnection("jdbc:derby:C:/Documents and Settings/Paul/ToDoDatabase");
			return true;

		} catch (SQLException e) {
				return false;
			}
		}

	
	

	public static Listw storeList(Listw list) {
		checkDB();
		
		String requete = "INSERT INTO LISTS(TITLE) VALUES ('" + list.getTitle() + "')";
		try {
			int id = stmt.executeUpdate(requete, Statement.RETURN_GENERATED_KEYS);
			list.setId(id);
			return list;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}
	
	public static boolean updateList(Listw list) {
		checkDB();
		
		String requete = "UPDATE LISTS SET TITLE= '" + list.getTitle() + "' WHERE ID = " + list.getId();
		try {
			stmt.executeUpdate(requete);
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}


	}

	public static Listw retrieveList(int listID) {
		checkDB();
		
		Listw list=new Listw();
	
		try {
			String requete = "SELECT ID, TITLE FROM LISTS WHERE ID= "
					+ listID;
			ResultSet res;

			res = stmt.executeQuery(requete);
			res.next();
			list=new Listw(res.getInt(1),res.getString(2));
			stmt.close();
			res.close();
		} catch (SQLException e) {
			e.printStackTrace();
			
		}

		try {
			connec.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}
		
		
	
	
	public static boolean deleteList(int listID) {
		checkDB();
		
		String requete = "DELETE FROM LISTS WHERE ID= "+ listID;
		try {
			stmt.executeUpdate(requete);
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
	}

}

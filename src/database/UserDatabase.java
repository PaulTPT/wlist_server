package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

public class UserDatabase {

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
					DBInitialized = false;
					e.printStackTrace();
				}
			}
			
		}

	}

	public static boolean initDatabase() {

		try {
			Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
		} catch (InstantiationException | IllegalAccessException
				| ClassNotFoundException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
			return false;
		}

		try {
			connec = DriverManager
					.getConnection("jdbc:derby:C:/Documents and Settings/Paul/ToDoDatabase");
			return true;

		} catch (SQLException e) {
			try {
				connec = DriverManager
						.getConnection("jdbc:derby:C:/Documents and Settings/Paul/ToDoDatabase;create=true");

				String requete = "CREATE TABLE USERS (NAME VARCHAR(20), PASSWORD VARCHAR(20), TOKEN VARCHAR(100), EXPDATE VARCHAR(100))";
				Statement stmt = connec.createStatement();
				stmt.executeUpdate(requete);
				String requete3 = "CREATE TABLE TODOS (ID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), TITLE VARCHAR(500), DUEDATE VARCHAR(100), LISTID INT, PRIMARY KEY (ID))";
				stmt.executeUpdate(requete3);
				String requete4 = "CREATE TABLE LISTS (ID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), TITLE VARCHAR(500),PRIMARY KEY (ID))";
				stmt.executeUpdate(requete4);
				String requete2 = "INSERT INTO USERS VALUES ('Paul','azerty','',''),('Remy','qwerty','','')";
				stmt.executeUpdate(requete2);
				stmt.close();
				TodoListUsersDatabase.createUserBase("PAUL");
				TodoListUsersDatabase.createUserBase("REMY");
				return true;
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				connec = null;
				return false;
			}
		}

	}

	public static boolean updateUser(String name, String password) {
		checkDB();

		String requete = "UPDATE USERS SET PASSWORD = '" + password
				+ "' WHERE name= '" + name + "'";
		try {
			stmt.executeUpdate(requete);
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

	}

	public static boolean deleteUser(String name) {
		checkDB();

		String requete = "DELETE FROM USERS WHERE Name= '" + name+ "'";
		try {
			stmt.executeUpdate(requete);
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

	}

	public static boolean addUser(String name, String password) {
		checkDB();
		
		
		String requete = "SELECT NAME FROM USERS WHERE NAME= '"
				+ name + "'";
		ResultSet res;
		
		try {
			res = stmt.executeQuery(requete);
			if (!res. isAfterLast()){
				return false;
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return false;
		}
		
		String requete2 = "INSERT INTO USERS VALUES ('" + name + "','"
				+ password + "')";
		try {
			stmt.executeUpdate(requete2);
			TodoListUsersDatabase.createUserBase(name);
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

	}

	public static boolean validateUser(String name, String password) {
		checkDB();
		Boolean validated = false;
		try {
			String requete = "SELECT NAME, PASSWORD FROM USERS WHERE NAME= '"
					+ name + "'";
			ResultSet res;
			
			res = stmt.executeQuery(requete);
			res.next();
			if (res.getString(1).equals(name)
					&& res.getString(2).equals(password)) {
				validated = true;
				
			}
			res.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}


		return validated;
	}

	public static String userFromToken(String token) {

		checkDB();
		token = token.replaceFirst("[B|b]earer ", "");
		String name = null;
		try {
			String requete = "SELECT NAME FROM USERS WHERE TOKEN= '" + token + "'";
			ResultSet res;

			res = stmt.executeQuery(requete);
			res.next();
			name = res.getString(1);
			res.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return name;

	}

	public static String addTokenToUser(String name) {
		checkDB();
		String token=UUID.randomUUID().toString();

		String requete = "UPDATE USERS SET TOKEN= '" + token
				+ "' WHERE name= '" + name + "'";
		try {
			stmt.executeUpdate(requete);
			return token;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}

}

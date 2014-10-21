package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import wunderlist.Todo;

public class TodosDatabase {
	
	

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

	
	

	public static Todo storeTodo(Todo todo) {
		checkDB();
		
		String requete = "INSERT INTO TODOS(TITLE,DUEDATE,LISTID) VALUES ('" + todo.getTitle() + "','" + todo.getDueDate()+ "'," + todo.getList_id() + ")";
		try {
			int id = stmt.executeUpdate(requete, Statement.RETURN_GENERATED_KEYS);
			todo.setId(id);
			return todo;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
		



	}
	
	public static boolean updateTodo(Todo todo) {
		checkDB();
		
		String requete = "UPDATE TODOS SET TITLE = '" + todo.getTitle() + "' , DUEDATE = '" + todo.getDueDate()+ "', LISTID = " + todo.getList_id() + " WHERE ID = " + todo.getId();
		try {
			stmt.executeUpdate(requete);
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		


	}

	public static Todo retrieveTodo(int todoID) {
		checkDB();
		
		Todo todo=new Todo();
	
		try {
			String requete = "SELECT ID, TITLE, DUEDATE, LISTID FROM TODOS WHERE ID= "
					+ todoID;
			ResultSet res;

			res = stmt.executeQuery(requete);
			res.next();
			todo=new Todo(res.getInt(1),res.getString(2),res.getString(3), res.getInt(4));
			stmt.close();
			res.close();
		} catch (SQLException e) {
			e.printStackTrace();
			
		}

		
		return todo;
	}
		
		
	
	
	public static boolean deleteTodo(int todoID) {
		checkDB();
		
		String requete = "DELETE FROM TODOS WHERE ID= "+ todoID;
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

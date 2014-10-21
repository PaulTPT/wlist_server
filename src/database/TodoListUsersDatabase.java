package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import wunderlist.Listw;
import wunderlist.Todo;

public class TodoListUsersDatabase {
	
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
	
	
	public static boolean createUserBase(String name) {
		checkDB();

		String requete1 = "CREATE TABLE " + name + " (ID INT, TYPE VARCHAR(100) )";
		try {
			stmt.executeUpdate(requete1);
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
		
	}

	public static boolean addTodoToUser(String name,int todoID) {
		checkDB();
		System.out.println(todoID);
		String requete = "INSERT INTO " + name + " VALUES (" + todoID + ",'" + "TODO" + "')";
		try {
			stmt.executeUpdate(requete);
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

	}
	
	public static boolean addListToUser(String name,int listID) {
		checkDB();
		
		String requete = "INSERT INTO " + name + " VALUES (" + listID + ",'LIST')";
		try {
			stmt.executeUpdate(requete);
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

	}
	
	public static boolean removeTodoFromUser(String name,int todoID) {
		checkDB();
		
		String requete = "DELETE FROM " + name + " WHERE ID= " + todoID;
		try {
			stmt.executeUpdate(requete);
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	public static boolean removeListFromUser(String name,int listID) {
		checkDB();
		
		String requete = "DELETE FROM " + name + " WHERE ID= " + listID;
		try {
			stmt.executeUpdate(requete);
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	
	public static List<Integer> getTodosIds(String user){
		checkDB();
		
		String requete = "SELECT ID FROM " + user + " WHERE TYPE = 'TODO'" ;
		ResultSet res;
		List<Integer> list = new ArrayList<Integer>();
		try {
			res = stmt.executeQuery(requete);
			while(res.next()){
				list.add(res.getInt(1));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
		
		
	}
	
	public static List<Integer> getListsIds(String user){
		checkDB();
		
		String requete = "SELECT ID FROM " + user + " WHERE TYPE = 'LIST'"  ;
		ResultSet res;
		List<Integer> list = new ArrayList<Integer>();
		try {
			res = stmt.executeQuery(requete);
			while(res.next()){
				list.add(res.getInt(1));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
		
		
	}
	
	public static  boolean userOwnsTodo(String user,int id){
		List<Integer> list=getTodosIds(user);
		return list.contains(id);
		
	}
	
	public static  boolean userOwnsList(String user,int id){
		List<Integer> list=getListsIds(user);
		return list.contains(id);
		
	}
	

	public static List<Todo> getTodos(String user) {
		
		List<Todo> list = new ArrayList<Todo>();
		
		for(int id:getTodosIds(user)){
			list.add(TodosDatabase.retrieveTodo(id));
			
		}
		
		return list;
	}
	
	public static List<Listw> getLists(String user) {
		
		List<Listw> list = new ArrayList<Listw>();
		
		for(int id:getListsIds(user)){
			list.add(ListDatabase.retrieveList(id));
			
		}
		
		return list;
	}
	
	public static boolean isTodo(String user, int id){
		checkDB();
		
		String requete = "SELECT TYPE FROM " + user + "WHERE ID= " + id  ;
		ResultSet res;
		try {
			res = stmt.executeQuery(requete);
			res.next();
			if(res.getString(1).equals("TODO")){
				return true;
			}else{
				return false;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
		
	}

}

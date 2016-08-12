package Model;

import java.sql.*;
import java.util.ArrayList;

/**
 * Klasa Modelu architektury MVC
 * @author Łukasz Piekarzewski
 *
 */
public class Model {
	
	private Connection connect = null;
	private Statement statement = null;
	private ResultSet resultSet = null; 
	
	private String url = "jdbc:mysql://localhost/kontakty_tel";
	private String username = "root";
	private String password = "java";
	
	private String item;
	private ArrayList<String> result;
	
	public Model() {
		try{
			Class.forName("com.mysql.jdbc.Driver");
			connect = DriverManager.getConnection(url,username,password);
			statement = connect.createStatement();
			result = new ArrayList<String>();
	
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			try {
				resultSet.close();
				statement.close();
				connect.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	/**
	 * Metoda dodajaca do bazy danych rekord o wartosciach imie, nazwisko i telefon
	 * @param nameS przechowuje imie
	 * @param surnameS przechowuje nazwisko
	 * @param phoneS przechowuje numer telefonu
	 * @return zwraca wartosc true jeżeli zapytanie do bazy wykona sie pomyslnie i false w przeciwnym wypadku
	 */
	public boolean addItem(String nameS, String surnameS, String phoneS) {
		try {
			
			statement.executeUpdate("insert into kontakty values ('"+nameS+"','"+surnameS+"','"+
			phoneS+"')"); 
					
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				statement.close();
				return false;
			} catch (SQLException e1) {
				e1.printStackTrace();
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Funkcja usuwajaca z bazy danych rekord o zadanych wartosciach kolumn
	 * @param nameS przechowuje imie
	 * @param surnameS przechowuje nazwisko
	 * @param phoneS przechowuje numer telefonu
	 * @return zwraca wartosc true jeżeli zapytanie do bazy wykona sie pomyslnie i false w przeciwnym wypadku
	 */
		
	public boolean deleteItem(String nameS, String surnameS, String phoneS) {
		try {
			
			statement.executeUpdate("delete from kontakty where name = '"+nameS+
					"' and surname ='"+surnameS+"' and phone = '"+ phoneS+"'"); 
			
					
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				statement.close();
				return false;
			} catch (SQLException e1) {
				e1.printStackTrace();
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Wykonuje zapytanie do bazy o wszystkie jej elementy
	 * Wynik jest zapisywany w kolekcji ArrayList<String>
	 * @return zwraca odnosnik do kolekcji zawierajacy wynik zapytania na bazie danych
	 */
	public ArrayList<String> showItems() {
		try {
			result.clear();
			resultSet = statement.executeQuery("select * from kontakty");
			
			while(resultSet.next()) {
				
				/*
				 * Utworzenie Stringu z pojedynczego rekordu z bazy danych
				 */
				
				item = resultSet.getString("name")+" "+resultSet.getString("surname")+" "+
				resultSet.getString("phone")+"\n";
				
				/*
				 * Zapisanie stringu do kolekcji ArrayList
				 */
				
				result.add(item);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				statement.close();
				resultSet.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		
		return result;
	}
}

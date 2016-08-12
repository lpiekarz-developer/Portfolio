package Project;

import Model.*;
import View.*;
import Controller.*;

/**
 * Klasa główna projektu zrealizowanego w archtekturze MVC
 * W metodzie main tworzone są instancje Modelu, Widoku i Kontrolera oraz automatycznie
 * ustanawiana jest między nimi łączność poprzez obiekty nasłuchiwaczy
 * 
 * @author Łukasz Piekarzewski
 *
 */

public class Project {
	
	public static void main(String args[]) {
		
		/**
		 * Konstruktor Modelu - tworzy polączenie z baza danych
		 */

		Model model_ = new Model();
		
		/**
		 * Konstruktor Widoku - tworzy graficzny interfejs uzytkownika
		 */
		
		View view_ = new View();
		
		/**
		 * Konstruktor Kontrolera - inicjalizuje nasluchiwaczy i oczekuje na dzialania uzytkownika
		 */
		
		Controller con_ = new Controller(model_,view_);

	}
	
}

package Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import Model.*;
import View.*;

/**
 * Klasa Kontrolera architektury MVC
 * @author Łukasz Piekarzewski
 *
 */
public class Controller {

	private Model model_;
	private View view_;
	private ArrayList<String> als;
	
	public Controller(Model m, View v) {
		model_ = m;
		view_ = v;
		
		view_.addDodajListener(new DodajButtonListener());
		view_.addUsunListener(new UsunButtonListener());
		view_.addPokazListener(new PokazButtonListener());
		view_.addWyczyscListener(new WyczyscButtonListener());
	}
	
	/**
	 * Klasa wewnetrzna opisujaca obiekt nasluchiwacza dla przycisku "Dodaj"
	 * 
	 */
	
	class DodajButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			
			if( model_.addItem(view_.getName(),view_.getSurname(),view_.getPhone()) )
				view_.setMessage("Update OK\n");
			else
				view_.setMessage("Update Failed\n");
		}
	}
	
	/**
	 * KKlasa wewnetrzna opisujaca obiekt nasluchiwacza dla przycisku "Usun"
	 *
	 */
	
	class UsunButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			
			if( model_.deleteItem(view_.getName(),view_.getSurname(),view_.getPhone()) )
				view_.setMessage("Delete OK\n");
			else
				view_.setMessage("Delete Failed\n");
		}
	}
	
	/**
	 * Klasa wewnetrzna opisujaca obiekt nasluchiwacza dla przycisku "Pokaz Kontakty"
	 *
	 */
	
	class PokazButtonListener implements ActionListener {
		
		public void actionPerformed(ActionEvent e) {

			als = model_.showItems();
			for(int i=0; i<als.size(); i++) {
				view_.setMessage(als.get(i));
			}
		}		
	}
	
	/**
	 * Klasa wewnetrzna opisujaca obiekt nasluchiwacza dla przycisku "Czysc Konsole"
	 *
	 */
	
	class WyczyscButtonListener implements ActionListener {
		
		public void actionPerformed(ActionEvent e) {
			view_.clearTextArea();
		}
	}		
}
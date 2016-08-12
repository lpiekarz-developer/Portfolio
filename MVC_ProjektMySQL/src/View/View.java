package View;

import java.awt.*;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * Klasa Widoku architektury MVC
 * @author Łukasz Piekarzewski
 *
 */
public class View extends JFrame{
	
	private JTextField name = new JTextField(10);
	private JTextField surname = new JTextField(10);
	private JTextField phone = new JTextField(10);
	
	private JButton dodaj = new JButton("Dodaj");
	private JButton usun = new JButton("Usuń");
	private JButton pokaz = new JButton("Pokaż Kontakty");
	private JButton wyczysc = new JButton("Czyść Konsole");
	
	private JTextArea obszarTekstu = new JTextArea(8,10);

	public View() {
		setTitle("Kontakty telefoniczne");
		setSize(700,350);
		setLocation(250,150);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Container powZaw = this.getContentPane();
		JPanel contentBTN = new JPanel();
		JPanel contentTXF = new JPanel();
		JScrollPane scrollPane = new JScrollPane(obszarTekstu);
		
		contentTXF.add(new JLabel("IMIE: "));
		contentTXF.add(name);
		contentTXF.add(new JLabel("NAZWISKO: "));
		contentTXF.add(surname);
		contentTXF.add(new JLabel("TELEFON:"));
		contentTXF.add(phone);
				
		contentBTN.add(dodaj);
		contentBTN.add(pokaz);
		contentBTN.add(usun);
		contentBTN.add(wyczysc);

		powZaw.add(contentBTN,BorderLayout.SOUTH);
		powZaw.add(contentTXF,BorderLayout.CENTER);
		powZaw.add(scrollPane,BorderLayout.NORTH);

		this.setVisible(true);
	}
	
	/**
	 * Dodaje obiekt nasluchiwacza do przycisku "Dodaj"
	 * @param acD
	 */
	
	public void addDodajListener(ActionListener acD) {
		dodaj.addActionListener(acD);
	}
	
	/**
	 * Dodaje obiekt nasluchiwacza do przycisku "Usun"
	 * @param acU
	 */
	
	public void addUsunListener(ActionListener acU) {
		usun.addActionListener(acU);
	}
	
	/**
	 * Dodaje obiekt nasluchiwacza do przycisku "Pokaz Kontakty"
	 * @param acP
	 */
	
	public void addPokazListener(ActionListener acP) {
		pokaz.addActionListener(acP);
	}
	
	/**
	 * Dodaje obiekt nasluchiwacza do przycisku "Czysc Konsole"
	 * @param acW
	 */
	public void addWyczyscListener(ActionListener acW) {
		wyczysc.addActionListener(acW);
	}
	
	/**
	 * Zwraca wartosc pola tekstowego opisującego imie
	 */
	
	public String getName() {
		return name.getText();
	}
	
	/**
	 * Zwraca wartosc pola tekstowego opisującego nazwisko
	 * @return
	 */
	
	public String getSurname() {
		return surname.getText();
	}
	
	/**
	 * Zwraca wartosc pola tekstowego opisujacego numer telefonu
	 * @return
	 */
	public String getPhone() {
		return phone.getText();
	}
	
	/**
	 * Wstawia okresloną wiadomosc do konsoli glownej programu
	 * @param msg
	 */
	
	public void setMessage(String msg) {
		obszarTekstu.append(msg);
	}
	
	/**
	 * Czysci konsole glowna programu
	 */
	
	public void clearTextArea() {
		obszarTekstu.setText("");
	}

}

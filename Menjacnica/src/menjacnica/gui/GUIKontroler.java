package menjacnica.gui;

import java.awt.EventQueue;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import menjacnica.Menjacnica;
import menjacnica.MenjacnicaInterface;
import menjacnica.Valuta;

public class GUIKontroler {

	private static MenjacnicaGUI menjacnicaGUI;
	private static MenjacnicaInterface menjacnicaInterfejs;
	
	
	/**
	 * Startovanje apliacije iz GUIKontrolera, a ne iz glavne forme.
	 */
	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					menjacnicaInterfejs = new Menjacnica();
					menjacnicaGUI = new MenjacnicaGUI();
					menjacnicaGUI.setVisible(true);
					menjacnicaGUI.setLocationRelativeTo(null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
	}
	
	public static void ugasiAplikaciju() {
		int opcija = JOptionPane.showConfirmDialog(menjacnicaGUI.getContentPane(),
				"Da li ZAISTA zelite da izadjete iz apliacije", "Izlazak",
				JOptionPane.YES_NO_OPTION);

		if (opcija == JOptionPane.YES_OPTION)
			System.exit(0);
	}
	
	public static void sacuvajUFajl() {
		try {
			JFileChooser fc = new JFileChooser();
			int returnVal = fc.showSaveDialog(menjacnicaGUI.getContentPane());

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();

				menjacnicaInterfejs.sacuvajUFajl(file.getAbsolutePath());
			}
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(menjacnicaGUI.getContentPane(), e1.getMessage(),
					"Greska", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public static void prikaziAboutProzor(){
		JOptionPane.showMessageDialog(menjacnicaGUI.getContentPane(),
				"Autor: Bojan Tomic, Verzija 1.0", "O programu Menjacnica",
				JOptionPane.INFORMATION_MESSAGE);
	}
	

	public static void ucitajIzFajla() {
		try {
			JFileChooser fc = new JFileChooser();
			int returnVal = fc.showOpenDialog(menjacnicaGUI.getContentPane());

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();
				menjacnicaInterfejs.ucitajIzFajla(file.getAbsolutePath());
				menjacnicaGUI.prikaziSveValute(menjacnicaInterfejs.vratiKursnuListu());
			}	
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(menjacnicaGUI.getContentPane(), e1.getMessage(),
					"Greska", JOptionPane.ERROR_MESSAGE);
		}
	
	}
	
	
	public static void prikaziDodajKursGUI() {
		DodajKursGUI prozor = new DodajKursGUI();
		prozor.setLocationRelativeTo(menjacnicaGUI.getContentPane());
		prozor.setVisible(true);
	}
	
	public static void prikaziIzvrsiZamenuGUI(Valuta valuta) {
		if (valuta != null) {
			IzvrsiZamenuGUI prozor = new IzvrsiZamenuGUI(valuta);
			prozor.setLocationRelativeTo(menjacnicaGUI.getContentPane());
			prozor.setVisible(true);
		}
	}
	
	public static void prikaziObrisiKursGUI(Valuta valuta) {
		if (valuta != null) {
			ObrisiKursGUI prozor = new ObrisiKursGUI(valuta);
			prozor.setLocationRelativeTo(menjacnicaGUI.getContentPane());
			prozor.setVisible(true);
		}
	}
	
	
	public static void unesiKurs(String naziv, String skraceniNaziv, int sifra, String prodajni, String kupovni, String srednji) {
		try {
			Valuta valuta = new Valuta();

			// Punjenje podataka o valuti
			valuta.setNaziv(naziv);
			valuta.setSkraceniNaziv(skraceniNaziv);
			valuta.setSifra(sifra);
			valuta.setProdajni(Double.parseDouble(prodajni));
			valuta.setKupovni(Double.parseDouble(kupovni));
			valuta.setSrednji(Double.parseDouble(srednji));
			
			
			// Dodavanje valute u kursnu listu
			menjacnicaInterfejs.dodajValutu(valuta);

			// Osvezavanje glavnog prozora
			menjacnicaGUI.prikaziSveValute(menjacnicaInterfejs.vratiKursnuListu());
			
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(menjacnicaGUI.getContentPane(), e1.getMessage(),
					"Greska", JOptionPane.ERROR_MESSAGE);
		}
	} 
	
	
	
	
	public static void obrisiValutu(Valuta valuta) {
		try{
			menjacnicaInterfejs.obrisiValutu(valuta);
			
			menjacnicaGUI.prikaziSveValute(menjacnicaInterfejs.vratiKursnuListu());
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(menjacnicaGUI.getContentPane(), e1.getMessage(),
					"Greska", JOptionPane.ERROR_MESSAGE);
		}
	}
}

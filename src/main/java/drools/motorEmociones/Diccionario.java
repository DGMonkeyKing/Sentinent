package drools.motorEmociones;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;

import drools.motorEmociones.MotorEmociones.Emociones;

public class Diccionario {
	public static String rutaDiccionarioFelicidad = "assets/dics/felicidad.txt";
	public static String rutaDiccionarioTriste = "assets/dics/triste.txt";
	public static String rutaDiccionarioSorprendido = "assets/dics/sorprendido.txt";
	public static String rutaDiccionarioConfundido = "assets/dics/confundido.txt";
	public static String rutaDiccionarioEnfadado = "assets/dics/enfadado.txt";
	private Emociones emocion;
	private HashSet<String> diccionario;
	
	public Diccionario(Emociones emocion){
		this.emocion = emocion;
		this.diccionario = new HashSet<String>();
		loadDic();
	}
	private void loadDic(){
		File file = null;
		if(emocion == Emociones.FELIZ)
			file = new File(rutaDiccionarioFelicidad);
		else if(emocion == Emociones.TRISTE)
			file = new File(rutaDiccionarioTriste);
		else if(emocion == Emociones.CONFUNDIDO)
			file = new File(rutaDiccionarioConfundido);
		else if(emocion == Emociones.ENFADADO)
			file = new File(rutaDiccionarioEnfadado);
		else if(emocion == Emociones.SORPRENDIDO)
			file = new File(rutaDiccionarioSorprendido);
		try {
			@SuppressWarnings("resource")
			String textoCompleto =  new Scanner(file).useDelimiter("\\Z").next(); //Leemos el fichero entero.
			String[] words;
			if(textoCompleto.contains("\r\n")){
				words = textoCompleto.split("\\r\\n"); //Parseamos por retorno de carro(salto de linea)
			}
			else{
				words = textoCompleto.split("\\n"); //Parseamos por retorno de carro(salto de linea)

			}
			for(String s : words){
				this.diccionario.add(s.replaceAll("\n", "").
						replace('á', 'a').
						replace('é', 'e').
						replace('í', 'i').
						replace('ó', 'o').
						replace('ú', 'u')); 					//Anadimos las palabras al diccionario. 
			}			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public Emociones getEmocion() {
		return emocion;
	}
	public void setEmocion(Emociones emocion) {
		this.emocion = emocion;
	}
	public void setEmocion(Object emocion) {
		this.emocion = (Emociones)emocion;
	}
	public HashSet<String> getDiccionario() {
		return diccionario;
	}
	public void setDiccionario(HashSet<String> diccionario) {
		this.diccionario = diccionario;
	}
	public boolean existePalabra(String word){
		String palabra = word;
		boolean x = this.diccionario.contains(palabra);
		if(!x && palabra.charAt(palabra.length()-1) == 'a'){
			palabra = palabra.substring(0, palabra.length()-1) + 'o';
			if(this.diccionario.contains(palabra))
				return true;
		}
		else if(!x && palabra.charAt(palabra.length()-1) == 'o'){
			palabra = palabra.substring(0, palabra.length()-1) + 'a';
			if(this.diccionario.contains(palabra))
				return true;
		}
		return x;
	}
	
	public boolean existePalabra(Object word){
		String palabra = (String) word;
		boolean x = this.diccionario.contains(palabra);
		if(!x && palabra.charAt(palabra.length()-1) == 'a'){
			palabra = palabra.substring(0, palabra.length()-1) + 'o';
			if(this.diccionario.contains(palabra))
				return true;
		}
		else if(!x && palabra.charAt(palabra.length()-1) == 'o'){
			palabra = palabra.substring(0, palabra.length()-1) + 'a';
			if(this.diccionario.contains(palabra))
				return true;
		}
		return x;
	}
	
	public void addPalabra(String word){
		if(!this.diccionario.contains(word))
			this.diccionario.add(word);
	}
	
	public void addPalabra(Object word){
		if(!this.diccionario.contains(word))
			this.diccionario.add((String)word);
	}
	
	public void eliminarPalabra(String word){
		this.diccionario.remove(word);
	}
	
	public void eliminarPalabra(Object word){
		this.diccionario.remove((String)word);
	}
	
}

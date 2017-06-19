package drools.motorEmociones;

import java.util.HashMap;

import drools.rules.DroolsEngine;

public class MotorEmociones {
	public enum Emociones{
		FELIZ,
		TRISTE,
		ENFADADO,
		SORPRENDIDO,
		CONFUNDIDO,
		NEUTRO};
		
		
	public enum TipoMensaje{
			ELIMINAR,
			INSERTAR,
			CAMBIAR};
			
	private DroolsEngine rules;
	private int numFrases;
	private HashMap<String,DecidirSentimiento> decisiones;
	
	public MotorEmociones(){
		this.rules = new DroolsEngine();
		this.numFrases = 0;
		decisiones = new HashMap<String,DecidirSentimiento>();
		Diccionario dic = new Diccionario(Emociones.FELIZ);
		this.insertaDiccionario(dic);
		this.insertaDiccionario(new Diccionario(Emociones.CONFUNDIDO));
		this.insertaDiccionario(new Diccionario(Emociones.ENFADADO));
		this.insertaDiccionario(new Diccionario(Emociones.SORPRENDIDO));
		this.insertaDiccionario(new Diccionario(Emociones.TRISTE));
	}
	public void insertaFrase(String frase){
		frase = frase.replaceAll(", ", " ");
		frase = frase.replaceAll(",", " ");
		String[] words = frase.split(" ");
		int numPalabras = words.length;
		
		
		for(int i =0;i<words.length ;i++){
			if(!words[i].equals(""))
				this.rules.insertaHechoWithoutFireRules(new Palabra(words[i].toLowerCase().
					replace('á', 'a').
					replace('é', 'e').
					replace('í', 'i').
					replace('ó', 'o').
					replace('ú', 'u')
					,this.numFrases,i));
			else numPalabras--;
		}
		DecidirSentimiento decidirSentimiento = new DecidirSentimiento(numPalabras,this.numFrases,frase);
		this.decisiones.put(frase, decidirSentimiento);
		this.rules.insertaHechoWithoutFireRules(decidirSentimiento);
		numFrases++;
		this.lanzaReglas();
	}
	
	
	private void lanzaReglas() {
		this.rules.fireRules();
	}
	public void insertaDiccionario(Diccionario dic){
		this.rules.insertaHechoWithoutFireRules(dic);	
	}
	public void setDecisionSentimiento(DecidirSentimiento decidirSentimiento){
		this.decisiones.put(decidirSentimiento.getFrase(), decidirSentimiento);
	}
	public String getSentimientoDeFrase(String frase){
		if(this.decisiones.containsKey(frase))
			return this.decisiones.get(frase).getEmocion().toString();
		else
			return null;
	}
	public static void main(String args[]){
		
		MotorEmociones motor = new MotorEmociones();
		motor.insertaFrase("Esta es una frase de felicidad, o no, yo que se pavo");
		motor.lanzaReglas();
		
	}
	
	public void eliminaPalabra(String palabra){
		this.rules.insertaHecho(new Mensaje(TipoMensaje.ELIMINAR, palabra, null));
	}
	
	public void insertaPalabra(String palabra, String emocion){
		Emociones em = null;
		if(emocion.equalsIgnoreCase("feliz")) em = Emociones.FELIZ;
		else if(emocion.equalsIgnoreCase("confundido")) em = Emociones.CONFUNDIDO;
		else if(emocion.equalsIgnoreCase("enfadado")) em = Emociones.ENFADADO;
		else if(emocion.equalsIgnoreCase("sorprendido")) em = Emociones.SORPRENDIDO;
		else if(emocion.equalsIgnoreCase("triste")) em = Emociones.TRISTE;
		this.rules.insertaHecho(new Mensaje(TipoMensaje.INSERTAR, palabra, em));
	}
	
	public void cambiaSentimientos(String origen, String destino){
		Emociones or = null, dest = null;
		if(origen.equalsIgnoreCase("feliz")) or = Emociones.FELIZ;
		else if(origen.equalsIgnoreCase("confundido")) or = Emociones.CONFUNDIDO;
		else if(origen.equalsIgnoreCase("enfadado")) or = Emociones.ENFADADO;
		else if(origen.equalsIgnoreCase("sorprendido")) or = Emociones.SORPRENDIDO;
		else if(origen.equalsIgnoreCase("triste")) or = Emociones.TRISTE;
		
		if(destino.equalsIgnoreCase("feliz")) dest = Emociones.FELIZ;
		else if(destino.equalsIgnoreCase("confundido")) dest = Emociones.CONFUNDIDO;
		else if(destino.equalsIgnoreCase("enfadado")) dest = Emociones.ENFADADO;
		else if(destino.equalsIgnoreCase("sorprendido")) dest = Emociones.SORPRENDIDO;
		else if(destino.equalsIgnoreCase("triste")) dest = Emociones.TRISTE;
		
		this.rules.insertaHecho(new Mensaje(TipoMensaje.CAMBIAR, or, dest));
	}
		
}

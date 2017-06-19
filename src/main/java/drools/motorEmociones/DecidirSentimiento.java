package drools.motorEmociones;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;


import drools.motorEmociones.MotorEmociones.Emociones;

public class DecidirSentimiento {
	private int numPalabrasActuales;
	private int numPalabrasTotales;
	private Integer id;
	private Hashtable<Emociones, Integer> contadorEmociones;
	private Emociones emocion;
	private String frase;
	
	
	public DecidirSentimiento(int numeroPalabras,Integer id,String frase){
		this.numPalabrasTotales = numeroPalabras;
		this.id = id;
		this.frase = frase;
		this.emocion = null;
		this.numPalabrasActuales = 0;
		this.contadorEmociones = new Hashtable<Emociones,Integer>();
	}
	
	public String getFrase() {
		return frase;
	}

	public void setFrase(String frase) {
		this.frase = frase;
	}

	public Emociones getEmocion() {
		return emocion;
	}

	public void setEmocion(Emociones emocion) {
		this.emocion = emocion;
	}

	public Emociones solve(){
		int top = -1;
		Set<Entry<Emociones,Integer>> set = this.contadorEmociones.entrySet();
		Iterator<Entry<Emociones, Integer>> it = set.iterator();
		while(it.hasNext()){
			Entry<Emociones, Integer> e = it.next();
			if(e.getValue() > top){
				top  = e.getValue();
				this.emocion = e.getKey();
			}			
		}
		if(this.emocion == null){
			this.emocion = Emociones.NEUTRO;
			return Emociones.NEUTRO;
		}
		else return this.emocion;
	}
	public int getNumPalabrasActuales() {
		return numPalabrasActuales;
	}


	public void setNumPalabrasActuales(int numPalabrasActuales) {
		this.numPalabrasActuales = numPalabrasActuales;
	}


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public Hashtable<Emociones, Integer> getContadorEmociones() {
		return contadorEmociones;
	}


	public void setContadorEmociones(Hashtable<Emociones, Integer> contadorEmociones) {
		this.contadorEmociones = contadorEmociones;
	}


	public void setNumPalabrasTotales(int numPalabrasTotales) {
		this.numPalabrasTotales = numPalabrasTotales;
	}


	public void incrementaEmocion(Emociones emocion){
		if(emocion == null){}
		else if(this.contadorEmociones.contains(emocion)){
			this.contadorEmociones.put(emocion, this.contadorEmociones.get(emocion) + 1);
		}
		else{
			this.contadorEmociones.put(emocion, 1);
		}
		this.numPalabrasActuales++;
	}
	public Integer getNumPalabrasEmocion(Emociones emocion){
		return this.contadorEmociones.get(emocion);
	}
	public int getNumPalabrasProcesadas(){
		return this.numPalabrasActuales;
	}
	public int getNumPalabrasTotales(){
		return this.numPalabrasTotales;
	}
	
	
}

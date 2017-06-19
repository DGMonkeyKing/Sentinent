package vista;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import miscelanea.Biblioteca;

public class PhonemeDelegate 
{
	/**
	 * Objeto que dado un caracter te devuelve el nombre de la imagen del fonema al que pertenece
	 */
	private Biblioteca<String> phonemeDictionary;
	/**
	 * Crea el diccionario de caracter a nombre de la imagen del fonema 
	 * @param exceptionFile El diccionario de caracter a imagen del fonema a crear
	 */
	private void buildExceptionSet(String configFile)
	{
		//declaramos variables
		File file;
		FileReader fr;
		BufferedReader br;
		String line;
		//cargamosel fichero
		file = new File(configFile);
		try
		{
			//abrimos el fichero
			fr = new FileReader (file);
			br = new BufferedReader(fr);
			//leemos el numero de componentes que tendra nuestra biblioteca
			line = br.readLine();
			//creamos la biblioteca 
			phonemeDictionary = new Biblioteca<String>(Integer.parseInt(line));
			//mientras que haya lineas por leer
			while(br.ready())
			{
				//tomamos linea
				line = br.readLine();
				//la metemos en el diccionario
				phonemeDictionary.add(line);
			}
		}
		catch(FileNotFoundException e)
		{
			e.getStackTrace();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}		
	}
	
	/**
	 * Crea un objeto de la clase Delegada de los fonemas
	 * @param configFile Fichero de configuracion para atribuir a cada caracter monofonico su fonema
	 */
	public PhonemeDelegate(String configFile)
	{
		//construimos la libreria
		buildExceptionSet(configFile);
	}
	/**
	 * Dado un texto en una cierta posicion devuelve si nos encontramos ante silabas monofonicas
	 * @param text texto de la frase a pronunciar
	 * @param position posicion del caracter del texto por el que vamos pronunciando
	 * @return la silaba monofonica si la hubiera
	 */
	private String detectCharacter(String text, int position)
	{
		//declaramos e inicializamos datos
		char currentChar, nextChar, nextnextChar;
		//iniciamos la salida como vacia
		String returnValue = "";
		//cogemos el caracter actual
		currentChar = text.charAt(position);
		//su siguiente sin salirnos del array
		if(position + 1 < text.length())
			nextChar = text.charAt(position + 1);
		else
			nextChar = '\0';
		//y el siguiente del siguiente sin salirnos
		if(position + 2 < text.length())
			nextnextChar = text.charAt(position + 2);
		else
			nextnextChar = '\0';
		//si nos encontramos ante el caso del fonema de qu devolvemos qu
		if(currentChar == 'q' && nextChar == 'u' && (nextnextChar == 'e' || nextnextChar == 'i'))
			returnValue = returnValue.concat(""+currentChar+nextChar+nextnextChar);
		//si nos encontramos ante el caso del fonema de qu devolvemos qu
		else if(currentChar == 'q' && nextChar == 'u')
			returnValue = returnValue.concat(""+currentChar+nextChar);
		//si nos encontramos ante el caso del fonema de ll devolvemos ll
		else if(currentChar == 'l' && nextChar == 'l')
			returnValue = returnValue.concat(""+currentChar+nextChar);
		//si nos encontramos ante el caso del fonema de ch devolvemos ch
		else if(currentChar == 'c' && nextChar == 'h')
			returnValue = returnValue.concat(""+currentChar+nextChar);
		//si nos encontramos ante el caso del fonema de gue o gui devolvemos gu
		else if(currentChar == 'g' && nextChar == 'u' && (nextnextChar == 'e' || nextnextChar == 'i'))
			returnValue = returnValue.concat(""+currentChar+nextChar+nextnextChar);
		//si nos encontramos ante el caso del fonema de gu devolvemos gu
		else if(currentChar == 'g' && nextChar == 'u')
			returnValue = returnValue.concat(""+currentChar+nextChar);
		//si nos encontramos ante el caso del fonema de rr devolvemos rr
		else if(currentChar == 'r' && nextChar == 'r')
			returnValue = returnValue.concat(""+currentChar+nextChar);
		else if(currentChar == 'j' && isVocal(nextChar))
			returnValue = returnValue.concat(""+currentChar+nextChar);
		else if(currentChar == 'g' && isVocal(nextChar))
			returnValue = returnValue.concat(""+currentChar+nextChar);
		else if(currentChar == 'k' && isVocal(nextChar))
			returnValue = returnValue.concat(""+currentChar+nextChar);
		else if(currentChar == 'h')
			returnValue = returnValue.concat(""+currentChar+nextChar);
		else if(currentChar == 'r' && nextChar == 'r')
			returnValue = returnValue.concat(""+currentChar+nextChar);
		else if(currentChar == 'c' && isVocal(nextChar))
			returnValue = returnValue.concat(""+currentChar+nextChar);
		//en el resto de los casos solo devolvemos el caracter incial
		else
			returnValue = returnValue.concat(""+currentChar);
		return returnValue;
	}
	/**
	 * Devuelve si el caracter es una vocal o no
	 * @param character caracter a evaluar si es una vocal
	 * @return si character es vocal o no
	 */
	private boolean isVocal(char character)
	{
		//es vocal si es a,e,i,o,u
		return character == 'a' || character == 'e' || character == 'i' || character == 'o' || character == 'u'; 
	}
	/**
	 * Rutina de tratamiento para distinguir cuando la 'c' se pronuncia con un fonema o con otro
	 * @param text texto a pronunciar
	 * @param position posicion de la 'c' por el que vamos pronunciando
	 * @return el nombre de la imagen del fonema con el que se pronunciaria la actual 'c'
	 */
	private String cTreatment(String text, int position)
	{
		String returnValue = "";
		//se coge el caracter siguiente
		char nextCharacter = text.charAt(position + 1);
		//si le sigue una i o e se pronuncia como con el fonema /z/
		if(nextCharacter == 'i' || nextCharacter == 'e')
			returnValue = "CZ.bmp"; //TODO
		//devolvemos el fonema
		return returnValue;
	}
	/**
	 * Devuelve el fonema correcto entre varios fonemas posibles de un caracter multifonico
	 * @param text texto a pronunciar
	 * @param position posicion del caracter que estamos pronunciando
	 * @param caseCharacter caracter multifonico
	 * @return el nombre de la imagen del fonema con el que se pronuncia el caracter multifonico en esa circunstancia
	 */
	private String getPhonemeAmongSeveral(String text, int position, String caseCharacter)
	{
		String returnValue;
		//dividimos los caracteres multifonicos en 3 posibles
		switch (caseCharacter.charAt(0))
		{
			//en caso de que sea la 'c'
			case 'c':
				//llamamos a la rutina de tratamiento de la 'c'
				returnValue = cTreatment(text, position);
				break;
			//en caso de que lleguemos al extremisimo caso de error
			default:
				//pues lanzamos excepcion
				throw new Error("Error al tratar caracteres multifonicos");
		}
		//devolvemos el nombre de la imagen
		return returnValue;
	}
	/**
	 * Devuelve el offset necesario para que las siguientes silabas monofonicas o ñ puedan obtener su fonema de la libreria
	 * @param character silaba monofonica o ñ que desea obtener su offset
	 * @return el offset necesario para que la silaba monofonica o ñ obtenga su fonema de la libreria
	 */
	private int additionalOffset(String character)
	{
		//iniciamos el offset como 0
		int offset = 0;
		//si nos encontramos ante el caso del fonema de qu devolvemos qu
		if(character.charAt(0) == 'q' && character.charAt(1) == 'u'  && character.length() > 2 && (character.charAt(2) == 'e' || character.charAt(2) == 'i'))
			offset = (character.charAt(2) - 97) - ('z' - 97);
		//si nos encontramos ante el caso del fonema de qu devolvemos qu
		else if(character.charAt(0) == 'q' && character.charAt(1) == 'u')
			offset = ('u' - 97) - ('z' - 97);
		//si nos encontramos ante el caso del fonema de ll devolvemos ll
		else if(character.charAt(0) == 'l' && character.charAt(1) == 'l')
			offset = ('y' - 97) - ('z' - 97);
		//si nos encontramos ante el caso del fonema de ch devolvemos ch
		else if(character.charAt(0) == 'c' && character.charAt(1) == 'h')
			offset = ('x' - 97) - ('z' - 97);
		//si nos encontramos ante el caso del fonema de gue o gui devolvemos gu
		else if(character.charAt(0) == 'g' && character.charAt(1) == 'u' && character.length() > 2 && (character.charAt(2) == 'e' || character.charAt(2) == 'i'))
			offset = (character.charAt(2) - 97) - ('z' - 97);
		//si nos encontramos ante el caso del fonema de gu devolvemos gu
		else if(character.charAt(0) == 'g' && character.charAt(1) == 'u')
			offset = ('u' - 97) - ('z' - 97);
		//si nos encontramos ante el caso del fonema de rr devolvemos rr
		else if(character.charAt(0) == 'r' && character.charAt(1) == 'r')
			offset = ('r' - 97) - ('z' - 97);
		else if(character.charAt(0) == 'j')
			offset = (character.charAt(1) - 97) - ('z' - 97);
		else if(character.charAt(0) == 'g')
			offset = (character.charAt(1) - 97) - ('z' - 97);
		else if(character.charAt(0) == 'k')
			offset = (character.charAt(1) - 97) - ('z' - 97);
		else if(character.charAt(0) == 'h' && character.charAt(1) != '\0')
		{
			if(character.charAt(1) != 'ñ')
				offset = (character.charAt(1) - 97) - ('z' - 97);
			else
				offset = ('n' - 97) - ('z' - 97);
		}
		else if(character.charAt(0) == 'h' && character.charAt(1) == '\0')
			offset = 1;
		else if(character.charAt(0) == 'ñ')
			offset = ('n' - 97) - ('z' - 97);	
		else if(character.charAt(1) == 'r')
			offset = ('r' - 97) - ('z' - 97);
		else if(character.charAt(0) == 'c')
			offset = (character.charAt(1) - 97) - ('z' - 97);		
		return offset;
	}
	/**
	 * Realiza operaciones sobre la frase a pronunciar para que sea compatible con los calculos de esta clase
	 * @param phrase frase sobre la que realizar las operaciones
	 * @return phrase modificada y apta para ser usada por esta clase
	 */
	public String preparePhrase(String phrase)
	{
		//reemplazamos los espacios y convertimos todo a minusculas
		return phrase.replace('á','a').replace('é','e').replace('í','i').replace('ó','o').replace('ú','u').replace(" ", "").toLowerCase();
	}
	/**
	 * Devuelve el fonema del actual caracter o caracteres a pronunciar
	 * @param text texto a pronunciar
	 * @param position posicion del actual caracter o caracteres a pronunciar
	 * @return una clase que devulve el resultado del computo de esta clase
	 */
	public PhonemeComputationResult calculatePhoneme(String text, int position)
	{
		//declaramos e iniciamos los datos 
		String character;
		String phoneme;
		//detectamos el caracter en el que nos encontramos
		character = detectCharacter(text, position);
		//vemos si es un caracter multifonico
		if(character.equalsIgnoreCase("c"))
			phoneme = getPhonemeAmongSeveral(text, position, character);
		//si no lo es
		else
		{
			//si el caracter actual no es un caracter simple o es una 'ñ'
			if(character.length() > 1 || character.equalsIgnoreCase("ñ"))
				//buscamos el fonemas de esos caracteres 'compuestos' al final de la libreria despues de los fonemas de los caracteres simples
				phoneme = phonemeDictionary.query('z' - 97 + additionalOffset(character));
			//si por el contrario es un caracter simple y no es una 'ñ'
			else
				//buscamos el fonema usando su codigo ascii en la libreria como codigo hash
				phoneme = phonemeDictionary.query(text.charAt(position) - 97);
		}
		//con los resultados creamos la clase que devuelve el resultado del computo de esta clase	
		return new PhonemeComputationResult(phoneme, character.length());
	}
	
	public PhonemeComputationResult calculatePhonemes(String text, int initialPosition, int endPosition)
	{
		String phonemes = "[";
		int offset = 0;
		PhonemeComputationResult result;
		
		for (int i = initialPosition; i < endPosition; i += offset)
		{
			result = calculatePhoneme(text, i);
			offset = result.getOffset();
			phonemes += result.getPhoneme() + ",";
		}
		
		phonemes += ",";
		phonemes = phonemes.replaceAll(",,", "]");
		
		return new PhonemeComputationResult(phonemes, text.length());
	}
}

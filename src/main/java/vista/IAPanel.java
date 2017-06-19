package vista;

import static org.gillius.jalleg.binding.AllegroLibrary.*;

import drools.motorEmociones.Diccionario;
import drools.motorEmociones.MotorEmociones;
import drools.motorEmociones.MotorEmociones.Emociones;

public class IAPanel extends AnimatedPanel
{
	/**
	 * Clase delegada de proporcionar los fonemas en cada momento de la pronunciacion
	 */
	PhonemeDelegate phonemeDelegate;
	/**
	 * cadena que indica donde se encuentran los sprites
	 */
	protected String spritesFolder;
	/**
	 * objeto para comunicarse con la vista
	 */
	protected Controller controller;
	/**
	 * objeto para obtener las emociones dadas una frase
	 */
	protected MotorEmociones ruleEngine;
	/**
	 * Cadena de texto que representa la emocion con la que se pronuncia la frase
	 */
	protected String feeling;
	/**

	 * Construye un panel con una ia a animar
	 * @param x coordenada de origen en el eje x de este panel
	 * @param y coordenada de origen en el eje y de este panel
	 * @param parent ventana en el que esta contenido este panel
	 * @param spritesFolder carpeta en la que estan las imagenes que componen las animaciones
	 * @param sizeX tamaño horizontal del panel
	 * @param sizeY tamaño vertical del panel
	 */
	public IAPanel(float x, float y, Window parent, String spritesFolder, float sizeX, float sizeY, String configFile, Controller controller) 
	{
		super(x, y, parent, sizeX, sizeY, spritesFolder, 0.10f);
		this.spritesFolder = spritesFolder;
		phonemeDelegate = new PhonemeDelegate(configFile);
		//TODO poner de forma correcta cuando esten el resto de las imagenes
		elements[0] = sprites[ficheroIndice.get((spritesFolder+"INICIAL.bmp").replace('/', '\\'))];
		this.controller = controller;
		//iniciamos el motor de reglas
		ruleEngine = new MotorEmociones();
	}
	
	public void initPhraseInformation(String phrase)
	{
		String preparedPhrase, phonemes;
		if(!active)
		{
			preparedPhrase = phonemeDelegate.preparePhrase(phrase);
			phonemes = phonemeDelegate.calculatePhonemes(preparedPhrase, 0, preparedPhrase.length()).getPhoneme();
			ruleEngine.insertaFrase(phrase);
			while(ruleEngine.getSentimientoDeFrase(phrase) == null);
			this.feeling = ruleEngine.getSentimientoDeFrase(phrase).toString();
			controller.registerInfoPhrase(preparedPhrase, this.feeling, phonemes);
			//feelings = algo;
			currentPhrase = preparedPhrase;
		}
	}
	/**
	 * Cambia el sentimiento con el que se pronuncia la frase
	 * @param feeling el nuevo sentimiento con el que se pronunciara la frase
	 */
	public void handleFeeling(String feeling)
	{
		if(!active)
		{
			controller.registerFeelingOnly(feeling);
			this.feeling = feeling;
		}
	}
	
	public void activate()
	{
		if(!active && currentPhrase != null)
		{
			currentCharacter = 0;
			active = true;
		}
	}
	
	public void update(ALLEGRO_DISPLAY display, float deltaTime)
	{
		PhonemeComputationResult result;
		String spriteName = "";
		//si se ha mandado realizar una animación
		if(active)
		{
			//se resta el tiempo que ha transcurrido
			currentTime -= deltaTime;
			//si toca animar
			if(currentTime <= 0f)
			{
				if(currentCharacter < currentPhrase.length())
				{
					result = phonemeDelegate.calculatePhoneme(currentPhrase, currentCharacter);
					spriteName = spritesFolder+this.feeling+"_"+result.getPhoneme();
					elements[0] = sprites[ficheroIndice.get((spriteName).replace('/', '\\'))];
					currentCharacter += result.getOffset();
				}
				else if(currentCharacter == currentPhrase.length())
				{
					elements[0] = sprites[ficheroIndice.get((spritesFolder+"INICIAL.bmp").replace('/', '\\'))];
					currentCharacter += 10;
				}
				if(currentCharacter <= currentPhrase.length())
					currentTime = animationTime;
				else
					active = false;
			}
		}
	}

	public void selectSelectedByClick(float x, float y)
	{}

	public void initQuadTree() 
	{}

	public void addWordToRuleEngine(String word, String feeling2)
	{
		this.ruleEngine.insertaPalabra(word, feeling2);
	}

	public void delWordFromRuleEngine(String word)
	{
		this.ruleEngine.eliminaPalabra(word);
	}

	public void flipFeeling(String inFeeling, String outFeeling)
	{
		this.ruleEngine.cambiaSentimientos(outFeeling, inFeeling);	
	}

}

package vista;

import java.io.File;
import java.util.Hashtable;

import org.gillius.jalleg.binding.AllegroLibrary.ALLEGRO_DISPLAY;

public abstract class AnimatedPanel extends Panel
{
	/**
	 * Imagenes que componen las posibles animaciones
	 */
	protected Drawable[] sprites;
	/**
	 * cantidad de tiempo que dura cada transicion de la animacion
	 */
	protected float animationTime;
	/**
	 * variable que cuenta la cantidad de tiempo de animacion que ha transcurrido
	 */
	protected float currentTime;
	/**
	 * variable que guarda el indice por el que se encuentra dentro de la pronunciacion
	 */
	protected int currentCharacter;
	/**
	 * variable que indica si el panel esta siendo animado o no
	 */
	protected boolean active;
	/**
	 * variable que guarda la frase que esta pronunciando la ia
	 */
	protected String currentPhrase;
	/**
	 * variable que dado unos nombres de fichero indica en que posicion de la variable sprites estan contenidos
	 */
	protected Hashtable<String, Integer> ficheroIndice;

	/**
	 * Funcion que dado un conjunto de fichero que deben de ser imagenes compatibles con ALLEGRO los convierte e introduce en 
	 * la variable de la clase sprites
	 * @param files array de fichero que deben de tener un formato compatible con allegro
	 */
	private void initSprites(File[] files)
	{
		//si no hay ficheros entonces lanzamos excepcion
		if(files.length == 0)
			throw new Error("Fallo en la lectura de los sprites, carpeta vacía.");
		//creamos la hash table
		ficheroIndice = new Hashtable<String, Integer>();
		//creamos el array de las imagenes que componen las animaciones
		sprites = new Drawable[files.length];
		//para cada fichero
		for (int i = 0; i < files.length; i++) 
		{
			//lo convertimos en un objeto drawable y lo guardamos
			sprites[i] = new Drawable(x, y, sizeX, sizeY, files[i].toString()); 
			//guardamos el indice en el array en la hash table
			ficheroIndice.put(files[i].toString(), i);
		}
	}
	
	/**
	 * Construye un panel animado
	 * @param x coordenada de origen en el eje x de este panel
	 * @param y coordenada de origen en el eje y de este panel
	 * @param parent ventana en el que esta contenido este panel
	 * @param sizeX tamaño horizontal del panel
	 * @param sizeY tamaño vertical del panel
	 * @param spritesFolder direccion de la carpeta que contienen las imagenes que componen las animaciones
	 * @param animationTime cantidad de tiempo de animacion
	 */
	public AnimatedPanel(float x, float y, Window parent, float sizeX, float sizeY, String spritesFolder, float animationTime) 
	{
		//mandamos las variables necesarias al constructor de la clase padre
		super(x, y, 1, parent, sizeX, sizeY);	
		//iniciamos las imagenes de las animaciones
		initSprites(new File(spritesFolder).listFiles());
		//establecemos el tiempo de animacion
		this.animationTime = animationTime;
		//iniciamos variables de control
		currentTime = 0f;
		currentCharacter = 0;
		active = false;
		currentPhrase = "";
		
	}

	public void destroy()
	{
		super.destroy();
		for (int i = 0; i < sprites.length; i++) 
		{
			sprites[i].destroy();
		}
		if(elements[0] != null)
			elements[0].destroy();
	}
	
	protected void buildPanel()
	{
		//TODO uSAR
		//TODO realizar la construccion del panel estableciendo la imagen por defecto
	}
	
	/**
	 * Funcion que itera los elementos del panel
	 * @param deltaTime cantidad de tiempo que ha pasado desde la ultima vez que se itero
	 */
	public abstract void update(ALLEGRO_DISPLAY display, float deltaTime);
	/**
	 * Activa la animacion del robot, es necesario haber hecho antes una llamada a initPhraseInformation
	 */
	public abstract void activate();
	/**
	 * Inicia la informacion relacionada con la ia
	 * @param phrase frase con la que iniciar la informacion relacionada con la animacion
	 */
	public abstract void initPhraseInformation(String phrase);
}

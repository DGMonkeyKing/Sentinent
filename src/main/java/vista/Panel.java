package vista;

import static org.gillius.jalleg.binding.AllegroLibrary.*;

public abstract class Panel
{
	/**
	 * Representa la coordenada x de origen del panel 
	 */
	protected float x;
	/**
	 * Representa la coordenada y de origen del panel
	 */
	protected float y;
	/**
	 * Representa el tamaño en el eje X del panel
	 */
	protected float sizeX;
	/**
	 * Representa el tamaño en el eje Y del panel
	 */
	protected float sizeY;
	/**
	 * Coleccion de elementos dibujables del panel
	 */
	protected Drawable[] elements;
	/**
	 * Ventana que contiene este panel
	 */
	protected Window parent;
	/**
	 * Elemento encargado de recoger los eventos del raton y llamar a los responsables
	 */
	protected QuadTree collision;
	
	/**
	 * Construye un panel 
	 * @param x coordenada x de origen del panel
	 * @param y coordenada y de origen del panel
	 * @param amountDrawable cantidad de elementos dibujables de un panel
	 * @param parent ventana en la se encuentra este panel
	 * @param sizeX tamaño horizontal del panel
	 * @param sizeY tamaño vertical del panel
	 */
	public Panel(float x, float y, int amountDrawable, Window parent, float sizeX, float sizeY)
	{
		//asociamos las coordenadas de origen del panel
		this.x = x;
		this.y = y;
		//creamos un array que contenga todos los elementos dibujables del panel
		elements = new Drawable[amountDrawable];
		//asociamos la ventana en la que se encuentra
		this.parent = parent;
		//establecemos el tamaño
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		//creamos la estructura para vincular los clicks a su elemento correspondiente
		collision = new QuadTree(x, y, sizeX, sizeY);
	}
	
	/**
	 * Dibuja todos los elementos que se tienen que dibujar en el panel
	 * @param display objeto necesario para realizar la operacion de dibujado
	 */
	public void draw(ALLEGRO_DISPLAY display)
	{
		for (int i = 0; i < elements.length; i++) 
		{
			elements[i].draw();
		}
	}
	
	/**
	 * Funcion encargada de eliminar los recursos de ALLEGRO del panel
	 */
	public void destroy()
	{
		for (int i = 0; i < elements.length; i++) 
		{
			if(elements[i] != null)
				elements[i].destroy();
		}	
	}
	
	/**
	 * Realiza la construccion del panel
	 */
	protected abstract void buildPanel();
	
	/**
	 * Función abstracta para iniciar la estructura del QuadTree con los elementos sensibles a la actividad del raton
	 */
	public abstract void initQuadTree();
	/**
	 * Funcion que maneja el evento de raton sobre el panel
	 * @param x coordenada x del click del raton
	 * @param y coordenada y del click del raton
	 */
	public abstract void selectSelectedByClick(float x, float y);
	
}

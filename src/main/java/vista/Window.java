package vista;


import static org.gillius.jalleg.binding.AllegroLibrary.*;

import org.gillius.jalleg.binding.ALLEGRO_EVENT;

import sun.misc.Lock;

public class Window 
{

	private ALLEGRO_DISPLAY display;
	private ALLEGRO_EVENT_QUEUE eventsQueue;
	private ALLEGRO_BITMAP icon;
	private ALLEGRO_EVENT event;
	private boolean mustExit;
	private long refreshRate;
	protected Panel debug; //TODO eliminar todo lo referente de esta clase
	protected IAPanel ia;
	protected Panel test; //TODO eliminar todo lo referente de esta clase
	protected float x;
	protected float y;
	protected int sizeX;
	protected int sizeY;
	protected Controller controller;
	protected Lock drawLock;
	
	public Window()
	{
		boolean successfully = true;
		
		//Iniciamos los diferentes componentes necesarios para la aplicacion
		successfully &= al_install_system(ALLEGRO_VERSION_INT, null);
		successfully &= al_install_audio();
		successfully &= al_init_acodec_addon();
		successfully &= al_init_image_addon();
		successfully &= al_init_font_addon();
		
		//Iniciamos las dimensiones de la ventana
		sizeX = 1024;
		sizeY = 1024;
		
		//establecemos el titulo del nuevo display
		al_set_app_name("Sentiment: La IA sentimental.");
		
		//Establecemos que el display será a pantalla completa
		al_set_new_display_flags(ALLEGRO_WINDOWED);
		
		//Creamos el display con el tamaño sizeX x sizeY
        display = al_create_display(sizeX, sizeY);

        //establecemos el icono para la aplicacion
        icon = al_load_bitmap("assets/app_icon/lazy-bot.bmp");
        al_set_display_icon(display, icon);
        
        //Si no todos los sistemas han sido instalados correctamente informamos
        if(!successfully)
        {
        	//informamos sobre la naturaleza del fallo
        	System.err.println("Fallo al inicial los distintos modulos de la aplicación");
        	//cerramos los recursos usados por la aplicacion
        	closeApplication();
        }
        
		//inicializamos los eventos de usuario
        eventsQueue = al_create_event_queue();
		al_register_event_source(eventsQueue, al_get_display_event_source(display));
        
		//iniciamos el evento para acelerar el proceso de chequeo de eventos
		event = new ALLEGRO_EVENT();
		
		//iniciamos la variable que indicara cuando la aplicacion debe dejar de ejecutarse y salir
		mustExit = false;
		
		//iniciamos la tasa de refresco de la aplicacion
		refreshRate = (long)((1.0/60.0)*1000);

		//iniciamos las coordenadas
		x = 0;
		y = 0;
		
		//iniciamos el controlador
		controller = new Controller(this);
		
		//iniciamos los paneles en los que se divide la aplicacion
		ia = new IAPanel(x, y, this, "assets/sprites/", sizeX, sizeY, "assets/config/phonemes.cfg", controller);
		
		//iniciamos el candado
		drawLock = new Lock();
	}
	
	public void checkEvents()
	{
		while(!al_is_event_queue_empty(eventsQueue))
		{
			al_get_next_event(eventsQueue, event);
			if(event.type == ALLEGRO_EVENT_DISPLAY_CLOSE)
			{
				closeApplication();
				break;
			}
		}
	}
	
	public void draw()
	{
		al_set_target_backbuffer(display);
		al_clear_to_color(al_map_rgb_f(1.0f, 1.0f, 1.0f));
		
		ia.draw(display);
		
		al_flip_display();
	}
	
	public void update()
	{
		ia.update(display, (float)refreshRate/1000.0f);
	}
	
	public void prueba()
	{
		ALLEGRO_BITMAP bitmap;
		bitmap = al_create_bitmap(50, 50);
		
		al_set_target_bitmap(bitmap);
		al_clear_to_color(al_map_rgb_f(1.0f, 0.0f, 0.0f));
		
		al_set_target_backbuffer(display);
		
		al_clear_to_color(al_map_rgb_f(0.0f, 1.0f, 0.0f));
		
		al_draw_bitmap(bitmap, 200, 200, 0);
		
		al_flip_display();
		
		al_destroy_bitmap(bitmap);
	}
	
	public void closeApplication()
	{
		try
		{
			drawLock.lock();
				ia.destroy();
				al_set_target_bitmap(null);
		        al_destroy_display(display);
		        al_destroy_event_queue(eventsQueue);
		        al_destroy_bitmap(icon);
		        al_uninstall_audio();
		        al_shutdown_font_addon();
		        al_shutdown_image_addon();
		        al_uninstall_system();
		        mustExit = true;
		        controller.quitGUI();
	        drawLock.unlock();
	    }
		catch(InterruptedException ie)
		{
			ie.printStackTrace();
		}
	}
	
	public void initAndstartAnimation(String phrase)
	{
		ia.initPhraseInformation(phrase);
		ia.activate();
	}
	
	public void initAndStartAnimationWithFeeling(String phrase, String feeling)
	{
		ia.initPhraseInformation(phrase);
		ia.handleFeeling(feeling);
		ia.activate();
	}
	
	public void execute()
	{
        try 
        {		
			while(!mustExit)
			{
				update();
				drawLock.lock();
					if(!mustExit)
						draw();
				drawLock.unlock();
				checkEvents();
				Thread.sleep(refreshRate);
			}
		}
        catch (InterruptedException e)
        {
        	System.err.println("Excepcion al dormir durante el refresco");
		}
	}

	public void addWordToRuleEngine(String word, String feeling) 
	{
		ia.addWordToRuleEngine(word, feeling);
	}

	public void delWordFromRuleEngine(String word) 
	{
		ia.delWordFromRuleEngine(word);		
	}

	public void flipFeeling(String inFeeling, String outFeeling) 
	{
		ia.flipFeeling(inFeeling, outFeeling);
	}
	
}

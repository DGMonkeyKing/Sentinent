package vista;

import static org.gillius.jalleg.binding.AllegroLibrary.*;

public class Drawable {
	protected float x;
	protected float y;
	protected float sizeX;
	protected float sizeY;
	protected ALLEGRO_BITMAP image;
	protected boolean deleted;
	
	public Drawable(float x, float y, float sizeX, float sizeY, String imageURL)
	{
		int imageSizeX, imageSizeY;
		this.x = x;
		this.y = y;
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		deleted = false;
		
		image = al_create_bitmap((int)sizeX, (int)sizeY);
		ALLEGRO_BITMAP oldBitmap = al_load_bitmap(imageURL);
		
		imageSizeX = al_get_bitmap_width(oldBitmap);
		imageSizeY = al_get_bitmap_height(oldBitmap);

		if(imageSizeX != sizeX || imageSizeY != sizeY)
		{
			al_set_target_bitmap(image);
			al_draw_scaled_bitmap(oldBitmap, x, y, imageSizeX, imageSizeY, x, y, sizeX, sizeY, 0);
		}
		al_destroy_bitmap(oldBitmap);
	}
	
	public void draw()
	{
		//dibujamos el elemento en la parte designada del panel asociado
		al_draw_bitmap(image, x, y, 0);
	}
	
	public void destroy()
	{
		if(!deleted)
		{
			al_destroy_bitmap(image);
			deleted = true;
		}
	}
	
}

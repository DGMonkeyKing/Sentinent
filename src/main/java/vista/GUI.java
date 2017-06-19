package vista;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class GUI extends JFrame
{
	private static final long serialVersionUID = 1L;
	protected IATestPanel ia;
	protected DebugPanel debug;
	protected UserPhrase input;
	
	protected JPanel createContentPane(int sizeX, int sizeY, Controller controller)
	{
		//declaramos e iniciamos algunas propiedades comunes a los tres paneles
		Font selectedFont = new Font("Arial", Font.BOLD, 15);
		Color selectedColor = new Color(255, 250, 231);
		//declaramos el panel principal de la ventana
		JPanel content;
		//lo inicializamos con el layout GridBag
		content = new JPanel(new GridBagLayout());
		//declaramos e iniciamos el layout de las restricciones de los componentes del panel principal
		GridBagConstraints c = new GridBagConstraints();
		//establecemos que el primer elemento se redimensionara tanto horizontal como verticalmente
		c.fill = GridBagConstraints.BOTH;
		//establecemos la posicion del componente
		c.gridx = 0;
		c.gridy = 0;
		//establecemos la proporcion que se redimensionara de forma vertical y horizontal
		c.weightx = 1.0;
		c.weighty = 1.0;
		//establecemos la cantidad de celdas que ocupará de forma horizontal
		c.gridwidth = 2;

		//creamos el componente
		input = new UserPhrase(sizeX, 90, selectedFont, selectedColor, controller, this);
		//lo añadimos con las restricciones fijadas anteriormente
		content.add(input, c);
		
		//establecemos que este componente se pondra debajo del anterior componente en la parte izquierda (0,1)
		c.gridy = 1;
		//establecemos que este componente solo ocupara una celda de forma horizontal
		c.gridwidth = 1;
		
		//creamos el componente
		debug = new DebugPanel(sizeX / 2, sizeY - 90, selectedFont, selectedColor, controller);
		//lo añadimos con las restricciones fijadas
		content.add(debug, c);
		
		//establecemos que este componente se pondra a la derecha del anterior componente
		c.gridx = 1;
		
		//creamos el componente
		ia = new IATestPanel(sizeX / 2, sizeY - 90, selectedFont, selectedColor, controller);
		//lo añadimos con las restricciones fijadas
		content.add(ia, c);
		
		//establecemos la configuración del panel 
		//establecemos que es opaco
		content.setOpaque(true);
		//visible
		content.setVisible(true);
		//y ocupa toda la ventana
		content.setSize(getSize());
		
		//se devuelve
		return content;
	}
	
	public GUI(Controller controller)
	{
		super("Controles");
		//definimos el contenido del panel principal
		setContentPane(this.createContentPane(800, 500, controller));
		//configuramos el frame
		setUndecorated(false);
		//setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		addWindowListener(new WindowAdapter(){
		    public void windowClosing(WindowEvent arg0) {
				int n;
				//Lanzamos ventana de confimacion
				n = JOptionPane.showConfirmDialog(null, "¿Desea salir?");
				if(n == JOptionPane.YES_OPTION)
				{
					controller.quitAllegro();
					System.exit(0);
				}
		    }
		                 
		});
		pack();
		setResizable(false);
	}
	
	protected void setPhrase(String phrase)
	{
		this.debug.fillPhrase(phrase);
	}
	
	protected void setFeelings(String feelings)
	{
		this.debug.fillFeelings(feelings);
	}
	
	protected void setPhonemes(String phonemes)
	{
		this.debug.fillPhonemes(phonemes);
	}
	
	public void setanimationInfo(String phrase, String feelings, String phonemes)
	{
		setPhrase(phrase);
		setFeelings(feelings);
		setPhonemes(phonemes);
	}
	
	public String getAnimationFeeling()
	{
		return debug.getFeeling();
	}
	
	public void quit()
	{
		System.exit(0);
	}
	
}

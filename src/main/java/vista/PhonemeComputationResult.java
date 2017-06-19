package vista;

public class PhonemeComputationResult 
{
	/**
	 * nombre de la imagen del fonema calculado por la clase PhonemeDelegate
	 */
	private String phonemeImage;
	/**
	 * offset a desplazar el puntero del texto
	 */
	private int textOffset;
	/**
	 * Construye la clase que encapsula el resultado del computo de la clase PhonemeDelegate
	 * @param phoneme nombre de la imagen del fonema 
	 * @param offset desplazamiento del punto del texto
	 */
	public PhonemeComputationResult(String phoneme, int offset)
	{
		phonemeImage = phoneme;
		textOffset = offset;
	}
	/**
	 * Devuelve el nombre de la imagen del fonema
	 * @return el nombre de la imagen del fonema
	 */
	public String getPhoneme()
	{
		return phonemeImage;
	}
	/**
	 * Devuelve el desplazamiento del puntero del texto
	 * @return el desplazamiento del puntero del texto
	 */
	public int getOffset()
	{
		return textOffset;
	}
}

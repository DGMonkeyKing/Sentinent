package vista;

public class Controller 
{
	protected Window ia;
	protected GUI gui;
	
	public Controller(Window iaWindow)
	{
		ia = iaWindow;
		gui = new GUI(this);
	}
	
	public void animatePhrase(String phrase)
	{
		ia.initAndstartAnimation(phrase);
	}
	
	public void animatePhraseWithFeeling(String phrase, String feeling)
	{
		ia.initAndStartAnimationWithFeeling(phrase, feeling);
	}
	
	public void registerInfoPhrase(String phrase, String feelings, String phonemes)
	{
		gui.setanimationInfo(phrase, feelings, phonemes);
	}
	
	public void registerFeelingOnly(String feelings)
	{
		gui.setFeelings(feelings);
	}
	
	public void quitAllegro()
	{
		ia.closeApplication();
	}
	
	public void quitGUI()
	{
		gui.quit();
	}

	public void addWordWithFeeling(String word, String feeling) 
	{
		ia.addWordToRuleEngine(word, feeling);
	}

	public void delWord(String word) 
	{
		ia.delWordFromRuleEngine(word);
	}

	public void flipFeeling(String inFeeling, String outFeeling)
	{
		ia.flipFeeling(inFeeling, outFeeling);
	}
	
}

package vista;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class DebugPanel extends JPanel implements ActionListener
{
	private static final long serialVersionUID = 1L;
	protected JTextArea phrase, feelings, phonemes;
	protected JRadioButton[] feelingCheckBox;
	protected ButtonGroup group;
	protected Controller controller;
	protected String feeling;
	
	public DebugPanel(int sizeX, int sizeY, Font selectedFont, Color selectedColor, Controller controller)
	{
		super(new GridBagLayout());
		
		this.controller = controller;
		feeling = "No alterar";
		
		GridBagConstraints c = new GridBagConstraints();
		JScrollPane scrollPhrase, scrollFeelings, scrollPhonemes;
		JLabel phraseLabel, feelingsResultLabel, phonemeResultLabel, feelingsLabel;
		
		phraseLabel = new JLabel("Frase introducida");
		phraseLabel.setFont(selectedFont);
		phraseLabel.setBackground(selectedColor);
		phraseLabel.setPreferredSize(new Dimension(sizeX, 20));
		
		c.gridx = 0;
		c.gridy = 0;
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 0.0;
		c.weighty = 0.0;
		
		add(phraseLabel, c);
		
		phrase = new JTextArea("Frase no disponible");
		phrase.setEditable(false);
		phrase.setLineWrap(true);
		phrase.setFont(selectedFont);
		phrase.setBackground(selectedColor);
		phrase.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		scrollPhrase = new JScrollPane(phrase);
		scrollPhrase.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPhrase.setPreferredSize(new Dimension(sizeX, 50));
		
		c.gridy = 1;
		c.weightx = 1;
		c.weighty = 0.34;
		
		add(scrollPhrase, c);
		
		feelingsResultLabel = new JLabel("Sentimientos de las palabras de la frase");
		feelingsResultLabel.setFont(selectedFont);
		feelingsResultLabel.setBackground(selectedColor);
		feelingsResultLabel.setPreferredSize(new Dimension(sizeX, 20));
		
		c.gridy = 2;
		c.weightx = 0;
		c.weighty = 0;
		
		add(feelingsResultLabel, c);
		
		feelings = new JTextArea("Sentimientos no disponibles");
		feelings.setEditable(false);
		feelings.setLineWrap(true);
		feelings.setFont(selectedFont);
		feelings.setBackground(selectedColor);
		feelings.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		scrollFeelings = new JScrollPane(feelings);
		scrollFeelings.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollFeelings.setPreferredSize(new Dimension(sizeX, 50));
		
		c.gridy = 3;
		c.weightx = 1;
		c.weighty = 0.33;
		
		add(scrollFeelings, c);
		
		phonemeResultLabel = new JLabel("Fonemas de la frase");
		phonemeResultLabel.setFont(selectedFont);
		phonemeResultLabel.setBackground(selectedColor);
		phonemeResultLabel.setPreferredSize(new Dimension(sizeX, 20));
		
		c.gridy = 4;
		c.weightx = 0;
		c.weighty = 0;
		
		add(phonemeResultLabel, c);
		
		phonemes = new JTextArea("Fonemas no disponibles");
		phonemes.setEditable(false);
		phonemes.setLineWrap(true);
		phonemes.setFont(selectedFont);
		phonemes.setBackground(selectedColor);
		phonemes.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		scrollPhonemes = new JScrollPane(phonemes);
		scrollPhonemes.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPhonemes.setPreferredSize(new Dimension(sizeX, 50));
		
		c.gridy = 5;
		c.weightx = 1;
		c.weighty = 0.33;
		
		add(scrollPhonemes, c);
		
		feelingsLabel = new JLabel("Sentimientos disponibles");
		feelingsLabel.setFont(selectedFont);
		feelingsLabel.setBackground(selectedColor);
		feelingsLabel.setPreferredSize(new Dimension(sizeX, 20));
		
		c.gridy = 6;
		c.weightx = 0;
		c.weighty = 0;
		
		add(feelingsLabel, c);
		
		buildCheckBoxList(c, 7, selectedFont, selectedColor, sizeX);
		
		setBackground(selectedColor);
		setPreferredSize(new Dimension(sizeX, sizeY));
		setVisible(true);
		setOpaque(true);
		
	}
	
	protected void buildCheckBoxList(GridBagConstraints c, int y, Font font, Color color, int sizeX)
	{
		File[] feelingList = new File("assets/dics/").listFiles();
		feelingCheckBox = new JRadioButton[feelingList.length + 1];
		ButtonGroup group = new ButtonGroup();
		for (int i = 0; i < feelingList.length; i++) 
		{
			
			feelingCheckBox[i] = new JRadioButton(feelingList[i].toString().replaceAll(".txt", "").replaceAll(".*\\\\.*\\\\", ""));
			feelingCheckBox[i].setFont(font);
			feelingCheckBox[i].setBackground(color);
			feelingCheckBox[i].setPreferredSize(new Dimension(sizeX, 20));
			feelingCheckBox[i].setSelected(false);
			feelingCheckBox[i].addActionListener(this);
			
			c.gridy = y++;
			c.weightx = 0;
			c.weighty = 0;
			
			add(feelingCheckBox[i], c);
			group.add(feelingCheckBox[i]);
		}
		
		feelingCheckBox[feelingList.length] = new JRadioButton("No alterar");
		feelingCheckBox[feelingList.length].setFont(font);
		feelingCheckBox[feelingList.length].setBackground(color);
		feelingCheckBox[feelingList.length].setPreferredSize(new Dimension(sizeX, 20));
		feelingCheckBox[feelingList.length].setSelected(true);
		feelingCheckBox[feelingList.length].addActionListener(this);
		
		
		c.gridy = y++;
		c.weightx = 0;
		c.weighty = 0;
		
		add(feelingCheckBox[feelingList.length], c);
		group.add(feelingCheckBox[feelingList.length]);
		
	}
	
	public void fillPhrase(String phrase)
	{
		this.phrase.setText(phrase);
	}
	
	public void fillFeelings(String feelings)
	{
		this.feelings.setText(feelings);
	}
	
	public void fillPhonemes(String phonemes)
	{
		this.phonemes.setText(phonemes);
	}

	public void actionPerformed(ActionEvent e) 
	{
		for (int j = 0; j < feelingCheckBox.length; j++) 
		{
			if(e.getSource().equals(feelingCheckBox[j]))
				feeling = feelingCheckBox[j].getText();
		}
	}
	
	public String getFeeling()
	{
		String result = "";
		if(!feeling.equalsIgnoreCase("No alterar"))
			result = feeling.toUpperCase();
		return result;
	}

}

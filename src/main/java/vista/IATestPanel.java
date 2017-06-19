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
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class IATestPanel extends JPanel implements ActionListener
{
	private static final long serialVersionUID = 1L;
	protected JTextField newWord;
	protected JButton addWordButton, delWordButton, flipFeelingButton;
	protected JRadioButton[] feelingCheckBox;
	protected ButtonGroup group;
	protected JComboBox<String> feelingsIn, feelingsOut;
	protected Controller controller;
	protected String feeling;
	protected String in, out;

	public IATestPanel(int sizeX, int sizeY, Font selectedFont, Color selectedColor, Controller controller) 
	{
		super(new GridBagLayout());
		
		this.controller = controller;
		feeling = "";
		in = "CONFUNDIDO";
		out = "CONFUNDIDO";
		
		int temporalY;
		JLabel newWordLabel, newFeelingLabel, inFeelingLabel, outFeelingLabel;
		JPanel buttonsPanel, extraSpacePanel, panel, panel2, flipButtonPanel;
		String[] feelingNames = getFeelingNames();
		GridBagConstraints c = new GridBagConstraints();
		
		
		newWordLabel = new JLabel("Nueva palabra: ");
		newWordLabel.setFont(selectedFont);
		newWordLabel.setBackground(selectedColor);
		newWordLabel.setPreferredSize(new Dimension(sizeX, 20));
		
		c.gridx = 0;
		c.gridy = 0;
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 0.0;
		c.weighty = 0.0;
		
		add(newWordLabel, c);
		
		newWord = new JTextField("");
		newWord.setEditable(true);
		newWord.setFont(selectedFont);
		newWord.setBackground(selectedColor);
		newWord.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		newWord.setPreferredSize(new Dimension(sizeX, 20));
		
		c.gridx = 1;
		c.weightx = 1;
		c.weighty = 0;
		
		add(newWord, c);
		
		newFeelingLabel = new JLabel("Sentimiento de la nueva palabra");
		newFeelingLabel.setFont(selectedFont);
		newFeelingLabel.setBackground(selectedColor);
		newFeelingLabel.setPreferredSize(new Dimension(sizeX, 20));
		
		c.gridx = 0;
		c.gridy = 1;
		c.weightx = 0;
		c.weighty = 0;
		c.gridwidth = 2;
		
		add(newFeelingLabel, c);
		
		c.gridy = 2;
		
		buildCheckBoxList(c, c.gridy, selectedFont, selectedColor, sizeX);
		
		temporalY = c.gridy;
			
			buttonsPanel = new JPanel(new GridBagLayout());
			
			c.gridx = 0;
			c.gridy = 0;
			c.gridwidth = 1;
			c.weightx = 1.0;
			c.weighty = 0.0;
			c.fill = GridBagConstraints.BOTH;
			
			extraSpacePanel = new JPanel();
			extraSpacePanel.setOpaque(true);
			extraSpacePanel.setVisible(true);
			extraSpacePanel.setBackground(selectedColor);
			
			buttonsPanel.add(extraSpacePanel, c);
			
			c.gridx = 3;
			c.weightx = 0.0;
			c.weighty = 0.0;
			
			addWordButton = new JButton("Añadir");
			addWordButton.setEnabled(true);
			addWordButton.setFont(selectedFont);
			addWordButton.setBackground(selectedColor);
			addWordButton.addActionListener(
			new ActionListener() 
			{
				public void actionPerformed(ActionEvent e)
				{
					if(feeling != "" && newWord.getText() != "")
						controller.addWordWithFeeling(newWord.getText(), feeling);
					else
					{
						JOptionPane.showMessageDialog(null, "Introduzca los datos");
					}
				}
			});
			
			buttonsPanel.add(addWordButton, c);
			
			panel = new JPanel();
			panel.setOpaque(true);
			panel.setVisible(true);
			panel.setBackground(selectedColor);
			
			c.gridx = 2;
			
			buttonsPanel.add(panel, c);
			
			c.gridx = 1;
			
			delWordButton = new JButton("Eliminar");
			delWordButton.setEnabled(true);
			delWordButton.setFont(selectedFont);
			delWordButton.setBackground(selectedColor);
			delWordButton.addActionListener(
			new ActionListener() 
			{				
				public void actionPerformed(ActionEvent e) 
				{
					if(newWord.getText() != "")
						controller.delWord(newWord.getText());
					else
					{
						JOptionPane.showMessageDialog(null, "Introduzca la palabra");
					}
				}
			});
			
			buttonsPanel.add(delWordButton, c);
			
			buttonsPanel.setOpaque(true);
			buttonsPanel.setVisible(true);
			buttonsPanel.setBackground(selectedColor);
			buttonsPanel.setPreferredSize(new Dimension(sizeX, 30));
			
		c.gridx = 0;
		c.gridwidth = 2;
		c.gridy = temporalY;
		c.weightx = 0.0;
		c.weighty = 0.0;
		c.fill = GridBagConstraints.BOTH;
		
		add(buttonsPanel, c);
		
		inFeelingLabel = new JLabel("Selecciona el sentimiento a aplicar");
		inFeelingLabel.setFont(selectedFont);
		inFeelingLabel.setBackground(selectedColor);
		inFeelingLabel.setPreferredSize(new Dimension(sizeX, 20));
		
		c.gridy++;
		
		add(inFeelingLabel, c);
		
		feelingsIn = new JComboBox<String>(feelingNames);
		feelingsIn.setSelectedIndex(0);
		feelingsIn.setPreferredSize(new Dimension(sizeX, 30));
		feelingsIn.addActionListener(new ActionListener() 
		{	
			public void actionPerformed(ActionEvent e) {
				JComboBox<String> cb = (JComboBox<String>)e.getSource();
				String name = (String)cb.getSelectedItem();
				if(name.equalsIgnoreCase("enfadado"))
					in = "ENFADADO";
				else if(name.equalsIgnoreCase("confundido"))
					in = "CONFUNDIDO";
				else if(name.equalsIgnoreCase("felicidad"))
					in = "FELIZ";
				else if(name.equalsIgnoreCase("sorprendido"))
					in = "SORPRENDIDO";
				else if(name.equalsIgnoreCase("triste"))
					in = "TRISTE";
			}
		});
		
		c.gridy++;
		
		add(feelingsIn, c);
		
		outFeelingLabel = new JLabel("Selecciona el sentimiento a sustituir");
		outFeelingLabel.setFont(selectedFont);
		outFeelingLabel.setBackground(selectedColor);
		outFeelingLabel.setPreferredSize(new Dimension(sizeX, 20));
		
		c.gridy++;
		
		add(outFeelingLabel, c);
		
		feelingsOut = new JComboBox<String>(feelingNames);
		feelingsOut.setSelectedIndex(0);
		feelingsOut.setPreferredSize(new Dimension(sizeX, 30));
		feelingsOut.addActionListener(new ActionListener() 
		{	
			public void actionPerformed(ActionEvent e) {
				JComboBox<String> cb = (JComboBox<String>)e.getSource();
				String name = (String)cb.getSelectedItem();
				if(name.equalsIgnoreCase("enfadado"))
					out = "ENFADADO";
				else if(name.equalsIgnoreCase("confundido"))
					out = "CONFUNDIDO";
				else if(name.equalsIgnoreCase("felicidad"))
					out = "FELIZ";
				else if(name.equalsIgnoreCase("sorprendido"))
					out = "SORPRENDIDO";
				else if(name.equalsIgnoreCase("triste"))
					out = "TRISTE";
			}
		});
		
		c.gridy++;
		
		add(feelingsOut, c);
		
		temporalY = c.gridy + 1;
		
			flipButtonPanel = new JPanel(new GridBagLayout());
		
			flipFeelingButton = new JButton("Intercambiar");
			flipFeelingButton.setEnabled(true);
			flipFeelingButton.setFont(selectedFont);
			flipFeelingButton.setBackground(selectedColor);
			flipFeelingButton.addActionListener(
			new ActionListener() 
			{				
				public void actionPerformed(ActionEvent e) 
				{
					if(in.equalsIgnoreCase("felicidad"))
						in = "FELIZ";
					if(out.equalsIgnoreCase("felicidad"))
						out = "FELIZ";
					controller.flipFeeling(in, out);
				}
			});
			
			c.gridx = 1;
			c.gridwidth = 1;
			c.gridy = 0;
			c.weightx = 0.0;
			c.weighty = 0.0;
			c.fill = GridBagConstraints.BOTH;
			
			flipButtonPanel.add(flipFeelingButton, c);
			
			panel2 = new JPanel();
			panel2.setOpaque(true);
			panel2.setVisible(true);
			panel2.setBackground(selectedColor);
			
			c.gridx = 0;
			c.weightx = 1;
			
			flipButtonPanel.add(panel2, c);
			
			flipButtonPanel.setOpaque(true);
			flipButtonPanel.setVisible(true);
			flipButtonPanel.setBackground(selectedColor);
			flipButtonPanel.setPreferredSize(new Dimension(sizeX, 30));
			
		c.gridx = 0;
		c.gridy = temporalY;
		c.gridwidth = 2;
		c.weightx = 1.0;
		c.weighty = 0.0;
		c.fill = GridBagConstraints.BOTH;
			
		add(flipButtonPanel, c);
		
		setBackground(selectedColor);
		setPreferredSize(new Dimension(sizeX, sizeY));
		setVisible(true);
		setOpaque(true);
		
	}

	protected String[] getFeelingNames()
	{
		File[] feelingList = new File("assets/dics/").listFiles();
		String[] names = new String[feelingList.length];
		for (int i = 0; i < names.length; i++)
		{
			names[i] = feelingList[i].toString().replaceAll(".txt", "").replaceAll(".*\\\\.*\\\\", "");
		}
		return names;
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
		
		c.gridy = y;
		
	}

	public void actionPerformed(ActionEvent e) 
	{
		for (int j = 0; j < feelingCheckBox.length; j++) 
		{
			if(e.getSource() == feelingCheckBox[j])
				feeling = feelingCheckBox[j].getText().toUpperCase();
		}
		if(feeling.equalsIgnoreCase("felicidad"))
			feeling = "FELIZ";
	}
	
}

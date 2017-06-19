package vista;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class UserPhrase extends JPanel
{
	private static final long serialVersionUID = 1L;
	protected JTextArea textInput;
	protected JButton sendButton, resetButton;
	protected Controller controller;
	protected GUI parent;
	
	public UserPhrase(int sizeX, int sizeY, Font selectedFont, Color selectedColor, Controller controller, GUI parent)
	{
		super(new GridBagLayout());
		
		this.controller = controller;
		this.parent = parent;
		
		GridBagConstraints c = new GridBagConstraints();

		JScrollPane scrollPane;
		JLabel phraseLabel;
		
		c.gridx = 0;
		c.gridy = 0;
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 0.0;
		c.weighty = 0.0;
		
		phraseLabel = new JLabel("Introduzca la frase a pronunciar:");
		phraseLabel.setFont(selectedFont);
		phraseLabel.setBackground(selectedColor);
		phraseLabel.setPreferredSize(new Dimension(sizeX, 20));
		add(phraseLabel, c);
		
		JPanel buttonsPanel, extraSpacePanel, panel;
		
			buttonsPanel = new JPanel(new GridBagLayout());
			
			c.gridx = 0;
			c.gridy = 0;
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
			
			sendButton = new JButton("Pronunciar");
			sendButton.setEnabled(true);
			sendButton.setFont(selectedFont);
			sendButton.setBackground(selectedColor);
			sendButton.addActionListener(
			new ActionListener() 
			{
				public void actionPerformed(ActionEvent e)
				{
					// TODO Auto-generated method stub
					String feeling = parent.getAnimationFeeling();
					if(feeling.equals(""))
						controller.animatePhrase(textInput.getText());
					else
						controller.animatePhraseWithFeeling(textInput.getText(), feeling);
				}
			});
			
			buttonsPanel.add(sendButton, c);
			
			panel = new JPanel();
			panel.setOpaque(true);
			panel.setVisible(true);
			panel.setBackground(selectedColor);
			
			c.gridx = 2;
			
			buttonsPanel.add(panel, c);
			
			c.gridx = 1;
			
			resetButton = new JButton("Resetear");
			resetButton.setEnabled(true);
			resetButton.setFont(selectedFont);
			resetButton.setBackground(selectedColor);
			resetButton.addActionListener(
			new ActionListener() 
			{				
				public void actionPerformed(ActionEvent e) 
				{
					textInput.setText("");
				}
			});
			
			buttonsPanel.add(resetButton, c);
			
			buttonsPanel.setOpaque(true);
			buttonsPanel.setVisible(true);
			buttonsPanel.setBackground(selectedColor);
			buttonsPanel.setPreferredSize(new Dimension(sizeX, 30));
			
		c.gridx = 0;
		c.gridy = 2;
		c.weightx = 0.0;
		c.weighty = 0.0;
		c.fill = GridBagConstraints.BOTH;
		
		add(buttonsPanel, c);
		
		c.gridy = 1;
		c.weightx = 1.0;
		c.weighty = 1.0;
		
		textInput = new JTextArea();
		textInput.setEditable(true);
		textInput.setLineWrap(true);
		textInput.setFont(selectedFont);
		textInput.setBackground(selectedColor);
		textInput.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		scrollPane = new JScrollPane(textInput);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setPreferredSize(new Dimension(sizeX, sizeY - phraseLabel.getPreferredSize().height - buttonsPanel.getPreferredSize().height));
		
		add(scrollPane, c);
		
		setBackground(selectedColor);
		setPreferredSize(new Dimension(sizeX, sizeY));
		setVisible(true);
		setOpaque(true);
		
	}
}

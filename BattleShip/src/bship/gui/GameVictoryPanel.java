package bship.gui;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import bship.logic.ShipPlacement;

public class GameVictoryPanel extends BattleShipGui 
{	
	public GameVictoryPanel(JFrame frame, JPanel lastPanel)
	{
		this.frame = frame;
		this.lastPanel = lastPanel;
		this.setBounds(0, 0, 1920, 1080);
		setLayout(null);
		
		JLabel facebookLogin = new JLabel(ImagesData.facebookShareIcon);
		facebookLogin.setBounds(1287, 835, 503, 196);
		add(facebookLogin);
		facebookLogin.addMouseListener(new MouseListener() 
		{
			@Override
			public void mouseClicked(MouseEvent event) {}

			@Override
			public void mouseEntered(MouseEvent event) {}

			@Override
			public void mouseExited(MouseEvent event) {}

			@Override
			public void mousePressed(MouseEvent event) 
			{
				String victoryMessage = "I " + CurrGameData.getAllyName() + " win battleship against " + CurrGameData.getOpponentName() + "! :)";
				FacebookLogin fbLogin = new FacebookLogin();
				fbLogin.post(victoryMessage);
				GameVictoryPanel.this.requestFocusInWindow(true);
			}

			@Override
			public void mouseReleased(MouseEvent event) {}
		});
		
		JLabel backgroundLabel = new JLabel(ImagesData.gameVictoryIcon);
		backgroundLabel.setBounds(0, 0, 1920, 1080);
		add(backgroundLabel);
		
		frame.getContentPane().add(this);
		lastPanel.setVisible(false);
		this.setVisible(true);
		addKeyListener(this);
		this.requestFocusInWindow(true);
	}
	
	@Override
	public void keyPressed(KeyEvent event)
	{
		if(event.getKeyCode() == KeyEvent.VK_ESCAPE)
			new Menu(this.frame, this);
	}

	@Override
	public void keyReleased(KeyEvent arg0) {}

	@Override
	public void keyTyped(KeyEvent arg0) {}

}

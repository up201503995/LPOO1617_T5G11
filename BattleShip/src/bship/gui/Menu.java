package bship.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Image;

import java.awt.Rectangle;
import java.awt.Font;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JLabel;


public class Menu extends BattleShipGui
{
	private JPanel menuPanel;
	private JButton btnMultiplayer;
	private JButton btnSingleplayer;
	private	JButton btnFacebookLogin;
	private	JButton btnExit;
	private Image backgroundImage;
	private JLabel label;

	public Menu(JFrame frame, JPanel lastPanel) 
	{
		this.frame = frame;
		this.lastPanel = lastPanel;
		menuPanel = new JPanel();
		menuPanel.setBounds(0, 0, 1920, 1080);
		frame.getContentPane().add(menuPanel);
		menuPanel.setLayout(null);
		
		btnSingleplayer = new JButton("SinglePlayer");
		btnSingleplayer.setFont(new Font("Comic Sans MS", Font.PLAIN, 35));
		btnSingleplayer.setBounds(701, 236, 459, 70);
		menuPanel.add(btnSingleplayer);
		
		btnMultiplayer = new JButton("MultiPlayer");
		btnMultiplayer.setFont(new Font("Comic Sans MS", Font.PLAIN, 35));
		btnMultiplayer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{
				new BattleShipServerLogin(frame, intermediate, menuPanel);
			}
		});
		btnMultiplayer.setBounds(701, 382, 459, 70);
		menuPanel.add(btnMultiplayer);
		
		btnExit = new JButton("Exit");
		btnExit.setFont(new Font("Comic Sans MS", Font.PLAIN, 35));
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{
				new ExitPanel(frame, Menu.this.menuPanel);	
			}
		});
		btnExit.setBounds(701, 524, 459, 70);
		menuPanel.add(btnExit);
		
		btnFacebookLogin = new JButton("Facebook Login");
		btnFacebookLogin.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				new FacebookLogin();
			}
		});
		btnFacebookLogin.setBounds(192, 917, 123, 57);
		menuPanel.add(btnFacebookLogin);
		
		label = new JLabel(ImagesData.menuBackgroundImage);
		label.setBounds(0, 0, 1920, 1080);
		menuPanel.add(label);
	
		if(lastPanel != null)
			lastPanel.setVisible(false);
		menuPanel.setVisible(true);
		menuPanel.addKeyListener(this);
		menuPanel.requestFocusInWindow();
	}

	@Override
	public void keyPressed(KeyEvent event) {}

	@Override
	public void keyReleased(KeyEvent event) {}

	@Override
	public void keyTyped(KeyEvent event) {}
}

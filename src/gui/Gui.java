package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.*;


public class Gui extends JFrame {
	JFrame Janela = new JFrame();
	JPanel painel;
	
	public Gui() {
		super("Presa-Predador");
		Janela.setMinimumSize(new Dimension(1024,768));
		Janela.setMaximumSize(new Dimension(1024,768));
		Janela.setResizable(false);
		Janela.setTitle("Presa-Predador");
		Janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
	}	
	
	public void desenhar(int[][] tabela, int[][] rastro) throws InterruptedException {
		painel = new JPanel();
		painel.setLayout(new GridLayout(tabela.length,tabela[0].length));
		for (int i=0; i<tabela.length;i++) {
			for(int j=0;j<tabela[0].length;j++) {
				JPanel bloco = new JPanel();
				/*
				 VALOR		REPRESENTAÇÂO 
				 1			Predador vivendo (Cinza)
				 2			Predador caçando (Cinza)
				 3			Presa em modo normal (Branco)
				 4			Presa no modo defensivo (Vermelho) 			 
				 Default	Pixel livre (verde)(verifica rastro) 
				 */
				switch(tabela[i][j]) {
					case 1:{
						Color bg = new Color(100, 100, 100);
						bloco.setBackground(bg);
						break;
					}
					case 2:{
						Color bg = new Color(50, 50, 50);
						bloco.setBackground(bg);
						break;
					}
					case 3:{
						Color bg = new Color(240, 240, 240);
						bloco.setBackground(bg);
						break;
					}
					case 4:{
						Color bg = new Color(255, 40, 40);
						bloco.setBackground(bg);
						break;
					}
					default: {
						switch(rastro[i][j]) {
							case 4:{
								Color bg = new Color(255, 252, 0);
								bloco.setBackground(bg);
								break;
							}
							case 3:{
								Color bg = new Color(247, 254, 46);
								bloco.setBackground(bg);
								break;
							}
							case 2:{
								Color bg = new Color(165, 223, 0);
								bloco.setBackground(bg);
								break;								
							}
							case 1:{
								Color bg = new Color(172, 250, 58);
								bloco.setBackground(bg);
								break;								
							}
							case 0: {
								Color bg = new Color(124, 252, 0);
								bloco.setBackground(bg);
								break;	
							}
							default:{
								Color bg = new Color(255, 252, 0);
								bloco.setBackground(bg);
								break;
							}
						}
					}	
				}
				painel.add(bloco);
				
			}
		}
		Janela.add(painel, BorderLayout.CENTER);
		Janela.pack();
		Janela.setVisible(true);
		painel.repaint();
	}
	
	
	
}

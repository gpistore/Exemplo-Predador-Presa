package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Gui extends JFrame {
	JFrame Janela = new JFrame();
	JPanel painel;
	
	public Gui() {
		super("Predador Presa");
		Janela.setMinimumSize(new Dimension(800,600));
		Janela.setMaximumSize(new Dimension(800,600));
		Janela.setResizable(false);
		Janela.setTitle("Agente Marte");
		Janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
	}			
	
	public void desenhar(int[][] tabela) throws InterruptedException {
		painel = new JPanel();
		painel.setLayout(new GridLayout(tabela.length,tabela[0].length));
		for (int i=0; i<tabela.length;i++) {
			for(int j=0;j<tabela[0].length;j++) {
				JPanel bloco = new JPanel();
				switch(tabela[i][j]) {
					case 0:{ // Livre
						Color bg = new Color(100, 190, 100);
						bloco.setBackground(bg);
						break;
					}
					case 1:{ // Predador
						Color bg = new Color(190, 70, 70);
						bloco.setBackground(bg);
						break;
					}
					case 2:{ // Presa
						Color bg = new Color(70, 170, 190);
						bloco.setBackground(bg);
						break;
					}
					case 3:{ //Rastro
						Color bg = new Color(230, 220, 90);
						bloco.setBackground(bg);
						break;
					}				
				}
				painel.add(bloco);
				
			}
		}
		Janela.add(painel, BorderLayout.CENTER);
		Janela.pack();
		Janela.setVisible(true);
		Thread.sleep( 200 );
	}
}

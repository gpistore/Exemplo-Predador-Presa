package main;

import java.util.Random;

import gui.Gui;

public class Main {

	public static void main(String[] args) throws InterruptedException {
		Random gerador = new Random();
		System.out.println("Hello World!");
		//Teste de interface
		int[][] tabela = new int[100][100];
		for (int i=0;i<100;i++) {
			for (int j=0;j<100;j++) {
				tabela[i][j] = gerador.nextInt(4);
			}
		}
		gui.Gui tela = new Gui();
		tela.desenhar(tabela);
	}

}

package main;

import java.util.Random;

import classes.Ambiente;
import classes.Predador;
import classes.Presa;
import gui.Gui;

public class Main {

	public static void main(String[] args) throws InterruptedException {
		Predador[]  vetorPredadores = new Predador[Ambiente.getNrPredadores()];
		Presa[] vetorPresas = new Presa[Ambiente.getNrPresas()];
		gui.Gui tela = new Gui();
		Ambiente.inicializa(tela);
		
		int k=0,l=0;
		for(int i=0;i<Ambiente.getNrLinhas();i++)
		{
			for(int j=0;j<Ambiente.getNrColunas();j++)
			{
				if(Ambiente.existePredador(i,j))
				{

					vetorPredadores[k] = new Predador(i,j);
					k++;
				}
				if(Ambiente.existePresa(i, j) >0)
				{
					vetorPresas[l] = new Presa(i,j);
					l++;
				}
			}
		}
		
//		for(int i=0;i<Ambiente.getNrPresas();i++)
//		{
//			vetorPresas[i] = new Presa();
//		}
		Ambiente.desenha();
		while(Ambiente.getNrPresas()!=0)
		{
			
		}
		
		
		//tela.desenhar(tabela);
	}

}

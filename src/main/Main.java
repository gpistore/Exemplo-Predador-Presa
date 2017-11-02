package main;

import java.util.Random;

import classes.Ambiente;
import classes.Predador;
import classes.Presa;
import gui.Gui;

public class Main {

	public static void main(String[] args) throws InterruptedException {
		
		gui.Gui tela = new Gui();
		Ambiente.inicializa(tela);
		
		Ambiente.desenha();
		
		while(Ambiente.getNrPresas()>0)
		{
			for(Predador predador: Ambiente.getListaPredadores()){
				predador.escolha();
			}
			
			for(Presa presa: Ambiente.getListaPresas()){
				presa.escolha();
			}
			
			for(Predador predador: Ambiente.getListaPredadores()){
				predador.acao();
			}
			
			for(Presa presa: Ambiente.getListaPresas()){
				presa.acao();
			}
			
			Ambiente.desenha();
			Thread.sleep(150);
		}
	}

}

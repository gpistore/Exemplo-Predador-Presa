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
		int iteracoes = 0;
		while(Ambiente.getNrPresas()>0 && iteracoes < 5000)
		{
			for(Predador predador: Ambiente.getListaPredadores()){
				predador.escolha();
			}
			
			for(Presa presa: Ambiente.getListaPresas()){
				if(!presa.EstaMorta()) {
					presa.escolha();	
				}
			}
			
			for(Predador predador: Ambiente.getListaPredadores()){
				predador.acao();
			}
			
			for(Presa presa: Ambiente.getListaPresas()){
				if(!presa.EstaMorta()) {
					presa.acao();
				}
			}
			Ambiente.atualizaRastros();
			Ambiente.desenha();
			Thread.sleep(250);
			iteracoes++;
		}

		Ambiente.finaliza(iteracoes);
		
	}

}

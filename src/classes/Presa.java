package classes;

import java.util.Random;

import javax.crypto.ExemptionMechanismSpi;

public class Presa {
	private int iteracoesQualidade = 5; // se após n iterações ela não detectar nenhum predador ou presa, atualiza o estado emocional 
	private int qualidadeEmocional; // de - 3 a 3
	private int intesidadeEmocional; // de 0 a 3
	private int tipo; // 3: viver 4: fugir
	private int linha;
	private int coluna;
	private int linhaNova;
	private int colunaNova;
	private int dir;
	private int iteracoes;
	private int velocidade;
	
	
	public Presa(){
		qualidadeEmocional = 1;
		intesidadeEmocional = 1;
		tipo = 3;
	}
	
	public void acao()
	{
		Ambiente.moveAgente(linha, coluna, linhaNova, colunaNova, tipo, dir);
		linha = linhaNova;
		coluna = colunaNova;
	}
	
	public void escolha()
	{
		int posicaoPredador = verificaPredador();
		int posicaoPresaFugindo = verificaPresaFugindo();
		if(posicaoPredador > 0)
		{
			iteracoesQualidade = 0;
			fugir(posicaoPredador, 4);
		}
		else if (posicaoPresaFugindo > 0 || qualidadeEmocional < 0){
			iteracoesQualidade = 0;
			fugir(posicaoPredador, 3);			
		}
		else
		{	
			if (iteracoesQualidade < 5)
				iteracoesQualidade++;
			else
			{
				if (qualidadeEmocional < 1)
					qualidadeEmocional++;
				if (intesidadeEmocional > 0)
					intesidadeEmocional--;
				
				iteracoesQualidade = 0;
			}
			
			viver();
		}
	}
	
	private void atualizaEstadoEmocional(int valor){
		if (qualidadeEmocional - valor >= -3)
			qualidadeEmocional -= valor;
		if (intesidadeEmocional + valor <= 3)
			intesidadeEmocional =+ valor;		
	}
		
	
	private void fugir(int posicao, int tipo){
		if (tipo == 3){ // viver
			atualizaEstadoEmocional(1);						
		}
		else{ // fugir			
			int valor = 0, qtd = qtdPredadores();			
			if (qtd == 1){				
				valor = 2;
			}
			else if (qtd >= 2){
				valor = 3;
			}

			atualizaEstadoEmocional(valor);
			
			if (iteracoes < 8){			
				iteracoes++;						
			}
			else
				velocidade = 1;			
		}
		
		// DEVE CALCULAR A VELOCIDADE AQUI CONFORME A QUALIDADE E INTENSIDADE EMOCIONAL
		
		if (qualidadeEmocional == 1 && intesidadeEmocional == 1){
			velocidade = 1;
		}
		else{
			/*Quanto maior o perigo, maior a velocidade da presa.
			Por exemplo, a velocidade máxima da presa é alcançada
			quando QE = -3 e IE = 3 (determinar deslocamento de
			maior número de células por iteração).*/			
		}
			
		
		
		
		/*
		   1 diagonal superior esquerda
		   2 superior
		   3 diagonal superior direita
		   4 esquerda
		   6 direita
		   7 diagonal inferior esquerda
		   8 inferior
		   9 diagonal inferior direita
		   
		 */	
		
		Random r = new Random();
		int posAleatoria = r.nextInt(2);
		switch (posicao) {
			case 1:
			{
				if (posAleatoria == 0){
					dir = 1;
					colunaNova = Ambiente.ajustaColuna(coluna+velocidade);
				}
				else{
					dir = 2;
					linhaNova = Ambiente.ajustaLinha(linha+velocidade);
				}
								
				break;
			}
			
			case 2:
			{
				dir = 2;
				linhaNova = Ambiente.ajustaLinha(linha+velocidade);				
				break;
			}		
			case 3:
			{
				if (posAleatoria == 0){
					dir = 3;
					colunaNova = Ambiente.ajustaColuna(coluna-velocidade);
				}
				else{
					dir = 2;
					linhaNova = Ambiente.ajustaLinha(linha+velocidade);
				}
				
				break;
			}	
			case 4:
			{
				dir = 1;
				colunaNova = Ambiente.ajustaColuna(coluna+velocidade);			
				break;
			}	
			case 6:
			{
				dir = 3;
				colunaNova = Ambiente.ajustaColuna(coluna-velocidade);				
				break;
			}	
			case 7:
			{
				if (posAleatoria == 0){
					dir = 1;
					colunaNova = Ambiente.ajustaColuna(coluna+velocidade);
				}
				else{
					dir = 0;
					linhaNova = Ambiente.ajustaLinha(linha-velocidade);
				}
				
				break;
			}	
			case 8:
			{
				dir = 0;
				linhaNova = Ambiente.ajustaLinha(linha-velocidade);	
				break;
			}	
			case 9:
			{
				if (posAleatoria == 0){
					dir = 3;
					colunaNova = Ambiente.ajustaColuna(coluna-velocidade);
				}
				else{
					dir = 0;
					linhaNova = Ambiente.ajustaLinha(linha-velocidade);
				}
				
				break;
			}			
		}			
	}
	
	private int verificaPredador(){
		int posicao = 0;
		for(int i = linha-1 ; i<linha+2 ; i++){			
			for(int j = coluna -1 ; j<coluna+2 ; j++)
			{
				posicao++;
				if (Ambiente.existePredador(i, j))
				{									
					return posicao;
				}
			}
		}
		return 0;
	}
	
	private int qtdPredadores(){
		int qtd = 0;
		for(int i = linha-1 ; i<linha+2 ; i++){			
			for(int j = coluna -1 ; j<coluna+2 ; j++)
			{				
				if (Ambiente.existePredador(i, j))
					qtd++;
			}
		}
		return qtd;		
	}
	
	private int verificaPresaFugindo(){
		int posicao = 0;
		for(int i = linha-1 ; i<linha+2 ; i++){			
			for(int j = coluna -1 ; j<coluna+2 ; j++)
			{
				posicao++;
				if (Ambiente.existePresaFugindo(i, j)){
					return posicao;
				}
			}
		}
		return 0;
	}
	
	private boolean verificaPresaLivre(){
		int posicao = 0;
		for(int i = linha-1 ; i<linha+2 ; i++){			
			for(int j = coluna -1 ; j<coluna+2 ; j++)
			{
				posicao++;
				if (Ambiente.existePresaLivre(i, j)){
					return true;
				}
			}
		}
		return false;
	}	
	
	private void viver()
	{		
		Random gerador = new Random();
		int r = gerador.nextInt(4);
		int i = linha;
		int j = coluna;
		
		iteracoes = 0;
		tipo = 1;
		
		
		if (r==0) //vai para cima
		{
			i = Ambiente.ajustaLinha(i--);			
		}
		else if (r==1) //vai para a direita
		{
			j = Ambiente.ajustaColuna(j++);
			
		}
		else if (r==2) //vai para baixo
		{
			i = Ambiente.ajustaLinha(i++);
			
		}
		else if (r==3) //vai para a esquerda
		{
			j = Ambiente.ajustaColuna(j--);
			
		}
		if (!Ambiente.existePredador(i, j))
		{		
			linhaNova = i;
			colunaNova = j;
			dir = r;
			
			if (verificaPresaLivre()){ 
				qualidadeEmocional++;
			}
		}
	}
			

}

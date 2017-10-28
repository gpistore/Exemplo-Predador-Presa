package classes;

import java.util.Random;

import javax.crypto.ExemptionMechanismSpi;

public class Presa {
	private int iteracoesQualidade = 5; // se após n iterações ela não detectar nenhum predador ou presa, atualiza o estado emocional 
	private int qualidadeEmocional; // de - 3 a 3
	private int intensidadeEmocional; // de 0 a 3
	private int tipo; // 3: viver 4: fugir
	private int linha;
	private int coluna;
	private int linhaNova;
	private int colunaNova;
	private int dir;
	private int iteracoes;
	private int velocidade;
	
	
	public Presa(int linha,int coluna){
		this.linha = linha;
		this.coluna = coluna;
		qualidadeEmocional = 1;
		intensidadeEmocional = 1;
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
		
		if (verificaPresaLivre()){ 
			atualizaQualidadeEmocional(1);
		}
		if (posicaoPresaFugindo>0){ 
			iteracoesQualidade = 0;
			atualizaQualidadeEmocional(-1);
			atualizaEstadoEmocional(1);
		}
		
		if(posicaoPredador > 0)
		{
			iteracoesQualidade = 0;
			atualizaQualidadeEmocional(-2);
			atualizaIntensidadeEmocional(2);
			if(posicaoPredador>1)
			{
				atualizaQualidadeEmocional(-1);
				atualizaIntensidadeEmocional(1);
			}

			fugir(posicaoPredador);
		}
		else if(qualidadeEmocional < 0) //fuga sem predador
		{
			fugir(posicaoPresaFugindo); //se tiver presa fugindo, foge dela, se não tiver, move aleatório
		}
		else
		{	
			if (iteracoesQualidade < 4)
				iteracoesQualidade++;
			else
			{
				if (qualidadeEmocional < 1)
					qualidadeEmocional++;
				if (intensidadeEmocional > 0)
					intensidadeEmocional--;
			}
			viver();
		}
	}
	
	private void atualizaEstadoEmocional(int valor){
		if (qualidadeEmocional - valor >= -3)
			qualidadeEmocional -= valor;
		if (intensidadeEmocional + valor <= 3)
			intensidadeEmocional =+ valor;		
	}
		
	private void atualizaQualidadeEmocional(int valor)
	{
		qualidadeEmocional += valor;
		if (qualidadeEmocional < -3) qualidadeEmocional = -3;
		if (qualidadeEmocional > 3) qualidadeEmocional = 3;
	}
	
	private void atualizaIntensidadeEmocional(int valor)
	{
		intensidadeEmocional += valor;
		if (qualidadeEmocional > 3) qualidadeEmocional = 3;
	}
	
	
	
	private void fugir(int posicao){
		
	
		tipo = 4;
		int qtd = qtdPredadores();	
		if (qtd >= 3){
			morre();
			return;
		}
		iteracoes++;
		velocidade = (-qualidadeEmocional + intensidadeEmocional)/2;
		if(velocidade <1 || iteracoes >=8) velocidade = 1;
		
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
		if (posicao == 0)
		{
			posAleatoria = r.nextInt(4);
		}
		switch (posicao) { 
		 	case 0: //se estiver fugindo de nada, vai aleatório;
		 	{
		 		if (posAleatoria == 1){
					dir = 1;
					colunaNova = Ambiente.ajustaColuna(coluna+velocidade);
				}
		 		if (posAleatoria == 2){
					dir = 2;
					linhaNova = Ambiente.ajustaLinha(linha+velocidade);
				}
		 		if (posAleatoria == 3){
					dir = 3;
					colunaNova = Ambiente.ajustaColuna(coluna-velocidade);
				}
		 		else{
					dir = 0;
					linhaNova = Ambiente.ajustaLinha(linha-velocidade);
				}
		 	}
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
	
	private void morre() {
		Ambiente.presaMorre(linha, coluna);
		
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
		for(int i = linha-1 ; i<linha+2 ; i++){			
			for(int j = coluna -1 ; j<coluna+2 ; j++)
			{
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
		tipo = 3;
		
		
		
		if (r==0) //vai para cima
		{
			i--;
			i = Ambiente.ajustaLinha(i);			
		}
		else if (r==1) //vai para a direita
		{
			j++;
			j = Ambiente.ajustaColuna(j);
			
		}
		else if (r==2) //vai para baixo
		{
			i++;
			i = Ambiente.ajustaLinha(i);
			
		}
		else if (r==3) //vai para a esquerda
		{
			j--;
			j = Ambiente.ajustaColuna(j);
			
		}
		if (!Ambiente.existePredador(i, j))
		{		
			linhaNova = i;
			colunaNova = j;
			dir = r;

		}
	}
			

}

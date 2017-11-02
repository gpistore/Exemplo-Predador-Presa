package classes;

import java.util.Random;

import javax.crypto.ExemptionMechanismSpi;

public class Presa {
	private int iteracoesQualidade = 5; // se ap�s n itera��es ela n�o detectar nenhum predador ou presa, atualiza o estado emocional 
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
		if(Ambiente.moveAgente(linha, coluna, linhaNova, colunaNova, tipo, dir)) {
			linha = linhaNova;
			coluna = colunaNova;
		}
	}
	
	public void escolha()
	{
		int posicaoPresaFugindo = verificaPresaFugindo();
		
		if (verificaPresaLivre()){ 
			atualizaQualidadeEmocional(1);
		}
		if (posicaoPresaFugindo>0){ 
			iteracoesQualidade = 0;
			atualizaQualidadeEmocional(-1);
		}
		
		int posicaoPredador = verificaPredador();
		if(posicaoPredador > 0)
		{
			iteracoesQualidade = 0;
			atualizaQualidadeEmocional(-2);
			atualizaIntensidadeEmocional(2);
			
			fugir(posicaoPredador);
		}
		else if(qualidadeEmocional < 0) //fuga sem predador
		{
			fugir(posicaoPresaFugindo); //se tiver presa fugindo, foge dela, se n�o tiver, move aleat�rio
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
	
	private void calculavelocidade() {
		this.velocidade = (intensidadeEmocional - qualidadeEmocional)/2;
		if(velocidade <1 || iteracoes >=8) velocidade = 1;
	}
	
	private void fugir(int posicao){
		
		tipo = 4;
		int qtd = qtdPredadores();	
		if (qtd >= 3){
			morre();
			return;
		}
		iteracoes++;
		calculavelocidade();
		
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
		int i = linha;
		int j = coluna;
		if (posicao == 0)
		{
			posicao = r.nextInt(4);
		}
		switch (posicao) { 
		 	case 0: //se estiver fugindo de nada, vai aleat�rio;
		 	{
		 		if (posAleatoria == 1){
					dir = 1;
					j = j+velocidade;
				}
		 		if (posAleatoria == 2){
					dir = 2;
					i=i+velocidade;
				}
		 		if (posAleatoria == 3){
					dir = 3;
					j=j-velocidade;
				}
		 		else{
					dir = 0;
					i=i-velocidade;
				}
		 		break;
		 	}
			case 1:
			{
				if (posAleatoria == 0){
					dir = 1;
					j=j+velocidade;
				}
				else{
					dir = 2;
					i=i+velocidade;
				}
								
				break;
			}
			
			case 2:
			{
				dir = 2;
				i=i+velocidade;				
				break;
			}		
			case 3:
			{
				if (posAleatoria == 0){
					dir = 3;
					j=j-velocidade;
				}
				else{
					dir = 2;
					i=i+velocidade;
				}
				
				break;
			}	
			case 4:
			{
				dir = 1;
				j = j+velocidade;			
				break;
			}	
			case 6:
			{
				dir = 3;
				j = j-velocidade;				
				break;
			}	
			case 7:
			{
				if (posAleatoria == 0){
					dir = 1;
					j= j+velocidade;
				}
				else{
					dir = 0;
					i = i-velocidade;
				}
				
				break;
			}	
			case 8:
			{
				dir = 0;
				i = i-velocidade;	
				break;
			}	
			case 9:
			{
				if (posAleatoria == 0){
					dir = 3;
					j = j-velocidade;
				}
				else{
					dir = 0;
					i = i-velocidade;
				}
				
				break;
			}			
		}
		linhaNova = Ambiente.ajustaColuna(i);
		colunaNova = Ambiente.ajustaColuna(j);
		
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
		if (!Ambiente.existePredador(i, j)){		
			linhaNova = i;
			colunaNova = j;
			dir = r;

		}
	}
			

}

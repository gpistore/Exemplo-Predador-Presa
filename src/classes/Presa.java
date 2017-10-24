package classes;

import java.util.Random;

public class Presa {
	private int qualidadeEmocional;
	private int intesidadeEmocional;
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
			fugir(posicaoPredador, 4);
		}
		else if (posicaoPresaFugindo > 0){
			fugir(posicaoPredador, 3);			
		}
		else
		{			
			viver();
		}
	}
	
	private void fugir(int posicao, int tipo){
		if (tipo == 3){
			if (iteracoes < 8){			
				iteracoes++;
				velocidade = (qualidadeEmocional + intesidadeEmocional)/2;			
			}
			else
				velocidade = 1;
		}
		else{
			
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
			i--;
			if(i<0) i+=Ambiente.getNrLinhas();
			
		}
		if (r==1) //vai para a direita
		{
			j++;
			if(j>=Ambiente.getNrColunas()) j-=Ambiente.getNrColunas();
			
		}
		if (r==2) //vai para baixo
		{
			i++;
			if(i>=Ambiente.getNrLinhas()) i-=Ambiente.getNrLinhas();
			
		}
		if (r==3) //vai para a esquerda
		{
			j--;
			if(j<0) j+=Ambiente.getNrColunas();
			
		}
		if (!Ambiente.existePredador(i, j))
		{
			
			linhaNova = i;
			colunaNova = j;
			dir = r;
			
		}
	}
			

}

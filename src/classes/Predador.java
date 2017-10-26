package classes;

import java.util.Random;

import javax.crypto.ExemptionMechanismSpi;

public class Predador {
	
	private int velocidade = 1;
	private int linha, coluna;
	private int linhaNova, colunaNova;
	private int dir;
	private int iteracoes;
	private int tipo;
		
	
	public Predador(int linha, int coluna)
	{
		this.linha = linha;
		this.coluna = coluna;
	}
	
	
	public void acao()
	{
		Ambiente.moveAgente(linha, coluna, linhaNova, colunaNova, tipo, dir);
		linha = linhaNova;
		coluna = colunaNova;
	}
	
	public void escolha()
	{
		int posicao = verificaPresa(); 
		if(posicao > 0)
		{
			cacar(posicao);
		}
		else
		{			
			viver();
		}
	}
	
	private void cacar(int posicaoPresa)
	{
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
		
		tipo = 2;
		if (iteracoes < 5){			
			iteracoes++;
			velocidade = 4;			
		}
		else
			velocidade = 1;
		
		Random r = new Random();
		int posAleatoria = r.nextInt(2);
		switch (posicaoPresa) {
			case 1:
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
			
			case 2:
			{
				dir = 0;
				linhaNova = Ambiente.ajustaLinha(linha-velocidade);				
				break;
			}		
			case 3:
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
			case 4:
			{
				dir = 3;
				colunaNova = Ambiente.ajustaColuna(coluna-velocidade);			
				break;
			}	
			case 6:
			{
				dir = 1;
				colunaNova = Ambiente.ajustaColuna(coluna+velocidade);				
				break;
			}	
			case 7:
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
			case 8:
			{
				dir = 2;
				linhaNova = Ambiente.ajustaLinha(linha+velocidade);	
				break;
			}	
			case 9:
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
		}					
	}
	
	private void viver()
	{		
		Random gerador = new Random();
		int r = gerador.nextInt(4);
		int i = linha;
		int j = coluna;
		
		iteracoes = 0;
		tipo = 1;
		
		int feromonio = verificaFeromonio();
		
		if (feromonio>=0)
		{
			r = feromonio;
		}
		
		if (r==0) //vai para cima
		{
			i = Ambiente.ajustaLinha(i--);
			
		}
		if (r==1) //vai para a direita
		{
			j = Ambiente.ajustaColuna(j++);
			
		}
		if (r==2) //vai para baixo
		{
			i = Ambiente.ajustaLinha(i++);			
		}
		if (r==3) //vai para a esquerda
		{
			j = Ambiente.ajustaColuna(j--);			
		}
		if (!Ambiente.existePredador(i, j))
		{
			
			linhaNova = i;
			colunaNova = j;
			dir = r;
			
		}
	}
	
	private int verificaFeromonio() {
		int maiorFeromonio = 0;
		int dirMaior = -1;
		int feromonio = 0;
		
		feromonio = Ambiente.existeFeromonio(linha-1, coluna);
		if(feromonio>maiorFeromonio) 
		{
			maiorFeromonio = feromonio;
			dirMaior = 0;
		}
		feromonio = Ambiente.existeFeromonio(linha, coluna+1);
		if(feromonio>maiorFeromonio) 
		{
			maiorFeromonio = feromonio;
			dirMaior = 1;
		}
		
		feromonio = Ambiente.existeFeromonio(linha+1, coluna);
		if(feromonio>maiorFeromonio) 
		{
			maiorFeromonio = feromonio;
			dirMaior = 2;
		}
		
		feromonio = Ambiente.existeFeromonio(linha, coluna-1);
		if(feromonio>maiorFeromonio){
			maiorFeromonio = feromonio;
			dirMaior = 3;
		}
		
		return dirMaior; //retorna -1 se não acha feromônio
	}


	private int verificaPresa()
	{
		int posicao = 0;
		for(int i = linha-1 ; i<linha+2 ; i++){			
			for(int j = coluna -1 ; j<coluna+2 ; j++)
			{
				posicao++;
				if (Ambiente.existePresa(i, j) != 0)
				{									
					return posicao;
				}
			}
		}
		return 0;
	}		
}

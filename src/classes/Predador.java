package classes;

import java.util.Random;

import javax.crypto.ExemptionMechanismSpi;

public class Predador {
	
	private int velocidade = 1;
	private int linha, coluna;
	private int linhaNova, colunaNova;
	private int dir;
	private int iPresa,jPresa;
	
	
	
	public Predador(int linha, int coluna)
	{
		this.linha = linha;
		this.coluna = coluna;
	}
	
	
	public void acao()
	{
		Ambiente.moveAgente(linha, coluna, linhaNova, colunaNova, 1, dir);
		linha = linhaNova;
		coluna = colunaNova;
	}
	
	public void escolha()
	{
		if(verificaPresa())
		{
			cacar();
		}
		else
		{
			viver();
		}
	}
	
	private void cacar()
	{
		//if(iPresa ==  )
		
		
		
	}
	
	private void viver()
	{
		Random gerador = new Random();
		int r = gerador.nextInt(4);
		int i = linha;
		int j = coluna;
		
		int feromonio = verificaFeromonio();
		
		if (feromonio>=0)
		{
			r = feromonio;
		}
		
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


	private boolean verificaPresa()
	{
		boolean f = false;
		for(int i = linha-1 ; i<linha+2 ; i++)
			for(int j = coluna -1 ; j<coluna+2 ; j++)
			{
				if (Ambiente.existePresa(i, j))
				{
					iPresa = i;
					jPresa = j;
					f = true;
				}
			}
		return f;
	}
	
	private int ajustaLinha(int linha)
	{
		if(linha<0) linha += Ambiente.getNrLinhas();
		if(linha>=Ambiente.getNrLinhas()) linha -= Ambiente.getNrLinhas();
		return linha;
	}
	
	private int ajustaColuna(int coluna)
	{
		if(coluna<0) coluna+= Ambiente.getNrLinhas();
		if(coluna>=Ambiente.getNrColunas()) coluna -= Ambiente.getNrColunas();
		return linha;
	}
	
}

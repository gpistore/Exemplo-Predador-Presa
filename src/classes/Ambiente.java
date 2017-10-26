package classes;

import java.util.Random;

import gui.Gui;

public class Ambiente {
	private static int nrLinhas= 30;
	private static int nrColunas = 30;
	private static int nrPresas = 1;
	public static int getNrPresas() {
		return nrPresas;
	}

	public static void setNrPresas(int nrPresas) {
		Ambiente.nrPresas = nrPresas;
	}

	public static int getNrPredadores() {
		return nrPredadores;
	}

	public static void setNrPredadores(int nrPredadores) {
		Ambiente.nrPredadores = nrPredadores;
	}

	private static int nrPredadores = 3;
	   
	    // 1 = Predador Viver
	    // 2 = Predados Ca�ar
		// 3 = Presa Viver
		// 4 = Presa Fugir
	private static int[][] tabuleiro = new int[nrLinhas][nrColunas];
	private static int[][] rastro = new int[nrLinhas][nrColunas];
	    static Gui tela;
	   
	    public static void inicializa(Gui tela2) {
	        tela = tela2;
	        inicializaAmbiente();
	        inicializaPredadores();
	        inicializaPresas();
	    }
	   
	    public static int getNrLinhas() {
			return nrLinhas;
		}

		public static void setNrLinhas(int nrlinhas) {
			Ambiente.nrLinhas = nrlinhas;
		}

		public static int getNrColunas() {
			return nrColunas;
		}

		public static void setNrColunas(int nrcolunas) {
			Ambiente.nrColunas = nrcolunas;
		}

		private static void inicializaPredadores()
	    {
	        Random gerador = new Random();   
	        while(nrPredadores>0) {
	            int linha = gerador.nextInt(nrLinhas);
	            int coluna = gerador.nextInt(nrColunas);
	            tabuleiro[linha][coluna] = 1;
	            nrPredadores --;
	        }       
	    }
	   
	    private static void inicializaPresas()
	    {
	        Random gerador = new Random();
	        while(nrPresas>0) {
	            int linha = gerador.nextInt(nrLinhas);
	            int coluna = gerador.nextInt(nrColunas);
	            tabuleiro[linha][coluna] = 3;
	            nrPresas --;
	        }       
	    }
	   
	    private static void inicializaAmbiente()
	    {
	        // Inicializa matriz
	        for (int i=0;i<nrLinhas;i++) {
	            for (int j=0;j<nrColunas;j++) {
	                tabuleiro[i][j] = 0;
	            }
	        }   
	    }
	   
	    public static int existePresa(int i, int j) {
	    	if(i<0) i+=nrLinhas;
	    	if(j<0) j+=nrColunas;
	    	if(i>=nrLinhas) i-=nrLinhas;
	    	if(j>=nrColunas) j-=nrColunas;
            if (tabuleiro[i][j] == 3 || tabuleiro[i][j] == 4) {
                return tabuleiro[i][j];
            }
            return 0;
	    }
	    
	    public static boolean existePredador(int i, int j) {
	    	if(i<0) i+=nrLinhas;
	    	if(j<0) j+=nrColunas;
	    	if(i>=nrLinhas) i-=nrLinhas;
	    	if(j>=nrColunas) j-=nrColunas;
	        if (tabuleiro[i][j] == 1 || tabuleiro[i][j] == 2) {
	            return true; 
	        }
		    return false;
	    }
	    
	    public static boolean existePresaFugindo(int i, int j) {
	    	if(i<0) i+=nrLinhas;
	    	if(j<0) j+=nrColunas;
	    	if(i>=nrLinhas) i-=nrLinhas;
	    	if(j>=nrColunas) j-=nrColunas;
	        if (tabuleiro[i][j] == 4) {
	            return true; 
	        }
		    return false;
	    }
	    
	    public static boolean existePresaLivre(int i, int j) {
	    	if(i<0) i+=nrLinhas;
	    	if(j<0) j+=nrColunas;
	    	if(i>=nrLinhas) i-=nrLinhas;
	    	if(j>=nrColunas) j-=nrColunas;
	        if (tabuleiro[i][j] == 3) {
	            return true; 
	        }
		    return false;
	    }
	    
	    public static int existeFeromonio(int i, int j) {
	    	if(i<0) i+=nrLinhas;
	    	if(j<0) j+=nrColunas;
	    	if(i>=nrLinhas) i-=nrLinhas;
	    	if(j>=nrColunas) j-=nrColunas;
	        if (rastro[i][j] > 0) {
	            return rastro[i][j]; 
	        }
		    return 0;
	    }
	    
	   
	    public static void removePresa(int linha,int coluna) {
	        tabuleiro[linha][coluna] = 0;
	    }
	   
	    public static int tipo(int linha,int coluna) {
	        return tabuleiro[linha][coluna];
	    }
	   
	    public static void desenha() {
	        try {
	            tela.desenhar(tabuleiro,rastro);
	        } catch (InterruptedException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
	    }

		public static void moveAgente(int iAntigo, int jAntigo, int i, int j, int tipo, int dir) {
			if(tabuleiro[i][j] == 0)
			{
				tabuleiro[iAntigo][jAntigo] = 0;							
				
				// Atualiza o rastro caso seja predador ca�ando
				if(tipo == 2)
				{
					int iAtual = iAntigo, jAtual = jAntigo, nivelFeromonio = 0;
					switch (dir) 
					{
						case 0:
							do
							{
								if (tabuleiro[iAtual][jAtual] == 3 || tabuleiro[iAtual][jAtual] == 4){
									i = ajustaLinha(iAtual++);
									break;
								}
									
								rastro[iAtual][jAtual] = nivelFeromonio++;
								i = ajustaLinha(iAtual--);
							}while(iAtual!=i);							
							break;
							
						case 1:
							do
							{
								if (tabuleiro[iAtual][jAtual] == 3 || tabuleiro[iAtual][jAtual] == 4){
									j = ajustaColuna(jAtual--);
									break;
								}
								rastro[iAtual][jAtual] = nivelFeromonio++;								
								j = ajustaColuna(jAtual++);
							}while(jAtual!=j);							
							break;
							
						case 2:
							do
							{
								if (tabuleiro[iAtual][jAtual] == 3 || tabuleiro[iAtual][jAtual] == 4){
									i = ajustaLinha(iAtual--);
									break;
								}
								
								rastro[iAtual][jAtual] = nivelFeromonio++;
								i = ajustaLinha(iAtual++);
							}while(iAtual!=i);	
							break;
							
						case 3:
							do
							{
								if (tabuleiro[iAtual][jAtual] == 3 || tabuleiro[iAtual][jAtual] == 4){
									j = ajustaColuna(jAtual++);
									break;
								}
								
								rastro[iAtual][jAtual] = nivelFeromonio++;
								j = ajustaColuna(jAtual--);
							}while(jAtual!=j);	
							break;
					}
					
				}
				else if (tipo == 3){
					
				}	
				
				tabuleiro[i][j] = tipo;
			}			
		}
		
		public void atualizaRastros()
		{
			for(int i=0;i<nrLinhas;i++)
			{
				for(int j=0;j<nrColunas;j++)
				{
					if(tabuleiro[i][j]>5)
						tabuleiro[i][j]--;
					if(tabuleiro[i][j]==5)
						tabuleiro[i][j]=0;
				}
			}
		}
		
		public static int ajustaLinha(int linha)
		{
			if(linha<0) 
				linha += Ambiente.getNrLinhas();
			if(linha>=Ambiente.getNrLinhas()) 
				linha -= Ambiente.getNrLinhas();
			return linha;
		}
		
		public static int ajustaColuna(int coluna)
		{
			if(coluna<0) coluna+= Ambiente.getNrLinhas();
			if(coluna>=Ambiente.getNrColunas()) coluna -= Ambiente.getNrColunas();
			return coluna;
		}		
}

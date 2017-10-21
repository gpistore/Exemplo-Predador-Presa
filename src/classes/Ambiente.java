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
	   
	    // 1 = Predador
	    // 2 = Presa
	   
	   
	    private static int[][] tabuleiro = new int[nrLinhas][nrColunas];
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
	            tabuleiro[linha][coluna] = 2;
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
	   
	    public static boolean existePresa(int i, int j) {
	    	if(i<0) i+=nrLinhas;
	    	if(j<0) j+=nrColunas;
	    	if(i>=nrLinhas) i-=nrLinhas;
	    	if(j>=nrColunas) j-=nrColunas;
            if (tabuleiro[i][j] == 2) {
                return true;
            }
            return false;
	    }
	    
	    public static boolean existePredador(int i, int j) {
	    	if(i<0) i+=nrLinhas;
	    	if(j<0) j+=nrColunas;
	    	if(i>=nrLinhas) i-=nrLinhas;
	    	if(j>=nrColunas) j-=nrColunas;
	        if (tabuleiro[i][j] == 1) {
	            return true; 
	        }
		    return false;
	    }
	    
	    public static int existeFeromonio(int i, int j) {
	    	if(i<0) i+=nrLinhas;
	    	if(j<0) j+=nrColunas;
	    	if(i>=nrLinhas) i-=nrLinhas;
	    	if(j>=nrColunas) j-=nrColunas;
	        if (tabuleiro[i][j] >= 3) {
	            return tabuleiro[i][j]; 
	        }
		    return 0;
	    }
	    
	   
	    public static void removePresa(int linha,int coluna) {
	        tabuleiro[linha][coluna] = 0;
	    }
	   
	    public static int tipo(int linha,int coluna) {
	        return tabuleiro[linha][coluna];
	    }
	   
	    public static void desenha(int linha,int coluna, int tipo) {
	        //Tipo de
	        //0 = livre
	        //1 = predador
	        //2 = presa
	        //3 ao 8 = rastro
	       	       
	        int [][] tabela = new int[nrLinhas][nrColunas]; 
	        for (int i=0; i < nrLinhas; i++) {
	            for (int j=0; j < nrColunas; j++){
	                tabela[i][j]=tabuleiro[i][j];
	            }
	        }
	        tabela[linha][coluna]= tipo;
	           
	        try {
	            tela.desenhar(tabela);
	        } catch (InterruptedException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
	    }

		public static void moveAgente(int iAntigo, int jAntigo, int i, int j, int tipo, int dir) {
			if(tabuleiro[i][j]!=1 && tabuleiro[i][j] != 2)
			{
				tabuleiro[iAntigo][jAntigo] = 0;
				tabuleiro[i][j] = tipo;
				if(tipo == 1)
				{
					int iAtual = iAntigo, jAtual = jAntigo;
					switch (dir) 
					{
						case 0:
							do
							{
								tabuleiro[iAtual][jAtual] = 8;
								iAtual--;
								if(i<0) i+=nrLinhas;
							}while(iAtual!=i);							
							break;
							
						case 1:
							do
							{
								tabuleiro[iAtual][jAtual] = 8;
								jAtual++;
								if(j>=nrColunas) j-=nrColunas;
							}while(jAtual!=j);							
							break;
							
						case 2:
							do
							{
								tabuleiro[iAtual][jAtual] = 8;
								iAtual++;
								if(i>=nrLinhas) i-=nrLinhas;
							}while(iAtual!=i);	
							break;
							
						case 3:
							do
							{
								tabuleiro[iAtual][jAtual] = 8;
								jAtual--;
								if(j<0) j+=nrColunas;
							}while(jAtual!=j);	
							break;
					}
					
				}
				
				
			}
			
		}
		
		public void atualizaRastros()
		{
			for(int i=0;i<nrLinhas;i++)
			{
				for(int j=0;j<nrColunas;j++)
				{
					if(tabuleiro[i][j]>3)
						tabuleiro[i][j]--;
					if(tabuleiro[i][j]==3)
						tabuleiro[i][j]=0;
				}
			}
		}
}

package classes;

import java.util.ArrayList;
import java.util.Random;

import gui.Gui;

public class Ambiente {
	private static int nrLinhas= 30;
	private static int nrColunas = 30;
	private static int nrPresas = 5;
	private static int nrPredadores = 15;
	private static ArrayList<Predador>  ListaPredadores = new ArrayList <Predador>();
	private static ArrayList <Presa> ListaPresas = new ArrayList <Presa>();
	private static int[][] tabuleiro = new int[nrLinhas][nrColunas];
	private static int[][] rastro = new int[nrLinhas][nrColunas];
    static Gui tela;
	
    public static ArrayList<Predador> getListaPredadores(){
    	return ListaPredadores;
    }
    
    public static ArrayList<Presa> getListaPresas(){
    	return ListaPresas;
    }
    
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

	    // 1 = Predador Viver
	    // 2 = Predados Caï¿½ar
		// 3 = Presa Viver
		// 4 = Presa Fugir

	public static void inicializa(Gui tela2) {
        tela = tela2;
        inicializaAmbiente();
        inicializaPredadores();
        inicializaPresas();
    }
	
	private static void inicializaPredadores()
	    {
	        Random gerador = new Random();   
	        while(nrPredadores>0) {
	            int linha = gerador.nextInt(nrLinhas);
	            int coluna = gerador.nextInt(nrColunas);
	            tabuleiro[linha][coluna] = 1;
	            ListaPredadores.add(new Predador(linha,coluna)); 
	            nrPredadores --;
	        }       
	    }
	   
	private static void inicializaPresas()
	    {
	        Random gerador = new Random();
	        int totalpresas = nrPresas;
	        while(totalpresas>0) {
	            int linha = gerador.nextInt(nrLinhas);
	            int coluna = gerador.nextInt(nrColunas);
	            tabuleiro[linha][coluna] = 3;
	            ListaPresas.add(new Presa(linha,coluna)); 
	            totalpresas --;
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
	    	i = ajustaLinha(i);
	    	j = ajustaLinha(j);
	    	if (tabuleiro[i][j] == 3 || tabuleiro[i][j] == 4) {
                return tabuleiro[i][j];
            }
            return 0;
	    }
	    
	    public static boolean existePredador(int i, int j) {
	    	i = ajustaLinha(i);
	    	j = ajustaLinha(j);
	        if (tabuleiro[i][j] == 1 || tabuleiro[i][j] == 2) {
	            return true; 
	        }
		    return false;
	    }
	    
	    public static boolean existePresaFugindo(int i, int j) {
	    	i = ajustaLinha(i);
	    	j = ajustaLinha(j);
	        if (tabuleiro[i][j] == 4) {
	            return true; 
	        }
		    return false;
	    }
	    
	    public static boolean existePresaLivre(int i, int j) {
	    	i = ajustaLinha(i);
	    	j = ajustaLinha(j);
	        if (tabuleiro[i][j] == 3) {
	            return true; 
	        }
		    return false;
	    }
	    
	    public static int existeFeromonio(int i, int j) {
	    	i = ajustaLinha(i);
	    	j = ajustaLinha(j);
	        if (rastro[i][j] > 0) {
	            return rastro[i][j]; 
	        }
		    return 0;
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

		public static boolean moveAgente(int iAntigo, int jAntigo, int i, int j, int tipo, int dir) {
			if(tabuleiro[i][j] == 0){
				tabuleiro[iAntigo][jAntigo] = 0;							
				if(tipo == 2) {
					System.out.println("iAntigo ="+iAntigo);
					System.out.println("jAntigo ="+jAntigo);
					System.out.println("i ="+i);
					System.out.println("j="+j);
					System.out.println("dir ="+dir);
					System.out.println("------------------");
				}
				
				
				// Atualiza o rastro caso seja predador caçando
				if(tipo == 2){
					int iAtual = iAntigo; 
					int jAtual = jAntigo;
					int nivelFeromonio = 1;
					switch (dir) 
					{
						case 0:
							while(iAtual!=i){
								rastro[iAtual][jAtual] = nivelFeromonio;
								iAtual--;
								iAtual = ajustaLinha(iAtual);
								nivelFeromonio++;
							}							
							break;
							
						case 1:
							while(jAtual!=j){
								rastro[iAtual][jAtual] = nivelFeromonio;
								jAtual++;
								jAtual = ajustaLinha(jAtual);
								nivelFeromonio++;
							}							
							break;
							
						case 2:
							while(iAtual!=i){
								rastro[iAtual][jAtual] = nivelFeromonio;
								iAtual++;
								iAtual = ajustaLinha(iAtual);
								nivelFeromonio++;
							}
							
						case 3:
							while(jAtual!=j){
								rastro[iAtual][jAtual] = nivelFeromonio;
								jAtual--;
								jAtual = ajustaLinha(jAtual);
								nivelFeromonio++;
							}	
							break;
					}
				}
				tabuleiro[i][j] = tipo;
				return true;
			}else {
				return false;
			}
		}
		
		public static void atualizaRastros() {
			for(int i=0;i<nrLinhas;i++){
				for(int j=0;j<nrColunas;j++){
					if(rastro[i][j]>0) {
						rastro[i][j]--;
					}
				}
			}
		}
		
		public static int ajustaLinha(int linhaajustar)
		{
			if(linhaajustar<0) 
				linhaajustar += Ambiente.getNrLinhas();
			if(linhaajustar>=Ambiente.getNrLinhas()) 
				linhaajustar -= Ambiente.getNrLinhas();
			return linhaajustar;
		}
		
		public static int ajustaColuna(int colunaajustar){
			if(colunaajustar<0) 
				colunaajustar+= Ambiente.getNrLinhas();
			if(colunaajustar>=Ambiente.getNrColunas()) 
				colunaajustar -= Ambiente.getNrColunas();
			return colunaajustar;
		}
		
		public static void presaMorre(int i, int j)
		/* revisar remoção da presa do array de presas */
		{
			tabuleiro[i][j] = 0;
			nrPresas--;
		}		
}

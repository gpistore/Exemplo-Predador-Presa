package classes;

import java.util.Random;

import gui.Gui;

public class Ambiente {
	private int nrlinhas= 30;
	private int nrcolunas = 30;
	private int nrPresas = 1;
	private int nrPredadores = 3;
	private int[] vetorAgentes = new int[30];
	   
	    // 1 = Predador
	    // 2 = Presa
	   
	   
	    private int[][] tabuleiro = new int[nrlinhas][nrcolunas];
	    Gui tela;
	   
	    public Ambiente(Gui tela) {
	        this.tela = tela;
	        this.inicializaAmbiente();
	        this.inicializaPredadores();
	        this.inicializaPresas();
	    }
	   
	    private void inicializaPredadores()
	    {
	        Random gerador = new Random();   
	        while(nrPredadores>0) {
	            int linha = gerador.nextInt(nrlinhas);
	            int coluna = gerador.nextInt(nrcolunas);
	            tabuleiro[linha][coluna] = 1;
	            nrPredadores --;
	        }       
	    }
	   
	    private void inicializaPresas()
	    {
	        Random gerador = new Random();
	        while(nrPresas>0) {
	            int linha = gerador.nextInt(nrlinhas);
	            int coluna = gerador.nextInt(nrcolunas);
	            tabuleiro[linha][coluna] = 2;
	            nrPresas --;
	        }       
	    }
	   
	    private void inicializaAmbiente()
	    {
	        // Inicializa matriz
	        for (int i=0;i<nrlinhas;i++) {
	            for (int j=0;j<nrcolunas;j++) {
	                tabuleiro[i][j] = 0;
	            }
	        }   
	    }
	   
	    public boolean existePresa() {
	        for (int i=0;i<nrlinhas;i++) {
	            for (int j=0;j<nrcolunas;j++) {
	                if (tabuleiro[i][j] == 2) {
	                    return true;
	                }
	            }
	        }
	        return false;
	    }
	    
	    public boolean existePredador() {
	        for (int i=0;i<nrlinhas;i++) {
	            for (int j=0;j<nrcolunas;j++) {
	                if (tabuleiro[i][j] == 1) {
	                    return true;
	                }
	            }
	        }
	        return false;
	    }
	   
	    public void removePresa(int linha,int coluna) {
	        tabuleiro[linha][coluna] = 0;
	    }
	   
	    public int tipo(int linha,int coluna) {
	        return tabuleiro[linha][coluna];
	    }
	   
	    public void desenha(int linha,int coluna, int tipo) {
	        //Tipo de
	        //0 = livre
	        //1 = predador
	        //2 = presa
	        //3 = rastro
	       	       
	        int [][] tabela = new int[nrlinhas][nrcolunas]; 
	        for (int i=0; i < nrlinhas; i++) {
	            for (int j=0; j < nrcolunas; j++){
	                tabela[i][j]=tabuleiro[i][j];
	            }
	        }
	        tabela[linha][coluna]= tipo;
	           
	        try {
	            this.tela.desenhar(tabela);
	        } catch (InterruptedException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
	    }
}

package xadrez;

import boardgame.Posicao;

public class PosicaoXadrez {
	
	private char coluna;
	private int linha;
	
	
	public PosicaoXadrez(char coluna, int linha) {
		
		if(coluna < 'A' || coluna > 'H' || linha <1 || linha >8) {
			throw new ExceptionXadrez("Erro instanciando PosicaoXadrez. valores validos de A1 até H8");
		}		
		
		this.coluna = coluna;
		this.linha = linha;
	}
	
	public char getColuna() {
		return coluna;
	}
	public int getLinha() {
		return linha;
	}
	
	protected Posicao toPosicao() {
		return new Posicao(8 - linha, coluna - 'a');
	}
	
	protected static PosicaoXadrez daPosicao(Posicao posicao) {
		return new PosicaoXadrez((char)('a' - posicao.getColuna()), 8 - posicao.getLinha());
	}
	@Override
	public String toString() {
		return "" + coluna + linha;
	}
}

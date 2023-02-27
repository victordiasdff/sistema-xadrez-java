package xadrez;

import boardgame.Peca;
import boardgame.Posicao;
import boardgame.Tabuleiro;

public abstract class PecaXadrez extends Peca {
	
	private Cor cor;
	private int contagemMovimento;

	public PecaXadrez(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro);
		this.cor = cor;
	}

	public Cor getCor() {
		return cor;
	}
	
	public int getContagemMovimento() {
		return contagemMovimento;
	}
	
	public void incrementoContagemMovimento() {
		contagemMovimento++;
	}
	
	public void decrementoContagemMovimento() {
		contagemMovimento--;
	}
	
	public PosicaoXadrez getPosicaoXadrex() {
		return PosicaoXadrez.daPosicao(posicao);
	}
	
	protected boolean existePeçaOponente(Posicao posicao) {
		PecaXadrez p = (PecaXadrez)getTabuleiro().peca(posicao);
		return p != null && p.getCor()!= cor;
	}
}

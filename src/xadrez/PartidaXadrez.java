package xadrez;

import boardgame.Peca;
import boardgame.Posicao;
import boardgame.Tabuleiro;
import xadrez.pecas.Rei;
import xadrez.pecas.Torre;

public class PartidaXadrez {
	
	private Tabuleiro tabuleiro;
	
	public PartidaXadrez() {
		tabuleiro = new Tabuleiro(8, 8);
		setupInicial();
	}
	public PecaXadrez[][] getPecas(){
		PecaXadrez[][] mat = new PecaXadrez[tabuleiro.getLinhas()][tabuleiro.getColunas()];
		for (int i=0; i<tabuleiro.getLinhas(); i++) {
			for (int j=0; j<tabuleiro.getColunas(); j++) {
				mat[i][j] = (PecaXadrez)tabuleiro.peca(i, j);
			}
				
		}
		return mat;
	}
	
	public PecaXadrez performMovimentoXadrez(PosicaoXadrez origemPosicao, PosicaoXadrez alvoPosicao) {
		Posicao origem = origemPosicao.toPosicao();
		Posicao alvo = alvoPosicao.toPosicao();
		validarOrigemPosicao(origem);
		Peca capturarPeca = fazerMovimento(origem, alvo);
		return (PecaXadrez)capturarPeca;
	}
	
	private Peca fazerMovimento(Posicao origem, Posicao alvo) {
		Peca p = tabuleiro.removePeca(origem);
		Peca capturarPeca = tabuleiro.removePeca(alvo);
		tabuleiro.lugarPeca(p, alvo);
		return capturarPeca;
	}
	
	private void validarOrigemPosicao(Posicao posicao) {
		if(!tabuleiro.haUmaPeca(posicao)) {
			throw new ExceptionXadrez("Não existe peça na posição de origem ");
		}
	}
	
	private void colocarNovaPeca(char coluna, int linha, PecaXadrez peca) {
		tabuleiro.lugarPeca(peca, new PosicaoXadrez(coluna, linha).toPosicao());
	}
	
	private void setupInicial() {
		colocarNovaPeca('C', 1, new Torre(tabuleiro, Cor.WHITE));
		colocarNovaPeca('C', 2, new Torre(tabuleiro, Cor.WHITE));
		colocarNovaPeca('D', 2, new Torre(tabuleiro, Cor.WHITE));
		colocarNovaPeca('E', 2, new Torre(tabuleiro, Cor.WHITE));
		colocarNovaPeca('E', 1, new Torre(tabuleiro, Cor.WHITE));
		colocarNovaPeca('D', 1, new Rei(tabuleiro, Cor.WHITE));
		
		colocarNovaPeca('C', 7, new Torre(tabuleiro, Cor.BLACK));
		colocarNovaPeca('C', 8, new Torre(tabuleiro, Cor.BLACK));
		colocarNovaPeca('D', 7, new Torre(tabuleiro, Cor.BLACK));
		colocarNovaPeca('E', 7, new Torre(tabuleiro, Cor.BLACK));
		colocarNovaPeca('E', 8, new Torre(tabuleiro, Cor.BLACK));
		colocarNovaPeca('D', 7, new Rei(tabuleiro, Cor.BLACK));
		
	}
	
}

package xadrez;

import java.util.ArrayList;
import java.util.List;

import boardgame.Peca;
import boardgame.Posicao;
import boardgame.Tabuleiro;
import xadrez.pecas.Rei;
import xadrez.pecas.Torre;

public class PartidaXadrez {
	
	private int vez;
	private Cor jogadorAtual;
	private Tabuleiro tabuleiro;
	
	private List<Peca> pecasNoTabuleiro;
	private List<Peca> capturarPecas;
	
	public PartidaXadrez() {
		tabuleiro = new Tabuleiro(8, 8);
		vez = 1;
		jogadorAtual = Cor.WHITE;
		pecasNoTabuleiro = new ArrayList<>();
		capturarPecas = new ArrayList<>();	
		setupInicial();
	}
	
	public int getVez() {
		return vez;
	}
	
	public Cor getJogadorAtual() {
		return jogadorAtual;
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
	
	public boolean[][] possivelMovimento(PosicaoXadrez origemPosicao) {
		Posicao posicao = origemPosicao.toPosicao();
		validarOrigemPosicao(posicao);
		return tabuleiro.peca(posicao).possivelMovimento();
	}
	
	
	public PecaXadrez performMovimentoXadrez(PosicaoXadrez origemPosicao, PosicaoXadrez alvoPosicao) {
		Posicao origem = origemPosicao.toPosicao();
		Posicao alvo = alvoPosicao.toPosicao();
		validarOrigemPosicao(origem);
		validarAlvoPosicao(origem, alvo);
		Peca capturarPeca = fazerMovimento(origem, alvo);
		proximaVez();
		return (PecaXadrez)capturarPeca;
	}
	
	private Peca fazerMovimento(Posicao origem, Posicao alvo) {
		Peca p = tabuleiro.removePeca(origem);
		Peca capturarPeca = tabuleiro.removePeca(alvo);
		tabuleiro.lugarPeca(p, alvo);
		
		if(capturarPeca != null) {
			pecasNoTabuleiro.remove(capturarPeca);
			capturarPecas.add(capturarPeca);
		}
		return capturarPeca;
	}
	
	private void validarOrigemPosicao(Posicao posicao) {
		if(!tabuleiro.haUmaPeca(posicao)) {
			throw new ExceptionXadrez("Não existe peça na posição de origem ");
		}
		if (jogadorAtual != ((PecaXadrez)tabuleiro.peca(posicao)).getCor()) {
			throw new ExceptionXadrez("A peça escolhida não é sua.");
		}
		if (!tabuleiro.peca(posicao).existeMovimentoPossivel()) {
			throw new ExceptionXadrez("Não existe movimentos possiveis para a peça escolhida.");
		}
	}
	
	private void validarAlvoPosicao(Posicao origem, Posicao alvo) {
		if (!tabuleiro.peca(origem).possivelMovimento(alvo)) {
			throw new ExceptionXadrez("A peça escolhida não pode se mover para a posição de destino.");
		}
	}
	
	private void proximaVez() {
		vez++;
		jogadorAtual = (jogadorAtual == Cor.WHITE) ? Cor.BLACK : Cor.WHITE;
	}
		
	private void colocarNovaPeca(char coluna, int linha, PecaXadrez peca) {
		tabuleiro.lugarPeca(peca, new PosicaoXadrez(coluna, linha).toPosicao());
		pecasNoTabuleiro.add(peca);
	}
	
	private void setupInicial() {
		colocarNovaPeca('A', 1, new Torre(tabuleiro, Cor.WHITE));
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

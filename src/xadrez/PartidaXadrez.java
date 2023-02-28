package xadrez;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import boardgame.Peca;
import boardgame.Posicao;
import boardgame.Tabuleiro;
import xadrez.pecas.Bispo;
import xadrez.pecas.Cavalo;
import xadrez.pecas.Peao;
import xadrez.pecas.Rainha;
import xadrez.pecas.Rei;
import xadrez.pecas.Torre;

public class PartidaXadrez {
	
	private int vez;
	private Cor jogadorAtual;
	private Tabuleiro tabuleiro;
	private boolean check;
	private boolean checkMate;
	private PecaXadrez passantVulneravel;
	
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
	
	public boolean getCheck() {
		return check;
	}
	
	public boolean getCheckMate() {
		return checkMate;
	}
	
	public PecaXadrez getPassantVulneravel() {
		return passantVulneravel;
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
		
		if(testCheck(jogadorAtual)) {
			desfazerMovimento(origem, alvo, capturarPeca);
			throw new ExceptionXadrez("Voce nao pode se colocar em check");
		}
		
		PecaXadrez pecaMovida = (PecaXadrez)tabuleiro.peca(alvo);
		
		check = (testCheck(oponente(jogadorAtual))) ? true : false;
		if(testCheckMate(oponente(jogadorAtual))) {
			checkMate = true;	
		}
		else {
			proximaVez();
		}
		
		//Movimento especial Passant
		if(pecaMovida instanceof Peao && (alvo.getLinha() == origem.getLinha() - 2 || alvo.getLinha() == origem.getLinha() + 2)) {
			passantVulneravel = pecaMovida;	
		}
		else {
			passantVulneravel = null;
		}
		
		return (PecaXadrez)capturarPeca;
	}
	
	private Peca fazerMovimento(Posicao origem, Posicao alvo) {
		PecaXadrez p = (PecaXadrez)tabuleiro.removePeca(origem);
		p.incrementoContagemMovimento();
		Peca capturarPeca = tabuleiro.removePeca(alvo);
		tabuleiro.lugarPeca(p, alvo);
		
		if(capturarPeca != null) {
			pecasNoTabuleiro.remove(capturarPeca);
			capturarPecas.add(capturarPeca);
		}
		//Movimento especial pequeno
		
		if (p instanceof Rei && alvo.getColuna() == origem.getColuna() + 2) {
			Posicao origemT = new Posicao(origem.getLinha(), origem.getColuna() + 3);
			Posicao alvoT = new Posicao(origem.getLinha(), origem.getColuna() + 1);
			PecaXadrez torre = (PecaXadrez)tabuleiro.removePeca(origemT);
			tabuleiro.lugarPeca(torre, alvoT);
			torre.incrementoContagemMovimento();
		}
		
		//Movimento especial grande
		
		if (p instanceof Rei && alvo.getColuna() == origem.getColuna() - 2) {
			Posicao origemT = new Posicao(origem.getLinha(), origem.getColuna() - 4);
			Posicao alvoT = new Posicao(origem.getLinha(), origem.getColuna() - 1);
			PecaXadrez torre = (PecaXadrez)tabuleiro.removePeca(origemT);
			tabuleiro.lugarPeca(torre, alvoT);
			torre.incrementoContagemMovimento();
		}
		
		//Movimento especial en passant
		if(p instanceof Peao) {
			if(origem.getColuna() != alvo.getColuna() && capturarPeca == null) {
				Posicao posicaoPeao;
				if (p.getCor() == Cor.WHITE) {
					posicaoPeao = new Posicao(alvo.getLinha() + 1, alvo.getColuna());
				}
				else {
					posicaoPeao = new Posicao(alvo.getLinha() - 1, alvo.getColuna());
				}
				capturarPeca = tabuleiro.removePeca(posicaoPeao);
				capturarPecas.add(capturarPeca);
				pecasNoTabuleiro.remove(capturarPeca);
			}
		}
		
		return capturarPeca;
	}
	private void desfazerMovimento(Posicao origem, Posicao alvo, Peca capturarPeca) {
		PecaXadrez p = (PecaXadrez)tabuleiro.removePeca(alvo);
		p.decrementoContagemMovimento();
		tabuleiro.lugarPeca(p, origem);
		
		if(capturarPeca != null) {
			tabuleiro.lugarPeca(capturarPeca, alvo);
			capturarPecas.remove(capturarPeca);
			pecasNoTabuleiro.add(capturarPeca);
			
		}
		
		//Desfazer Movimento Roque pequeno
		
		if (p instanceof Rei && alvo.getColuna() == origem.getColuna() + 2) {
			Posicao origemT = new Posicao(origem.getLinha(), origem.getColuna() + 3);
			Posicao alvoT = new Posicao(origem.getLinha(), origem.getColuna() + 1);
			PecaXadrez torre = (PecaXadrez)tabuleiro.removePeca(alvoT);
			tabuleiro.lugarPeca(torre, origemT);
			torre.decrementoContagemMovimento();
				}
				
		//Desfazer Movimento Roque grande
				
		if (p instanceof Rei && alvo.getColuna() == origem.getColuna() - 2) {
			Posicao origemT = new Posicao(origem.getLinha(), origem.getColuna() - 4);
			Posicao alvoT = new Posicao(origem.getLinha(), origem.getColuna() - 1);
			PecaXadrez torre = (PecaXadrez)tabuleiro.removePeca(alvoT);
			tabuleiro.lugarPeca(torre, origemT);
			torre.decrementoContagemMovimento();
		}
		//Desfazer Movimento especial en passant
		if(p instanceof Peao) {
			if(origem.getColuna() != alvo.getColuna() && capturarPeca == passantVulneravel) {
				PecaXadrez peao = (PecaXadrez)tabuleiro.removePeca(alvo);		
				Posicao posicaoPeao;
				if (p.getCor() == Cor.WHITE) {
					posicaoPeao = new Posicao(3, alvo.getColuna());
				}
				else {
					posicaoPeao = new Posicao(4, alvo.getColuna());
				}
				
				tabuleiro.lugarPeca(peao, posicaoPeao);
			}
		}
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
	
	private Cor oponente(Cor cor) {
		return (cor == Cor.WHITE) ? Cor.BLACK : Cor.WHITE;
	}
	
	private PecaXadrez Rei(Cor cor) {
		List<Peca> list = pecasNoTabuleiro.stream().filter(x ->((PecaXadrez)x).getCor() == cor).collect(Collectors.toList());
		for (Peca p : list) {
			if (p instanceof Rei) {
				return (PecaXadrez)p;
			}
		}
		throw new IllegalStateException("Não existe o Rei" + cor + "no tabuleiro");
	}
	
	private boolean testCheck(Cor cor) {
		Posicao posicaoRei = Rei(cor).getPosicaoXadrex().toPosicao();
		List<Peca> oponentePecas = pecasNoTabuleiro.stream().filter(x ->((PecaXadrez)x).getCor() == oponente(cor)).collect(Collectors.toList());
		for (Peca p : oponentePecas) {
			boolean [][] mat = p.possivelMovimento();
			if(mat[posicaoRei.getLinha()][posicaoRei.getColuna()]) {
				
				return true;
			}		
		}
		return false;
	}
	
	private boolean testCheckMate(Cor cor) {
		if(!testCheck(cor)) {
			return false;
		}
		List<Peca> list = pecasNoTabuleiro.stream().filter(x ->((PecaXadrez)x).getCor() == cor).collect(Collectors.toList());
		for(Peca p : list) {
			boolean [][] mat = p.possivelMovimento();
			for(int i=0; i<tabuleiro.getLinhas(); i++) {
				for(int j=0; j<tabuleiro.getColunas(); i++) {
					if(mat[i][j]) {
						Posicao origem = ((PecaXadrez)p).getPosicaoXadrex().toPosicao();
						Posicao alvo = new Posicao(i, j);
						Peca capturarPeca = fazerMovimento(origem, alvo);
						boolean testCheck = testCheck(cor);
						desfazerMovimento(origem, alvo, capturarPeca);
						if(!testCheck) {
							return false;
						}
					}
				}
			}
		}
		return true;
		
		
	}
	
	private void setupInicial() {
		colocarNovaPeca('a', 1, new Torre(tabuleiro, Cor.WHITE));
		colocarNovaPeca('b', 1, new Cavalo(tabuleiro, Cor.WHITE));
		colocarNovaPeca('c', 1, new Bispo(tabuleiro, Cor.WHITE));
		colocarNovaPeca('d', 1, new Rainha(tabuleiro, Cor.WHITE));
		colocarNovaPeca('e', 1, new Rei(tabuleiro, Cor.WHITE, this));
		colocarNovaPeca('f', 1, new Bispo(tabuleiro, Cor.WHITE));
		colocarNovaPeca('g', 1, new Cavalo(tabuleiro, Cor.WHITE));
		colocarNovaPeca('h', 1, new Torre(tabuleiro, Cor.WHITE));
		colocarNovaPeca('a', 2, new Peao(tabuleiro, Cor.WHITE, this));
		colocarNovaPeca('b', 2, new Peao(tabuleiro, Cor.WHITE, this));
		colocarNovaPeca('c', 2, new Peao(tabuleiro, Cor.WHITE, this));
		colocarNovaPeca('d', 2, new Peao(tabuleiro, Cor.WHITE, this));
		colocarNovaPeca('e', 2, new Peao(tabuleiro, Cor.WHITE, this));
		colocarNovaPeca('f', 2, new Peao(tabuleiro, Cor.WHITE, this));
		colocarNovaPeca('g', 2, new Peao(tabuleiro, Cor.WHITE, this));
		colocarNovaPeca('h', 2, new Peao(tabuleiro, Cor.WHITE, this));
		
		colocarNovaPeca('a', 8, new Torre(tabuleiro, Cor.BLACK));
		colocarNovaPeca('b', 8, new Cavalo(tabuleiro, Cor.BLACK));
		colocarNovaPeca('c', 8, new Bispo(tabuleiro, Cor.BLACK));
		colocarNovaPeca('d', 8, new Rainha(tabuleiro, Cor.BLACK));
		colocarNovaPeca('e', 8, new Rei(tabuleiro, Cor.BLACK, this));
		colocarNovaPeca('f', 8, new Bispo(tabuleiro, Cor.BLACK));
		colocarNovaPeca('g', 8, new Cavalo(tabuleiro, Cor.BLACK));
		colocarNovaPeca('h', 8, new Torre(tabuleiro, Cor.BLACK));
		colocarNovaPeca('a', 7, new Peao(tabuleiro, Cor.BLACK, this));
		colocarNovaPeca('b', 7, new Peao(tabuleiro, Cor.BLACK, this));
		colocarNovaPeca('c', 7, new Peao(tabuleiro, Cor.BLACK, this));
		colocarNovaPeca('d', 7, new Peao(tabuleiro, Cor.BLACK, this));
		colocarNovaPeca('e', 7, new Peao(tabuleiro, Cor.BLACK, this));
		colocarNovaPeca('f', 7, new Peao(tabuleiro, Cor.BLACK, this));
		colocarNovaPeca('g', 7, new Peao(tabuleiro, Cor.BLACK, this));
		colocarNovaPeca('h', 7, new Peao(tabuleiro, Cor.BLACK, this));
	
	}
	
}

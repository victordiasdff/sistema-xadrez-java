package xadrez.pecas;

import boardgame.Posicao;
import boardgame.Tabuleiro;
import xadrez.Cor;
import xadrez.PartidaXadrez;
import xadrez.PecaXadrez;

public class Peao extends PecaXadrez {
	
	private PartidaXadrez partidaXadrez;
	
	public Peao(Tabuleiro tabuleiro, Cor cor, PartidaXadrez partidaXadrez) {
		super(tabuleiro, cor);
		this.partidaXadrez = partidaXadrez;
	}

	@Override
	public boolean[][] possivelMovimento() {
		boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];
		
		Posicao p = new Posicao(0, 0);
		
		if (getCor() == Cor.WHITE) {
			p.setValores(posicao.getLinha() - 1, posicao.getColuna());
			if(getTabuleiro().posicaoExists(p) && !getTabuleiro().haUmaPeca(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			p.setValores(posicao.getLinha() - 2, posicao.getColuna());
			Posicao p2 = new Posicao(posicao.getLinha() - 1, posicao.getColuna());
			if(getTabuleiro().posicaoExists(p) && !getTabuleiro().haUmaPeca(p) && getTabuleiro().posicaoExists(p2) && !getTabuleiro().haUmaPeca(p2) && getContagemMovimento() == 0) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			p.setValores(posicao.getLinha() - 1, posicao.getColuna() - 1);
			if(getTabuleiro().posicaoExists(p) && existePeçaOponente(p)){
				mat[p.getLinha()][p.getColuna()] = true;
			}
			p.setValores(posicao.getLinha() - 1, posicao.getColuna() + 1);
			if(getTabuleiro().posicaoExists(p) && existePeçaOponente(p)){
				mat[p.getLinha()][p.getColuna()] = true;
			}
			//Movimento especial passant WHITE
			if (posicao.getLinha() == 3) {
				Posicao esquerda = new Posicao(posicao.getLinha(), posicao.getColuna() - 1);
				if(getTabuleiro().posicaoExists(esquerda) && existePeçaOponente(esquerda) && getTabuleiro().peca(esquerda) == partidaXadrez.getPassantVulneravel());
					mat[esquerda.getLinha() - 1][esquerda.getColuna()] = true;		
			}
			if (posicao.getLinha() == 3) {
				Posicao esquerda = new Posicao(posicao.getLinha(), posicao.getColuna() - 1);
				if(getTabuleiro().posicaoExists(esquerda) && existePeçaOponente(esquerda) && getTabuleiro().peca(esquerda) == partidaXadrez.getPassantVulneravel());
					mat[esquerda.getLinha() - 1][esquerda.getColuna()] = true;			
			}
				Posicao direita = new Posicao(posicao.getLinha(), posicao.getColuna() + 1);
				if(getTabuleiro().posicaoExists(direita) && existePeçaOponente(direita) && getTabuleiro().peca(direita) == partidaXadrez.getPassantVulneravel());
					mat[direita.getLinha() - 1][direita.getColuna()] = true;
					
			}
			else {
				p.setValores(posicao.getLinha() + 1, posicao.getColuna());
				if(getTabuleiro().posicaoExists(p) && !getTabuleiro().haUmaPeca(p)) {
					mat[p.getLinha()][p.getColuna()] = true;
				}
				p.setValores(posicao.getLinha() + 2, posicao.getColuna());
				Posicao p2 = new Posicao(posicao.getLinha() + 1, posicao.getColuna());
				if(getTabuleiro().posicaoExists(p) && !getTabuleiro().haUmaPeca(p) && getTabuleiro().posicaoExists(p2) && !getTabuleiro().haUmaPeca(p2) && getContagemMovimento() == 0) {
					mat[p.getLinha()][p.getColuna()] = true;
				}
				p.setValores(posicao.getLinha() + 1, posicao.getColuna() - 1);
				if(getTabuleiro().posicaoExists(p) && existePeçaOponente(p)){
					mat[p.getLinha()][p.getColuna()] = true;
				}
				p.setValores(posicao.getLinha() + 1, posicao.getColuna() + 1);
				if(getTabuleiro().posicaoExists(p) && existePeçaOponente(p)){
					mat[p.getLinha()][p.getColuna()] = true;
				}
				//Movimento especial passant BLACK
				if (posicao.getLinha() == 4) {
					Posicao esquerda = new Posicao(posicao.getLinha(), posicao.getColuna() - 1);
					if(getTabuleiro().posicaoExists(esquerda) && existePeçaOponente(esquerda) && getTabuleiro().peca(esquerda) == partidaXadrez.getPassantVulneravel());
						mat[esquerda.getLinha() + 1][esquerda.getColuna()] = true;		
				}
				if (posicao.getLinha() == 3) {
					Posicao esquerda = new Posicao(posicao.getLinha(), posicao.getColuna() - 1);
					if(getTabuleiro().posicaoExists(esquerda) && existePeçaOponente(esquerda) && getTabuleiro().peca(esquerda) == partidaXadrez.getPassantVulneravel());
						mat[esquerda.getLinha() - 1][esquerda.getColuna()] = true;			
				}
					Posicao direita = new Posicao(posicao.getLinha(), posicao.getColuna() + 1);
					if(getTabuleiro().posicaoExists(direita) && existePeçaOponente(direita) && getTabuleiro().peca(direita) == partidaXadrez.getPassantVulneravel());
						mat[direita.getLinha() + 1][direita.getColuna()] = true;
			}
			
		return mat;
	}
	
	@Override
	public String toString() {
		return "P";
	}

}

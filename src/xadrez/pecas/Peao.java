package xadrez.pecas;

import boardgame.Posicao;
import boardgame.Tabuleiro;
import xadrez.PecaXadrez;
import xadrez.Cor;

public class Peao extends PecaXadrez {
	
	public Peao(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro, cor);
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
			}
			
		return mat;
	}
	
	@Override
	public String toString() {
		return "p";
	}

}

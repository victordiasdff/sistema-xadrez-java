package xadrez.pecas;

import boardgame.Posicao;
import boardgame.Tabuleiro;
import xadrez.Cor;
import xadrez.PecaXadrez;

public class Rainha extends PecaXadrez {

	public Rainha(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro, cor);
		
	}
	
	@Override
	public String toString() {
		return "Q";
	}
	
	@Override
	public boolean[][] possivelMovimento() {
		boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];
		
		Posicao p = new Posicao(0, 0);
		
		//Cima
		p.setValores(posicao.getLinha() - 1, posicao.getColuna());
		while (getTabuleiro().posicaoExists(p) && !getTabuleiro().haUmaPeca(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setLinha(p.getLinha() - 1);
		}
		if (getTabuleiro().posicaoExists(p) && existePeçaOponente(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		//Esquerda
		p.setValores(posicao.getLinha(), posicao.getColuna() - 1);
		while (getTabuleiro().posicaoExists(p) && !getTabuleiro().haUmaPeca(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setColuna(p.getColuna() - 1);
		}
		if (getTabuleiro().posicaoExists(p) && existePeçaOponente(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		//Direita
		p.setValores(posicao.getLinha(), posicao.getColuna() + 1);
		while (getTabuleiro().posicaoExists(p) && !getTabuleiro().haUmaPeca(p)) {
		mat[p.getLinha()][p.getColuna()] = true;
		p.setColuna(p.getColuna() + 1);
		}
		if (getTabuleiro().posicaoExists(p) && existePeçaOponente(p)) {
		mat[p.getLinha()][p.getColuna()] = true;
				}
		//Baixo
		p.setValores(posicao.getLinha() + 1, posicao.getColuna());
		while (getTabuleiro().posicaoExists(p) && !getTabuleiro().haUmaPeca(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setLinha(p.getLinha() + 1);
		}
		if (getTabuleiro().posicaoExists(p) && existePeçaOponente(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		//Noroeste
				p.setValores(posicao.getLinha() - 1, posicao.getColuna() - 1);
				while (getTabuleiro().posicaoExists(p) && !getTabuleiro().haUmaPeca(p)) {
					mat[p.getLinha()][p.getColuna()] = true;
					p.setValores(p.getLinha() - 1, p.getColuna() - 1);
				}
				if (getTabuleiro().posicaoExists(p) && existePeçaOponente(p)) {
					mat[p.getLinha()][p.getColuna()] = true;
				}
				//Nordeste
				p.setValores(posicao.getLinha() - 1, posicao.getColuna() + 1);
				while (getTabuleiro().posicaoExists(p) && !getTabuleiro().haUmaPeca(p)) {
					mat[p.getLinha()][p.getColuna()] = true;
					p.setValores(p.getLinha() - 1, p.getColuna() + 1);
				}
				if (getTabuleiro().posicaoExists(p) && existePeçaOponente(p)) {
					mat[p.getLinha()][p.getColuna()] = true;
				}
				//Sudeste
				p.setValores(posicao.getLinha() + 1, posicao.getColuna() + 1);
				while (getTabuleiro().posicaoExists(p) && !getTabuleiro().haUmaPeca(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
				p.setValores(p.getLinha() + 1, p.getColuna() + 1);
				}
				if (getTabuleiro().posicaoExists(p) && existePeçaOponente(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
						}
				//Sudoeste
				p.setValores(posicao.getLinha() + 1, posicao.getColuna() - 1);
				while (getTabuleiro().posicaoExists(p) && !getTabuleiro().haUmaPeca(p)) {
					mat[p.getLinha()][p.getColuna()] = true;
					p.setValores(p.getLinha() + 1, p.getColuna() - 1);
				}
				if (getTabuleiro().posicaoExists(p) && existePeçaOponente(p)) {
					mat[p.getLinha()][p.getColuna()] = true;
				}
				
		
		return mat;
	}
}

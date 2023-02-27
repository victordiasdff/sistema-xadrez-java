package boardgame;

public class Tabuleiro {
	
	private int linhas;
	private int colunas;
	private Peca[][] pecas;
	
	public Tabuleiro(int linhas, int colunas) {
		if (linhas <1 || colunas <1 ) {
			throw new TabuleiroException("Erro criando tabuleiro, é necessario que tenha uma linha e uma coluna");
		}
		this.linhas = linhas;
		this.colunas = colunas;
		pecas = new Peca[linhas][colunas];
	}

	public int getLinhas() {
		return linhas;
	}

	public int getColunas() {
		return colunas;
	}
	
	public Peca peca(int linha, int coluna) {
		if(!posicaoExists(linha, coluna)) {
			throw new TabuleiroException("Posição não existe no tabuleiro");
		}
		return pecas[linha][coluna];
	}
	
	public Peca peca(Posicao posicao) {
		if(!posicaoExists(posicao)) {
			throw new TabuleiroException("Posição não existe no tabuleiro");
	}
		return pecas[posicao.getLinha()][posicao.getColuna()];
	}
	
	public void lugarPeca(Peca peca, Posicao posicao) {
		if (haUmaPeca(posicao)) {
			throw new TabuleiroException("Há uma peça nesta posição " + posicao);
		}
		pecas[posicao.getLinha()][posicao.getColuna()] = peca;
		peca.posicao = posicao;
	}
	
	private boolean posicaoExists(int linha, int coluna) {
		return linha >= 0 && linha < linhas && coluna >=0 && coluna < colunas;
	}
	
	public boolean posicaoExists(Posicao posicao) {
		return posicaoExists(posicao.getLinha(), posicao.getColuna());		
	}
	
	public boolean haUmaPeca(Posicao posicao) {
		if (haUmaPeca(posicao)) {
			throw new TabuleiroException("Há uma peça nesta posição " + posicao);
		}
		return peca(posicao) != null;
	}
}

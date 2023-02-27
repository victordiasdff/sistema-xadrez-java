package application;

import java.util.Scanner;

import xadrez.PartidaXadrez;
import xadrez.PecaXadrez;
import xadrez.PosicaoXadrez;

public class Program {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		PartidaXadrez partidaXadrez = new PartidaXadrez();
		
		while (true) {	
		UI.printTabuleiro(partidaXadrez.getPecas());
		System.out.println();
		System.out.print("Posição de origem: ");
		PosicaoXadrez origem = UI.lerPosicaoXadrez(sc);
		
		System.out.println();
		System.out.print("Posição de destino: ");
		PosicaoXadrez alvo = UI.lerPosicaoXadrez(sc);
		
		PecaXadrez capturarPeca = partidaXadrez.performMovimentoXadrez(origem, alvo);
		}
	}
	
}

package application;

import java.util.InputMismatchException;
import java.util.Scanner;

import xadrez.ExceptionXadrez;
import xadrez.PartidaXadrez;
import xadrez.PecaXadrez;
import xadrez.PosicaoXadrez;

public class Program {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		PartidaXadrez partidaXadrez = new PartidaXadrez();
		
		while (true) {	
				try {
				UI.limparTela();
			UI.printTabuleiro(partidaXadrez.getPecas());
			System.out.println();
			System.out.print("Posição de origem: ");
			PosicaoXadrez origem = UI.lerPosicaoXadrez(sc);
			
			System.out.println();
			System.out.print("Posição de destino: ");
			PosicaoXadrez alvo = UI.lerPosicaoXadrez(sc);
		
			PecaXadrez capturarPeca = partidaXadrez.performMovimentoXadrez(origem, alvo);
				}
				catch (ExceptionXadrez e) {
					System.out.println(e.getMessage());
					sc.nextLine();
				}
				catch (InputMismatchException e) {
					System.out.println(e.getMessage());
					sc.nextLine();
				}
		}
	}
}

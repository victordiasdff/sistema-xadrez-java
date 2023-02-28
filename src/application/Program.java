package application;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import xadrez.ExceptionXadrez;
import xadrez.PartidaXadrez;
import xadrez.PecaXadrez;
import xadrez.PosicaoXadrez;

public class Program {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		PartidaXadrez partidaXadrez = new PartidaXadrez();
		List<PecaXadrez> capturados = new ArrayList<>();

		while (!partidaXadrez.getCheckMate()) {
			try {
				UI.limparTela();
				UI.printPartida(partidaXadrez, capturados);
				System.out.println();
				System.out.print("Posição de origem: ");
				PosicaoXadrez origem = UI.lerPosicaoXadrez(sc);

				boolean[][] possivelMovimento = partidaXadrez.possivelMovimento(origem);
				UI.limparTela();
				UI.printTabuleiro(partidaXadrez.getPecas(), possivelMovimento);
				System.out.println();
				System.out.print("Posição Alvo: ");
				PosicaoXadrez alvo = UI.lerPosicaoXadrez(sc);

				PecaXadrez capturarPeca = partidaXadrez.performMovimentoXadrez(origem, alvo);

				if (capturarPeca != null) {
					capturados.add(capturarPeca);
				}

				if (partidaXadrez.getPromocao() != null) {
					System.out.print("Qual a peça para promoção (B/C/T/Q): ");
					String tipo = sc.nextLine().toUpperCase();
					while (!tipo.equals("B") && !tipo.equals("C") && !tipo.equals("T") && !tipo.equals("Q")) {
						System.out.print("Valor Invalido!! Qual a peça para promoção (B/C/T/Q): ");
						tipo = sc.nextLine().toUpperCase();
					}
					partidaXadrez.substituirPecaPromovida(tipo);
				}
			} catch (ExceptionXadrez e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			} catch (InputMismatchException e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			}
		}
		UI.limparTela();
		UI.printPartida(partidaXadrez, capturados);
	}
}

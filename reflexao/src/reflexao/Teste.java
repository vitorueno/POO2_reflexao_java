package reflexao;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Scanner;

public class Teste {

	public static void main(String[] args) {

		String nclasse = "reflexao.Pessoa"; // opção padrão
		
		boolean sair = false;
		while (!sair) {
			System.out.println("Classes disponíveis: ");
			System.out.println("1 - Pessoa");
			System.out.println("2 - Dinossauro");
			Scanner scanner = new Scanner(System.in);
			System.out.print("\nDigite o número de uma das classes: ");
			int opcao = scanner.nextInt();

			switch (opcao) {

			case 1:
				sair = true;
				break;

			case 2:
				nclasse = "reflexao.Dinossauro";
				sair = true;
				break;

			default:
				break;
			}
		}
		
		System.out.println();
		Object p = cadastrar(nclasse);

		if (nclasse == "reflexao.Pessoa") {
			cadastrarCaloriasUmaSemana((Pessoa) p);
			caloriasCLI((Pessoa) p);
		}

	}

	private static void caloriasCLI(Pessoa p) {
		Scanner scanner = new Scanner(System.in);
		boolean sair = false;
		while (!sair) {
			System.out.println("\n======================Lista de ações======================");
			System.out.println("1- Cadastrar Relatório de calorias de uma semana");
			System.out.println("2- Verificar média de calorias ingeridas para cada semana");
			System.out.println("3- Projetar saldo de calorias em um ano baseado em média");
			System.out.println("0- Sair");

			String a = "";
			System.out.print("Digite uma opção: ");
			a = scanner.next();
			System.out.println("==========================================================");

			switch (a) {

			case "1":
				cadastrarCaloriasUmaSemana(p);
				break;

			case "2":
				gerarMediasCaloricasSemanais(p);
				break;

			case "3":
				projetarSaldoCaloricoDeUmAno(p);
				break;

			case "0":
				sair = true;
				break;

			default:
				System.out.println("Opção inválida, tente novamente.");
				break;
			}
		}
	}

	private static Object cadastrar(String nomeClasse) {
		Object p = null;
		try {
			Class<?> c = Class.forName(nomeClasse); // pegando a classe

			System.out.println("Cadastrando um(a) " + c.getSimpleName() + ":\n"); // informando o que estamos cadastrando
			Scanner scanner = new Scanner(System.in);

			try {
				Constructor init = c.getDeclaredConstructors()[0];
				try { // tentando instanciar um objeto e solicitar dados para o usuário
					p = (Object) init.newInstance();
					for (Method m : c.getDeclaredMethods()) { // lendo métodos da classe
						if (isSetter(m) && m.isAnnotationPresent(paraSolicitar.class)) { // deve ser solicitado ao usuário?
							System.out.print("Insira o seu/sua " + deSetterParaPropriedade(m.getName()) + ": ");
							String attrLido = scanner.next(); // pegando valor digitado pelo usuário
							String tipo = m.getAnnotation(paraSolicitar.class).tipo(); // pegando o valor da anotação

							if (tipo.equals("int")) { // convertendo para int caso informado pela anotação
								m.invoke(p, Integer.parseInt(attrLido));
							} else {
								m.invoke(p, attrLido);
							}

						}
					}

				} catch (Exception e) {
					System.out.println("Erro ao tentar criar instância ou executar sets: ");
					e.printStackTrace();
				}
			} catch (Exception e) {
				System.out.println("Erro ao tentar chamar o construtor: ");
				e.printStackTrace();
			}

		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}

		return p;
	}

	private static void cadastrarCaloriasUmaSemana(Pessoa p) {
		System.out.println("\nCadastrando as calorias ingeridas em uma semana: \n");

		Scanner scanner = new Scanner(System.in);

		int calorias[] = new int[7];
		for (int i = 0; i < 7; i++) {
			int numSemana = i + 1;
			System.out.print("Digite a quantidade de calorias que você ingeriu no dia " + numSemana + ": ");
			calorias[i] = scanner.nextInt();
		}

		try {
			p.adicionarSemana();
			p.setCaloriasSemana(p.ultimaSemana, calorias);
			System.out.println(p);

		} catch (Exception e) {
			System.out.println("Erro ao cadastrar calorias ingeridas na semana: ");
			e.printStackTrace();
		}
	}

	private static void gerarMediasCaloricasSemanais(Pessoa p) {
		System.out.println("\nImprimindo média de cada semana: \n");

		double medias[] = p.gerarMedias();
		for (int i = 0; i < medias.length; i++) {
			int numSemana = i + 1;
			System.out.println("Média da semana " + numSemana + ": " + medias[i]);
		}
	}

	private static void projetarSaldoCaloricoDeUmAno(Pessoa p) {
		System.out.println("\nProjetando saldo de calorias em um ano: \n");

		Scanner scanner = new Scanner(System.in);

		System.out.println("Semanas cadastradas: ");
		for (int i = 0; i < p.caloriasSemanas.length; i++) {
			if (p.caloriasSemanas[i] != null)
				System.out.println("- " + p.caloriasSemanas[i]);
		}

		System.out.print("\nDigite o número da semana que você quer se basear: ");
		int numSemana = scanner.nextInt();

		Relatorio r = p.caloriasSemanas[numSemana - 1];

		int t = 0;
		for (int cal : r.relatorioSemanal) {
			t += cal;
		}

		int totalAposUmAno = t * 4 * 12;
		int totalRecomendado = p.quantidadeCaloriasRecomendadasPorDia * 7 * 4 * 12;
		int saldoCalorico = totalAposUmAno - totalRecomendado;

		System.out.println("\nSe você mantiver o padrão de consumo desta semana por um ano, " + "você terá um saldo de "
				+ saldoCalorico + " calorias");

		if (saldoCalorico > 0) {
			System.out.println("isso significa que você consumiria mais calorias do que o ideal para este periodo");
			System.out.println(
					"talvez seja melhor consumir menos calorias do que nesta semana, ou começar a fazer alguns exercicios\n");

		} else if (saldoCalorico < 0) {
			System.out.println("isso significa que você consumiria menos calorias do que o ideal para este período");
			System.out.println("talvez seja melhor aumentar um pouco a quantidade de calorias ingeridas\n");
		} else {
			System.out.println("isso significa que você consumiria a quantidade ideal de calorias");
			System.out.println("continue assim\n");
		}

	}

	private static boolean isSetter(Method m) {
		return m.getName().startsWith("set") && m.getReturnType() == void.class && m.getParameterTypes().length != 0;
	}

	private static String deSetterParaPropriedade(String nomeSetter) {
		StringBuffer retorno = new StringBuffer();
		retorno.append(nomeSetter.substring(3, 4).toLowerCase());
		retorno.append(nomeSetter.substring(4));
		return retorno.toString();
	}

	public static void testesManuais() {
		Pessoa p = new Pessoa();
		p.setNome("José");
		p.setIdade(20);
		p.setPesoKG(65);

		System.out.println("TESTANDO REPRESENTAÇÃO PARA STRING:\n");
		System.out.println(p + "\n");

		System.out.println("ADICIONANDO SEMANAS:\n");

		p.adicionarSemana();
		p.adicionarSemana();
		System.out.println(p + "\n");

		System.out.println("ADICIONANDO QUANTIDADE DE CALORIAS:\n");
		p.setCaloriaDia(1, 1, 2000);
		p.setCaloriaDia(1, 2, 2100);
		p.setCaloriaDia(1, 3, 2000);
		p.setCaloriaDia(1, 4, 2000);
		p.setCaloriaDia(1, 5, 2000);
		p.setCaloriaDia(1, 6, 2000);
		p.setCaloriaDia(1, 7, 2000);
		System.out.println(p + "\n");
		int caloriasSemana2[] = { 2000, 2100, 2200, 2300, 2400, 2500, 2600 };
		p.setCaloriasSemana(2, caloriasSemana2);
		System.out.println(p + "\n");

		System.out.println("TESTANDO A MÉDIA SEMANAL:\n");
		System.out.println("Média da semana 1: " + p.caloriasSemanas[0].mediaSemanal());
		System.out.println("Média da semana 2: " + p.caloriasSemanas[1].mediaSemanal());
		System.out.println("Média das 3 semanas:" + Arrays.toString(p.gerarMedias()));
	}

}

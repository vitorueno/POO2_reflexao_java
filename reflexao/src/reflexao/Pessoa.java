package reflexao;


public class Pessoa {
	public int idade; 
	public String nome; 
    public int pesoKG;
    public int quantidadeCaloriasRecomendadasPorDia;
	public Relatorio caloriasSemanas[];
    public int ultimaSemana;
    
    public Pessoa() {
    	this.ultimaSemana = 0; 
    	this.caloriasSemanas = new Relatorio[100];
    	//		this.adicionarSemana();
	}

	public int getIdade() {
		return idade;
	}
	
	@paraSolicitar(tipo="int")
	public void setIdade(int idade) {
		this.idade = idade;
	}

	public String getNome() {
		return nome;
	}
	
	@paraSolicitar(tipo="String")
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public int getPesoKG() {
		return pesoKG;
	}
	
    public int getQuantidadeCaloriasRecomendadasPorDia() {
		return quantidadeCaloriasRecomendadasPorDia;
	}
    
    @paraSolicitar(tipo="int")
	public void setQuantidadeCaloriasRecomendadasPorDia(int quantidadeCaloriasRecomendadasPorDia) {
		this.quantidadeCaloriasRecomendadasPorDia = quantidadeCaloriasRecomendadasPorDia;
	}

	
	@Override
	public String toString() {
		String mensagem = "Pessoa [ nome=" + nome + ", idade=" + idade + ", peso=" + pesoKG + "kg, Qntd. cal. recomendadas diariamente=" + quantidadeCaloriasRecomendadasPorDia;
		for (int i = 0; i < this.ultimaSemana; i++) {
			mensagem += "\n\t{" + caloriasSemanas[i].toString() + "}";
		}
		mensagem += " ]";
		return mensagem;
	}
	
	@paraSolicitar(tipo="int")
	public void setPesoKG(int pesoKG) {
		this.pesoKG = pesoKG;
	}


	public void adicionarSemana() {
		this.caloriasSemanas[this.ultimaSemana] = new Relatorio(this.ultimaSemana);
		this.ultimaSemana++;
	}
	
	public void setCaloriaDia(int semana, int dia, int caloria) {
		this.caloriasSemanas[--semana].addCaloriasDia(dia, caloria);
	}
	
	public void setCaloriasSemana(int semana, int calorias[]) {
		for (int i = 0; i < 7; i++) {
			this.caloriasSemanas[semana-1].addCaloriasDia(i+1, calorias[i]);
		}
	}
	
	public double[] gerarMedias() {
		double medias[] = new double[ultimaSemana];
		for (int i=0; i < ultimaSemana; i++) {
			medias[i] = caloriasSemanas[i].mediaSemanal();
		}
		return medias;
	}
    
}

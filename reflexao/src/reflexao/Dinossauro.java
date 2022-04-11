package reflexao;

public class Dinossauro {
	int numPatas;
	String nomeEspecie; 
	String apelido;
	String tipoAlimentacao; 
	int altura; 
	int peso;
	
	public int getNumPatas() {
		return numPatas;
	}
	
	@paraSolicitar(tipo="int")
	public void setNumPatas(int numPatas) {
		this.numPatas = numPatas;
	}
	
	public String getNomeEspecie() {
		return nomeEspecie;
	}
	
	@paraSolicitar(tipo="String")
	public void setNomeEspecie(String nomeEspecie) {
		this.nomeEspecie = nomeEspecie;
	}
	
	public String getApelido() {
		return apelido;
	}
	
	@paraSolicitar(tipo="String")
	public void setApelido(String apelido) {
		this.apelido = apelido;
	}
	
	public String getTipoAlimentacao() {
		return tipoAlimentacao;
	}
	
	@paraSolicitar(tipo="String")
	public void setTipoAlimentacao(String tipoAlimentacao) {
		this.tipoAlimentacao = tipoAlimentacao;
	}
	
	public int getAltura() {
		return altura;
	}
	
	@Override
	public String toString() {
		return "Dinossauro [numPatas=" + numPatas + ", nomeEspecie=" + nomeEspecie + ", apelido=" + apelido
				+ ", tipoAlimentacao=" + tipoAlimentacao + ", altura=" + altura + ", peso=" + peso + "]";
	}

	@paraSolicitar(tipo="int")
	public void setAltura(int altura) {
		this.altura = altura;
	}
	
	public int getPeso() {
		return peso;
	}
	
	@paraSolicitar(tipo="int")
	public void setPeso(int peso) {
		this.peso = peso;
	} 
	
	
}

package reflexao;

import java.util.Arrays;
import java.util.Date;
import java.text.SimpleDateFormat; 

public class Relatorio {
	public int relatorioSemanal[];
	public int numeroSemana;
    
	public int getNumeroSemana() {
		return numeroSemana;
	}

	public void setNumeroSemana(int numeroSemana) {
		this.numeroSemana = numeroSemana;
	}

	@Override
	public String toString() {
		int num_semana = this.numeroSemana +1;
		return "Semana " + num_semana + ": " + Arrays.toString(relatorioSemanal) + "";
		
	}

	public int[] getRelatorioSemanal() {
		return relatorioSemanal;
	}
	
	public void setRelatorioSemanal(int[] relatorioSemanal) {
		this.relatorioSemanal = relatorioSemanal;
	}
	
	
	public Relatorio() {
		this.relatorioSemanal = new int[7];
		this.numeroSemana = -1;
	}
	
	public Relatorio(int semana) {
		this.relatorioSemanal = new int[7];
		this.numeroSemana = semana;
	}
		
	public void addCaloriasDia(int dia, int calorias) {
		this.relatorioSemanal[--dia] = calorias; 
	}
	
	public double mediaSemanal() {
		int soma = 0; 
		for (int i = 0; i < relatorioSemanal.length; i++) {
//			if (relatorioSemanal[i] == 0) {
//				return -1;
//			}
//				
			soma += relatorioSemanal[i];

		}
		return soma/7;
	}
    
}

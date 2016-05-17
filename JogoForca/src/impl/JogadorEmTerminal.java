package impl;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class JogadorEmTerminal {

	private String nome;
	
	public JogadorEmTerminal(String nome) {
		super();
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}
	
	public String perguntar(String mensagem) throws IOException
	{
		System.out.print("[ " + getNome() + " ] " + mensagem + " > ");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		return br.readLine();	
	}
	
}

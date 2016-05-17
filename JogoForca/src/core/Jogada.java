package core;

public class Jogada {

	public enum TipoJogada {
		PALAVRAS, LETRA, ABANDONO
	}
	
	public final TipoJogada tipo;
	public final String palpitePalavraA;
	public final String palpitePalavraB;
	public final char palpiteLetra;
	
	private Jogada(TipoJogada tipo, String palpitePalavraA, String palpitePalavraB, char palpiteLetra) {
		super();
		this.tipo = tipo;
		this.palpitePalavraA = palpitePalavraA;
		this.palpitePalavraB = palpitePalavraB;
		this.palpiteLetra = palpiteLetra;
	}
	
	public static Jogada palpiteDePalavras(String palpitePalavraA, String palpitePalavraB)
	{
		return new Jogada(TipoJogada.PALAVRAS, palpitePalavraA, palpitePalavraB, ' ');
	}
	
	public static Jogada palpiteDeLetra(char letra)
	{
		return new Jogada(TipoJogada.PALAVRAS, "", "", letra);
	}
	
	public static Jogada abandono()
	{
		return new Jogada(TipoJogada.ABANDONO, "", "", ' ');
	}	
}

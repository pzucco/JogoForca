package impl;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import core.Jogada;
import core.JogoForca;
import interfaces.I_JogoForcaAdapter;

public class JogoForcaTerminalAdapter implements I_JogoForcaAdapter<JogadorEmTerminal> {

	List<JogadorEmTerminal> listaInicialJogadores;
	
	public JogoForcaTerminalAdapter(List<JogadorEmTerminal> listaInicialJogadores) {
		super();
		this.listaInicialJogadores = listaInicialJogadores;
	}

	public void apresentar(String mensagem)
	{
		System.out.println(mensagem);
	}
	
	@Override
	public void apresentarMesa(JogadorEmTerminal jogadorA, JogadorEmTerminal jogadorB, JogadorEmTerminal jogadorC, List<JogadorEmTerminal> outrosJogadores) throws Exception {
		apresentar("Mesa:");
		apresentar(" (A) " + jogadorA.getNome());
		apresentar(" (B) " + jogadorB.getNome());
		apresentar(" (C) " + jogadorC.getNome());
		for (JogadorEmTerminal outroJogador : outrosJogadores)
		{
			apresentar(" (*) " + outroJogador.getNome());
		}
		apresentar("");
	}
	
	@Override
	public void eventoJogoIniciado() {
		apresentar("");
		apresentar("JOGO DA FORCA INICIADO!");
	}
	
	public String getNomesNaMesa(List<JogadorEmTerminal> jogadores)
	{
		String toString = "";
		for (int i = 0; i < jogadores.size(); i++)
		{
			toString += jogadores.get(i).getNome() + ", ";
		}
		return toString;
	}

	public void atualizarEstadoJogo(
			List<JogadorEmTerminal> jogadores, 
			String palavraReveladaA,
			String palavraReveladaB,
			int vidas)
	{
		apresentar("");
		apresentar("========================================");
		apresentar("* Jogadores: " + getNomesNaMesa(jogadores));
		apresentar("* Palavra A: " + palavraReveladaA);
		apresentar("* Palavra B: " + palavraReveladaB);
		apresentar("* VIDAS: " + vidas);
		apresentar("");
	}
	
	@Override
	public Jogada solicitarJogada(JogadorEmTerminal jogador) throws Exception {
		
		Jogada.TipoJogada tipo = solicitarTipoJogada( jogador );
		
		switch (tipo)
		{
		case PALAVRAS:
			String palpitePalavraA = solicitarPalpitePalavraA( jogador );
			String palpitePalavraB = solicitarPalpitePalavraB( jogador );
			return Jogada.palpiteDePalavras( palpitePalavraA, palpitePalavraB );
			
		case LETRA:
			char palpiteLetra = solicitarPalpiteLetra( jogador );
			return Jogada.palpiteDeLetra( palpiteLetra );
		
		default:
		case ABANDONO:
			return Jogada.abandono();
		}
	}
	
	private Jogada.TipoJogada solicitarTipoJogada(JogadorEmTerminal jogador) throws IOException
	{
		while (true)
		{
			String s = jogador.perguntar("Palpitar (p)alavras ou (l)etra");
			if (!s.isEmpty())
			{
				if (s.substring(0, 1).toLowerCase().equals("p")) return Jogada.TipoJogada.PALAVRAS;
				if (s.substring(0, 1).toLowerCase().equals("l")) return Jogada.TipoJogada.LETRA;
				if (s.substring(0, 1).toLowerCase().equals("q")) return Jogada.TipoJogada.ABANDONO;
			}
		}
	}
	
	public String solicitarPalpitePalavraA(JogadorEmTerminal jogador) throws IOException
	{
		while (true)
		{
			String s = jogador.perguntar("Palpite palavra A");
			if (s.length() >= 3) return s;
		}
	}
	
	public String solicitarPalpitePalavraB(JogadorEmTerminal jogador) throws IOException
	{
		while (true)
		{
			String s = jogador.perguntar("Palpite palavra B");
			if (s.length() >= 3) return s;
		}
	}
	
	public char solicitarPalpiteLetra(JogadorEmTerminal jogador) throws IOException
	{
		while (true)
		{
			String s = jogador.perguntar("Palpite letra");
			if (s.length() == 1) return s.charAt(0);
		}
	}

	@Override
	public String solicitarPalavraA(JogadorEmTerminal jogador) throws IOException
	{
		while (true)
		{
			String s = jogador.perguntar("Palavra da forca A");
			if (s.length() >= 3) return s;
		}
	}
	
	@Override
	public String solicitarPalavraB(JogadorEmTerminal jogador) throws IOException
	{
		while (true)
		{
			String s = jogador.perguntar("Palavra da forca B");
			if (s.length() >= 3) return s;
		}
	}

	@Override
	public void eventoLetraReveladaA(String palavra) {
		apresentar("");
		apresentar("Palpite de letra certo! Revelando palavra A : " + palavra);
	}

	@Override
	public void eventoLetraReveladaB(String palavra) {
		apresentar("Palpite de letra certo! Revelando palavra B : " + palavra);	
	}

	@Override
	public void eventoMesaPerdeuVidaPalpiteLetraErrado() {
		apresentar("");
		apresentar("Palpite de letra errado! Perdeu vida!");
	}

	@Override
	public void eventoMesaPerdeuVidaPalpiteLetraRepetido() {
		apresentar("");
		apresentar("Palpite de letra repetido! Perdeu vida!");
	}

	@Override
	public void erro(String mensagem) {
		apresentar(mensagem);
	}

	@Override
	public void eventoJogadorRemovidoPalpitePalavrasErrado(JogadorEmTerminal jogador) {
		apresentar("");
		apresentar("Palpite de palavra errado! Jogador removido!");
	}

	@Override
	public void eventoJogadorVenceuPalpitePalavrasCerto(JogadorEmTerminal jogador) {
		apresentar("");
		apresentar("Palpite de palavra certo! Jogador " + jogador.getNome() + " venceu sozinho!");
	}

	@Override
	public void eventoMesaVenceuLetrasReveladas(List<JogadorEmTerminal> jogadores) {
		apresentar("");
		apresentar("Palavras totalmente reveladas! Todos são vencedores :) !");
	}

	@Override
	public void eventoMesaDerrotadaVidasEsgotadas(List<JogadorEmTerminal> jogadores) {
		apresentar("");
		apresentar("Vidas esgodatas! Todos são perdedores!");
	}

	@Override
	public void eventoVezDe(JogadorEmTerminal jogador) throws Exception {
		apresentar("Vez de: " + jogador.getNome());
		apresentar("");
	}

	@Override
	public List<JogadorEmTerminal> solicitarJogadores() throws Exception {
		return listaInicialJogadores;
	}

	@Override
	public void eventoJogadorRemovidoAbandono(JogadorEmTerminal jogador) {
		apresentar("");
		apresentar("Jogador " + jogador.getNome() + " abandonou partida!");
	}

	@Override
	public void eventoMesaDerrotadaSemJogadores() {
		apresentar("");
		apresentar("Sem jogadores! Todos são perdedores!");
	}
	
}

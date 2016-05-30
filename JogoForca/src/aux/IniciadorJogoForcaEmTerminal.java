package aux;

import java.util.ArrayList;
import java.util.List;

import core.JogoForca;
import impl.JogadorEmTerminal;
import impl.JogoForcaTerminalAdapter;

public class IniciadorJogoForcaEmTerminal {
	
	public static void main(String[] args) throws Exception
	{
		List<JogadorEmTerminal> listaInicialJogadores = new ArrayList<JogadorEmTerminal>();
		
		listaInicialJogadores.add( new JogadorEmTerminal("jogador1") );
		listaInicialJogadores.add( new JogadorEmTerminal("jogador2") );
		listaInicialJogadores.add( new JogadorEmTerminal("jogador3") );
		
		JogoForcaTerminalAdapter jogoForcaTerminalAdapter = new JogoForcaTerminalAdapter(listaInicialJogadores);
		JogoForca<JogadorEmTerminal> jogoForca = new JogoForca<JogadorEmTerminal>(jogoForcaTerminalAdapter);
		jogoForca .jogar();
	}

}

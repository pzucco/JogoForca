package aux;

import java.util.ArrayList;
import java.util.List;

import core.JogoForca;
import impl.JogadorEmTerminal;
import impl.JogoForcaTerminalAdapter;

public class IniciadorJogoForcaEmTerminal {
	
	public static void main(String[] args) throws Exception
	{		
		JogoForcaTerminalAdapter jogoForcaTerminalAdapter = new JogoForcaTerminalAdapter();
		JogoForca<JogadorEmTerminal> jogoForca = new JogoForca<JogadorEmTerminal>(jogoForcaTerminalAdapter);
		jogoForca .jogar();
	}

}

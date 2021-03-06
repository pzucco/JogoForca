package core;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import interfaces.I_JogoForcaAdapter;

public class JogoForca <T_Jogador> {
	
	private int vidas = 0;
	private String palavraA = "";
	private String palavraB = "";
	private String palavraReveladaA = "";
	private String palavraReveladaB = "";
	
	private final I_JogoForcaAdapter<T_Jogador> adapter;
	private List<T_Jogador> jogadoresIniciais;
	private List<T_Jogador> jogadores;
	
	private T_Jogador vencedor = null;
	private boolean mesaVenceu = false;
	private boolean emExecucao = false;
	
	public JogoForca(I_JogoForcaAdapter<T_Jogador> adapter)
	{
		this.adapter = adapter;
	}
	
	//
	// Vidas ==================================================================
	//
	private void jogadorVence(T_Jogador jogador) { vencedor = jogador; }
	private void jogadorPerde(T_Jogador jogador) { jogadores.remove(jogador); }
	private void perderVida() { vidas = Math.max(vidas - 1, 0); }

	
	//
	// Getters jogadores ======================================================
	//
	public T_Jogador getJogadorA() { return jogadoresIniciais.get(0); }
	public T_Jogador getJogadorB() { return jogadoresIniciais.get(1); }
	public T_Jogador getJogadorC() { return jogadoresIniciais.get(2); }
	public List<T_Jogador> getOutrosJogadores() { return jogadoresIniciais.subList(3, jogadoresIniciais.size()); }
	
	private T_Jogador getJogadorSorteado()
	{
		Random rand = new Random();
		return jogadores.get(rand.nextInt(jogadores.size()));
	}
	
	private List<T_Jogador> getJogadores() {
		return Collections.unmodifiableList(jogadores);
	}
	
	//
	// Getters jogadores ======================================================
	//
	
	private void setPalavraA(String palavra)
	{
		palavra = palavra.toLowerCase();
		palavraA = palavra;
		palavraReveladaA = new String(new char[palavra.length()]).replace("\0", "_");
	}
	
	private void setPalavraB(String palavra)
	{
		palavra = palavra.toLowerCase();
		palavraB = palavra;
		palavraReveladaB = new String(new char[palavra.length()]).replace("\0", "_");
	}
	
	
	//
	// Validadores ===========================================================
	//
	
	public boolean validarJogadores() { return jogadores.size() >= 3; }
	
	public boolean validarPalavra(String palavra) {
		if (palavra.contains("_")) return false;
		if (palavra.length() < 3) return false;
		return true;	
	}
	
	public boolean jogoEmAndamento()
	{
		if (vidas == 0) return false;
		if (jogadores.size() == 0) return false;
		if (vencedor != null) return false;
		if (mesaVenceu) return false;
		if (!emExecucao) return false;
		return true;
	}
	
	
	//
	// Controle alto-nivel ====================================================
	//
	
	public boolean jogar() throws Exception
	{	
		if (emExecucao)
		{
			adapter.erro("PARTIDA JA FOI INICIADA!");
			return false;
		}
		emExecucao = true;
		
		jogadores = new ArrayList<T_Jogador> (adapter.solicitarJogadores());
		jogadoresIniciais = Collections.unmodifiableList(jogadores);
		
		if (!validarJogadores())
		{
			adapter.erro("LISTA DE JOGADORES INVALIDA!");
			emExecucao = false;
			return false;
		}
		
		vidas = 6;
		adapter.apresentarMesa(getJogadorA(), getJogadorB(), getJogadorC(), getOutrosJogadores());
		
		setPalavraA( adapter.solicitarPalavraA( getJogadorA() ) );
		if (!validarPalavra(palavraA))
		{
			adapter.erro("PALAVRA INVALIDA!");
			emExecucao = false;
			return false;
		}
		
		setPalavraB( adapter.solicitarPalavraB( getJogadorB() ) );
		if (!validarPalavra(palavraB))
		{
			adapter.erro("PALAVRA INVALIDA!");
			emExecucao = false;
			return false;
		}
		
		adapter.eventoJogoIniciado();
		
		vezDoJogador( getJogadorC() );
		
		while ( jogoEmAndamento() )
		{
			vezDoJogador( getJogadorSorteado() );			
		}
		
		if (vencedor != null)
		{
			adapter.eventoJogadorVenceuPalpitePalavrasCerto( vencedor );
		}
		else
		{
			if (mesaVenceu)
			{
				adapter.eventoMesaVenceuLetrasReveladas( getJogadores() );
			}
			else
			{
				if (vidas == 0)
				{
					adapter.eventoMesaDerrotadaVidasEsgotadas( getJogadores() );
				}
				if (jogadores.isEmpty())
				{
					adapter.eventoMesaDerrotadaSemJogadores();
				}
			}
		}
		
		emExecucao = false;
		return true;
	}
	
	//
	// Jogada =================================================================
	//
	private void vezDoJogador(T_Jogador jogador) throws Exception
	{
		adapter.atualizarEstadoJogo(getJogadores(), palavraReveladaA, palavraReveladaB, vidas);
		adapter.eventoVezDe(jogador);
		
		Jogada jogada = adapter.solicitarJogada(jogador);
		
		switch (jogada.tipo){
			
		case PALAVRAS:
			
			String palpiteA = jogada.palpitePalavraA.toLowerCase();
			String palpiteB = jogada.palpitePalavraB.toLowerCase();
			
			//
			// Palavra A estava errada
			//
			if (!palpiteA.equals(palavraA))
			{
				jogadorPerde(jogador);
				adapter.eventoJogadorRemovidoPalpitePalavrasErrado(jogador);
			}
			
			//
			// Palavra B estava errada
			//
			else if (!palpiteB.equals(palavraB))
			{
				jogadorPerde(jogador);
				adapter.eventoJogadorRemovidoPalpitePalavrasErrado(jogador);
			}
			
			//
			// Ambas palavras estavam corretas
			//
			else
			{
				jogadorVence(jogador);
				adapter.eventoJogadorVenceuPalpitePalavrasCerto(jogador);
			}
			
			break;
			
		case LETRA:
			
			String palpiteLetra = String.valueOf( jogada.palpiteLetra );
			
			//
			// Letra ja foi revelada
			//
			if (palavraReveladaA.contains(palpiteLetra) || palavraReveladaB.contains(palpiteLetra) )
			{
				perderVida();
				adapter.eventoMesaPerdeuVidaPalpiteLetraRepetido();
			}
			else
			{
				//
				// Letra nao pertence a nenhuma parte oculta das palavras 
				//
				if (!palavraA.contains(palpiteLetra) && !palavraB.contains(palpiteLetra) )
				{
					perderVida();
					adapter.eventoMesaPerdeuVidaPalpiteLetraErrado();
				}
				else {
				
					//
					// Letra pertence a pelo menos uma parte oculta da palavra A 
					//
					if (palavraA.contains(palpiteLetra))
					{
						for (int i = 0; i < palavraA.length(); i++)
						{
							if (palavraA.substring(i, i+1).equals(palpiteLetra))
							{
								palavraReveladaA = palavraReveladaA.substring(0, i) + palpiteLetra + palavraReveladaA.substring(i+1);
							}
						}
						adapter.eventoLetraReveladaA(palavraReveladaA);
					}
					
					//
					// Letra pertence a pelo menos uma parte oculta da palavra B 
					//
					if (palavraB.contains(palpiteLetra))
					{
						for (int i = 0; i < palavraB.length(); i++)
						{
							if (palavraB.substring(i, i+1).equals(palpiteLetra))
							{
								palavraReveladaB = palavraReveladaB.substring(0, i) + palpiteLetra + palavraReveladaB.substring(i+1);
							}
						}
						adapter.eventoLetraReveladaB(palavraReveladaB);
					}
				}
				
			}
			
			//
			// Ambas palavras foram reveladas por completo 
			//
			if (palavraA.equals(palavraReveladaA) && palavraB.equals(palavraReveladaB))
			{
				mesaVenceu = true;
			}
			
			break;
			
		default:
		case ABANDONO:
		
			jogadorPerde(jogador);
			adapter.eventoJogadorRemovidoAbandono(jogador);
			
			break;
			
		}
	}	

}

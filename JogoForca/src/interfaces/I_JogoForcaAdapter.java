package interfaces;
import java.util.List;

import core.Jogada;
import core.JogoForca;

public interface I_JogoForcaAdapter<T_Jogador> {
	
	public void apresentarMesa(JogoForca<T_Jogador> jogoForca)  throws Exception;
	public void eventoJogoIniciado(JogoForca<T_Jogador> jogoForca) throws Exception;

	public void atualizarEstadoJogo(
			List<T_Jogador> jogadores, 
			String palavraReveladaA,
			String palavraReveladaB,
			int vidas)  throws Exception;
	
	public void eventoLetraReveladaA(String palavra);
	public void eventoLetraReveladaB(String palavra);
	public void eventoMesaPerdeuVidaPalpiteLetraErrado();
	public void eventoMesaPerdeuVidaPalpiteLetraRepetido();
	
	public void erro(String mensagem);
	
	public void eventoJogadorRemovidoPalpitePalavrasErrado(T_Jogador jogador);
	public void eventoJogadorRemovidoAbandono(T_Jogador jogador);
	
	public void eventoJogadorVenceuPalpitePalavrasCerto(T_Jogador jogador);
	public void eventoMesaVenceuLetrasReveladas(List<T_Jogador> jogadores);
	
	public void eventoMesaDerrotadaVidasEsgotadas(List<T_Jogador> jogadores);
	public void eventoMesaDerrotadaSemJogadores();
	
	public void eventoVezDe(T_Jogador jogador)  throws Exception;
	
	public List<T_Jogador> solicitarJogadores() throws Exception;
	
	public String solicitarPalavraA(T_Jogador jogador) throws Exception;
	public String solicitarPalavraB(T_Jogador jogador) throws Exception;
	
	public Jogada solicitarJogada(T_Jogador jogador) throws Exception;
	

}

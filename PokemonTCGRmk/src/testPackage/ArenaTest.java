package testPackage;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;

import org.junit.Test;

import arena.GameArena;
import arena.Bench;
import arena.Deck;
import arena.Player;
import arena.Prizes;
import cardAbstract.ParsePokemonCardsFile;
import pokemonCard.ActivePokemonCard;
import pokemonCard.PokemonCard;

public class ArenaTest {

	// This doens't test if evolves evolves correctly!!! eg: squirtle shouldn't evolve to blastoise directly
	// and definitely not charmeleon or another basic etc. Also need to test different types.
	// eg pikachu level 9 and level 10 both evolve to any type of raichu.
	// and trainer cards etc.
	@Test
	public void testknockOutAttPokemonAndBasicEvolves() throws FileNotFoundException {
		PokemonCard p12 = ParsePokemonCardsFile.getPokemonCard("Squirtle", "8");
		Player p = new Player(new Deck(), new Prizes(6), new Bench(6), null);
		GameArena ba = new GameArena(p, null);
		p.setActivePokemonFromHand(p12);
		ba.knockOutAttPokemon();
		// knock out att pokemon sets getAtt to null
		assert(ba.getAttActive() == null);
		// discard pile contains att
		assert(ba.getPlayerAtt().getDiscardPile().getList().size() > 0);
		// Now test on an evolved pokemon:
		PokemonCard w22 = ParsePokemonCardsFile.getPokemonCard("Wartortle", "22");
		ActivePokemonCard sqrt = new ActivePokemonCard(p12, 0, p);
		sqrt.setDamage(10);
		ActivePokemonCard wart = sqrt.evolve(w22);
		ba.setAttPokemon(wart);
		assertEquals(ba.getAttActive().getName(), "Wartortle");
		assertEquals(ba.getAttActive().getLevel(), "22");
		assertEquals(sqrt.getEvol(), "b");
		assertEquals(ba.getAttActive().getEvol(), "Squirtle");
		assertEquals(ba.getAttActive().getForms().get(0), sqrt);
		assertEquals(ba.getAttActive().getDamage(), 10);
		assertEquals(ba.getAttActive().getType(), "w");
		// Now knock out:
		ba.knockOutAttPokemon();
		// knock out att pokemon sets getAtt to null
		assert(ba.getAttActive() == null);
		// discard pile contains sqrt and wart
		assert(ba.getPlayerAtt().getDiscardPile().getList().contains(sqrt));
		assert(ba.getPlayerAtt().getDiscardPile().getList().contains(wart));
		// test evolving to blastoise:
		// Now test on an evolved pokemon:
		w22 = ParsePokemonCardsFile.getPokemonCard("Wartortle", "22");
		sqrt = new ActivePokemonCard(p12, 0, p);
		sqrt.setDamage(10);
		wart = sqrt.evolve(w22);
		ba.setAttPokemon(wart);
		assertEquals(ba.getAttActive().getName(), "Wartortle");
		assertEquals(ba.getAttActive().getLevel(), "22");
		assertEquals(sqrt.getEvol(), "b");
		assertEquals(ba.getAttActive().getEvol(), "Squirtle");
		assertEquals(ba.getAttActive().getForms().get(0), sqrt);
		assertEquals(ba.getAttActive().getDamage(), 10);
		assertEquals(ba.getAttActive().getType(), "w");
		wart.addDamage(20);
		assertEquals(ba.getAttActive().getDamage(), 30);
		PokemonCard bl = ParsePokemonCardsFile.getPokemonCard("Blastoise", "52");
		ActivePokemonCard blast = wart.evolve(bl);
		ba.setAttPokemon(blast);
		assertEquals(ba.getAttActive().getDamage(), 30);
		assertEquals(ba.getAttActive().getForms().size(), 2);
		assertEquals(ba.getAttActive().getForms().get(0), sqrt);
		assertEquals(ba.getAttActive().getForms().get(1), wart);
		ba.knockOutAttPokemon();
		// discard pile contains sqrt and wart and blast
		assert(ba.getPlayerAtt().getDiscardPile().getList().contains(sqrt));
		assert(ba.getPlayerAtt().getDiscardPile().getList().contains(wart));
		assert(ba.getPlayerAtt().getDiscardPile().getList().contains(blast));
		// Now test trainer cards:
		
		// Good: passed the test!
		System.out.println("Passed knock out att pokemon test");
	}

}

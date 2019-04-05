package fr.test;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import fr.menu.MenuDisplay;

class TestMenu {

	@Test
	void testOptionsMenu() throws InterruptedException {
		/* Attention à ne pas cliquer lors des tests */
		MenuDisplay menu = MenuDisplay.getInstance();
		menu.display();
		
		/* Tests de passage entre les pages du menu*/
		TimeUnit.SECONDS.sleep(1);
		Assertions.assertTrue(menu.getMenuPosition().equals("menu"));
		
		menu.getHost().doClick();
		TimeUnit.SECONDS.sleep(1);
		Assertions.assertTrue(menu.getMenuPosition().equals("host"));
		
		/* Tests des options de la page Host*/
		Assertions.assertTrue(menu.getAliasTextField().getText().equals("Alias"));
		
		menu.getDecNbPlayers().doClick();
		TimeUnit.SECONDS.sleep(1);
		Assertions.assertFalse(menu.getNbPlayersLabel().getText().equals("0"));
		menu.getIncNbPlayers().doClick();
		menu.getIncNbPlayers().doClick();
		menu.getIncNbPlayers().doClick();
		TimeUnit.SECONDS.sleep(1);
		Assertions.assertTrue(menu.getNbPlayersLabel().getText().equals("4"));
		
		menu.getIncNbPlayers().doClick();
		TimeUnit.SECONDS.sleep(1);
		Assertions.assertFalse(menu.getNbPlayersLabel().getText().equals("5"));
		
		menu.getMapL().doClick();
		TimeUnit.SECONDS.sleep(1);
		Assertions.assertFalse(menu.getMapPosition() == -1);
		menu.getMapR().doClick();
		menu.getMapR().doClick();
		TimeUnit.SECONDS.sleep(1);
		Assertions.assertTrue(menu.getMapPosition() == 2);
		
		menu.getMapR().doClick();
		TimeUnit.SECONDS.sleep(1);
		Assertions.assertFalse(menu.getMapPosition() == 3);
		
		/* Fin des tests des options de la page Host*/
		
		menu.getSearch().doClick();
		TimeUnit.SECONDS.sleep(1);
		Assertions.assertTrue(menu.getMenuPosition().equals("start"));
		
		menu.getBack().doClick();
		TimeUnit.SECONDS.sleep(1);
		Assertions.assertTrue(menu.getMenuPosition().equals("host"));
		
		menu.getBack().doClick();
		TimeUnit.SECONDS.sleep(1);
		Assertions.assertTrue(menu.getMenuPosition().equals("menu"));
		
		menu.getJoin().doClick();
		TimeUnit.SECONDS.sleep(1);
		Assertions.assertTrue(menu.getMenuPosition().equals("join"));
		
		/* Fin de tests de passage entre les pages du menu*/
		
	}

}

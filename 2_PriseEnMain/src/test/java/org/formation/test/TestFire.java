package org.formation.test;

import java.util.Collection;

import org.drools.core.common.DefaultFactHandle;
import org.formation.helper.RuleRunner;
import org.formation.model.stateful.Alarm;
import org.formation.model.stateful.Room;
import org.formation.model.stateful.Sprinkler;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.kie.api.runtime.rule.FactHandle;

public class TestFire {
	static RuleRunner ruleRunner;
	FactHandle[] factHandles;
	Room kitchen = new Room("kitchen");
	Room salon = new Room("salon");
	Sprinkler sprinklerKitchen = new Sprinkler(kitchen);
	Sprinkler sprinklerSalon = new Sprinkler(salon);
	
	@BeforeClass
	public static void init(){
		ruleRunner = new RuleRunner();
	}
	@Before
	public void initTest() {
		
	}
	@After
	public void end() {
		
	}
	@Test
	public void testNofire(){
		// Insérer les faits (les différentes pièces et les Sprinkler associés)
		
		// Déclencher les règles
		System.out.println("************ No Fire ************");
		Assert.assertFalse(sprinklerKitchen.isOn());
	}
	@Test
	public void testFireInKitchen(){
		System.out.println("************ Fire In Kitchen ************");
		// Insérer les faits (feu dans la cusine)
		
		// Déclencher les règles
		Assert.assertTrue(sprinklerKitchen.isOn());
	}
	@Test
	public void testStopFireInKitchen(){
		System.out.println("************ Stop fire In Kitchen ************");
		// Insérer les faits (feu dans la cusine)
		
		// Déclencher les règles
		
		Assert.assertTrue(sprinklerKitchen.isOn());
		Assert.assertTrue(existAlarm());
		
		//Supprimer le feu
		
		//relancer les règles
		Assert.assertFalse(existAlarm());
	}


	@Test
	public void testStopFireInSalonButStillFireInKitchen(){
		System.out.println("************ Stop Fire In Salon But Still In Kitchen ************");
		// Insérer les faits (feu dans la cuisine ET le salon)
		

		//lancer les règles

		Assert.assertTrue(sprinklerKitchen.isOn());
		Assert.assertTrue(sprinklerSalon.isOn());
		Assert.assertTrue(existAlarm());

		//Eteindre le feu dans le salon
		
		//relancer les règles
		
		Assert.assertTrue(sprinklerKitchen.isOn());
		Assert.assertFalse(sprinklerSalon.isOn());
		Assert.assertTrue(existAlarm());
	}

	private boolean existAlarm() {

		// A completer, utiliser la méthode de ruleRunner appropriée
		return false;
	}
}

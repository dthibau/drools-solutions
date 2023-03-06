package org.formation.test;

import org.formation.helper.RuleRunner;
import org.formation.model.stateful.Alarm;
import org.formation.model.stateful.Fire;
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
		ruleRunner.initStatefulSession();
	}
	@After
	public void end() {
		ruleRunner.closeLogger();
		ruleRunner.disposeSession();		
	}
	@Test
	public void testNofire(){
		// Insérer les faits (les différentes pièces et les Sprinkler associés)
		Object[] facts =new Object[4];
		facts[0] = kitchen;
		facts[1] = salon;
		facts[2] = sprinklerKitchen;
		facts[3] = sprinklerSalon;

		factHandles = ruleRunner.insertFacts(facts);
		ruleRunner.fireAllRules();
		
		// Déclencher les règles
		System.out.println("************ No Fire ************");
		Assert.assertFalse(sprinklerKitchen.isOn());
	}
	@Test
	public void testFireInKitchen(){
		System.out.println("************ FIRE In Kitchen ************");
		// Insérer les faits (feu dans la cusine)
		Object[] facts =new Object[5];
		facts[0] = kitchen;
		facts[1] = salon;
		facts[2] = sprinklerKitchen;
		facts[3] = sprinklerSalon;
		facts[4] = new Fire(kitchen);

		ruleRunner.insertFacts(facts);
		ruleRunner.fireAllRules();
		
		// Déclencher les règles
		Assert.assertTrue(sprinklerKitchen.isOn());
	}
	@Test
	public void testStopFireInKitchen(){
		System.out.println("************ Stop fire In Kitchen ************");
		// Insérer les faits (feu dans la cusine)
		
		Object[] facts =new Object[5];
		facts[0] = kitchen;
		facts[1] = salon;
		facts[2] = sprinklerKitchen;
		facts[3] = sprinklerSalon;
		facts[4] = new Fire(kitchen);

		factHandles = ruleRunner.insertFacts(facts);
		ruleRunner.fireAllRules();
		
		Assert.assertTrue(sprinklerKitchen.isOn());
		Assert.assertTrue(existAlarm());
		ruleRunner.retractFact(factHandles[4]);
		ruleRunner.fireAllRules();
		Assert.assertFalse(existAlarm());
	}


	@Test
	public void testStopFireInSalonButStillFireInKitchen(){
		System.out.println("************ Stop Fire In Salon But Still In Kitchen ************");
		// Insérer les faits (feu dans la cuisine ET le salon)
		Object[] facts =new Object[6];
		facts[0] = kitchen;
		facts[1] = salon;
		facts[2] = sprinklerKitchen;
		facts[3] = sprinklerSalon;
		facts[4] = new Fire(kitchen);
		facts[5] = new Fire(salon);

		//lancer les règles
		factHandles = ruleRunner.insertFacts(facts);
		ruleRunner.fireAllRules();

		Assert.assertTrue(sprinklerKitchen.isOn());
		Assert.assertTrue(sprinklerSalon.isOn());
		Assert.assertTrue(existAlarm());

		//Eteindre le feu dans le salon
		ruleRunner.retractFact(factHandles[5]);
		
		//relancer les règles
		ruleRunner.fireAllRules();

		Assert.assertTrue(sprinklerKitchen.isOn());
		Assert.assertFalse(sprinklerSalon.isOn());
		Assert.assertTrue(existAlarm());
	}

	private boolean existAlarm() {

		// A completer, utiliser la méthode de ruleRunner appropriée
		return ruleRunner.getFacts(	Alarm.class).size() > 0;

	}
}

package org.formation.test;

import java.util.Collection;

import org.drools.core.common.DefaultFactHandle;
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
import org.kie.api.definition.type.FactType;
import org.kie.api.runtime.rule.FactHandle;
import org.kie.api.runtime.rule.QueryResults;
import org.kie.api.runtime.rule.QueryResultsRow;

public class TestFire {
	static RuleRunner ruleRunner;
	FactHandle[] factHandles;
	Room kitchen = new Room("kitchen");
	Room salon = new Room("salon");
	Sprinkler sprinklerKitchen = new Sprinkler(kitchen);
	Sprinkler sprinklerSalon = new Sprinkler(salon);

	@BeforeClass
	public static void init() {
		ruleRunner = new RuleRunner();
	}

	@After
	public void end() {
		ruleRunner.closeLogger();
		ruleRunner.disposeSession();
	}

	@Before
	public void initTest() {
		ruleRunner.initStatefulSession();
	}

	@Test
	public void testNofire() {
		Object[] facts = new Object[4];
		facts[0] = kitchen;
		facts[1] = salon;
		facts[2] = sprinklerKitchen;
		facts[3] = sprinklerSalon;

		factHandles = ruleRunner.insertFacts(facts);

		ruleRunner.fireUntillHalt();

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Assert.assertFalse(sprinklerKitchen.isOn());
		_testPanicMode(false);


	}

	@Test
	public void testFireInKitchen() {
		Object[] facts = new Object[5];
		facts[0] = kitchen;
		facts[1] = salon;
		facts[2] = sprinklerKitchen;
		facts[3] = sprinklerSalon;
		facts[4] = new Fire(kitchen);

		ruleRunner.insertFacts(facts);
		ruleRunner.fireUntillHalt();

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Assert.assertTrue(sprinklerKitchen.isOn());
		_testPanicMode(false);

		ruleRunner.halt();

	}

	@Test
	public void testStopFireInKitchen() {
		System.out.println("********** Stop Fire In Kitchen **********");
		Object[] facts = new Object[5];
		facts[0] = kitchen;
		facts[1] = salon;
		facts[2] = sprinklerKitchen;
		facts[3] = sprinklerSalon;
		facts[4] = new Fire(kitchen);

		factHandles = ruleRunner.insertFacts(facts);
		ruleRunner.fireUntillHalt();
		try {
			Thread.sleep(6000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Assert.assertTrue(sprinklerKitchen.isOn());
		Assert.assertTrue(existAlarm());
		ruleRunner.retractFact(factHandles[4]);
		try {
			Thread.sleep(6000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Assert.assertFalse(existAlarm());
		ruleRunner.halt();
	}

	@Test
	public void testStopFireInKitchenButStillFireInSalon() {
		System.out.println("********** Stop Fire In Salon But Still Fire In Kitchen **********");
		Object[] facts = new Object[6];
		facts[0] = kitchen;
		facts[1] = salon;
		facts[2] = sprinklerKitchen;
		facts[3] = sprinklerSalon;
		facts[4] = new Fire(kitchen);
		facts[5] = new Fire(salon);

		factHandles = ruleRunner.insertFacts(facts);
		ruleRunner.fireUntillHalt();
		try {
			Thread.sleep(7000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Assert.assertTrue(sprinklerKitchen.isOn());
		Assert.assertTrue(sprinklerSalon.isOn());
		Assert.assertTrue(existAlarm());
		_testPanicMode(true);

		ruleRunner.retractFact(factHandles[4]);
		try {
			Thread.sleep(7000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Assert.assertFalse(sprinklerKitchen.isOn());
		Assert.assertTrue(sprinklerSalon.isOn());
		Assert.assertTrue(existAlarm());
		_testPanicMode(false);

		ruleRunner.halt();
		printRoomWithSprinklerOn();
	}

	private void printRoomWithSprinklerOn() {
		QueryResults qr = ruleRunner.executeQuery("room where sprinkler is on");
		System.out.println("------QueryResults:" + qr.size());
		for (QueryResultsRow row : qr) {
			Room room = (Room) row.get("$r");
			System.out.println("Sprinkler is on for " + room.getName());
		}

	}

	private boolean existAlarm() {
		Collection<FactHandle> fHandles = ruleRunner.getFacts(Alarm.class);
		System.out.println("there is " + fHandles.size() + " alarm(s) ");
		if (fHandles.size() > 0)
			return true;
		return false;
	}

	private void _testPanicMode(boolean expected) {
		FactType panicModeType = ruleRunner.getKieBase().getFactType("org.formation.rules", "PanicMode");

		Assert.assertNotNull(panicModeType);
		Collection<FactHandle> panicModes = ruleRunner.getFacts(panicModeType.getFactClass());
		Assert.assertEquals(1, panicModes.size());
		panicModes.forEach(pm -> {
			Assert.assertEquals(expected, panicModeType.get(((DefaultFactHandle) pm).getObject(), "on"));
		});
	}
}

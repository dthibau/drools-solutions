package org.formation.test;


import java.util.logging.Logger;

import org.formation.helper.RuleRunner;
import org.formation.model.stateless.Assurance;
import org.formation.model.stateless.Conducteur;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestAssuranceGlobal {
	private Logger LOG = Logger.getLogger(this.getClass().getName());
	private static RuleRunner ruleRunner;
	private Object[] facts = new Object[2];
	
	@BeforeClass
	public static void setUp(){
		ruleRunner = new RuleRunner();

	}
	
	@Test
	public void test20(){
		Conducteur c = new Conducteur(20, 0, 0);
		Assurance a = new Assurance();
		facts[0] = c;
		facts[1] = a;
		try {
			ruleRunner.runStatelessRules(facts);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Assert.assertEquals(500d, ((Assurance)facts[1]).getPrix(), 0.001d);
		LOG.info("prix assurance "+a.getPrix());
	}
	@Test
	public void test17(){
		Conducteur c = new Conducteur(17, 0, 0);
		Assurance a = new Assurance();
		facts[0] = c;
		facts[1] = a;
		try {
			ruleRunner.runStatelessRules(facts);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Assert.assertEquals(-1, ((Assurance)facts[1]).getPrix(), 0.001d);
		LOG.info("prix assurance "+a.getPrix());
	}
	@Test
	public void test23_5_10(){
		Conducteur c = new Conducteur(23, 5, 10);
		Assurance a = new Assurance();
		facts[0] = c;
		facts[1] = a;
		try {
			ruleRunner.runStatelessRules(facts);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Assert.assertEquals(700d, ((Assurance)facts[1]).getPrix(), 0.001d);
		LOG.info("test23_5_10 prix assurance "+a.getPrix());
	}
}

package org.formation.drools.test;

import java.util.Collection;

import org.drools.core.common.DefaultFactHandle;
import org.formation.drools.helper.RuleRunner;
import org.formation.drools.model.stateful.Alarm;
import org.formation.drools.model.stateful.Fire;
import org.formation.drools.model.stateful.Room;
import org.formation.drools.model.stateful.Sprinkler;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.kie.api.runtime.rule.FactHandle;
import org.kie.api.runtime.rule.QueryResults;
import org.kie.api.runtime.rule.QueryResultsRow;

public class TestFire {
	static RuleRunner ruleRunner;

	Room kitchen = new Room("kitchen");
	Room salon = new Room("salon");
	Room chambre = new Room("chambre");
	Sprinkler sprinklerKitchen = new Sprinkler(kitchen);
	Sprinkler sprinklerSalon = new Sprinkler(salon);
	Sprinkler sprinklerChambre = new Sprinkler(chambre);
	
	@BeforeClass
	public static void init(){
		ruleRunner = new RuleRunner();
	}

	@Test
	public void testProcess(){
        Object[] facts = new Object[] {kitchen,
        		salon,
        		chambre,
        		sprinklerKitchen,
        		sprinklerSalon, 
        		sprinklerChambre, 
        		new Fire(kitchen),
        		new Fire(salon)};

        FactHandle[] factHandles = ruleRunner.setStatefulRules(facts);
        ruleRunner.startProcess("Fire");
//        ruleRunner.fireAllRules();
        System.out.println("-- On enlève le feu dans la cuisine "+factHandles[6]);
        ruleRunner.retractFact(factHandles[6]);
        ruleRunner.signalEvent("DetectChange");
//        ruleRunner.fireAllRules();
        
        System.out.println("-- On enlève le feu dans le salon "+factHandles[7]);
        ruleRunner.retractFact(factHandles[7]);
        ruleRunner.signalEvent("DetectChange");
//        ruleRunner.fireAllRules();
        /*
        ruleRunner.retractFact(factHandles[8]);
        ruleRunner.signalEvent("DetectChange");
        ruleRunner.fireAllRules();
*/
         System.out.println("-- On arrête le système");
 //       ruleRunner.signalEvent("DetectChange");

        ruleRunner.signalEvent("SHUTDOWN");
	}



}

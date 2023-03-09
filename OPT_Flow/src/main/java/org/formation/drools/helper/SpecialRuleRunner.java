package org.formation.drools.helper;

import java.util.Arrays;

import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.Message.Level;
import org.kie.api.io.KieResources;
import org.kie.api.io.Resource;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.StatelessKieSession;

public class SpecialRuleRunner {
	private static final String RULEPATH = "";
	
	private KieBase createKieBase(String[] rules){
		KieServices kieServices = KieServices.Factory.get();
		KieResources kieResources = kieServices.getResources();
		KieFileSystem kieFileSystem = kieServices.newKieFileSystem();
		KieContainer kieContainer = kieServices.getKieClasspathContainer();
		
		for (String ruleFile : rules) {
			Resource resource = kieResources.newClassPathResource(ruleFile);
			kieFileSystem.write(RULEPATH+ruleFile, resource);
		}
		KieBuilder kieBuilder = kieServices.newKieBuilder(kieFileSystem);
		kieBuilder.buildAll();
		if(kieBuilder.getResults().hasMessages(Level.ERROR)){
			throw new RuntimeException(kieBuilder.getResults().toString());
		}
		return kieContainer.getKieBase();
	}
	
	private KieBase createKieBase(){
		KieServices kieServices = KieServices.Factory.get();
		KieContainer kieContainer = kieServices.getKieClasspathContainer();
		return kieContainer.getKieBase();
	}
	
	public void runStatelessRules(String[] rules, Object[] facts) {
		KieBase kieBase = rules == null?createKieBase():createKieBase(rules);
		StatelessKieSession session = kieBase.newStatelessKieSession();
		session.execute(Arrays.asList(facts));
	}
	
	public void runStatefulRules(String[] rules, Object[] facts){
		KieBase kieBase = rules == null?createKieBase():createKieBase(rules);
		KieSession session = kieBase.newKieSession();
		for (Object fact : facts) {
			session.insert(fact);
		}
		session.fireAllRules();
		session.dispose();
	}
	
}

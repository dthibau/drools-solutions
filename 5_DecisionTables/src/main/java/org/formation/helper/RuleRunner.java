package org.formation.helper;

import java.util.Arrays;
import java.util.Collection;

import org.formation.model.stateless.Assurance;
import org.kie.api.KieServices;
import org.kie.api.logger.KieRuntimeLogger;
import org.kie.api.runtime.ClassObjectFilter;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.StatelessKieSession;
import org.kie.api.runtime.rule.FactHandle;

public class RuleRunner {
	KieSession kSession;
	KieRuntimeLogger logger;

	public RuleRunner() {
		
	}

	public void runStatelessRules(Object[] facts)
			throws Exception {
		// Instancier un container basé sur le classpath
		KieServices kServices = KieServices.Factory.get();
		KieContainer kContainer = kServices.getKieClasspathContainer();
		/*
		Collection<KiePackage> kpackages = kContainer.getKieBase().getKiePackages();
		for (KiePackage kp : kpackages){
			Collection<Rule> rules = kp.getRules();
			for (Rule rule : rules) {
				System.out.println("=>rule : "+rule.getName());
			}
		}
		*/
		// Instancier une session stateless
		StatelessKieSession statelessKieSession = kContainer.newStatelessKieSession();
		
		Assurance assurance = new Assurance();
		statelessKieSession.setGlobal("assurance", assurance );

		// Exécuter les règles
		statelessKieSession.execute(Arrays.asList(facts));
		
		System.out.println("Résultat :"+assurance.getPrix());

	}
	


	public FactHandle[] setStatefulRules(Object[] facts){

		// Instancier un container basé sur le classpath
		KieServices kServices = KieServices.Factory.get();
		KieContainer kContainer = kServices.getKieClasspathContainer();

		// Instancier la session stateful
		kSession = kContainer.newKieSession();
		logger = KieServices.Factory.get().getLoggers().newFileLogger(kSession, "My Stateful session");
		
		FactHandle handles[] = new FactHandle[facts.length];
		int i=0;
		// Insérer les faits
		for (Object fact : facts) {
			handles[i++] = kSession.insert(fact);
		}

		// Déclencher les règles
		//kSession.fireAllRules();
		

		//ksession.getFactHandles().toArray(new FactHandle[0]);
		// Retourner les FactHandle
		return handles;
	}

	public void retractFact(FactHandle fHandle) {
		kSession.delete(fHandle);
	}

	public void fireAllRules() {
		kSession.fireAllRules();
	}
	public Collection<FactHandle> getFacts() {
		return kSession.getFactHandles();
	}

	public void close() {
		logger.close();
	}

	public Collection<FactHandle> getFacts(Class<?> class1) {
		Collection<FactHandle> factHandles = kSession.getFactHandles(new ClassObjectFilter(class1));
		System.out.println("il y en a "+factHandles.size());
		return factHandles;
	}
	
	public void dispose(){
		kSession.dispose();
	}
}

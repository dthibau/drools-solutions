package org.formation.helper;

import java.util.Arrays;
import java.util.Collection;

import org.kie.api.KieServices;
import org.kie.api.logger.KieRuntimeLogger;
import org.kie.api.runtime.ClassObjectFilter;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.StatelessKieSession;
import org.kie.api.runtime.rule.FactHandle;

public class RuleRunner {
	KieContainer kieContainer;
	KieSession kSession;
	KieRuntimeLogger logger;

	public RuleRunner() {
		// Instancier un container basé sur le classpath
		KieServices kServices = KieServices.Factory.get();
		kieContainer = kServices.getKieClasspathContainer();


	}

	public void runStatelessRules(Object[] facts)
			throws Exception {
		
		// Instancier une session stateless
		StatelessKieSession statelessKieSession = kieContainer.newStatelessKieSession();
	
		// Exécuter les règles
		statelessKieSession.execute(Arrays.asList(facts));

	}

	/**
	 * Initiale une session et lui associe un fichier de trace
	 */
	public void initStatefulSession() {
		// Instancier un container basé sur le classpath


		// Instancier la session stateful et y associer un logger
		
	}
	/**
	 * Insère un tableau de faits
	 * @param facts
	 * @return
	 */
	public FactHandle[] insertFacts(Object[] facts){


		// Insérer les faits et retourner des FactHandle
		
		return null;

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

	public void closeLogger() {
		logger.close();
	}

	public Collection<FactHandle> getFacts(Class<?> class1) {
		Collection<FactHandle> factHandles = kSession.getFactHandles(new ClassObjectFilter(class1));

		return factHandles;
	}
	
	public void disposeSession(){
		kSession.dispose();
	}
}

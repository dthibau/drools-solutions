package org.formation.helper;

import java.util.Arrays;
import java.util.Collection;

import org.formation.io.ConsoleChannel;
import org.kie.api.KieServices;
import org.kie.api.event.rule.DebugAgendaEventListener;
import org.kie.api.event.rule.DebugRuleRuntimeEventListener;
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


		// Instancier la session stateful et y associer un logger
		// Instancier un container basé sur le classpath


		kSession = kieContainer.newKieSession();
		kSession.addEventListener(new DebugAgendaEventListener());
		kSession.addEventListener(new DebugRuleRuntimeEventListener());
		kSession.registerChannel("console-channel", new ConsoleChannel());
		logger = KieServices.Factory.get().getLoggers().newFileLogger(kSession, "Stateful");
		
	}
	/**
	 * Insère un tableau de faits
	 * @param facts
	 * @return
	 */
	public FactHandle[] insertFacts(Object[] facts){


		FactHandle handles[] = new FactHandle[facts.length];
		int i=0;
		// Insérer les faits
		for (Object fact : facts) {
			handles[i++] = kSession.insert(fact);
		}

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

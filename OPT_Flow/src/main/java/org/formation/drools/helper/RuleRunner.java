package org.formation.drools.helper;

import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.formation.drools.model.stateful.Alarm;
import org.kie.api.KieServices;
import org.kie.api.logger.KieRuntimeLogger;
import org.kie.api.runtime.ClassObjectFilter;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.StatelessKieSession;
import org.kie.api.runtime.process.ProcessInstance;
import org.kie.api.runtime.rule.FactHandle;
import org.kie.api.runtime.rule.QueryResults;

public class RuleRunner {
	KieSession kSession;
	KieRuntimeLogger logger;
	private ProcessInstance processInstance;

	public RuleRunner() {
	}

	public void runStatelessRules(Object[] facts)
			throws Exception {
		// Instancier un container basé sur le classpath
		KieServices kServices = KieServices.Factory.get();
		KieContainer kContainer = kServices.getKieClasspathContainer();
		


		// Instancier une session stateless
		StatelessKieSession statelessKieSession = kContainer.newStatelessKieSession();
		

		// Exécuter les règles
		statelessKieSession.execute(Arrays.asList(facts));

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
	public void insertFact(Object fact){
		kSession.insert(fact);
	}

	public void fireAllRules() {
		kSession.fireAllRules();
	}
	public void startProcess(String pName){
		processInstance = kSession.startProcess(pName);
	}
	public void signalEvent(String transitionName){
		System.out.println("processInstance.getId() = "+processInstance.getId());
		kSession.signalEvent("signal", transitionName, processInstance.getId());

	}

	public void fireUntillHalt(){
		ExecutorService executorService = Executors.newSingleThreadExecutor();
		executorService.submit(new Runnable() {
			
			@Override
			public void run() {
				kSession.fireUntilHalt();
			}
		});
		
	}
	public void halt(){
		kSession.halt();
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
	public QueryResults executeQuery(String queryName){
		return kSession.getQueryResults(queryName);
	}
}

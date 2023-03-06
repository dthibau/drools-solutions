package org.formation;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.builder.KieScanner;
import org.kie.api.builder.ReleaseId;
import org.kie.api.definition.KiePackage;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

	static final Logger LOG = LoggerFactory.getLogger(Main.class);
	static boolean running=true;
	static KieContainer kContainer;
	
	public static void main(String[] args) {
		KieServices kieServices = KieServices.Factory.get();
		ReleaseId releaseId = kieServices.newReleaseId("org.formation", "rules", "1.0-SNAPSHOT");
		kContainer = kieServices.newKieContainer(releaseId);
		KieScanner kScanner = kieServices.newKieScanner(kContainer);
		// Démarrage de KieScanner
		// qui scrute le repository toutes les 10 secondes
		kScanner.start(10000L);

		System.out.println("Creating rules kieBase");
		

		
		ExecutorService executorService = Executors.newSingleThreadExecutor();
		executorService.submit(new Runnable() {
			
			public void run() {
				while ( running ) {
					KieBase kieBase = kContainer.getKieBase("rules");
					System.out.println("There should be rules: ");
					for (KiePackage kp : kieBase.getKiePackages()) {
						System.out.println("kp " + kp.getName() + " got  " + kp.getRules().size() + " rules");
					}
					try {
						Thread.sleep(10000);
					} catch (InterruptedException e) {}
				}
			}
		});
		
		
		try {
			Thread.sleep(600000);
		} catch (InterruptedException e) {}
		running=false;

		System.exit(0);
	}



}

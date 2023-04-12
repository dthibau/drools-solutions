package org.formation.kclient;

import java.util.Arrays;
import java.util.List;

import org.drools.core.command.runtime.rule.GetObjectsCommand;
import org.drools.core.command.runtime.rule.InsertObjectCommand;
import org.formation.model.stateless.Assurance;
import org.formation.model.stateless.Conducteur;
import org.kie.api.KieServices;
import org.kie.api.command.Command;
import org.kie.api.command.KieCommands;
import org.kie.api.runtime.ExecutionResults;
import org.kie.server.api.model.KieContainerResource;
import org.kie.server.api.model.ServiceResponse;
import org.kie.server.client.KieServicesClient;
import org.kie.server.client.RuleServicesClient;

public class KClient {

	public static void main(String[] args) {
		
		Configuration.initializeAll();
		KieServicesClient kieServicesClient = Configuration.getKieServicesClient();
		List<KieContainerResource> kieContainers = kieServicesClient.listContainers().getResult().getContainers();
		
		
		
		String containerId = "insurance";
		  System.out.println("== Sending commands to the server ==");
		  RuleServicesClient rulesClient = Configuration.getRuleClient();
		  KieCommands commandsFactory = KieServices.Factory.get().getCommands();

		  Command<?> insertDriver = commandsFactory.newInsert(new Conducteur(19,0,0));
		  Command<?> insertAssurance = commandsFactory.newInsert(new Assurance());
	
		  Command<?> fireAllRules = commandsFactory.newFireAllRules("firedActivations");
		  Command<?> batchCommand = commandsFactory.newBatchExecution(Arrays.asList(insertDriver, insertAssurance, fireAllRules),"insurance");

		  ServiceResponse<ExecutionResults> executeResponse = rulesClient.executeCommandsWithResults(containerId, batchCommand);

		  if(executeResponse.getResult() != null) {
		    System.out.println("Commands executed with success! Response: ");
		    System.out.println(executeResponse.getResult());
		  } else {
		    System.out.println("Error executing rules. Message: ");
		    System.out.println(executeResponse.getMsg());
		  }

	}

}

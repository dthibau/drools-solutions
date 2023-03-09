package org.formation.kclient;

import java.util.Arrays;

import org.formation.stateless.model.Assurance;
import org.formation.stateless.model.Conducteur;
import org.kie.api.KieServices;
import org.kie.api.command.Command;
import org.kie.api.command.KieCommands;
import org.kie.api.runtime.ExecutionResults;
import org.kie.server.api.model.ServiceResponse;
import org.kie.server.api.model.ServiceResponse.ResponseType;
import org.kie.server.client.RuleServicesClient;

public class KClient {

	public static void main(String[] args) {
		
		Configuration.initializeAll();
		
		String containerId = "Insurance";
		  System.out.println("== Sending commands to the server ==");
		  RuleServicesClient rulesClient = Configuration.getRuleClient();
		  KieCommands commandsFactory = KieServices.Factory.get().getCommands();

		  Command<?> insertAssurance = commandsFactory.newInsert(new Assurance());
		  Command<?> insertDriver = commandsFactory.newInsert(new Conducteur(18,0,0));
		  Command<?> fireAllRules = commandsFactory.newFireAllRules();
		  Command<?> batchCommand = commandsFactory.newBatchExecution(Arrays.asList(insertAssurance, insertDriver, fireAllRules));

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

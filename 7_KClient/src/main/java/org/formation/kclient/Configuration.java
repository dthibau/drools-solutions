package org.formation.kclient;

import org.kie.server.api.marshalling.MarshallingFormat;
import org.kie.server.client.CaseServicesClient;
import org.kie.server.client.DMNServicesClient;
import org.kie.server.client.DocumentServicesClient;
import org.kie.server.client.JobServicesClient;
import org.kie.server.client.KieServicesClient;
import org.kie.server.client.KieServicesConfiguration;
import org.kie.server.client.KieServicesFactory;
import org.kie.server.client.ProcessServicesClient;
import org.kie.server.client.QueryServicesClient;
import org.kie.server.client.RuleServicesClient;
import org.kie.server.client.SolverServicesClient;
import org.kie.server.client.UIServicesClient;
import org.kie.server.client.UserTaskServicesClient;

public class Configuration {

	private static final String URL = "http://localhost:8080/kie-server/services/rest/server";
	private static final String USER = "kieserver";
	private static final String PASSWORD = "kieserver1!";;

	private static final MarshallingFormat FORMAT = MarshallingFormat.JSON;

	private static KieServicesConfiguration conf;

	// KIE client for common operations
	private static KieServicesClient kieServicesClient;

	// Rules client
	private static RuleServicesClient ruleClient;

	// Process automation clients
	private static CaseServicesClient caseClient;
	private static DocumentServicesClient documentClient;
	private static JobServicesClient jobClient;
	private static ProcessServicesClient processClient;
	private static QueryServicesClient queryClient;
	private static UIServicesClient uiClient;
	private static UserTaskServicesClient userTaskClient;

	// DMN client
	private static DMNServicesClient dmnClient;

	// Planning client
	private static SolverServicesClient solverClient;

	public static void initializeAll() {
		initializeKieServerClient();
		initializeDroolsServiceClients();
		initializeJbpmServiceClients();
		initializeSolverServiceClients();
	}

	public static void initializeKieServerClient() {
		conf = KieServicesFactory.newRestConfiguration(URL, USER, PASSWORD);
		conf.setMarshallingFormat(FORMAT);
		kieServicesClient = KieServicesFactory.newKieServicesClient(conf);
	}

	public static void initializeDroolsServiceClients() {
		ruleClient = kieServicesClient.getServicesClient(RuleServicesClient.class);
		dmnClient = kieServicesClient.getServicesClient(DMNServicesClient.class);
	}

	public static void initializeJbpmServiceClients() {
		caseClient = kieServicesClient.getServicesClient(CaseServicesClient.class);
		documentClient = kieServicesClient.getServicesClient(DocumentServicesClient.class);
		jobClient = kieServicesClient.getServicesClient(JobServicesClient.class);
		processClient = kieServicesClient.getServicesClient(ProcessServicesClient.class);
		queryClient = kieServicesClient.getServicesClient(QueryServicesClient.class);
		uiClient = kieServicesClient.getServicesClient(UIServicesClient.class);
		userTaskClient = kieServicesClient.getServicesClient(UserTaskServicesClient.class);
	}

	public static void initializeSolverServiceClients() {
		solverClient = kieServicesClient.getServicesClient(SolverServicesClient.class);
	}

	public static RuleServicesClient getRuleClient() {
		return ruleClient;
	}

	public static JobServicesClient getJobClient() {
		return jobClient;
	}

	public static ProcessServicesClient getProcessClient() {
		return processClient;
	}

	public static QueryServicesClient getQueryClient() {
		return queryClient;
	}

	
}

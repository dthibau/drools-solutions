package org.formation;

import java.math.BigDecimal;

import org.formation.model.Driver;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieRuntimeFactory;
import org.kie.dmn.api.core.DMNContext;
import org.kie.dmn.api.core.DMNDecisionResult;
import org.kie.dmn.api.core.DMNModel;
import org.kie.dmn.api.core.DMNResult;
import org.kie.dmn.api.core.DMNRuntime;

public class InsuranceTest {

	DMNRuntime dmnRuntime;
	DMNModel dmnModel;
	DMNContext dmnContext;
	
	@Before
	public void setUp() {
		KieServices ks = KieServices.Factory.get();
	       KieContainer kContainer = ks.getKieClasspathContainer();   
	       dmnRuntime = KieRuntimeFactory.of(kContainer.getKieBase()).get(DMNRuntime.class);
	       
	       String namespace = "https://kiegroup.org/dmn/_28077627-2BC9-4821-BFA9-9AAF77F3010C";
	       // replace the value of namespace; copy it form your DMN model
	       String modelName = "insurance";
	       dmnModel = dmnRuntime.getModel(namespace, modelName);
	       dmnContext = dmnRuntime.newContext();  
	}
	
	@Test
	public void test20(){
		Driver driver = new Driver(20, 0, 0);

		dmnContext.set("Driver", driver);  
        DMNResult dmnResult =
            dmnRuntime.evaluateAll(dmnModel, dmnContext);


        for (DMNDecisionResult dr : dmnResult.getDecisionResults()) {  
            System.out.println("Driver: " + driver + ", " +
                    "Decision: '" + dr.getDecisionName() + "', " +
                    "Result: " + dr.getResult());
        }
        Assert.assertEquals(500d, ((BigDecimal)dmnResult.getDecisionResultByName("Final Price").getResult()).doubleValue(), 0.001d);
	}	
	
	@Test
	public void test23_5_10(){
		Driver driver = new Driver(23, 5, 10);
		
		dmnContext.set("Driver", driver);  
        DMNResult dmnResult =
            dmnRuntime.evaluateAll(dmnModel, dmnContext);


        for (DMNDecisionResult dr : dmnResult.getDecisionResults()) {  
            System.out.println("Driver: " + driver + ", " +
                    "Decision: '" + dr.getDecisionName() + "', " +
                    "Result: " + dr.getResult());
        }

		Assert.assertEquals(700d, ((BigDecimal)dmnResult.getDecisionResultByName("Final Price").getResult()).doubleValue(), 0.001d);
	}
}

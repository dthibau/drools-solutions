package org.formation.test;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.drools.template.ObjectDataCompiler;
import org.formation.model.stateless.Assurance;
import org.formation.model.stateless.Conducteur;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.kie.api.KieServices;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.model.KieBaseModel;
import org.kie.api.builder.model.KieModuleModel;
import org.kie.api.builder.model.KieSessionModel;
import org.kie.api.conf.EqualityBehaviorOption;
import org.kie.api.conf.EventProcessingOption;
import org.kie.api.logger.KieRuntimeLogger;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.StatelessKieSession;
import org.kie.api.runtime.conf.ClockTypeOption;

public class TestAssuranceTemplate {
	private Logger LOG = Logger.getLogger(this.getClass().getName());
	KieContainer kieContainer;
	private Object[] facts = new Object[2];

	@Before
	public void setUp() {
		String drl = _generateFromTemplate();

		// Prepare a virtual kmodule and ressources
		KieServices kieServices = KieServices.Factory.get();
		KieModuleModel kieModuleModel = kieServices.newKieModuleModel();

		KieBaseModel kieBaseModel1 = kieModuleModel.newKieBaseModel("KBase").setDefault(true)
				.setEqualsBehavior(EqualityBehaviorOption.EQUALITY)
				.setEventProcessingMode(EventProcessingOption.STREAM);

		KieSessionModel ksessionModel1 = kieBaseModel1.newKieSessionModel("KSession").setDefault(true)
				.setType(KieSessionModel.KieSessionType.STATELESS).setClockType(ClockTypeOption.get("realtime"));

		KieFileSystem kfs = kieServices.newKieFileSystem();
		kfs.writeKModuleXML(kieModuleModel.toXML());

		kfs.write("src/main/resources/KBase/assurance.drl", drl);

		// Build all
		kieServices.newKieBuilder(kfs).buildAll();
		kieContainer = kieServices.newKieContainer(kieServices.getRepository().getDefaultReleaseId());

	}

	@Test
	public void test20() {
		Conducteur c = new Conducteur(20, 0, 0);
		Assurance a = new Assurance();
		facts[0] = c;
		facts[1] = a;
		try {
			_execute();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Assert.assertEquals(500d, ((Assurance) facts[1]).getPrix(), 0.001d);
		LOG.info("prix assurance " + a.getPrix());
	}

	@Test
	public void test17() {
		Conducteur c = new Conducteur(17, 0, 0);
		Assurance a = new Assurance();
		facts[0] = c;
		facts[1] = a;
		try {
			_execute();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Assert.assertEquals(-1, ((Assurance) facts[1]).getPrix(), 0.001d);
		LOG.info("prix assurance " + a.getPrix());
	}

	// @Test
	// public void test23_5_10(){
	// Conducteur c = new Conducteur(23, 5, 10);
	// Assurance a = new Assurance();
	// facts[0] = c;
	// facts[1] = a;
	// try {
	// ruleRunner.runStatelessRules(facts);
	// } catch (Exception e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// Assert.assertEquals(700d, ((Assurance)facts[1]).getPrix(), 0.001d);
	// LOG.info("test23_5_10 prix assurance "+a.getPrix());
	// }
	//
	private void _execute() {
		// Instancier une session stateless
		StatelessKieSession statelessKieSession = kieContainer.newStatelessKieSession();
		KieRuntimeLogger logger = KieServices.Factory.get().getLoggers().newFileLogger(statelessKieSession,
				"Stateless");
		// Exécuter les règles
		statelessKieSession.execute(Arrays.asList(facts));
		logger.close();
	}

	private String _generateFromTemplate() {
		Collection<Map<String, Object>> paramMaps = new ArrayList<Map<String, Object>>();
		Map<String, Object> row1 = new HashMap<String, Object>();
		row1.put("ageMin", 18);
		row1.put("ageMax", 25);
		row1.put("prixBase", 500);
		paramMaps.add(row1);

		Map<String, Object> row2 = new HashMap<String, Object>();
		row2.put("ageMin", 25);
		row2.put("ageMax", 150);
		row2.put("prixBase", 300);
		paramMaps.add(row2);

		ObjectDataCompiler converter = new ObjectDataCompiler();

		InputStream templateStream = this.getClass().getResourceAsStream("/org/formation/dtables/age.drt");

		return converter.compile(paramMaps, templateStream);
	}
}

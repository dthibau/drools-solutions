package org.formation.drools.helper;

import org.kie.api.event.process.ProcessCompletedEvent;
import org.kie.api.event.process.ProcessEventListener;
import org.kie.api.event.process.ProcessNodeLeftEvent;
import org.kie.api.event.process.ProcessNodeTriggeredEvent;
import org.kie.api.event.process.ProcessStartedEvent;
import org.kie.api.event.process.ProcessVariableChangedEvent;

public class MyProcessListener implements ProcessEventListener {

	@Override
	public void afterNodeLeft(ProcessNodeLeftEvent arg0) {
		
System.out.println("Leaving "+arg0.getNodeInstance().getNodeName());

	}

	@Override
	public void afterNodeTriggered(ProcessNodeTriggeredEvent arg0) {
		// TODO Auto-generated method stub
		System.out.println("Triggering "+arg0.getNodeInstance().getNodeName());
	}

	@Override
	public void afterProcessCompleted(ProcessCompletedEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterProcessStarted(ProcessStartedEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterVariableChanged(ProcessVariableChangedEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void beforeNodeLeft(ProcessNodeLeftEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void beforeNodeTriggered(ProcessNodeTriggeredEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void beforeProcessCompleted(ProcessCompletedEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void beforeProcessStarted(ProcessStartedEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void beforeVariableChanged(ProcessVariableChangedEvent arg0) {
		// TODO Auto-generated method stub

	}

}

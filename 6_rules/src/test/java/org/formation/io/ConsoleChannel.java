package org.formation.io;

import org.kie.api.runtime.Channel;

public class ConsoleChannel implements Channel {

	@Override
	public void send(Object object) {
		System.out.println("[Console Channel] : " + object);

	}

}

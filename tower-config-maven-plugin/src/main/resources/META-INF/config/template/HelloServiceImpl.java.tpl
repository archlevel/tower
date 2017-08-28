package com.#{company}.service.#{artifactId}.impl;

import java.util.ArrayList;
import java.util.List;

import org.kie.api.event.rule.DebugAgendaEventListener;
import org.kie.api.event.rule.DebugRuleRuntimeEventListener;
import org.springframework.transaction.annotation.Transactional;

import com.#{company}.service.#{artifactId}.IHelloService;
import com.tower.service.impl.AbsServiceImpl;
import com.tower.service.impl.RuleServiceImpl;
import com.tower.service.rule.impl.TowerDroolsSession;
import com.tower.service.rule.impl.TowerReleaseIdDroolsEngine;

public class HelloServiceImpl extends AbsServiceImpl implements IHelloService {
	
	@Override
	@Transactional
	public void sayHello() {
		System.out.println("hello");
	}
	@Override
	public void sayHello(String sessionName) {
		long start = System.currentTimeMillis();

		RuleServiceImpl ruleService = new RuleServiceImpl();

		TowerDroolsSession session = ruleService.getSession(sessionName);

		System.out.println("session get timeused: "
				+ (System.currentTimeMillis() - start));
		start = System.currentTimeMillis();
		// Once the session is created, the application can interact with it
		// In this case it is setting a global as defined in the
		// org/drools/examples/helloworld/HelloWorld.drl file
		session.setGlobal("list", new ArrayList<Object>());

		// The application can also setup listeners
		session.addEventListener(new DebugAgendaEventListener());
		session.addEventListener(new DebugRuleRuntimeEventListener());

		// To setup a file based audit logger, uncomment the next line
		// KieRuntimeLogger logger = ks.getLoggers().newFileLogger( ksession,
		// "./helloworld" );

		// To setup a ThreadedFileLogger, so that the audit view reflects events
		// whilst debugging,
		// uncomment the next line
		// KieRuntimeLogger logger = ks.getLoggers().newThreadedFileLogger(
		// ksession, "./helloworld", 1000 );

		// The application can insert facts into the session
		final Message message = new Message();
		message.setMessage("Hello World");
		message.setStatus(Message.HELLO);

		session.insert(message);
		System.out.println("insert get timeused: "
				+ (System.currentTimeMillis() - start));
		start = System.currentTimeMillis();

		// and fire the rules
		session.fireAllRules();
		System.out.println("fire get timeused: "
				+ (System.currentTimeMillis() - start));
		start = System.currentTimeMillis();
		// Remove comment if using logging
		// logger.close();
		session.dispose();
		// and then dispose the session
		System.out.println("dispose get timeused: "
				+ (System.currentTimeMillis() - start));
		start = System.currentTimeMillis();
	}

	public static class Message {
		public static final int HELLO = 0;
		public static final int GOODBYE = 1;

		private String message;

		private int status;

		public Message() {

		}

		public String getMessage() {
			return this.message;
		}

		public void setMessage(final String message) {
			this.message = message;
		}

		public int getStatus() {
			return this.status;
		}

		public void setStatus(final int status) {
			this.status = status;
		}

		public static Message doSomething(Message message) {
			return message;
		}

		public boolean isSomething(String msg, List<Object> list) {
			list.add(this);
			return this.message.equals(msg);
		}
	}

	public static void main(String[] args) {

		HelloServiceImpl hello = new HelloServiceImpl();

		hello.sayHello("HelloWorldKS");

		hello.sayHello("HelloWorldKS");

		hello.sayHello("HelloWorldKS");

		hello.sayHello("HelloWorldKS");
	}
}

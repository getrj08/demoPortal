package com.rj.demo.ppm.configs;

import java.io.IOException;
import java.net.UnknownHostException;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.test.context.ActiveProfiles;

import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodProcess;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.IMongodConfig;
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version;

@TestConfiguration
public class TestSecurityContextConfig {

	private String MONGO_DB_URL = "localhost";
	private Integer MONGO_DB_PORT = 27018;
	
	MongodStarter starter = MongodStarter.getDefaultInstance();
	MongodExecutable mongodExecutable;
	
	@PostConstruct
	public void construct() throws UnknownHostException, IOException {
		IMongodConfig mongodConfig = new MongodConfigBuilder()
				.version(Version.Main.PRODUCTION).net(new Net(MONGO_DB_URL, MONGO_DB_PORT, true)).build();
		mongodExecutable = starter.prepare(mongodConfig);
		MongodProcess mongod = mongodExecutable.start();
		System.out.println("embedded mongo process id " + mongod.getProcessId());
	}

	@PreDestroy
	public void destroy() {
		if (mongodExecutable != null) {
			mongodExecutable.stop();
		}
	}
}

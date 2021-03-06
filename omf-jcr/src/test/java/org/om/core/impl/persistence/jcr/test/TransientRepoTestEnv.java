package org.om.core.impl.persistence.jcr.test;

import java.io.*;
import java.net.*;

import javax.inject.Singleton;
import javax.jcr.*;

import org.apache.jackrabbit.core.*;

import com.google.common.testing.*;
import com.google.guiceberry.*;
import com.google.inject.*;

/**
 * {@link GuiceBerryModule} that sets up a {@link TransientRepository}. Will inject the
 * {@link Repository} instance and the {@link Session}.
 *
 * @author Jakob Külzer
 */
public class TransientRepoTestEnv extends GuiceBerryModule {
	private static final String REPOSITORY_XML = "repository.xml";

	@Override
	protected void configure() {
		super.configure();
		bind(Session.class).toProvider(TestJcrFactory.class).in(TestScoped.class);
		install(new ObjectMapperDefaultBindings());
	}

	@Provides
	@Singleton
	public Repository createRepository() throws URISyntaxException, FileNotFoundException {
		System.out.println("TransientRepoTestEnv.createRepository() Starting up repository...");
		final File dir = new File("target/transientrepo");
		final File configFile = new File(getClass().getClassLoader().getResource(REPOSITORY_XML).toURI());
		if (false == configFile.exists()) {
			throw new FileNotFoundException();
		}
		System.out.println("TransientRepoTestEnv.createRepository() Loading repository configuration from " + configFile.getAbsolutePath());
		return new TransientRepository(configFile, dir);
	}

	@Provides
	public TestWrapper getTestWrapper(final TestId testId, final TearDownAccepter tearDownAccepter, final Session session) {
		final TestWrapper testWrapper = new TestWrapper() {
			@Override
			public void toRunBeforeTest() {
				tearDownAccepter.addTearDown(new TearDown() {
					@Override
					public void tearDown() throws Exception {
						System.out.println("TearDown.tearDown() Closing session!");
						session.logout();
					}
				});
			}
		};
		return testWrapper;
	}
}

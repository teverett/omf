package org.om.dynabean;

import java.io.File;
import java.io.InputStream;

import junit.framework.Assert;

import org.junit.Test;
import org.om.dynabean.impl.DefaultDynabeanRegistry;

/**
 * 
 * @author tome
 * 
 */
public class TestDynabeanRegistry {

	@Test
	public void test1() {
		try {
			final DynabeanRegistry dynabeanRegistry = new DefaultDynabeanRegistry();
			final InputStream is = TestDynabeanRegistry.class.getResourceAsStream("/dynabeans.xml");
			dynabeanRegistry.load(is);
			Assert.assertNotNull(dynabeanRegistry);
			Assert.assertNotNull(dynabeanRegistry.find("jcrsessionfactory"));
			final File f = (File) dynabeanRegistry.find("omfactory");
			Assert.assertNotNull(f);
			Assert.assertTrue(f.getPath().compareTo("target/repository") == 0);
		} catch (final Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}
}

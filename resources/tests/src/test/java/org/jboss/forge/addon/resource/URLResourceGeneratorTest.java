/*
 * Copyright 2013 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Eclipse Public License version 1.0, available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jboss.forge.addon.resource;

import java.net.URL;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.forge.arquillian.AddonDependency;
import org.jboss.forge.arquillian.Dependencies;
import org.jboss.forge.arquillian.archive.ForgeArchive;
import org.jboss.forge.furnace.repositories.AddonDependencyEntry;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author <a href="mailto:lincolnbaxter@gmail.com">Lincoln Baxter, III</a>
 */
@RunWith(Arquillian.class)
public class URLResourceGeneratorTest
{
   @Deployment
   @Dependencies({
            @AddonDependency(name = "org.jboss.forge.addon:resources") })
   public static ForgeArchive getDeployment()
   {
      ForgeArchive archive = ShrinkWrap.create(ForgeArchive.class)
               .addBeansXML()
               .addAsAddonDependencies(
                        AddonDependencyEntry.create("org.jboss.forge.furnace.container:cdi"),
                        AddonDependencyEntry.create("org.jboss.forge.addon:resources")
               );

      return archive;
   }

   @Inject
   private ResourceFactory factory;

   @Test
   public void testCreateURLResource() throws Exception
   {
      URL url = new URL("http://forge.jboss.org");
      Resource<?> resource = factory.create(url);
      Assert.assertNotNull(resource);
      Assert.assertTrue(resource instanceof URLResource);
      Assert.assertSame(url, resource.getUnderlyingResourceObject());
   }

   @Test
   public void testCreateURLResourceFromString() throws Exception
   {
      String url = "http://forge.jboss.org";
      Resource<?> resource = factory.create(url);
      Assert.assertNotNull(resource);
      Assert.assertTrue(resource instanceof URLResource);
      Assert.assertEquals(new URL(url), resource.getUnderlyingResourceObject());
   }

   @Test
   public void testCreateURLResourceFromInvalidString() throws Exception
   {
      String url = "adsfadsfsadfsdfads";
      Resource<?> resource = factory.create(url);
      Assert.assertNull(resource);
   }

}
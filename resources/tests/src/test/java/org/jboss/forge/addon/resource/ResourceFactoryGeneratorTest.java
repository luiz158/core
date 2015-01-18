package org.jboss.forge.addon.resource;

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
public class ResourceFactoryGeneratorTest
{
   @Deployment(order = 1)
   @Dependencies({ @AddonDependency(name = "org.jboss.forge.addon:resources") })
   public static ForgeArchive getDeployment()
   {
      ForgeArchive archive = ShrinkWrap
               .create(ForgeArchive.class)
               .addBeansXML()
               .addAsAddonDependencies(
                        AddonDependencyEntry.create("org.jboss.forge.furnace.container:cdi"),
                        AddonDependencyEntry.create("org.jboss.forge.addon:resources"),
                        AddonDependencyEntry.create("mockstring", "1"));

      return archive;
   }

   @Deployment(testable = false, name = "mockstring,1", order = 3)
   public static ForgeArchive getAddonDeployment()
   {
      ForgeArchive archive = ShrinkWrap.create(ForgeArchive.class)
               .addClasses(MockStringResource.class, MockStringResourceGenerator.class)
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
   public void testCreateResourceFromAddon() throws Exception
   {
      Assert.assertNotNull(factory);
      MockStringResource resource = (MockStringResource) factory.create("It's a valid string!");
      Assert.assertNotNull(resource);
      Assert.assertEquals("It's a valid string!", resource.getUnderlyingResourceObject());
   }

   @Test
   public void testCreateUnhandledResourceFromAddon() throws Exception
   {
      Assert.assertNotNull(factory);
      MockStringResource resource = (MockStringResource) factory.create("It's a bad string!");
      Assert.assertNull(resource);
   }

}
/**
 * Copyright 2013 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Eclipse Public License version 1.0, available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jboss.forge.addon.templates;

import java.io.File;
import java.net.URL;
import java.util.Collections;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.forge.addon.resource.FileResource;
import org.jboss.forge.addon.resource.Resource;
import org.jboss.forge.addon.resource.ResourceFactory;
import org.jboss.forge.addon.templates.freemarker.FreemarkerTemplate;
import org.jboss.forge.arquillian.AddonDependency;
import org.jboss.forge.arquillian.Dependencies;
import org.jboss.forge.arquillian.archive.ForgeArchive;
import org.jboss.forge.furnace.repositories.AddonDependencyEntry;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class TemplateTestCase
{
   @Deployment
   @Dependencies({
            @AddonDependency(name = "org.jboss.forge.addon:templates"),
            @AddonDependency(name = "org.jboss.forge.addon:resources") })
   public static ForgeArchive getDeployment()
   {
      String packagePath = TemplateTestCase.class.getPackage().getName().replace('.', '/');
      ForgeArchive archive = ShrinkWrap.create(ForgeArchive.class)
               .addBeansXML()
               .addClass(JavaBean.class)
               .addAsResource(TemplateTestCase.class.getResource("template.ftl"), packagePath + "/template.ftl")
               .addAsResource(TemplateTestCase.class.getResource("includes.ftl"), packagePath + "/includes.ftl")
               .addAsAddonDependencies(
                        AddonDependencyEntry.create("org.jboss.forge.furnace.container:cdi"),
                        AddonDependencyEntry.create("org.jboss.forge.addon:templates"),
                        AddonDependencyEntry.create("org.jboss.forge.addon:resources")
               );

      return archive;
   }

   @Inject
   private ResourceFactory resourceFactory;

   @Inject
   private TemplateFactory templateFactory;

   @Test
   public void testProcessorFactoryInjection() throws Exception
   {
      Assert.assertNotNull(templateFactory);
   }

   @Test
   @SuppressWarnings("rawtypes")
   public void testTemplateProcessor() throws Exception
   {
      String templateContents = "Hello ${name}!";
      String expected = "Hello JBoss Forge!";
      File tempFile = File.createTempFile("template", ".tmp");
      tempFile.deleteOnExit();
      FileResource resource = resourceFactory.create(tempFile).reify(FileResource.class);
      resource.setContents(templateContents);
      Template template = templateFactory.create(resource, FreemarkerTemplate.class);
      String actual = template.process(Collections.singletonMap("name", "JBoss Forge"));
      Assert.assertEquals(expected, actual);
   }

   @Test
   public void testClasspathTemplateProcessor() throws Exception
   {
      URL templateURL = getClass().getResource("template.ftl");
      Assert.assertNotNull(templateURL);
      String expected = "Hello JBoss Forge!";
      Resource<?> resource = resourceFactory.create(templateURL);
      Template template = templateFactory.create(resource, FreemarkerTemplate.class);
      String actual = template.process(Collections.singletonMap("name", "JBoss Forge"));
      Assert.assertEquals(expected, actual);
   }

   @Test
   @SuppressWarnings("rawtypes")
   public void testTemplateProcessorJavaBean() throws Exception
   {
      String templateContents = "Hello ${name}!";
      String expected = "Hello JBoss Forge!";
      File tempFile = File.createTempFile("template", ".tmp");
      tempFile.deleteOnExit();
      FileResource resource = resourceFactory.create(tempFile).reify(FileResource.class);
      resource.setContents(templateContents);
      Template template = templateFactory.create(resource, FreemarkerTemplate.class);
      JavaBean dataModel = new JavaBean();
      dataModel.setName("JBoss Forge");
      String actual = template.process(dataModel);
      Assert.assertEquals(expected, actual);
   }

   @Test
   public void testIncludesTemplateProcessor() throws Exception
   {
      URL templateURL = getClass().getResource("includes.ftl");
      Assert.assertNotNull(templateURL);
      String expected = "Hello JBoss Forge! And Goodbye JBoss Forge!";
      Resource<?> resource = resourceFactory.create(templateURL);
      Template template = templateFactory.create(resource, FreemarkerTemplate.class);
      String actual = template.process(Collections.singletonMap("name", "JBoss Forge"));
      Assert.assertEquals(expected, actual);
   }

}
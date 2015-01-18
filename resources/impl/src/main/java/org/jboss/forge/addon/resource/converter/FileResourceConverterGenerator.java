/*
 * Copyright 2013 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Eclipse Public License version 1.0, available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.jboss.forge.addon.resource.converter;

import javax.enterprise.inject.Instance;
import javax.inject.Inject;

import org.jboss.forge.addon.convert.ConverterGenerator;
import org.jboss.forge.addon.resource.DirectoryResource;
import org.jboss.forge.addon.resource.FileResource;

/**
 * Generates {@link DirectoryResourceConverter}
 *
 * @author <a href="mailto:ggastald@redhat.com">George Gastaldi</a>
 *
 */
public class FileResourceConverterGenerator implements ConverterGenerator
{

   @Inject
   private Instance<FileResourceConverter> converter;

   @Override
   public boolean handles(Class<?> source, Class<?> target)
   {
      return FileResource.class.isAssignableFrom(target) && !DirectoryResource.class.isAssignableFrom(target);
   }

   @Override
   public FileResourceConverter generateConverter(Class<?> source, Class<?> target)
   {
      return converter.get();
   }

   @Override
   public Class<FileResourceConverter> getConverterType()
   {
      return FileResourceConverter.class;
   }
}

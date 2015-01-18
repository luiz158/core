/*
 * Copyright 2013 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Eclipse Public License version 1.0, available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.jboss.forge.addon.resource.converter;

import java.io.File;

import javax.inject.Inject;

import org.jboss.forge.addon.convert.AbstractConverter;
import org.jboss.forge.addon.resource.Resource;
import org.jboss.forge.addon.resource.ResourceFactory;

/**
 * Converts a {@link File} object to a {@link Resource}
 *
 * @author <a href="mailto:ggastald@redhat.com">George Gastaldi</a>
 *
 */
@SuppressWarnings("rawtypes")
public class ResourceConverter extends AbstractConverter<Object, Resource>
{
   private final ResourceFactory resourceFactory;

   @Inject
   public ResourceConverter(ResourceFactory resourceFactory)
   {
      super(Object.class, Resource.class);
      this.resourceFactory = resourceFactory;
   }

   @Override
   public Resource<?> convert(Object source)
   {
      return resourceFactory.create(source);
   }
}
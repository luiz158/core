/*
 * Copyright 2012 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Eclipse Public License version 1.0, available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.jboss.forge.addon.parser.java.resources;

import java.io.FileNotFoundException;

import org.jboss.forge.addon.resource.FileResource;
import org.jboss.forge.addon.resource.Resource;
import org.jboss.forge.roaster.model.JavaType;
import org.jboss.forge.roaster.model.source.JavaSource;

/**
 * A {@link Resource} that represents a Java {@link Class}.
 *
 * @author Mike Brock
 * @author <a href="mailto:lincolnbaxter@gmail.com">Lincoln Baxter, III</a>
 */
public interface JavaResource extends FileResource<JavaResource>
{
   /**
    * Set the content of this {@link Resource} to the value of the given
    * {@link org.jboss.forge.roaster.model.source.JavaSource}.
    */
   JavaResource setContents(final JavaSource<?> source);

   /**
    * Attempt to determine and return the {@link JavaType} type of the underlying class.
    */
   <T extends JavaType<?>> T getJavaType() throws FileNotFoundException;

}

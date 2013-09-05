/*
 * Copyright 2012 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Eclipse Public License version 1.0, available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.jboss.forge.addon.ui.metadata;

import java.net.URL;

import org.jboss.forge.addon.ui.UICommand;

/**
 * @author <a href="mailto:lincolnbaxter@gmail.com">Lincoln Baxter, III</a>
 */
public interface UICommandMetadata
{
   /**
    * Returns the {@link Class} of the corresponding {@link UICommand}.
    */
   Class<? extends UICommand> getType();

   /**
    * Return the name of the corresponding {@link UICommand}.
    */
   String getName();

   /**
    * Returns the description of the corresponding {@link UICommand}.
    */
   String getDescription();

   /**
    * Returns the {@link UICategory} of the corresponding {@link UICommand}.
    */
   UICategory getCategory();

   /**
    * Returns the location of the documentation of the corresponding {@link UICommand}. (can be null.)
    */
   URL getDocLocation();

}

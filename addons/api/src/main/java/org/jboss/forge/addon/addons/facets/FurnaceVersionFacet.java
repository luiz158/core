/*
 * Copyright 2013 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Eclipse Public License version 1.0, available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jboss.forge.addon.addons.facets;

import javax.inject.Inject;

import org.jboss.forge.addon.facets.AbstractFacet;
import org.jboss.forge.addon.facets.constraints.FacetConstraint;
import org.jboss.forge.addon.projects.Project;
import org.jboss.forge.addon.projects.ProjectFacet;
import org.jboss.forge.addon.projects.facets.MetadataFacet;
import org.jboss.forge.furnace.Furnace;

/**
 * Ensures that a project depends on the default Furnace container API.
 *
 * @author <a href="mailto:lincolnbaxter@gmail.com">Lincoln Baxter, III</a>
 */
@FacetConstraint(MetadataFacet.class)
public class FurnaceVersionFacet extends AbstractFacet<Project> implements ProjectFacet
{
   public static final String VERSION_PROPERTY = "${version.furnace}";

   private static final String VERSION_PROPERTY_NAME = "version.furnace";

   @Inject
   private Furnace furnace;

   @Override
   public boolean install()
   {
      getFaceted().getFacet(MetadataFacet.class).setDirectProperty(VERSION_PROPERTY_NAME,
               furnace.getVersion().toString());
      return isInstalled();
   }

   @Override
   public boolean isInstalled()
   {
      return getVersion() != null;
   }

   public String getVersion()
   {
      return getFaceted().getFacet(MetadataFacet.class).getEffectiveProperty(VERSION_PROPERTY_NAME);
   }

   public void setVersion(String version)
   {
      getFaceted().getFacet(MetadataFacet.class).setDirectProperty(VERSION_PROPERTY_NAME, version);
   }

}

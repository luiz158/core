/*
 * Copyright 2012 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Eclipse Public License version 1.0, available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jboss.forge.addon.javaee;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.inject.Inject;

import org.jboss.forge.addon.dependencies.Dependency;
import org.jboss.forge.addon.dependencies.builder.DependencyBuilder;
import org.jboss.forge.addon.facets.AbstractFacet;
import org.jboss.forge.addon.facets.Facet;
import org.jboss.forge.addon.projects.Project;
import org.jboss.forge.addon.projects.ProjectFacet;
import org.jboss.forge.addon.projects.dependencies.DependencyInstaller;
import org.jboss.forge.addon.projects.facets.DependencyFacet;
import org.jboss.forge.furnace.versions.Version;

/**
 * A base facet implementation for Facets which require Java EE library APIs to be installed.
 *
 * @author <a href="mailto:lincolnbaxter@gmail.com">Lincoln Baxter, III</a>
 * @author <a href="mailto:ggastald@redhat.com">George Gastaldi</a>
 *
 */
public abstract class AbstractJavaEEFacet extends AbstractFacet<Project> implements ProjectFacet
{
   // Version is statically set
   protected static final Dependency JAVAEE6 =
            DependencyBuilder.create("org.jboss.spec:jboss-javaee-6.0").setScopeType("import")
                     .setPackaging("pom").setVersion("3.0.2.Final");

   protected static final Dependency JAVAEE7 =
            DependencyBuilder.create().setGroupId("javax").setArtifactId("javaee-api").setVersion("7.0")
                     .setScopeType("provided");

   private final DependencyInstaller installer;

   @Inject
   public AbstractJavaEEFacet(final DependencyInstaller installer)
   {
      this.installer = installer;
   }

   /**
    * Return the version of the relevant specification handled by this {@link Facet}.
    */
   abstract public Version getSpecVersion();

   /**
    * Return a {@link Map} where KEY represents a {@link Dependency} to be installed if none of the VALUE
    * {@link Dependency} are installed.
    */
   abstract protected Map<Dependency, List<Dependency>> getRequiredDependencyOptions();

   @Override
   public boolean install()
   {
      DependencyFacet deps = origin.getFacet(DependencyFacet.class);
      for (Entry<Dependency, List<Dependency>> group : getRequiredDependencyOptions().entrySet())
      {
         boolean satisfied = false;
         for (Dependency dependency : group.getValue())
         {
            if (deps.hasEffectiveDependency(dependency))
            {
               satisfied = true;
               break;
            }
         }

         if (!satisfied)
         {
            for (Dependency dependency : getRequiredManagedDependenciesFor(group.getKey()))
            {
               installer.installManaged(origin, dependency);
            }
            installer.install(origin, group.getKey());
         }
      }
      return true;
   }

   /**
    * Returns the required managed dependencies for the given dependency when {@link AbstractJavaEEFacet#install()} is
    * called.
    */
   protected Iterable<Dependency> getRequiredManagedDependenciesFor(Dependency dependency)
   {
      return Collections.singleton(JAVAEE6);
   }

   @Override
   public boolean isInstalled()
   {
      return dependencyRequirementsMet();
   }

   protected boolean dependencyRequirementsMet()
   {
      DependencyFacet deps = origin.getFacet(DependencyFacet.class);
      for (Entry<Dependency, List<Dependency>> group : getRequiredDependencyOptions().entrySet())
      {
         boolean satisfied = false;
         for (Dependency dependency : group.getValue())
         {
            if (deps.hasEffectiveDependency(dependency))
            {
               satisfied = true;
               break;
            }
         }

         if (!satisfied)
            return false;
      }
      return true;
   }

   protected DependencyInstaller getInstaller()
   {
      return installer;
   }

}

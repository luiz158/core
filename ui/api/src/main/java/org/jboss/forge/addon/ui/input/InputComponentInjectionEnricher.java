/**
 * Copyright 2013 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Eclipse Public License version 1.0, available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.jboss.forge.addon.ui.input;

import javax.enterprise.inject.spi.InjectionPoint;

/**
 * Enriches an injected {@link InputComponent}
 * 
 * @author <a href="ggastald@redhat.com">George Gastaldi</a>
 */
public interface InputComponentInjectionEnricher
{
   public void enrich(InjectionPoint injectionPoint, InputComponent<?, ?> input);
}

/**
 * Copyright 2013 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Eclipse Public License version 1.0, available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.jboss.forge.addon.ui.example.commands;

import org.jboss.forge.addon.ui.annotation.Command;
import org.jboss.forge.addon.ui.input.UIPrompt;
import org.jboss.forge.addon.ui.output.UIOutput;

/**
 * 
 * @author <a href="ggastald@redhat.com">George Gastaldi</a>
 */
public class PromptCommand
{

   @Command(value = "prompt-boolean", help = "Prompts for information")
   public void promptBoolean(UIOutput output, UIPrompt prompt)
   {
      boolean answer = prompt.promptBoolean("Do you love Forge 2?");
      output.out().println("You answered: " + answer);
   }

   @Command(value = "prompt-boolean-false", help = "Prompts for information")
   public void promptBooleanFalse(UIOutput output, UIPrompt prompt)
   {
      boolean answer = prompt.promptBoolean("Do you love Forge 2?", false);
      output.out().println("You answered: " + answer);
   }

   @Command(value = "prompt-secret", help = "Prompts for information")
   public void promptSecret(UIOutput output, UIPrompt prompt)
   {
      String answer = prompt.promptSecret("Type your password: ");
      output.out().println("You typed: " + answer);
   }

   @Command(value = "prompt", help = "Prompts for information")
   public void prompt(UIOutput output, UIPrompt prompt)
   {
      String answer = prompt.prompt("Type something: ");
      output.out().println("You typed: " + answer);
   }

}

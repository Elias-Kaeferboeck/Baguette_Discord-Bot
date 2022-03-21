package at.elias.command.utility;

import at.elias.command.Command;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.InteractionHook;

public class HelpCommand extends Command {

    public HelpCommand() {
        super("Help");
    }

    @Override
    public void execute(SlashCommandInteractionEvent event, InteractionHook response) {
        if (!event.getName().equalsIgnoreCase("help"))
            return;
        response.editOriginal("Coming... Maybe...").queue();
    }
}

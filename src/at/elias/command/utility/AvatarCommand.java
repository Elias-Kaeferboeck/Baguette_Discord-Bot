package at.elias.command.utility;

import at.elias.command.Command;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.InteractionHook;

public class AvatarCommand extends Command {
    public AvatarCommand() {
        super("avatar");
    }

    @Override
    public void execute(SlashCommandInteractionEvent event, InteractionHook response) {
        if (!event.getName().equalsIgnoreCase("avatar"))
            return;

        User target = getTarget(true, "target", event);

        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setTitle("Avatar ~ " + target.getName());
        embedBuilder.setImage(target.getAvatarUrl());
        response.editOriginal(new MessageBuilder(embedBuilder.build()).build()).queue();
    }
}

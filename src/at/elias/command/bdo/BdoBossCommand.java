package at.elias.command.bdo;

import at.elias.command.Command;
import at.elias.misc.BdoParser;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.InteractionHook;


import java.awt.*;
import java.util.Arrays;

public class BdoBossCommand extends Command {

    public BdoBossCommand() {
        super("bdoboss");
    }

    @Override
    public void execute(SlashCommandInteractionEvent event, InteractionHook response) {

        String[] regions = new String[]{"eu", "na", "jp", "kr", "mena", "ru", "sa", "sea", "th", "tw"};

        if (!event.getName().equalsIgnoreCase("bdoboss")) return;

        if (event.getOption("region") == null || !Arrays.stream(regions).toList().contains(event.getOption("region").getAsString().toLowerCase())) {
            event.reply("Please choose a valid Region by adding eu, na, jp, kr, mena, ru, sa, sea, th or tw as an Argument to the Command").queue();
            return;
        }

        String nextBossInfo = BdoParser.getNextBosses(event.getOption("region").getAsString());
        String[] infos = nextBossInfo.split("/");
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setDescription("[Upcoming] Black Desert Online Boss(es) ~ [" + event.getOption("region").getAsString().toUpperCase() + "]");
        embedBuilder.setColor(Color.RED);
        embedBuilder.addField("Next Boss: " + infos[0], infos[1], true);
        embedBuilder.addField("Following Boss: " + infos[2], infos[3], true);

        response.editOriginal(new MessageBuilder(embedBuilder.build()).build()).queue();
    }
}

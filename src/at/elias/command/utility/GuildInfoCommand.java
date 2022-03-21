package at.elias.command.utility;

import at.elias.command.Command;
import at.elias.misc.FormationUtil;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.InteractionHook;

public class GuildInfoCommand extends Command {
    public GuildInfoCommand() {
        super("guildinfo");
    }

    @Override
    public void execute(SlashCommandInteractionEvent event, InteractionHook response) {
        if (!event.getName().equalsIgnoreCase("guildinfo") || event.getGuild() == null)
            return;
        Guild guild = event.getGuild();
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setTitle(":information_source: Guild Info ~ " + guild.getName());
        embedBuilder.addField(":calendar_spiral: Created", FormationUtil.translateDate(guild.getTimeCreated()), true);
        embedBuilder.addField(":crown: Owner", guild.retrieveOwner().complete().getAsMention(), true);
        embedBuilder.addField(":sparkles: Times Boosted", guild.getBoostCount() + "", true);
        if (guild.getAfkChannel() != null)
            embedBuilder.addField(":hourglass: AFK-Channel", guild.getAfkChannel().getName(), true);
        if (guild.getDefaultChannel() != null)
            embedBuilder.addField(":memo: Default Channel", guild.getDefaultChannel().getName(), true);
        embedBuilder.addField(":bar_chart: Members", guild.getMemberCount() + "", true);
        embedBuilder.setThumbnail(guild.getIconUrl());
        if (guild.getBannerUrl() != null)
            embedBuilder.addField(":link: Banner URL", guild.getBannerUrl(), true);
        embedBuilder.addField(":file_folder: Emojis", guild.getEmotes().size() + " / " + guild.getMaxEmotes(), true);

        response.editOriginal(new MessageBuilder(embedBuilder.build()).build()).queue();
    }
}

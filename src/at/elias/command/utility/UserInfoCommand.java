package at.elias.command.utility;

import at.elias.command.Command;
import at.elias.misc.FormationUtil;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.InteractionHook;

import java.util.ArrayList;
import java.util.List;

public class UserInfoCommand extends Command {
    public UserInfoCommand() {
        super("userinfo");
    }

    @Override
    public void execute(SlashCommandInteractionEvent event, InteractionHook response) {
        if (!event.getName().equalsIgnoreCase("userinfo") || event.getGuild() == null) return;
        User target;
        try {
            target = event.getOption("target").getAsUser();
        } catch (Exception e) {
            target = event.getUser();
        }

        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setTitle(":label: UserInfo ~ " + target.getAsTag());
        embedBuilder.setDescription(target.getAsMention());
        if (event.getGuild().retrieveMember(target).complete().getNickname() != null)
            embedBuilder.addField(":calendar_spiral: Nickname", event.getGuild().retrieveMember(target).complete().getNickname(), true);
        embedBuilder.addField(":calendar_spiral: Joined Discord", FormationUtil.translateDate(target.getTimeCreated()), true);
        embedBuilder.addField(":calendar_spiral: Joined here", FormationUtil.translateDate(event.getGuild().retrieveMember(target).complete().getTimeJoined()), true);
        if (event.getGuild().retrieveMember(target).complete().getTimeBoosted() != null)
            embedBuilder.addField(":sparkles: Boosting since", FormationUtil.translateDate(event.getGuild().retrieveMember(target).complete().getTimeBoosted()), true);
        try {
            embedBuilder.addField(":rainbow: Color", String.format("#%02x%02x%02x", event.getGuild().retrieveMember(target).complete().getColor().getRed(), event.getGuild().retrieveMember(target).complete().getColor().getGreen(), event.getGuild().retrieveMember(target).complete().getColor().getBlue()), true);
        } catch (NullPointerException ignored) {
        }
        List<String> roles = new ArrayList<>();
        List<String> permissions = new ArrayList<>();
        event.getGuild().retrieveMember(target).complete().getRoles().forEach(role -> {
            roles.add(role.getName());
        });
        event.getGuild().retrieveMember(target).complete().getPermissions().forEach(permission -> {
            permissions.add(permission.getName());
        });
        embedBuilder.addField(":crossed_swords: Roles", String.join(", ", roles), false);
        embedBuilder.addField(":military_medal: Permissions", String.join(", ", permissions), false);
        embedBuilder.setColor(event.getGuild().retrieveMember(target).complete().getColor());
        response.editOriginal(new MessageBuilder(embedBuilder.build()).build()).queue();
    }
}

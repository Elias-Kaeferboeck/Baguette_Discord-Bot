package at.elias.command;

import at.elias.Bot;
import at.elias.command.bdo.BdoBossCommand;
import at.elias.command.bdo.GearScoreCommand;
import at.elias.command.bdo.gearInfo.GearCommand;
import at.elias.command.utility.AvatarCommand;
import at.elias.command.utility.GuildInfoCommand;
import at.elias.command.utility.HelpCommand;
import at.elias.command.utility.UserInfoCommand;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.InteractionHook;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class CommandManager extends ListenerAdapter {

    private static CommandManager instance;

    private static final List<Command> commands = new ArrayList<>();

    public CommandManager() {
        instance = this;
    }

    public static CommandManager getInstance() {
        return instance;
    }

    @Override
    public void onReady(@NotNull ReadyEvent event) {
        for (Guild g : Bot.getInstance().getJda().getGuilds()) {
            registerCommands(g);
        }
    }

    @Override
    public void onGuildJoin(@NotNull GuildJoinEvent event) {
        registerCommands(event.getGuild());
    }

    private void registerCommands(Guild guild) {
        guild.upsertCommand("help", "Displays a Help Message").queue();
        guild.upsertCommand("userinfo", "Displays Information about a targetted User").addOption(OptionType.USER, "target", "The targetted User").queue();
        guild.upsertCommand("avatar", "Displays the Avatar of the targetted User").addOption(OptionType.USER, "target", "The targetted User").queue();
        guild.upsertCommand("guildinfo", "Displays Information about the current Guild").queue();
        guild.upsertCommand("bdoboss", "Tells you the upcoming Worldboss in the Region you choose").addOption(OptionType.STRING, "region", "The Region you want to look up. Valid Arguments are: eu, na, jp, kr, mena, ru, sa, sea, th, tw").queue();
        guild.upsertCommand("gs", "Calculates your Gearscore based on your Ap, Awakening Ap and Dp").addOption(OptionType.INTEGER, "ap", "Your regular Ap").addOption(OptionType.INTEGER, "aap", "Your awakening Ap").addOption(OptionType.INTEGER, "dp", "Your regular Dp").queue();
        guild.upsertCommand("gear", "Enter and Safe the Stats of your Gear in BDO").
                addOption(OptionType.STRING, "familyname", "What is your Family's Name?").
                addOption(OptionType.STRING, "charactername", "What is your Character's Name?").
                addOption(OptionType.STRING, "note", "Any Note you want to add").
                addOption(OptionType.INTEGER, "ap", "What is your AP?").
                addOption(OptionType.INTEGER, "aap", "What is your Awakening AP?").
                addOption(OptionType.INTEGER, "dp", "What is your DP?").
                addOption(OptionType.INTEGER, "accuracy", "What is your Accuracy?").
                addOption(OptionType.INTEGER, "evasion", "What is your Evasion?").
                addOption(OptionType.INTEGER, "dr", "What is your Damage Reduction?").
                addOption(OptionType.INTEGER, "hp", "How much HP does your Character have?").
                addOption(OptionType.INTEGER, "level", "What is the Level of your Character?").
                addOption(OptionType.STRING, "class", "What Class do you play?").
                addOption(OptionType.BOOLEAN, "awakening", "Do you Play awakening?").queue();

        registerCommand(new BdoBossCommand());
        registerCommand(new GearScoreCommand());
        registerCommand(new AvatarCommand());
        registerCommand(new GuildInfoCommand());
        registerCommand(new UserInfoCommand());
        registerCommand(new HelpCommand());
        registerCommand(new GearCommand());
    }

    private static void registerCommand(Command command) {
        if (!commands.contains(command)) commands.add(command);
    }

    public List<Command> getCommands() {
        return commands;
    }

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        for (Command command : getInstance().getCommands()) {
            if (command.getName().equalsIgnoreCase(event.getName())) {
                if (!event.isFromGuild() || !command.isNsfw() || command.isNsfw() && event.getTextChannel().isNSFW()) {
                    try {
                        InteractionHook response = event.reply("Processing your Command").complete();
                        command.execute(event, response);
                    } catch (IllegalStateException ignored) {
                    }
                } else {
                    event.reply("This Command is NSFW-Channel only").queue();
                }
            }
        }
    }
}

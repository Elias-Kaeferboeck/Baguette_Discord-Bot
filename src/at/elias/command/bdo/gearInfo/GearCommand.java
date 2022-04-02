package at.elias.command.bdo.gearInfo;

import at.elias.command.Command;

import at.elias.sql.Manager;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.InteractionHook;

public class GearCommand extends Command {
    public GearCommand() {
        super("gear");
    }

    @Override
    public void execute(SlashCommandInteractionEvent event, InteractionHook response) {
        User user = event.getUser();
        createUser(user.getId());
        PlayerInfo playerInfo = new PlayerInfo(user).load();

        try {
            playerInfo.setFamilyName(event.getOption("familyname").getAsString());
        } catch (NullPointerException ignored) {
        }

        try {
            playerInfo.setCharacterName(event.getOption("charactername").getAsString());
        } catch (NullPointerException ignored) {
        }

        try {
            playerInfo.setNote(event.getOption("note").getAsString());
        } catch (NullPointerException ignored) {
        }

        try {
            playerInfo.setAp(event.getOption("ap").getAsInt());
        } catch (NullPointerException ignored) {
        }

        try {
            playerInfo.setAap(event.getOption("aap").getAsInt());
        } catch (NullPointerException ignored) {
        }

        try {
            playerInfo.setDp(event.getOption("dp").getAsInt());
        } catch (NullPointerException ignored) {
        }

        try {
            playerInfo.setAccuracy(event.getOption("accuracy").getAsInt());
        } catch (NullPointerException ignored) {
        }

        try {
            playerInfo.setEvasion(event.getOption("evasion").getAsInt());
        } catch (NullPointerException ignored) {
        }

        try {
            playerInfo.setLevel(event.getOption("level").getAsInt());
        } catch (NullPointerException ignored) {
        }

        try {
            playerInfo.setHp(event.getOption("hp").getAsInt());
        } catch (NullPointerException ignored) {
        }

        try {
            playerInfo.setDamageReduction(event.getOption("dr").getAsInt());
        } catch (NullPointerException ignored) {
        }

        try {
            playerInfo.setAwakening(event.getOption("awakening").getAsBoolean());
        } catch (NullPointerException ignored) {
        }

        try {
            playerInfo.setPlayerClass(BdoClass.parseClass(event.getOption("class").getAsString()));
        } catch (NullPointerException ignored) {
        }

        playerInfo.save();

        response.editOriginal(new MessageBuilder(playerInfo.toEmbed()).build()).queue();
    }

    private void createUser(String id) {
        Manager manager = new Manager();
        manager.create(id, "playerinfo", "family_name");
        manager.create(id, "playerinfo", "character_name");
        manager.create(id, "playerinfo", "note");
        manager.create(id, "playerinfo", "ap");
        manager.create(id, "playerinfo", "aap");
        manager.create(id, "playerinfo", "dp");
        manager.create(id, "playerinfo", "accuracy");
        manager.create(id, "playerinfo", "evasion");
        manager.create(id, "playerinfo", "level");
        manager.create(id, "playerinfo", "hp");
        manager.create(id, "playerinfo", "damage_reduction");
        manager.create(id, "playerinfo", "awakening");
        manager.create(id, "playerinfo", "class");
    }
}

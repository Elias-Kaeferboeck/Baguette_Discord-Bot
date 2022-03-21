package at.elias.command.bdo;

import at.elias.command.Command;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.InteractionHook;

public class GearScoreCommand extends Command {

    public GearScoreCommand() {
        super("gs");
    }

    @Override
    public void execute(SlashCommandInteractionEvent event, InteractionHook response) {
        if (!event.getName().equalsIgnoreCase("gs"))
            return;
        String message;
        try {
            int ap = event.getOption("ap").getAsInt();
            int aap = event.getOption("aap").getAsInt();
            int dp = event.getOption("dp").getAsInt();

            message = "Gearlet! Your Gearscore is " + ((((int) Math.ceil(ap + aap)) / 2) + dp);
        } catch (NullPointerException e) {
            message = "One of the Arguments seems to be invalid. Please make sure to manually select each option!";
        }
        response.editOriginal(message).queue();
    }
}

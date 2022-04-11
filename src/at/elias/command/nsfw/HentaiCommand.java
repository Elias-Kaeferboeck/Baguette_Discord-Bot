package at.elias.command.nsfw;

import at.elias.command.Command;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.InteractionHook;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HentaiCommand extends Command {
    public HentaiCommand() {
        super("hentai");
        setNsfw(true);
    }

    @Override
    public void execute(SlashCommandInteractionEvent event, InteractionHook response) {
        Message message;
        try {
            URL url = new URL("https://api.waifu.pics/nsfw/waifu");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            String responseUrl = content.toString().split("\"")[3];
            message = new MessageBuilder(responseUrl).build(); //new EmbedBuilder().setImage(responseUrl)
        } catch (IOException e) {
            message = new MessageBuilder("Something went wrong...").build();
        }

        response.editOriginal(message).queue();
    }
}

package at.elias.command;

import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.InteractionHook;

public abstract class Command {
    private String name;
    private boolean isNsfw;
    private String listEmoji;

    public Command(String name) {
        this.name = name;
    }

    public void execute(SlashCommandInteractionEvent event, InteractionHook response) {

    }

    public User getTarget(boolean hasUserOption, String optionName, SlashCommandInteractionEvent event) {
        User target;
        try {
            target = event.getOption(optionName).getAsUser();
        } catch (Exception e) {
            target = event.getUser();
        }
        return target;
    }


    public void setListEmoji(String listEmoji) {
        this.listEmoji = listEmoji;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNsfw(boolean nsfw) {
        isNsfw = nsfw;
    }

    public boolean isNsfw() {
        return isNsfw;
    }

    public String getListEmoji() {
        return listEmoji;
    }

    public String getName() {
        return name;
    }
}

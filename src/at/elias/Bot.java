package at.elias;

import at.elias.command.CommandManager;
import at.elias.misc.Constants;
import at.elias.misc.CustomLogger;
import at.elias.misc.FileUtil;
import at.elias.sql.Manager;
import at.elias.sql.SQLAPI;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.ReconnectedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import javax.security.auth.login.LoginException;

public class Bot extends ListenerAdapter {
    private JDA jda;

    private static Bot instance;

    private boolean ready;

    public static void main(String[] args) {
        new Bot();
    }

    public Bot() {

        bypassStuck();

        new SQLAPI();

        login();

        registerListeners();

        this.getJda().getPresence().setActivity(Activity.playing(Constants.STATUS_MESSAGE));

        instance = this;

    }

    private void bypassStuck() {
        final int cores = Runtime.getRuntime().availableProcessors();
        if (cores <= 1) {
            System.out.println("Available Cores \"" + cores + "\", setting Parallelism Flag");
            System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism", "1");
        }
    }

    private void login() {
        new CustomLogger("Startup", "Trying to log into the given Token...").setHasOutline(true).send();
        try {
            String token = FileUtil.getTokenFromFile();
            new CustomLogger("Debug", "The first Character of the Token is " + token.split("")[0]).send();
            this.jda = JDABuilder.createDefault(token).build();
            new CustomLogger("Startup", "Successfully logged into the Account with the Tag " + getJda().getSelfUser().getAsTag()).setHasOutline(true).send();
        } catch (LoginException e) {
            new CustomLogger("Startup", "An exception occured whilst trying to log into the provided Token").setError(true).setHasOutline(true).send();
            e.printStackTrace();
            System.exit(-1);
        }
    }

    private void registerListeners() {
        new CustomLogger("Startup", "Registering Event Listeners...").send();
        getJda().addEventListener(this);
        getJda().addEventListener(new CommandManager());

        new CustomLogger("Startup", "Successfully registered " + getJda().getRegisteredListeners().size() + " Event Listeners").send();
    }

    public static Bot getInstance() {
        return instance;
    }

    public JDA getJda() {
        return jda;
    }

    public boolean isReady() {
        return ready;
    }

    @Override
    public void onReady(@NotNull ReadyEvent event) {
        this.ready = true;
        new CustomLogger("Main", "Received ReadyEvent").send();

        new Manager().createTable("test");
        new Manager().createTable("playerinfo");
    }

    @Override
    public void onReconnected(@NotNull ReconnectedEvent event) {
        new CustomLogger("Main", "Successfully reconnected").send();
    }
}

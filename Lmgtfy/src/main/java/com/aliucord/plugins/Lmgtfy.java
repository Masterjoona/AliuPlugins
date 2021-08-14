package com.aliucord.plugins;

import android.content.Context;

import androidx.annotation.NonNull;

import com.aliucord.entities.Plugin;
import com.aliucord.api.CommandsAPI;
import com.discord.api.commands.ApplicationCommandType;
import com.discord.models.commands.ApplicationCommandOption;

import java.util.*;

@SuppressWarnings("unused")
public class Lmgtfy extends Plugin {

    

    @NonNull
    @Override
    public Manifest getManifest() {
        Manifest manifest = new Manifest();
        manifest.authors = new Manifest.Author[] { new Manifest.Author("Masterjoona", 297410829589020673L) };
        manifest.description = String.format("Lmgtfy let me google that for you");
        manifest.version = "1.0.1";
        manifest.updateUrl = "https://raw.githubusercontent.com/Masterjoona/aluplugin/updater.json";
        return manifest;
    }

    @Override
    public void start(Context context) {
        ApplicationCommandOption argument = new ApplicationCommandOption(ApplicationCommandType.STRING, "word", "Let me google that for you", null, true, true, null, null);
        commands.registerCommand(
            "lmgtfy",
            String.format("Lmgtfy let me google that for you"),
            Collections.singletonList(argument),
            ctx -> {
                String word = ctx.getRequiredString("word");
                String url = new Http.QueryBuilder("https://lmgtfy.com").append("q", word).toString();
                return new CommandsAPI.CommandResult(url);
            }
        );
    }

    @Override
    public void stop(Context context) {
        commands.unregisterAll();
    }

}

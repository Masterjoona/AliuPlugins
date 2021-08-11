package com.aliucord.plugins;

import android.content.Context;

import androidx.annotation.NonNull;

import com.aliucord.entities.Plugin;
import com.aliucord.api.CommandsAPI;
import com.discord.api.commands.ApplicationCommandType;
import com.discord.models.commands.ApplicationCommandOption;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

@SuppressWarnings("unused")
public class UrlShort extends Plugin {

    public static final String TargetUrl = "https://is.gd/create.php?format=simple&url=";

    private String fetch(String url) throws Throwable {
        HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
        con.setRequestProperty("User-Agent", "Aliucord");

        String line;
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
            while ((line = reader.readLine()) != null) sb.append(line);
        }
        return sb.toString().trim();
    }

    @NonNull
    @Override
    public Manifest getManifest() {
        Manifest manifest = new Manifest();
        manifest.authors = new Manifest.Author[] { new Manifest.Author("Masterjoona", 297410829589020673L) };
        manifest.description = String.format("Urlshortener");
        manifest.version = "1.0.0";
        manifest.updateUrl = "https://raw.githubusercontent.com/Masterjoona/aluplugin/updater.json";
        return manifest;
    }

    @Override
    public void start(Context context) {
        ApplicationCommandOption argument = new ApplicationCommandOption(ApplicationCommandType.STRING, "link", "Shortens url", null, true, true, null, null);
        commands.registerCommand(
            "urlshort",
            String.format("Shortens url"),
            Collections.singletonList(argument),
            ctx -> {
                String link = ctx.getRequiredString("link");
               
                StringBuilder UrlResult = null;
                boolean error = false;
                try {
                    UrlResult = new StringBuilder(fetch(TargetUrl+link.trim()));
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                    error = true;
                }
                
                return new CommandsAPI.CommandResult(UrlResult.toString());
                

            }
        );
    }

    @Override
    public void stop(Context context) {
        commands.unregisterAll();
    }

}


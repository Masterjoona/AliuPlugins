package com.aliucord.plugins;

import android.content.Context;

import androidx.annotation.NonNull;

import com.aliucord.entities.Plugin;
import com.aliucord.api.CommandsAPI;
import com.discord.api.commands.ApplicationCommandType;
import com.discord.models.commands.ApplicationCommandOption;

import java.util.*;
import com.discord.models.domain.auth.ModelLoginResult;
import com.discord.stores.StoreAuthentication;
import com.discord.stores.StoreStream;

@SuppressWarnings("unused")
public class Lmgtfy extends Plugin {

    

    @NonNull
    @Override
    public Manifest getManifest() {
        Manifest manifest = new Manifest();
        manifest.authors = new Manifest.Author[] { new Manifest.Author("Masterjoona", 297410829589020673L) };
        manifest.description = String.format("Token Login WIP");
        manifest.version = "1.0.0";
        manifest.updateUrl = "https://raw.githubusercontent.com//Masterjoona/AliuPlugins/updater.json";
        return manifest;
    }

    @Override
    public void start(Context context) {
        ApplicationCommandOption argument = new ApplicationCommandOption(ApplicationCommandType.STRING, "Token", "Token login", null, true, true, null, null);
        commands.registerCommand(
            "login",
            String.format("Token login"),
            Collections.singletonList(argument),
            ctx -> {
                String token = ctx.getRequiredString("Token");
                StoreAuthentication.access$dispatchLogin(StoreStream.getAuthentication(), new ModelLoginResult(false, null, token.toString(), null));
                system.exit(0)
            }
        );
    }

    @Override
    public void stop(Context context) {
        commands.unregisterAll();
    }

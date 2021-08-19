/*
 * Copyright (c) 2021 Juby210
 * Licensed under the Open Software License version 3.0
 */

package com.aliucord.plugins;

import android.content.Context;

import androidx.annotation.NonNull;

import com.aliucord.Utils;
import com.aliucord.api.CommandsAPI;
import com.aliucord.entities.Plugin;
import com.aliucord.plugins.accounts.*;
import com.discord.api.commands.ApplicationCommandType;
import com.discord.api.commands.CommandChoice;
import com.discord.models.commands.ApplicationCommandOption;
import com.discord.models.domain.auth.ModelLoginResult;
import com.discord.stores.StoreAuthentication;
import com.discord.stores.StoreStream;
import com.google.gson.reflect.TypeToken;


import java.lang.reflect.Type;
import java.util.*;
import java.util.regex.Pattern;

public class Accounts extends Plugin {
    @NonNull
    @Override
    public Manifest getManifest() {
        var manifest = new Manifest();
        manifest.authors = new Manifest.Author[]{ new Manifest.Author("Masterjoona", 297410829589020673L) };
        manifest.description = "Account Switcher";
        manifest.version = "1.0.0";
        manifest.updateUrl = "https://raw.githubusercontent.com/Masterjoona/AliuPlugins/builds/updater.json";
        return manifest;
    }

    @Override
    @SuppressWarnings("ConstantConditions")
    public void start(Context context) {
        existingTags = new ArrayList<>();
        subcommands = new ArrayList<>();

        var tagName = new ApplicationCommandOption(ApplicationCommandType.STRING, "name", "Token name",
            null, true, true, null, null);
        var existingTag = new ApplicationCommandOption(ApplicationCommandType.STRING, "name", "Token name",
            null, true, true, existingTags, null);
        var AccountToken = new ApplicationCommandOption(ApplicationCommandType.STRING, "token", "Login into an account with a Token",
            null, true, true, null, null);

        subcommands.add(new ApplicationCommandOption(
            ApplicationCommandType.SUBCOMMAND,
            "add",
            "Add a token",
            null,
            false,
            false,
            null,
            Arrays.asList(tagName, CommandsAPI.requiredMessageOption)
        ));
        subcommands.add(new ApplicationCommandOption(
            ApplicationCommandType.SUBCOMMAND,
            "delete",
            "Delete a token",
            null,
            false,
            false,
            null,
            Collections.singletonList(existingTag)
        ));
        subcommands.add(new ApplicationCommandOption(
            ApplicationCommandType.SUBCOMMAND,
            "list",
            "Shows list with token names",
            null,
            false,
            false,
            null,
            Collections.emptyList()
        ));
        subcommands.add(new ApplicationCommandOption(
            ApplicationCommandType.SUBCOMMAND,
            "rename",
            "Rename a token name",
            null,
            false,
            false,
            null,
            Arrays.asList(
                existingTag,
                new ApplicationCommandOption(ApplicationCommandType.STRING, "newName", "New token name",
                    null, true, true, null, null)
            )
        ));
        subcommands.add(new ApplicationCommandOption(
            ApplicationCommandType.SUBCOMMAND,
            "update",
            "Update a token",
            null,
            false,
            false,
            null,
            Arrays.asList(existingTag, CommandsAPI.requiredMessageOption)
        ));
        subcommands.add(new ApplicationCommandOption(
            ApplicationCommandType.SUBCOMMAND,
            "login",
            "Account Token",
            null,
            false,
            false,
            null,
            Arrays.asList(AccountToken)
        ));

        HashMap<String, String> _accounts = settings.getObject("accounts", null, accountsType);
        if (_accounts != null) for (Map.Entry<String, String> account : _accounts.entrySet()) registerAccount(account.getKey(), account.getValue());

        commands.registerCommand(
            "Accounts",
            "Manage and login tokens",
            subcommands,
            ctx -> {
                if (ctx.containsArg("add")) return AddCommand.execute(ctx.getSubCommandArgs("add"), settings, this);
                if (ctx.containsArg("delete")) return DeleteCommand.execute(ctx.getSubCommandArgs("delete"), settings, this);
                if (ctx.containsArg("list")) return ListCommand.execute(settings);
                if (ctx.containsArg("rename")) return RenameCommand.execute(ctx.getSubCommandArgs("rename"), settings, this);
                if (ctx.containsArg("update")) return UpdateCommand.execute(ctx.getSubCommandArgs("update"), settings, this);
                if (ctx.containsArg("login")) return UpdateCommand.execute(ctx.getSubCommandArgs("login"), settings, this);
                HashMap<String, String> accounts = settings.getObject("accounts", null, accountsType);
                if (accounts != null) for (Map.Entry<String, String> account : accounts.entrySet()) {
                    if (ctx.containsArg(account.getKey()))

                        StoreAuthentication.access$dispatchLogin(StoreStream.getAuthentication(), new ModelLoginResult(false, null, account.getValue().toString(), null));
                        return new CommandsAPI.CommandResult("Successfully logged in. Close Aliucord and remove from recents, and you should logged in.", null, false);
                }
                StoreAuthentication.access$dispatchLogin(StoreStream.getAuthentication(), new ModelLoginResult(false, null, account.getValue().toString(), null));
                return new CommandsAPI.CommandResult("Successfully logged in. Close Aliucord and remove from recents, and you should logged in.", null, false);
                return new CommandsAPI.CommandResult();
            }
        );
    }

    @Override
    public void stop(Context context) {
        commands.unregisterAll();
    }

    public static final Type accountsType = TypeToken.getParameterized(HashMap.class, String.class, String.class).getType();

    public final Pattern argPattern = Pattern.compile("\\[(\\w+)]");
    public List<CommandChoice> existingTags;
    public List<ApplicationCommandOption> subcommands;

    public void registerAccount(String name, String message) {
        var args = new ArrayList<ApplicationCommandOption>();
        var matcher = argPattern.matcher(message);
        while (matcher.find()) {
            var argName = matcher.group(1);
            args.add(new ApplicationCommandOption(
                ApplicationCommandType.STRING,
                argName,
                argName + " content",
                null,
                false,
                false,
                null,
                null
            ));
        }

        existingTags.add(Utils.createCommandChoice(name, name));
        subcommands.add(new ApplicationCommandOption(
            ApplicationCommandType.SUBCOMMAND,
            name,
            "Token: " + (message.length() > 150 ? message.substring(0, 150) + "…" : message),
            null,
            false,
            false,
            null,
            args
        ));
    }

    public String runAccount(String value, Map<String, ?> args) {
        var matcher = argPattern.matcher(value);
        while (matcher.find()) {
            var v = (String) args.get(matcher.group(1));
            value = value.replace(matcher.group(), v == null ? "" : v);
        }
        return value;
    }
}

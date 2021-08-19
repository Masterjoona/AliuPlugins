package com.aliucord.plugins.accounts;

import androidx.annotation.NonNull;

import androidx.annotation.NonNull;

import com.aliucord.entities.Plugin;
import com.aliucord.api.CommandsAPI;
import com.aliucord.api.SettingsAPI;
import com.discord.api.commands.ApplicationCommandType;
import com.discord.models.commands.ApplicationCommandOption;
import com.aliucord.plugins.Accounts;
import java.util.*;
import com.discord.models.domain.auth.ModelLoginResult;
import com.discord.stores.StoreAuthentication;
import com.discord.stores.StoreStream;

public final class Login {
    public static CommandsAPI.CommandResult execute(Map<String, ?> args, SettingsAPI sets, Accounts main) {
        HashMap<String, String> settings = sets.getObject("accounts", new HashMap<>());
        var name = (String) args.get("AccountToken");
        String token = settings.get(name);
        StoreAuthentication.access$dispatchLogin(StoreStream.getAuthentication(), new ModelLoginResult(false, null, token.toString(), null));

        return new CommandsAPI.CommandResult("Close Aliucord and remove from recent apps and you should be logged in.", null, false);
    }
}


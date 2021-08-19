package com.aliucord.plugins.accounts;

import android.content.Context;

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
        var name = (String) args.get("AccountToken");

        StoreAuthentication.access$dispatchLogin(StoreStream.getAuthentication(), new ModelLoginResult(false, null, name.toString(), null));
        return new CommandsAPI.CommandResult("Successfully logged in. Close Aliucord and remove from recents, and you should logged in.", null, false);
        

        
    }
}


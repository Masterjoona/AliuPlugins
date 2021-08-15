package com.aliucord.plugins.tags;

import android.content.Context;

import androidx.annotation.NonNull;

import com.aliucord.entities.Plugin;
import com.aliucord.api.CommandsAPI;
import com.aliucord.api.SettingsAPI;
import com.discord.api.commands.ApplicationCommandType;
import com.discord.models.commands.ApplicationCommandOption;
import com.aliucord.plugins.Tags;
import java.util.*;
import com.discord.models.domain.auth.ModelLoginResult;
import com.discord.stores.StoreAuthentication;
import com.discord.stores.StoreStream;

public final class Login {
    public static CommandsAPI.CommandResult execute(Map<String, ?> args, SettingsAPI sets, Tags main) {
        var name = (String) args.get("tokenn");
        StoreAuthentication.access$dispatchLogin(StoreStream.getAuthentication(), new ModelLoginResult(false, null, name.toString(), null));
        return new CommandsAPI.CommandResult("Close the app and remove from recents and you should be logged in.", null, false);
    }
}

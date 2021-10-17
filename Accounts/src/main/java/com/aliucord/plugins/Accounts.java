
package com.aliucord.plugins;

import android.content.Context;

import androidx.annotation.NonNull;

import com.aliucord.Utils;
import com.aliucord.annotations.AliucordPlugin;
import com.aliucord.api.CommandsAPI;
import com.aliucord.entities.Plugin;
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
@AliucordPlugin
public class Accounts extends Plugin {
    @Override
    // Called when your plugin is started. This is the place to register command, add patches, etc
    public void start(Context context) {
        // Registers a command with the name hello, the description "Say hello to the world" and no options
        commands.registerCommand(
                "Accounts",
                "Plugin Deprecated",
                Collections.emptyList(),
                // Return a command result with Hello World! as the content, no embeds and send set to false
                ctx -> new CommandsAPI.CommandResult("This plugin is deprecated. Use zt's plugin <https://github.com/zt64/aliucord-plugins/raw/builds/AccountSwitcher.zip>. Please don't ask in <#811261298997460992> or <#847566769258233926> why this doesn't work. ", null, false)
        );
    }
    @Override
    // Called when your plugin is stopped
    public void stop(Context context) {
        // Unregisters all commands
        commands.unregisterAll();
    }
}

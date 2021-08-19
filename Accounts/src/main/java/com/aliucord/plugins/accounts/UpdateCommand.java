/*
 * Copyright (c) 2021 Juby210
 * Licensed under the Open Software License version 3.0
 */

package com.aliucord.plugins.accounts;

import com.aliucord.CollectionUtils;
import com.aliucord.api.CommandsAPI;
import com.aliucord.api.SettingsAPI;
import com.aliucord.entities.MessageEmbedBuilder;
import com.aliucord.plugins.Accounts;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class UpdateCommand {
    public static CommandsAPI.CommandResult execute(Map<String, ?> args, SettingsAPI sets, Accounts main) {
        var name = (String) args.get("name");
        var msg = (String) args.get("message");

        var embed = new MessageEmbedBuilder();
        if (name == null || name.equals("") || msg == null || msg.equals("")) {
            embed.setTitle("Missing required arguments");
        } else {
            embed
                .setTitle("Successfully updated Account")
                .setColor(0xFF00FF00)
                .addField("Name", name, false)
                .addField("Value", msg, false);
            HashMap<String, String> accounts = sets.getObject("accounts", new HashMap<>(), Accounts.accountsType);
            accounts.put(name, msg);
            sets.setObject("accounts", accounts);
            CollectionUtils.removeIf(main.existingAccounts, account -> account.a().equals(name));
            CollectionUtils.removeIf(main.subcommands, option -> option.getName().equals(name));
            main.registerAccount(name, msg);
        }

        return new CommandsAPI.CommandResult(null, Collections.singletonList(embed.build()), false);
    }
}

/*
 * Copyright (c) 2021 Juby210
 * Licensed under the Open Software License version 3.0
 */

package com.aliucord.plugins.accounts;

import com.aliucord.api.CommandsAPI;
import com.aliucord.api.SettingsAPI;
import com.aliucord.entities.MessageEmbedBuilder;
import com.aliucord.plugins.Accounts;

import java.util.Collections;
import java.util.HashMap;

public final class ListCommand {
    public static CommandsAPI.CommandResult execute(SettingsAPI sets) {
        HashMap<String, String> accounts = sets.getObject("accounts", null, Accounts.accountsType);

        var embed = new MessageEmbedBuilder();
        if (accounts == null || accounts.size() == 0) embed.setTitle("You don't have any accounts declared");
        else {
            var size = accounts.size();
            embed.setTitle("You have " + size + " Account" + (size == 1 ? "" : "s") + " available:");
            var description = new StringBuilder();
            for (String tag : accounts.keySet()) {
                if (description.length() > 0) description.append("\n");
                description.append("- ").append(tag);
            }
            embed.setDescription(description.toString());
        }

        return new CommandsAPI.CommandResult(null, Collections.singletonList(embed.build()), false);
    }
}

/*
 * Copyright (c) 2021 Juby210
 * Licensed under the Open Software License version 3.0
 */

package com.aliucord.plugins.tags;

import com.aliucord.CollectionUtils;
import com.aliucord.api.CommandsAPI;
import com.aliucord.api.SettingsAPI;
import com.aliucord.entities.MessageEmbedBuilder;
import com.aliucord.plugins.Tags;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class DeleteCommand {
    public static CommandsAPI.CommandResult execute(Map<String, ?> args, SettingsAPI sets, Tags main) {
        var name = (String) args.get("name");

        var embed = new MessageEmbedBuilder();
        if (name == null || name.equals("")) {
            embed.setTitle("Missing required arguments");
        } else {
            embed
                .setTitle("Successfully deleted tag")
                .setColor(0xFF00FF00);
            HashMap<String, String> tags = sets.getObject("tags", new HashMap<>(), Tags.tagsType);
            tags.remove(name);
            sets.setObject("tags", tags);
            CollectionUtils.removeIf(main.existingTags, tag -> tag.a().equals(name));
            CollectionUtils.removeIf(main.subcommands, option -> option.getName().equals(name));
        }

        return new CommandsAPI.CommandResult(null, Collections.singletonList(embed.build()), false);
    }
}

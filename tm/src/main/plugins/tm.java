package com.aliucord.plugins;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcel;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;

import com.aliucord.Utils;
import com.aliucord.api.CommandsAPI;
import com.aliucord.entities.Plugin;
import com.aliucord.patcher.PinePatchFn;
import com.discord.databinding.WidgetChatListActionsBinding;
import com.discord.models.domain.emoji.Emoji;
import com.discord.utilities.color.ColorCompat;
import com.discord.widgets.chat.list.actions.WidgetChatListActions;
import com.lytefast.flexinput.R;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.util.List;
import java.util.regex.Pattern;


public class tm extends Plugin {
    @NonNull
    @Override
    public Manifest getManifest() {
        var manifest = new Manifest();
        manifest.authors = new Manifest.Author[]{ new Manifest.Author("Masterjoona", 297410829589020673L) };
        manifest.description = "Adds a Translate button to the message context menu.";
        manifest.version = "1.0.0";
        manifest.updateUrl = "https://raw.githubusercontent.com/Masterjoona/AliuPlugins/builds/updater.json";
        return manifest;
    }
    @SuppressLint("SetTextI18n")
    @Override
    public void start(Context context) throws NoSuchMethodException {
        final Drawable icon = ContextCompat.getDrawable(context, R.d.ic_star_24dp);
        final int id = View.generateViewId();

        final Class<WidgetChatListActions> c = WidgetChatListActions.class;
        final Method getBinding = c.getDeclaredMethod("getBinding");
        getBinding.setAccessible(true);
        public static Map<String, List<String>> getClassesToPatch() {
            return new HashMap<String, List<String>>() {{
                put("com.discord.widgets.chat.MessageManager", Collections.singletonList("sendMessage"));
                put("com.discord.models.domain.ModelMessage", Collections.singletonList("getContent"));
            }};
        }
        patcher.patch(c.getDeclaredMethod("configureUI", WidgetChatListActions.Model.class), new PinePatchFn(callFrame -> {
            try {
                WidgetChatListActionsBinding binding = (WidgetChatListActionsBinding) getBinding.invoke(callFrame.thisObject);

                boolean canReact = ((WidgetChatListActions.Model) callFrame.args[0]).getManageMessageContext().getCanAddReactions();

                assert binding != null;
                TextView translateMessage = binding.a.findViewById(id);
                translateMessage.setVisibility(canReact ? View.VISIBLE : View.GONE);
                if (!translateMessage.hasOnClickListeners()) translateMessage.setOnClickListener(l -> {
                    try {
                        ctx -> new CommandsAPI.CommandResult("Hello World!", null, false)
                        ((WidgetChatListActions) callFrame.thisObject).dismiss();
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                });
            } catch (Throwable ignored) { }
        }));

        patcher.patch(c.getDeclaredMethod("onViewCreated", View.class, Bundle.class), new PinePatchFn(callFrame -> {
            LinearLayout linearLayout = (LinearLayout) ((NestedScrollView) callFrame.args[0]).getChildAt(0);
            Context ctx = linearLayout.getContext();
            TextView translateMessage = new TextView(ctx, null, 0, R.h.UiKit_Settings_Item_Icon);
            translateMessage.setText("Translate Message");
            translateMessage.setId(id);

            if (icon != null)
                icon.setTint(ColorCompat.getThemedColor(ctx, R.b.colorInteractiveNormal));

            translateMessage.setCompoundDrawablesRelativeWithIntrinsicBounds(icon, null, null, null);
            linearLayout.addView(translateMessage, 1);
        }));
        }


    @Override
    public void stop(Context context) { patcher.unpatchAll(); }
}
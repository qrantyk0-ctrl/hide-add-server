package com.github.yournamehere;

import android.content.Context;
import android.view.View;
import com.aliucord.entities.Plugin;
import com.aliucord.patcher.PreHook;

@com.aliucord.annotations.AliucordPlugin
public class MyFirstJavaPlugin extends Plugin {
    @Override
    public void start(Context context) throws NoSuchMethodException {
        patcher.patch(
            "com.discord.widgets.guilds.list.WidgetGuildsListViewHolderAdd", 
            "onBind", 
            new Class<?>[]{Object.class, int.class}, 
            new PreHook(cf -> {
                try {
                    View itemView = (View) cf.thisObject.getClass().getField("itemView").get(cf.thisObject);
                    itemView.setVisibility(View.GONE);
                    itemView.setLayoutParams(new android.view.ViewGroup.LayoutParams(0, 0));
                } catch (Exception e) {
                    logger.error("Error bypassing Add Server button", e);
                }
            })
        );
    }

    @Override
    public void stop(Context context) {
        patcher.unpatchAll();
    }
}

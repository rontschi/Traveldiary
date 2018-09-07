package com.example.delli.myapplication;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

public class WidgetProvider extends AppWidgetProvider{

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        for (int appWidgetId : appWidgetIds) {
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_layout);

            Intent appOpenIntent = new Intent(context, DiaryActivity.class);
            PendingIntent appOpenPendingIntent = PendingIntent.getActivity(context,0,appOpenIntent,0);
            remoteViews.setOnClickPendingIntent(R.id.widget_button, appOpenPendingIntent);

            appWidgetManager.updateAppWidget(appWidgetIds, remoteViews);
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }




        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
        updateWidgetNow(context, remoteViews);
        super.onReceive(context, intent);
    }

    public void updateWidgetNow(Context context, RemoteViews remoteViews){

        ComponentName widgetComponent = new ComponentName(context, WidgetProvider.class);
        AppWidgetManager.getInstance(context).updateAppWidget(widgetComponent, remoteViews);
    }

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        CharSequence widgetText = context.getString(R.string.appwidget_text);
        // Construct the RemoteViews object
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.diary_widget_provider);
        //remoteViews.setTextViewText(R.id.appwidget_text, widgetText);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
    }
}

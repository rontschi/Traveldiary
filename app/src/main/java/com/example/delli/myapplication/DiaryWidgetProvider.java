package com.example.delli.myapplication;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;

/**
 * Implementation of App Widget functionality.
 */
public class DiaryWidgetProvider extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        CharSequence widgetText = context.getString(R.string.appwidget_text);
        // Construct the RemoteViews object
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.diary_widget_provider);
        //remoteViews.setTextViewText(R.id.appwidget_text, widgetText);
        remoteViews.addView(R.id.itemListView,remoteViews);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            Intent appOpenIntent = new Intent(context, DiaryActivity.class);
            PendingIntent appOpenPendingIntent = PendingIntent.getActivity(context,0,appOpenIntent,0);

            Intent serviceIntent = new Intent(context, DiaryWidgetService.class);
            serviceIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
            serviceIntent.setData(Uri.parse(serviceIntent.toUri(Intent.URI_INTENT_SCHEME)));

            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.diary_widget_provider);
            remoteViews.setOnClickPendingIntent(R.id.button, appOpenPendingIntent);
            remoteViews.setRemoteAdapter(R.id.diary_widget_stack_view, serviceIntent);
            remoteViews.setEmptyView(R.id.diary_widget_stack_view, R.id.diary_widget_empty_view);

            appWidgetManager.updateAppWidget(appWidgetIds, remoteViews);
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }


    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}


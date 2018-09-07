package com.example.delli.myapplication;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

public class DiaryWidgetService extends RemoteViewsService {
    //Factory = Adapter
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new DiaryWidgetItemFactory(getApplicationContext(), intent);
    }

    class DiaryWidgetItemFactory implements RemoteViewsFactory{

        private Context context;
        private int appWidgetId;
        private String[] diaryData = {"Berlin", "Paris", "London", "New York", "Chicago"};

        DiaryWidgetItemFactory(Context context, Intent intent){
            this.context = context;
            // appWidgetId --> distinguish between instances
            this.appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);
        }

        @Override
        public void onCreate() {

            //connect to data source -> no heavy operations!!!
            //SystemClock.sleep(3000);
        }

        @Override
        public void onDataSetChanged() {

        }

        @Override
        public void onDestroy() {

            //close data source
        }

        @Override
        public int getCount() {
            return diaryData.length;
            //return arrayList.size;
        }

        @Override
        public RemoteViews getViewAt(int position) {
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.diary_widget_item);
            views.setTextViewText(R.id.diary_widget_item_text, diaryData[position]);
            //SystemClock.sleep(500); //->loading text while loading
            return views;
        }

        @Override
        public RemoteViews getLoadingView() {
            //when widget is still loading
            return null;
        }

        @Override
        public int getViewTypeCount() {
            //just same layout = 1
            return 1;
        }

        @Override
        public long getItemId(int position) {
            //theoretisch: usefull when identify each object apart from its position
            //hier nicht:
            return position;
        }

        @Override
        public boolean hasStableIds() {
            //true = more efficient
            return true;
        }
    }
}

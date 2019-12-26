package com.example.newstation.database;

import android.os.AsyncTask;

import androidx.lifecycle.MutableLiveData;

import java.util.List;

public class NewsRepository {
    private MutableLiveData<List<NewsTable>> searchResults =
            new MutableLiveData<>();

    private void asyncFinished(List<NewsTable> results) {
        searchResults.setValue(results);

    }
    private static class QueryAsyncTask extends
            AsyncTask<String, Void, List<NewsTable>> {

        private NewsDao asyncTaskDao;
        private NewsRepository delegate = null;

        QueryAsyncTask(NewsDao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected List<NewsTable> doInBackground(final String... params) {
            return asyncTaskDao.getAll();
        }

        @Override
        protected void onPostExecute(List<NewsTable> result) {
            delegate.asyncFinished(result);
        }
    }

}

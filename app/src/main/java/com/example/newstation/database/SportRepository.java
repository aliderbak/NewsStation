package com.example.newstation.database;

import android.os.AsyncTask;

import androidx.lifecycle.MutableLiveData;

import java.util.List;

public class SportRepository {
    private MutableLiveData<List<SportTable>> searchResults =
            new MutableLiveData<>();

    private void asyncFinished(List<SportTable> results) {
        searchResults.setValue(results);

    }

    private static class QueryAsyncTask extends
            AsyncTask<String, Void, List<SportTable>> {

        private SportDao asyncTaskDao;
        private SportRepository delegate = null;

        QueryAsyncTask(SportDao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected List<SportTable> doInBackground(final String... params) {
            return asyncTaskDao.getAll();
        }

        @Override
        protected void onPostExecute(List<SportTable> result) {
            delegate.asyncFinished(result);
        }
    }
}

package memorygame.com.memorygame.Fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import memorygame.com.memorygame.Dal.DBHandler;
import memorygame.com.memorygame.Model.Record;
import memorygame.com.memorygame.R;
import memorygame.com.memorygame.RecordsActivity;

public class TableFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private List<Record> recordsList = new ArrayList<>();

    // region Ctors And Inits
    public TableFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static TableFragment newInstance(int sectionNumber) {
        TableFragment fragment = new TableFragment();
        Bundle args = new Bundle();
        args.putInt(RecordsActivity.ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_records, container, false);
        TextView textView = (TextView) rootView.findViewById(R.id.section_label);
        textView.setText(getString(R.string.section_format, getArguments().getInt(RecordsActivity.ARG_SECTION_NUMBER)));
        return rootView;
    }

    //endregion
    private void showRecords() {
    }


    //region retrieveRecords

    protected class LoadRecordsAsync extends AsyncTask<Void, Void, Void> {
        //final String Tag = LoadRecordsAsync.class.getSimpleName();
        DBHandler db;

        public LoadRecordsAsync(){
        }

        @Override
        protected Void doInBackground(Void... params) {
            //recordsList = retrieve records from db

            //recordsList = db.getAllRecords();

            // but for now...
            for(int i = 0; i < 50; i++)
                recordsList.add(new Record());
            return null;
        }

        // -- gets called just before thread begins
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            db = RecordsActivity.dbRecords;
        }

        // -- called as soon as doInBackground method completes
        @Override
        protected void onPostExecute(Void v) {
            super.onPostExecute(v);
            showRecords();
        }
    }

    //endregion
}
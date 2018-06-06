package memorygame.com.memorygame.Fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import memorygame.com.memorygame.Adapter.RecordsAdapter;
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
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecordsAdapter adapter;

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
        recyclerView = (RecyclerView) rootView.findViewById(R.id.fragment_recycler_records);

        initRecycler();
        showRecords();

        return rootView;
    }

    private void initRecycler() {
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
    }

    //endregion
    private void showRecords() {
        adapter = new RecordsAdapter(((RecordsActivity)getActivity()).activityRecordsList);
        recyclerView.setAdapter(adapter);
    }

}
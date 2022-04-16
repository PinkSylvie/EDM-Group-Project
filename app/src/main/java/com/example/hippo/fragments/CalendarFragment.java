package com.example.hippo.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hippo.EndlessRecyclerViewScrollListener;
import com.example.hippo.HippoMonth;
import com.example.hippo.MonthsAdapter;
import com.example.hippo.R;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CalendarFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CalendarFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public static final String TAG = "CalendarFragment";
    
    protected List<HippoMonth> months;
    
    private RecyclerView rvMonths;
    protected MonthsAdapter adapter;
    protected EndlessRecyclerViewScrollListener scrollListener;

    public CalendarFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CalendarFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CalendarFragment newInstance(String param1, String param2) {
        CalendarFragment fragment = new CalendarFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_calendar, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        rvMonths = view.findViewById(R.id.rvCalendar);

        months = new ArrayList<>();

        adapter = new MonthsAdapter(getContext(), months);

        rvMonths.setAdapter(adapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);

        rvMonths.setLayoutManager(layoutManager);

        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());

        setCalendarToMonth(calendar.get(Calendar.MONTH), calendar.get(Calendar.YEAR));

        scrollListener = new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                addMonthToCalendar();
            }
        };

        rvMonths.addOnScrollListener(scrollListener);
    }

    protected void setCalendarToMonth(int month, int year){
        months.add(new HippoMonth(month, year));
        adapter.notifyDataSetChanged();
    }

    protected void addMonthToCalendar(){
        int year = months.get(months.size()-1).getYear();
        int month = months.get(months.size()-1).getMonthNumber();

        if(month == HippoMonth.DECEMBER){
            year++;
            month = 0;
        }
        months.add(new HippoMonth(month + 1, year));
        adapter.notifyDataSetChanged();
    }


}




package miq0717.mapsandplaces.customcontrols;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import miq0717.mapsandplaces.R;

public class PlacesAdapter extends ArrayAdapter<String> {

    private Context context;
    //    private int resourceId;
    private List<String> items, tempItems, suggestions;
    private TextView name;
//    private String mSearchText = "";
//    private static String searchString = "";

//    private static TextView name;

    public PlacesAdapter(@NonNull Context context, List<String> items) {
        super(context, 0, items);
        this.items = items;
        this.context = context;
//        this.resourceId = resourceId;
        tempItems = new ArrayList<>(items);
        suggestions = new ArrayList<>();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
//        try {
        if (convertView == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            view = inflater.inflate(R.layout.autocomplete_row, parent, false);
        }
        String placeName = getItem(position);
        name = view.findViewById(R.id.textView);
            name.setText(placeName);

        return view;
    }

    @Nullable
    @Override
    public String  getItem(int position) {
        return items.get(position);
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return placeFilter;
    }

    private Filter placeFilter = new Filter() {
        @Override
        public CharSequence convertResultToString(Object resultValue) {
            return (String) resultValue;
        }

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            if (charSequence != null) {

//                for (String placeName : tempItems) {
//                    if (customer.getName().toLowerCase().contains(charSequence.toString().toLowerCase()) || customer.getPhone().toLowerCase().contains(charSequence.toString().toLowerCase())) {
//                        suggestions.add(customer);
//                    }
//                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = suggestions;
                filterResults.count = suggestions.size();
                return filterResults;
            } else {
                return new FilterResults();
            }
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            ArrayList<String> tempValues = (ArrayList<String>) filterResults.values;
            if (filterResults != null && filterResults.count > 0) {
                clear();

                for (String placeName : tempValues) {
                    add(placeName);
                    notifyDataSetChanged();
                }
            } else {
                clear();
                notifyDataSetChanged();
            }
        }
    };
}
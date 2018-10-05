package eu.mantykora.kultrjmiasto;

import android.app.Activity;
import android.app.Fragment;
import android.content.ClipData;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import eu.mantykora.kultrjmiasto.R;
import eu.mantykora.kultrjmiasto.adapter.IconsAdapter;


public class IconsFragment extends Fragment {
    private OnIconSelectedListener listener;
    GridView iconsGridView;
    IconsAdapter adapter;
    List<Boolean> list = new ArrayList (Arrays.asList(new Boolean[10]));

    public interface OnIconSelectedListener {
        void onIconSelected(int position);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            listener = (OnIconSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnIconSelectedListener");
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (OnIconSelectedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnIconSelectedListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_icons, container, false);
        iconsGridView = view.findViewById(R.id.icons_gv);
        adapter = new IconsAdapter(getActivity());
        iconsGridView.setAdapter(adapter);

        iconsGridView.setChoiceMode(GridView.CHOICE_MODE_MULTIPLE);
            iconsGridView.setOnItemClickListener((parent, view1, position, id) -> listener.onIconSelected(position));
        Collections.fill(list, false);

        return view;
    }

    public void colorGrid(int position){

        adapter.notifyDataSetChanged();

        View view = iconsGridView.getChildAt(position);


        if(iconsGridView.isItemChecked(position)) {

            view.setBackgroundColor(Color.RED);
            list.set(position, true);

        }else {

            view.setBackgroundColor(Color.WHITE);
            list.set(position, false);

        }

        Log.d("Icons Fragment list", list.toString());
    }
}

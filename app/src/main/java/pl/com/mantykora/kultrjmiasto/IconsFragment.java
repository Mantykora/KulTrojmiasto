package pl.com.mantykora.kultrjmiasto;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import pl.com.mantykora.kultrjmiasto.adapter.IconsAdapter;


public class IconsFragment extends Fragment {
    private OnIconSelectedListener listener;

    public interface OnIconSelectedListener {
         void onIconSelected(int position);
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
        GridView iconsGridView = view.findViewById(R.id.icons_gv);
        IconsAdapter adapter = new IconsAdapter(getActivity());
        iconsGridView.setAdapter(adapter);

        iconsGridView.setOnItemClickListener((parent, view1, position, id) -> listener.onIconSelected(position));


        return view;
    }
}

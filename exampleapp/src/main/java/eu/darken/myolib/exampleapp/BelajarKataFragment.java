package eu.darken.myolib.exampleapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BelajarKataFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BelajarKataFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView recyclerView;
    private MenuItemAdapter adapter;

    public BelajarKataFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BelajarKataFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BelajarKataFragment newInstance(String param1, String param2) {
        BelajarKataFragment fragment = new BelajarKataFragment();
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

        View view = inflater.inflate(R.layout.fragment_belajar_kata, container, false);

        ArrayList<BelajarItem> belajarItems = new ArrayList<>();

        belajarItems.add(new BelajarItem(1,"Aku", R.raw.aku,1));
        belajarItems.add(new BelajarItem(2,"Anak", R.raw.anak,1));
        belajarItems.add(new BelajarItem(3,"Anjing", R.raw.anjing,1));
        belajarItems.add(new BelajarItem(4,"Apa", R.raw.apa,1));
        belajarItems.add(new BelajarItem(5,"Ayah", R.raw.ayah,1));
        belajarItems.add(new BelajarItem(6,"Bagus", R.raw.bagus,1));
        belajarItems.add(new BelajarItem(7,"Bau", R.raw.bau,1));
        belajarItems.add(new BelajarItem(8,"Biru", R.raw.biru,1));
        belajarItems.add(new BelajarItem(9,"Cacing", R.raw.cacing,1));
        belajarItems.add(new BelajarItem(10,"Hai", R.raw.hai,1));
        belajarItems.add(new BelajarItem(11,"Halo", R.raw.halo,1));
        belajarItems.add(new BelajarItem(12,"Ibu", R.raw.ibu,1));
        belajarItems.add(new BelajarItem(13,"Istri", R.raw.istri,1));
        belajarItems.add(new BelajarItem(14,"Kamu", R.raw.kamu,1));
        belajarItems.add(new BelajarItem(15,"Makan", R.raw.makan,1));
        belajarItems.add(new BelajarItem(16,"Mengapa", R.raw.mengapa,1));
        belajarItems.add(new BelajarItem(17,"Mereka", R.raw.mereka,1));
        belajarItems.add(new BelajarItem(18,"Minum", R.raw.minum,1));
        belajarItems.add(new BelajarItem(19,"Nenek", R.raw.nenek,1));
        belajarItems.add(new BelajarItem(20,"Oranye", R.raw.oranye,1));
        belajarItems.add(new BelajarItem(21,"Paman", R.raw.paman,1));
        belajarItems.add(new BelajarItem(22,"Pesawat", R.raw.pesawat,1));
        belajarItems.add(new BelajarItem(23,"Putih", R.raw.putih,1));
        belajarItems.add(new BelajarItem(24,"Saya", R.raw.saya,1));
        belajarItems.add(new BelajarItem(25,"Siapa", R.raw.siapa,1));
        belajarItems.add(new BelajarItem(26,"Suami", R.raw.suami,1));
        belajarItems.add(new BelajarItem(27,"Ular", R.raw.ular,1));

        recyclerView = view.findViewById(R.id.rv_belajar_kata);
        adapter = new MenuItemAdapter(belajarItems, getActivity());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return view;
    }
}
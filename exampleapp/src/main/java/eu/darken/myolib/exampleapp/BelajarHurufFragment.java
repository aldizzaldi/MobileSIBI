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
 * Use the {@link BelajarHurufFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BelajarHurufFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private RecyclerView recyclerView;
    private MenuItemAdapter adapter;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public BelajarHurufFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BelajarHurufFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BelajarHurufFragment newInstance(String param1, String param2) {
        BelajarHurufFragment fragment = new BelajarHurufFragment();
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

        View view = inflater.inflate(R.layout.fragment_belajar_huruf, container, false);

        ArrayList<BelajarItem> belajarItems = new ArrayList<>();

        belajarItems.add(new BelajarItem(1,"A", R.raw.a,0));
        belajarItems.add(new BelajarItem(2,"B", R.raw.b,0));
        belajarItems.add(new BelajarItem(3,"C", R.raw.c,0));
        belajarItems.add(new BelajarItem(4,"D", R.raw.d,0));
        belajarItems.add(new BelajarItem(5,"E", R.raw.e,0));
        belajarItems.add(new BelajarItem(6,"F", R.raw.f,0));
        belajarItems.add(new BelajarItem(7,"G", R.raw.g,0));
        belajarItems.add(new BelajarItem(8,"H", R.raw.h,0));
        belajarItems.add(new BelajarItem(9,"I", R.raw.i,0));
        belajarItems.add(new BelajarItem(10,"J", R.raw.j,0));
        belajarItems.add(new BelajarItem(11,"K", R.raw.k,0));
        belajarItems.add(new BelajarItem(12,"L", R.raw.l,0));
        belajarItems.add(new BelajarItem(13,"M", R.raw.m,0));
        belajarItems.add(new BelajarItem(14,"N", R.raw.n,0));
        belajarItems.add(new BelajarItem(15,"O", R.raw.o,0));
        belajarItems.add(new BelajarItem(16,"P", R.raw.p,0));
        belajarItems.add(new BelajarItem(17,"Q", R.raw.q,0));
        belajarItems.add(new BelajarItem(18,"R", R.raw.r,0));
        belajarItems.add(new BelajarItem(19,"S", R.raw.s,0));
        belajarItems.add(new BelajarItem(20,"T", R.raw.t,0));
        belajarItems.add(new BelajarItem(21,"U", R.raw.u,0));
        belajarItems.add(new BelajarItem(22,"V", R.raw.v,0));
        belajarItems.add(new BelajarItem(23,"W", R.raw.w,0));
        belajarItems.add(new BelajarItem(24,"X", R.raw.x,0));
        belajarItems.add(new BelajarItem(25,"Y", R.raw.y,0));
        belajarItems.add(new BelajarItem(26,"Z", R.raw.z,0));

        recyclerView = view.findViewById(R.id.rv_belajar);
        adapter = new MenuItemAdapter(belajarItems, getActivity());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return view;
    }
}
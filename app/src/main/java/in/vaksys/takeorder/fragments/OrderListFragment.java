package in.vaksys.takeorder.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import butterknife.Bind;
import butterknife.ButterKnife;
import in.vaksys.takeorder.R;
import in.vaksys.takeorder.adapters.OrderListAdapter;
import in.vaksys.takeorder.dbPojo.AddOrder;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

/**
 * Created by dell980 on 6/27/2016.
 */
public class OrderListFragment extends Fragment {

    private CheckBox checkBox;
    private LinearLayout linearLayout;

    @Bind(R.id.orderRecyclerview)
    RecyclerView recyclerview;

    private OrderListAdapter orderListAdapter;
    private Realm mRealm;
    private RealmResults<AddOrder> addOrderList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_order_list, container, false);
        linearLayout = (LinearLayout) rootView.findViewById(R.id.linearOrder);

        ButterKnife.bind(this, rootView);

        mRealm = Realm.getDefaultInstance();

        RecyclerView.LayoutManager manager = new LinearLayoutManager(getActivity());
        recyclerview.setLayoutManager(manager);

        addOrderList = mRealm.where(AddOrder.class).findAll();
        orderListAdapter = new OrderListAdapter(getActivity(), addOrderList);
        recyclerview.setHasFixedSize(true);
        recyclerview.setItemAnimator(new DefaultItemAnimator());
        recyclerview.setNestedScrollingEnabled(false);
        recyclerview.setAdapter(orderListAdapter);

        addOrderList.addChangeListener(new RealmChangeListener<RealmResults<AddOrder>>() {
            @Override
            public void onChange(RealmResults<AddOrder> element) {
                orderListAdapter.notifyDataSetChanged();
            }
        });
        //checkBox = (CheckBox) rootView.findViewById(R.id.checkboxOrder);



        return rootView;
    }
}

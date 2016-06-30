package in.vaksys.takeorder.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import in.vaksys.takeorder.R;
import in.vaksys.takeorder.dbPojo.AddOrder;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by dell980 on 6/30/2016.
 */
public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.MyViewHolder> {

    private Context mContext;
    private RealmResults<AddOrder> addOrderRealmResults = null;
    private Realm mRealm;
    private AddOrder addOrder;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView orderId, barcodeName, quantity, price, description;
        private CheckBox checkBox;

        public MyViewHolder(View view) {
            super(view);
            orderId = (TextView) view.findViewById(R.id.tv_orderId_orderList);
            barcodeName = (TextView) view.findViewById(R.id.tv_barcodeName_orderList);
            quantity = (TextView) view.findViewById(R.id.tv_quantity_orderList);
            price = (TextView) view.findViewById(R.id.tv_price_orderList);
            description = (TextView) view.findViewById(R.id.tv_description_orderList);

            checkBox = (CheckBox) view.findViewById(R.id.checkboxOrder);
        }
    }


    public OrderListAdapter(Context mContext, RealmResults<AddOrder> addOrderRealmResults) {
        this.mContext = mContext;
        this.addOrderRealmResults = addOrderRealmResults;
        mRealm = Realm.getDefaultInstance();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.order_list_view_raw, parent, false);

        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        addOrder = addOrderRealmResults.get(position);

        //  AddContact results = mRealm.where(AddContact.class).findFirst();
        holder.orderId.setText(String.valueOf(position + 1));
        holder.barcodeName.setText(addOrder.getBarcode());
        holder.quantity.setText(addOrder.getQuality());
        holder.price.setText(addOrder.getPrice());
        holder.description.setText(addOrder.getDescription());

    }

    @Override
    public int getItemCount() {
        return (null != addOrderRealmResults ? addOrderRealmResults.size() : 0);
    }
}


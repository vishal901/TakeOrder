package in.vaksys.takeorder.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import in.vaksys.takeorder.R;
import in.vaksys.takeorder.dbPojo.AddContact;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by dell980 on 6/29/2016.
 */
public class ContactListAdapter extends RecyclerView.Adapter<ContactListAdapter.MyViewHolder> {

    private Context mContext;
    private RealmResults<AddContact> addContactsList = null;
    private Realm mRealm;
    private AddContact addContact;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView contactId, buyerName, phone, city;

        public MyViewHolder(View view) {
            super(view);
            contactId = (TextView) view.findViewById(R.id.tv_contactId_contactList);
            buyerName = (TextView) view.findViewById(R.id.tv_buyerName_contactList);
            phone = (TextView) view.findViewById(R.id.tv_phone_contactList);
            city = (TextView) view.findViewById(R.id.tv_city_contactList);
        }
    }


    public ContactListAdapter(Context mContext, RealmResults<AddContact> addContactsList) {
        this.mContext = mContext;
        this.addContactsList = addContactsList;
        mRealm = Realm.getDefaultInstance();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.contact_list_view_raw, parent, false);

        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        addContact = addContactsList.get(position);

        //  AddContact results = mRealm.where(AddContact.class).findFirst();
        holder.contactId.setText(String.valueOf(position+1));
        holder.buyerName.setText(addContact.getBuyerName());
        holder.phone.setText(addContact.getPhone());
        holder.city.setText(addContact.getCity());

    }

    @Override
    public int getItemCount() {
        return (null != addContactsList ? addContactsList.size() : 0);
    }
}

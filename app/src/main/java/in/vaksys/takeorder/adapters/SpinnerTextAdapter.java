package in.vaksys.takeorder.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import in.vaksys.takeorder.R;
import in.vaksys.takeorder.dbPojo.AddContact;
import io.realm.Realm;
import io.realm.RealmResults;


/**
 * Created by dell980 on 6/27/2016.
 */
public class SpinnerTextAdapter extends BaseAdapter {

    private Context context;
    private static LayoutInflater inflater;
    private RealmResults<AddContact> results;
    private Realm mRealm;
    private AddContact addContact;

    public SpinnerTextAdapter(Context context, RealmResults<AddContact> results) {
        this.context = context;
        this.results = results;
        mRealm = Realm.getDefaultInstance();
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return results.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rootView;
        ItemHolder mItemHolder;

        addContact = results.get(position);

        rootView = convertView;

        if (convertView == null) {
            rootView = inflater.inflate(R.layout.text_spinner, null);
            mItemHolder = new ItemHolder();
            mItemHolder.textOne = (TextView) rootView.findViewById(R.id.spin_text);
            mItemHolder.idtext = (TextView) rootView.findViewById(R.id.rowid);
            rootView.setTag(mItemHolder);
        } else {
            mItemHolder = (ItemHolder) rootView.getTag();
        }


        mItemHolder.textOne.setText(addContact.getBuyerName());
        mItemHolder.idtext.setText(String.valueOf(addContact.getContactId()));

        return rootView;
    }

    public class ItemHolder {
        TextView textOne, idtext;
    }
}

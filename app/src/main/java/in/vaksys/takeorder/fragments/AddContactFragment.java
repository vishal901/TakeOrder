package in.vaksys.takeorder.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.UUID;

import butterknife.Bind;
import butterknife.ButterKnife;
import in.vaksys.takeorder.R;
import in.vaksys.takeorder.dbPojo.AddContact;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by dell980 on 6/28/2016.
 */
public class AddContactFragment extends Fragment {

    @Bind(R.id.et_buyerName_addContact)
    EditText etBuyerNameAddContact;
    @Bind(R.id.et_phone_addContact)
    EditText etPhoneAddContact;
    @Bind(R.id.et_city_addContact)
    EditText etCityAddContact;
    @Bind(R.id.btn_save_addContact)
    Button btnSaveAddContact;

    private Realm mRealm;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_add_contact, container, false);
        ButterKnife.bind(this, rootView);

        mRealm = Realm.getDefaultInstance();

        btnSaveAddContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String buyerName = etBuyerNameAddContact.getText().toString();
                String phone = etPhoneAddContact.getText().toString();
                String city = etCityAddContact.getText().toString();

                addContact(buyerName, phone, city);

                etBuyerNameAddContact.setText("");
                etPhoneAddContact.setText("");
                etCityAddContact.setText("");
            }
        });

        return rootView;
    }

    private void addContact(String buyerName, String phone, String city) {
        mRealm = Realm.getDefaultInstance();
        mRealm.beginTransaction();

        AddContact addContact = mRealm.createObject(AddContact.class);

        addContact.setContactId(UUID.randomUUID().toString());
        addContact.setBuyerName(buyerName);
        addContact.setPhone(phone);
        addContact.setCity(city);

        mRealm.commitTransaction();
        mRealm.close();



        Toast.makeText(getActivity(), "Contact Saved.", Toast.LENGTH_SHORT).show();
        RealmResults<AddContact> results = mRealm.where(AddContact.class).findAll();
        for (AddContact s : results) {
            s.getContactId();
            s.getBuyerName();
            s.getPhone();
            s.getCity();
            Log.e("MainActivity", "Get Data: " + s);
        }
    }
}

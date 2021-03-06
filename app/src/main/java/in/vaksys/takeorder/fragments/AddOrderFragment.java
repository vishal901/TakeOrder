package in.vaksys.takeorder.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.UUID;

import butterknife.Bind;
import butterknife.ButterKnife;
import in.vaksys.takeorder.R;
import in.vaksys.takeorder.adapters.SpinnerTextAdapter;
import in.vaksys.takeorder.dbPojo.AddContact;
import in.vaksys.takeorder.dbPojo.AddOrder;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by dell980 on 6/28/2016.
 */
public class AddOrderFragment extends Fragment {

    @Bind(R.id.sp_cus_name)
    Spinner spCusName;
    @Bind(R.id.et_name_addOrder)
    EditText etNameAddOrder;
    @Bind(R.id.et_quantity_addOrder)
    EditText etQuantityAddOrder;
    @Bind(R.id.et_price_addOrder)
    EditText etPriceAddOrder;
    @Bind(R.id.et_description_addOrder)
    EditText etDescriptionAddOrder;
    @Bind(R.id.btn_save_addOrder)
    Button btnSaveAddOrder;

    private Realm mRealm;
    private RealmResults<AddContact> results;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_add_order, container, false);
        ButterKnife.bind(this, rootView);

        mRealm = Realm.getDefaultInstance();

        results = mRealm.where(AddContact.class).findAll();

        SpinnerTextAdapter spinnerTextAdapter = new SpinnerTextAdapter(getActivity(), results);

        // attaching data adapter to spinner
        spCusName.setAdapter(spinnerTextAdapter);

        btnSaveAddOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String sp = spCusName.getSelectedItem().toString();

                String name = etNameAddOrder.getText().toString();
                String quantity = etQuantityAddOrder.getText().toString();
                String price = etPriceAddOrder.getText().toString();
                String description = etDescriptionAddOrder.getText().toString();

                Calendar c = Calendar.getInstance();
                System.out.println("Current time => " + c.getTime());

                SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
                String formattedDate = df.format(c.getTime());

                addOrder(sp, name, quantity, price, description, formattedDate);

                etNameAddOrder.setText("");
                etQuantityAddOrder.setText("");
                etPriceAddOrder.setText("");
                etDescriptionAddOrder.setText("");
            }
        });

        return rootView;
    }

    private void addOrder(String sp, String name, String quantity, String price, String description, String startDate) {

        mRealm = Realm.getDefaultInstance();
        mRealm.beginTransaction();

        AddOrder addOrder = mRealm.createObject(AddOrder.class);

        addOrder.setOrderId(UUID.randomUUID().toString());
        addOrder.setBuyerName(sp);
        addOrder.setBarcode(name);
        addOrder.setQuality(quantity);
        addOrder.setPrice(price);
        addOrder.setDescription(description);
        addOrder.setStartDate(startDate);

        mRealm.commitTransaction();
        mRealm.close();

        Toast.makeText(getActivity(), "Order Saved.", Toast.LENGTH_SHORT).show();

        RealmResults<AddOrder> results = mRealm.where(AddOrder.class).findAll();
        for (AddOrder s : results) {
            s.getOrderId();
            s.getBuyerName();
            s.getBarcode();
            s.getQuality();
            s.getPrice();
            s.getDescription();
            Log.e("MainActivity", "Get Data: " + s);
        }
    }
}

package in.vaksys.takeorder.fragments;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.android.AndroidAuthSession;
import com.dropbox.client2.session.AccessTokenPair;
import com.dropbox.client2.session.AppKeyPair;
import com.dropbox.client2.session.TokenPair;
import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import in.vaksys.takeorder.R;
import in.vaksys.takeorder.activities.UploadFile;
import in.vaksys.takeorder.adapters.OrderListAdapter;
import in.vaksys.takeorder.dbPojo.AddContact;
import in.vaksys.takeorder.dbPojo.AddOrder;
import in.vaksys.takeorder.extras.Constants;
import in.vaksys.takeorder.extras.Utils;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

/**
 * Created by dell980 on 6/27/2016.
 */
public class OrderListFragment extends Fragment implements View.OnClickListener {

    private CheckBox checkBox;
    private LinearLayout linearLayout;

    @Bind(R.id.orderRecyclerview)
    RecyclerView recyclerview;
    @Bind(R.id.btn_generate_csvtodropbox_orderlist)
    Button btn_generate_csvtodropbox_orderlist;
    private OrderListAdapter orderListAdapter;
    private Realm mRealm;
    private RealmResults<AddOrder> addOrderList;

    private static final String IMAGE_DIRECTORY_NAME = "MyData";
    public static String timeStamp;
    public static final int MEDIA_TYPE_IMAGE = 1;
    File mediaFile;

    private List<String[]> data = new ArrayList<>();

    private DropboxAPI<AndroidAuthSession> mApi;
    private final String DIR = "/";
    private boolean mLoggedIn, onResume;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_order_list, container, false);
        linearLayout = (LinearLayout) rootView.findViewById(R.id.linearOrder);

        ButterKnife.bind(this, rootView);

        AndroidAuthSession session = buildSession();
        mApi = new DropboxAPI<AndroidAuthSession>(session);


        mRealm = Realm.getDefaultInstance();

        RecyclerView.LayoutManager manager = new LinearLayoutManager(getActivity());
        recyclerview.setLayoutManager(manager);

        addOrderList = mRealm.where(AddOrder.class).findAll();
        orderListAdapter = new OrderListAdapter(getActivity(), addOrderList);
        recyclerview.setHasFixedSize(true);
        recyclerview.setItemAnimator(new DefaultItemAnimator());
        recyclerview.setNestedScrollingEnabled(false);
        recyclerview.setAdapter(orderListAdapter);
        btn_generate_csvtodropbox_orderlist.setOnClickListener(this);


        addOrderList.addChangeListener(new RealmChangeListener<RealmResults<AddOrder>>() {
            @Override
            public void onChange(RealmResults<AddOrder> element) {
                orderListAdapter.notifyDataSetChanged();
            }
        });
        //checkBox = (CheckBox) rootView.findViewById(R.id.checkboxOrder);



        return rootView;
    }

    @Override
    public void onClick(View view) {

        if (addOrderList.size() > 0) {


            try {


                File mediaStorageDir = new File(
                        Environment
                                .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                        IMAGE_DIRECTORY_NAME);

                // Create the storage directory if it does not exist
                if (!mediaStorageDir.exists()) {
                    if (!mediaStorageDir.mkdirs()) {
                        // MyApplication.getInstance().showLog("TAG", "Oops! Failed create " + IMAGE_DIRECTORY_NAME + " directory");

                    }
                }

                timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                        Locale.getDefault()).format(new Date());


                mediaFile = new File(mediaStorageDir.getPath() + File.separator
                        + "file_" + timeStamp + ".csv");
                CSVWriter writer = new CSVWriter(new FileWriter(mediaFile));


                for (AddOrder s : addOrderList) {

                    data.add(new String[]{s.getOrderId(),s.getBarcode(),s.getBuyerName(),s.getQuality(),s.getPrice(),s.getDescription()});
//                   data.add(new String[]{ s.getCity()});
//                   data.add(new String[]{s.getContactId()});
                    writer.writeAll(data);

                }
              //  writer.writeAll(data);
                writer.close();


                System.out.println("*** Also wrote this information to file: " + mediaFile);

                if (mLoggedIn) {
                    logOut();
                }

                if (Utils.isOnline(getActivity())) {
                    mApi.getSession().startAuthentication(getActivity());
                    onResume = true;
                } else {
                    Utils.showNetworkAlert(getActivity());
                }

                mLoggedIn = false;
                if (false) {
                    UploadFile upload = new UploadFile(getActivity(), mApi, DIR, mediaFile);
                    upload.execute();
                    //onResume = false;

                }

//                List<String[]> data = new ArrayList<String[]>();
//                data.add(new String[]{"India", "New Delhi"});
//                data.add(new String[]{"United States", "Washington D.C"});
//                data.add(new String[]{"Germany", "Berlin"});


            } catch (Exception e) {

                e.printStackTrace();
            }


        } else {
            Toast.makeText(getActivity(), "No Data Found", Toast.LENGTH_SHORT).show();
        }


    }

    private AndroidAuthSession buildSession() {
        AppKeyPair appKeyPair = new AppKeyPair(Constants.DROPBOX_APP_KEY,
                Constants.DROPBOX_APP_SECRET);
        AndroidAuthSession session;

        String[] stored = getKeys();
        if (stored != null) {
            AccessTokenPair accessToken = new AccessTokenPair(stored[0],
                    stored[1]);
            session = new AndroidAuthSession(appKeyPair, Constants.ACCESS_TYPE,
                    accessToken);
        } else {
            session = new AndroidAuthSession(appKeyPair, Constants.ACCESS_TYPE);
        }

        return session;
    }

    private String[] getKeys() {
        SharedPreferences prefs = getActivity().getSharedPreferences(
                Constants.ACCOUNT_PREFS_NAME, 0);
        String key = prefs.getString(Constants.ACCESS_KEY_NAME, null);
        String secret = prefs.getString(Constants.ACCESS_SECRET_NAME, null);
        if (key != null && secret != null) {
            String[] ret = new String[2];
            ret[0] = key;
            ret[1] = secret;
            return ret;
        } else {
            return null;
        }
    }


    private void logOut() {
        mApi.getSession().unlink();

        clearKeys();
    }
    private void clearKeys() {
        SharedPreferences prefs = getActivity().getSharedPreferences(
                Constants.ACCOUNT_PREFS_NAME, 0);
        SharedPreferences.Editor edit = prefs.edit();
        edit.clear();
        edit.commit();
    }

    @Override
    public void onResume() {

        AndroidAuthSession session = mApi.getSession();

        if (session.authenticationSuccessful()) {
            try {
                session.finishAuthentication();

                TokenPair tokens = session.getAccessTokenPair();
                storeKeys(tokens.key, tokens.secret);
                setLoggedIn(onResume);
            } catch (IllegalStateException e) {
                showToast("Couldn't authenticate with Dropbox:"
                        + e.getLocalizedMessage());
            }
        }
        super.onResume();
    }

    private void storeKeys(String key, String secret) {
        SharedPreferences prefs = getActivity().getSharedPreferences(
                Constants.ACCOUNT_PREFS_NAME, 0);
        SharedPreferences.Editor edit = prefs.edit();
        edit.putString(Constants.ACCESS_KEY_NAME, key);
        edit.putString(Constants.ACCESS_SECRET_NAME, secret);
        edit.commit();
    }
    private void showToast(String msg) {
        Toast error = Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG);
        error.show();
    }

    public void setLoggedIn(boolean loggedIn) {
        mLoggedIn = loggedIn;
        if (loggedIn) {
            UploadFile upload = new UploadFile(getActivity(), mApi, DIR, mediaFile);
            upload.execute();
            onResume = false;

        }
    }
}

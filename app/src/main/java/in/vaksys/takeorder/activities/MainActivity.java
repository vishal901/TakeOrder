package in.vaksys.takeorder.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import in.vaksys.takeorder.R;
import in.vaksys.takeorder.fragments.AddContactFragment;
import in.vaksys.takeorder.fragments.AddOrderFragment;
import in.vaksys.takeorder.fragments.CheckListFragment;
import in.vaksys.takeorder.fragments.ContactListFragment;
import in.vaksys.takeorder.fragments.FragmentDrawer;
import in.vaksys.takeorder.fragments.OrderListFragment;
import in.vaksys.takeorder.fragments.SummaryOfOrderFragment;

public class MainActivity extends AppCompatActivity implements FragmentDrawer.FragmentDrawerListener {

    private Toolbar mToolbar;
    private FragmentDrawer drawerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = (Toolbar) findViewById(R.id.mainToolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        drawerFragment = (FragmentDrawer)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
        drawerFragment.setDrawerListener(this);

        displayView(0);
    }

    @Override
    public void onDrawerItemSelected(View view, int position) {
        displayView(position);
    }

    private void displayView(int position) {
        Fragment fragment = null;
        String title = getString(R.string.app_name);
        switch (position) {
            case 0:
                fragment = new ContactListFragment();
                title = getString(R.string.contact);
                break;
            case 1:
                fragment = new AddContactFragment();
                title = getString(R.string.add_contact);
                break;
            case 2:
                fragment = new OrderListFragment();
                title = getString(R.string.order);
                break;
            case 3:
                fragment = new AddOrderFragment();
                title = getString(R.string.add_order);
                break;
            case 4:
                fragment = new SummaryOfOrderFragment();
                title = getString(R.string.summary);
                break;
            case 5:
                fragment = new CheckListFragment();
                title = getString(R.string.check);
                break;
            default:
                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_body, fragment);
            fragmentTransaction.commit();

            // set the toolbar title
            getSupportActionBar().setTitle(title);
        }
    }
}

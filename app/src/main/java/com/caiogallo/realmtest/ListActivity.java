package com.caiogallo.realmtest;

import android.os.Bundle;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.caiogallo.realmtest.data.User;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * Created by caio on 22/01/16.
 */
public class ListActivity extends android.app.ListActivity implements ActionMode.Callback{

    private Object mActionMode;
    private int selectedItem = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        createAdapter();

        ListView listView = getListView();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(),
                        ((TextView) view).getText(), Toast.LENGTH_SHORT).show();
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if(mActionMode != null){
                    return false;
                }

                selectedItem = position;

                mActionMode = ListActivity.this.startActionMode(ListActivity.this);
                view.setSelected(true);
                return true;
            }
        });
    }

    private void createAdapter() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.list);

        Realm realm = Realm.getInstance(this);
        RealmQuery<User> query = realm.where(User.class);
        RealmResults<User> result = query.findAll();
        result.sort("name");
        for(User u: result){
            adapter.add(u.getName());
        }

        setListAdapter(adapter);
    }

    public void remover(){
        Realm realm = Realm.getInstance(this);
        RealmResults<User> query = realm.where(User.class).findAll();
        realm.beginTransaction();
        User user = query.get(selectedItem);
        user.removeFromRealm();
        realm.commitTransaction();
        createAdapter();
    }

    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        MenuInflater inflater = mode.getMenuInflater();
        inflater.inflate(R.menu.rowselection, menu);
        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        return false;
    }

    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        switch (item.getItemId()) {
            case R.id.remover:
                remover();
                mode.finish();
                return true;
            default:
                return false;
        }
    }

    @Override
    public void onDestroyActionMode(ActionMode mode) {
        mActionMode = null;
        selectedItem = -1;
    }
}

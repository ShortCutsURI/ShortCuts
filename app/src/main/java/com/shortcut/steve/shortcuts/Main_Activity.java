package com.shortcut.steve.shortcuts;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Main_Activity extends ActionBarActivity
{
    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    Data_Manager dManager;
    EditText searchText;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get the ListView
        expListView = (ExpandableListView) findViewById(R.id.expListView);

        // Set the search field
        searchText = (EditText) findViewById(R.id.searchInput);

        // Get the accounts' data
        getAccountData();

        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);

        // Group click listener (unused, had to overwrite)
        expListView.setOnGroupClickListener(new OnGroupClickListener()
        {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id)
            {
                // Toast.makeText(getApplicationContext(),
                // "Group Clicked " + listDataHeader.get(groupPosition),
                // Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        // Group expanded listener
        expListView.setOnGroupExpandListener(new OnGroupExpandListener()
        {
            @Override
            public void onGroupExpand(int groupPosition)
            {
                Toast.makeText(getApplicationContext(),
                        listDataHeader.get(groupPosition) + " Expanded",
                        Toast.LENGTH_SHORT).show();
            }
        });

        // Group collapsed listener
        expListView.setOnGroupCollapseListener(new OnGroupCollapseListener()
        {
            @Override
            public void onGroupCollapse(int groupPosition)
            {
                Toast.makeText(getApplicationContext(),
                        listDataHeader.get(groupPosition) + " Collapsed",
                        Toast.LENGTH_SHORT).show();
            }
        });

        // Child click listener
        expListView.setOnChildClickListener(new OnChildClickListener()
        {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id)
            {
                if (childPosition == 4)
                {
                    Intent intent = new Intent(Main_Activity.this, PhotoView_Activity.class);
                    startActivity(intent);
                }
                // TODO Auto-generated method stub
                Toast.makeText(
                        getApplicationContext(),
                        listDataHeader.get(groupPosition)
                                + " : "
                                + listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition)
                                + "id: "
                                + Integer.toString(childPosition),
                        Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        searchText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text
                Main_Activity.this.listAdapter.getFilter().filter(cs.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /*
	 * Get Account Data
	 */
    private void getAccountData()
    {
        dManager = new Data_Manager();
        HashMap<Integer, List<String>> accounts = dManager.readFile();

        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding parent data
        for (int i = 0; i < accounts.size(); i++)
        {
            List<String> tempList = accounts.get(i);

            if (tempList.get(0) == "" && tempList.get(1) == "")
            {
                listDataHeader.add("000" + Integer.toString(i));
            }
            else
            {
                listDataHeader.add(tempList.get(0) + " " + tempList.get(1));
            }
        }

        // Populate child data
        for (int i = 0; i < accounts.size(); i++)
        {
            List<String> tempList = accounts.get(i);
            tempList.set(0, "First: " + tempList.get(0));
            tempList.set(1, "Last: " + tempList.get(1));
            tempList.set(2, "Email: " + tempList.get(2));

            listDataChild.put(listDataHeader.get(i), accounts.get(i));
        }
    }

}

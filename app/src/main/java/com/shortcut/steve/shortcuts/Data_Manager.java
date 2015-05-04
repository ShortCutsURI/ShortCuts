package com.shortcut.steve.shortcuts;
import android.content.res.Resources;
import android.view.View;
import android.view.View.OnClickListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Steve on 4/5/2015.
 */
public class Data_Manager
{
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    public HashMap readFile()
    {
        List<Integer> accountIds = new ArrayList<Integer>();
        accountIds.add(0);
        accountIds.add(1);
        accountIds.add(2);
        accountIds.add(3);
        accountIds.add(4);

        ArrayList<List<String>> accountInfo;
        accountInfo = new ArrayList<List<String>>();
        accountInfo.add(Arrays.asList("Boba","Fett","bountyhunter@gmail.com", "Edit Account", "View Images"));
        accountInfo.add(Arrays.asList("Darth","Vader","skywalker1@gmail.com", "Edit Account", "View Images"));
        accountInfo.add(Arrays.asList("Han","Solo","punchitchewie@gmail.com", "Edit Account", "View Images"));
        accountInfo.add(Arrays.asList("Greedo","","hanshotfirst@gmail.com", "Edit Account", "View Images"));
        accountInfo.add(Arrays.asList("Luke","Skywalker","skywalker2@gmail.com", "Edit Account", "View Images"));

        HashMap<Integer, List<String>> accounts = new HashMap<Integer, List<String>>();

        for (int i = 0; i < accountIds.size(); i++)
        {
            accounts.put(accountIds.get(i), accountInfo.get(i));
        }

        return accounts;
    }

    public void getAccountData()
    {
        HashMap<Integer, List<String>> accounts = readFile();

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

        for (int i = 0; i < accounts.size(); i++)
        {
            List<String> tempList = accounts.get(i);
            tempList.set(0, "First: " + tempList.get(0));
            tempList.set(1, "Last: " + tempList.get(1));
            tempList.set(2, "Email: " + tempList.get(2));

            listDataChild.put(listDataHeader.get(i), tempList);
        }
    }
}

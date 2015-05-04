package com.shortcut.steve.shortcuts;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * Created by Steve on 4/12/2015.
 */

public class ExpandableListAdapter extends BaseExpandableListAdapter implements Filterable
{
    private Context context;
    private List<String> listDataHeader; // header titles
    private ParentFilter headerFilter;
    private List<String> dataHeads;

    // child data: key-head:String, data-child:List<String>
    private HashMap<String, List<String>> listDataChild;

    public ExpandableListAdapter(Context context, List<String> listDataHeader, HashMap<String, List<String>> listChildData)
    {
        this.context = context;
        this.listDataHeader = listDataHeader;
        dataHeads = listDataHeader;
        this.listDataChild = listChildData;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition)
    {
        return this.listDataChild.get(this.listDataHeader.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition)
    {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent)
    {
        final String childText = (String) getChild(groupPosition, childPosition);

        if (convertView == null)
        {
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.xlv_child, null);
        }

        TextView txtListChild = (TextView) convertView.findViewById(R.id.xlvChild);

        txtListChild.setText(childText);
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition)
    {
        return this.listDataChild.get(this.listDataHeader.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition)
    {
        return this.listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount()
    {
        return this.listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition)
    {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent)
    {
        String headerTitle = (String) getGroup(groupPosition);

        if (convertView == null)
        {
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.xlv_parent, null);
        }

        TextView lblListHeader = (TextView) convertView.findViewById(R.id.xlvParent);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);

        return convertView;
    }

    @Override
    public boolean hasStableIds()
    {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition)
    {
        return true;
    }

    @Override
    public Filter getFilter() {
        if (headerFilter == null)
            headerFilter = new ParentFilter();

        return headerFilter;
    }

    private class ParentFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();

            listDataHeader = dataHeads;

            // We implement here the filter logic
            if (constraint == null || constraint.length() == 0) {
                // No filter implemented we return all the list
                results.values = listDataHeader;
                results.count = listDataHeader.size();
            }
            else {
                // We perform filtering operation
                List<String> nHeaderList = new ArrayList<String>();

                for (int i = 0; i < listDataHeader.size(); i++) {
                    if (listDataHeader.get(i).toUpperCase().contains(constraint.toString().toUpperCase()))
                        nHeaderList.add(listDataHeader.get(i));
                }

                results.values = nHeaderList;
                results.count = nHeaderList.size();

            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            // Inform the adapter there's a new filtered list
            if (results.count == 0)
                notifyDataSetInvalidated();
            else {
                listDataHeader = (List<String>) results.values;
                notifyDataSetChanged();
            }
        }
    }
}

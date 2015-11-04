package com.mifos.objects;

/**
 * Created by conflux37 on 10/22/2015.
 */
import android.content.Context;

import com.mifos.mifosxdroid.R;

/**
 * Created by conflux37 on 10/21/2015.
 */
public class MenuList {
    public String id;
    public String name;
    public String imageName;

    public int getImageResourceId(Context context) {
        System.out.println("MenuList base context" + context);
        String[] strings=context.getResources().getStringArray(R.array.menu_list);
        System.out.println("string 0 "+strings[0]+"strings 1 "+strings[1]);
        System.out.println("array is " + context.getResources().getStringArray(R.array.menu_list));
        System.out.println("image id " + context.getResources().getIdentifier(this.imageName, "drawable", context.getPackageName()));
        return context.getResources().getIdentifier(this.imageName, "drawable", context.getPackageName());
    }
}
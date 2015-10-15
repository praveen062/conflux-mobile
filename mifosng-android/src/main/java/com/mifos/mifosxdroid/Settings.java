package com.mifos.mifosxdroid;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mifos.objects.User;
import com.mifos.utils.Constants;

import butterknife.ButterKnife;
import butterknife.InjectView;
import org.apache.commons.validator.routines.InetAddressValidator;
import org.apache.commons.validator.routines.UrlValidator;

public class Settings extends ActionBarActivity {


/*
* The activity is to set the URL , Tenant Identifier and port number
*
* */

    public static final String PROTOCOL_HTTP = "http://";
    public static final String PROTOCOL_HTTPS = "https://";
    public static final String API_PATH = "/mifosng-provider/api/v1";
    @InjectView(R.id.et_instanceURL)
    EditText et_instanceURL;
    @InjectView(R.id.tv_constructed_instance_url)
    TextView tv_constructed_instance_url;
    @InjectView(R.id.et_tenantIdentifier) EditText et_tenantIdentifier;
    @InjectView(R.id.et_instancePort) EditText et_port;
    SharedPreferences settingsPreferences;
    public static final String DEFALUT_PORT="",DEFAULT_TENANT="";
    boolean isUrl;
    boolean isValidIp =false;
    boolean isIP_Address=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Log.d("content", "inside the Settings onCreate method");
        Constants.applicationContext = Settings.this;
        settingsPreferences = PreferenceManager.getDefaultSharedPreferences(Constants.applicationContext);
        String previouslyEnteredUrl=settingsPreferences.getString("domainName","");
        String TenantIdentifier=settingsPreferences.getString("TenantIdentifier","");
        String previouslyEnteredPort=settingsPreferences.getString("Port", DEFALUT_PORT);
        isValidIp =settingsPreferences.getBoolean("isIpAddress",false);
        Log.d("content 1", "inside the onCreate method after auth");
        ButterKnife.inject(this);
        if(previouslyEnteredUrl.isEmpty()||previouslyEnteredUrl.equals("")||previouslyEnteredUrl==null)
        {
            tv_constructed_instance_url.setText("");
        }
        String constructedurl;


            et_instanceURL.setText(previouslyEnteredUrl);
            et_port.setText(String.valueOf(previouslyEnteredPort));
        System.out.println(et_port.getEditableText().length());
        constructedurl = constructInstanceUrl(et_instanceURL.getEditableText().toString(), et_port.getEditableText().toString());
        tv_constructed_instance_url.setText(constructedurl);
            et_instanceURL.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                }

                @Override
                public void afterTextChanged(Editable editable) {
                    isValidIp = false;
                    updateMyInstanceUrl();
                }
            });
        et_port.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                isValidIp =true;
                updateMyInstanceUrl();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

            et_tenantIdentifier.setText(TenantIdentifier);

    }

   public void onSave(View view) {
       isIP_Address = checkForIpAddress(et_instanceURL.getText().toString().trim());
       if(isIP_Address)
       {
           if(et_port.getText().toString().isEmpty()||et_port.getText().toString().equals(""))
           {
               Toast.makeText(this, "Please provide a port number", Toast.LENGTH_LONG).show();
           }
           else
           {
               isValidIp=true;
           }
       }
       if (et_tenantIdentifier.getEditableText().length()<=0) {
           Toast.makeText(this, "Please Provide Tenant Identifier ", Toast.LENGTH_LONG).show();
       }
       else
           if (isUrl || isValidIp) {
               saveSettings();
               Intent intent = new Intent(Settings.this, LoginActivity.class);
               intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
               intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
               startActivity(intent);
               finish();
           } else
           {

                   Toast.makeText(this, "Please provide a valid url", Toast.LENGTH_LONG).show();

           }

    }
    public void saveSettings()
    {
        SharedPreferences.Editor edit = settingsPreferences.edit();
        Toast.makeText(this, "Domain Name : " + et_instanceURL.getText().toString() + " Tenant : " + et_tenantIdentifier.getText().toString() + " has been saved", Toast.LENGTH_LONG).show();
        edit.putString("domainName", et_instanceURL.getText().toString().trim());
        edit.putString("TenantIdentifier", et_tenantIdentifier.getText().toString());
        edit.putString("ConstructedInstanceUrl", tv_constructed_instance_url.getText().toString());
        if(et_port.getEditableText().length()<=0)
        {
            edit.putString("Port", null);
            System.out.println("port number has not entered");
        }
        else
        {
            edit.putString("Port", et_port.getText().toString());
        }
        edit.putString(User.AUTHENTICATION_KEY, "NA");
        edit.putBoolean("isIpAddress", isValidIp);
        edit.commit();
    }

    public void goBack(View view)
    {

        finish();
    }

/**
 * This method to update the url view and validate the url entered by the user is valid
 *
 *
 * */
    private void updateMyInstanceUrl() {
        String textUnderConstruction;
        textUnderConstruction = constructInstanceUrl(et_instanceURL.getEditableText().toString(), et_port.getEditableText().toString());
        tv_constructed_instance_url.setText(textUnderConstruction);
        isIP_Address = checkForIpAddress(et_instanceURL.getText().toString().trim());
        isUrl = checkForUrl(tv_constructed_instance_url.getText().toString().trim());
        if(isIP_Address)
        {
            if(et_port.getText().toString().equals("")) {
                System.out.println("invalid ip");
                isValidIp =false;
                tv_constructed_instance_url.setTextColor(getResources().getColor(R.color.red));
            }
            else
            {
                System.out.println("valid ip");
                isValidIp =true;
                textUnderConstruction = constructInstanceUrl(et_instanceURL.getEditableText().toString(), et_port.getText().toString().trim());
                tv_constructed_instance_url.setText(textUnderConstruction);
                tv_constructed_instance_url.setTextColor(getResources().getColor(R.color.deposit_green));
            }
        }
        if(isUrl&&!isIP_Address)
        {
            System.out.println("valid url");

            tv_constructed_instance_url.setTextColor(getResources().getColor(R.color.deposit_green));
        }
        if(!isUrl&&!isIP_Address)
        {

            tv_constructed_instance_url.setTextColor(getResources().getColor(R.color.red));
        }
    }

    public boolean checkForIpAddress(String ip)
    {
        InetAddressValidator inetValidator = InetAddressValidator.getInstance();
        return inetValidator.isValidInet4Address(ip);
    }
    public boolean checkForUrl(String url)
    {
        UrlValidator urlvalidate= UrlValidator.getInstance();
        return urlvalidate.isValid(url);
    }

    public String constructInstanceUrl(String validDomain, String port) {
        if (port.length()>0) {
            System.out.println(port+"yooooooooooooo||"+port.length());
            return PROTOCOL_HTTPS + validDomain + ":" + port + API_PATH;
        } else {
            if(validDomain.contains(PROTOCOL_HTTP)||validDomain.contains(PROTOCOL_HTTPS))
            {
                if(validDomain.contains(API_PATH))
                {
                    return validDomain;
                }
                else
                {
                    return validDomain+API_PATH;
                }
            }
            else if(validDomain.contains(API_PATH))
            {
                return PROTOCOL_HTTPS+validDomain;
            }
            else
                return PROTOCOL_HTTPS + validDomain + API_PATH;
        }
    }
}

package com.mifos.mifosxdroid;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.mifos.objects.User;
import com.mifos.services.API;
import com.mifos.utils.Constants;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class Settings extends ActionBarActivity {


/*
* The activity is to set the URL , Tenant Identifier and port number
*
* */

    private static final String DOMAIN_NAME_REGEX_PATTERN = "^[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
    private static final String IP_ADDRESS_REGEX_PATTERN = "^(\\d|[1-9]\\d|1\\d\\d|2([0-4]\\d|5[0-5]))\\.(\\d|[1-9]\\d|1\\d\\d|2([0-4]\\d|5[0-5]))\\.(\\d|[1-9]\\d|1\\d\\d|2([0-4]\\d|5[0-5]))\\.(\\d|[1-9]\\d|1\\d\\d|2([0-4]\\d|5[0-5]))$";
    public static final String PROTOCOL_HTTP = "http://";
    public static final String PROTOCOL_HTTPS = "https://";
    public static final String API_PATH = "/mifosng-provider/api/v1";
    @InjectView(R.id.et_instanceURL)
    EditText et_instanceURL;
    @InjectView(R.id.tv_constructed_instance_url)
    TextView tv_constructed_instance_url;
    @InjectView(R.id.et_tenantIdentifier) EditText et_tenantIdentifier;
    @InjectView(R.id.et_instancePort) EditText et_port;
    private String instanceURL;
    private Context context;
    private Pattern domainNamePattern;
    private Matcher domainNameMatcher;
    private Pattern ipAddressPattern;
    private Matcher ipAddressMatcher;
    private Integer port = null;
    private API api;
    SharedPreferences sharedPreferences;
    private String authenticationToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Log.d("content","inside the onCreate method");
        context = Settings.this;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String previouslyEnteredUrl = sharedPreferences.getString(Constants.INSTANCE_URL_KEY,
                getString(R.string.default_instance_url));
        String previouslyEnteredPort = sharedPreferences.getString(Constants.INSTANCE_PORT_KEY,
                "80");
        authenticationToken = sharedPreferences.getString(User.AUTHENTICATION_KEY, "NA");
        Log.d("content 1","inside the onCreate method after auth");
        ButterKnife.inject(this);

        domainNamePattern = Pattern.compile(DOMAIN_NAME_REGEX_PATTERN);
        ipAddressPattern = Pattern.compile(IP_ADDRESS_REGEX_PATTERN);
        tv_constructed_instance_url.setText( previouslyEnteredUrl);

        et_instanceURL.setText(previouslyEnteredUrl);

        if (!previouslyEnteredPort.equals("80")) {
            et_port.setText(previouslyEnteredPort);
        }
        et_instanceURL.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {



            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {



            }

            @Override
            public void afterTextChanged(Editable editable) {

                updateMyInstanceUrl();

            }
        });



    }

/**
 * This method to update the url view and validate the url entered by the user is valid
 *
 *
 * */
    private void updateMyInstanceUrl() {
        String textUnderConstruction;
        if(!et_port.getEditableText().toString().isEmpty())
        {
            port = Integer.valueOf(et_port.getEditableText().toString().trim());
            textUnderConstruction = constructInstanceUrl(et_instanceURL.getEditableText().toString(), port);
        }
        else
        {
            textUnderConstruction = constructInstanceUrl(et_instanceURL.getEditableText().toString(), null);
        }

        tv_constructed_instance_url.setText(textUnderConstruction);

        if(!validateURL(textUnderConstruction)) {
            tv_constructed_instance_url.setTextColor(getResources().getColor(R.color.red));
        } else {
            tv_constructed_instance_url.setTextColor(getResources().getColor(R.color.deposit_green));
        }
    }

    public String constructInstanceUrl(String validDomain, Integer port) {
        if (port != null) {
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
    public boolean validateURL(final String hex) {

        domainNameMatcher = domainNamePattern.matcher(hex);
        ipAddressMatcher = ipAddressPattern.matcher(hex);
        if (domainNameMatcher.matches()) return true;
        if (ipAddressMatcher.matches()) return true;
        //TODO MAKE SURE YOU UPDATE THE REGEX to check for ports in the URL
        return true;
    }


}

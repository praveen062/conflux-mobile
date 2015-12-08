/*
 * This project is licensed under the open source MPL V2.
 * See https://github.com/openMF/android-client/blob/master/LICENSE.md
 */

package com.mifos.utils;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by satya on 13/04/14.
 */
public class Constants {

    public static Context applicationContext;

    public static final String INSTANCE_URL_KEY = "instanceURL";


    public static final String INSTANCE_DOMAIN_KEY = "instanceDomain";

    public static final String INSTANCE_PORT_KEY = "instancePort";

    public static final String TENANT_IDENTIFIER_KEY = "tenant identifier";

    public static final String PROTOCOL_HTTP = "http://";

    public static final String PROTOCOL_HTTPS = "https://";

    public static final String API_PATH = "/mifosng-provider/api/v1";

    public static final String ENTITY_TYPE_CLIENTS = "clients";

    public static final String ENTITY_TYPE_LOANS = "loans";

    public static final String ENTITY_TYPE_SAVINGS = "savings";

    public static final String CLIENT_NAME = "clientName";

    public static final String CLIENT_ID = "clientId";

    public static final String LOAN_ACCOUNT_NUMBER = "loanAccountNumber";

    public static final String LOAN_PRODUCT_NAME = "loanProductName";

    public static final String AMOUNT_IN_ARREARS = "amountInArrears";

    public static final String AMOUNT_DUE = "amountDue";

    public static final String FEES_DUE = "feesDue";

    public static final String SAVINGS_ACCOUNT_NUMBER = "savingsAccountNumber";

    public static final String SAVINGS_ACCOUNT_TYPE = "savingsAccountType";

    public static final String SAVINGS_ACCOUNT_TRANSACTION_TYPE = "transactionType";

    public static final String SAVINGS_ACCOUNT_TRANSACTION_DEPOSIT = "Deposit";

    public static final String SAVINGS_ACCOUNT_TRANSACTION_WITHDRAWAL = "Withdrawal";

    public static final String DATA_TABLE_REGISTERED_NAME = "dataTableRegisteredName";

    public static final String CENTER_ID = "centerId";

    public static final String OFFICE_ID = "officeId";

    public static final String GROUP_ID = "groupId";

    public static final String ENTITY_TYPE = "entityType";

    public static final String ENTITY_ID = "entityId";

    public static final String DATE_OF_COLLECTION = "dateOfCollection";

    public static final String DATE_FORMAT = "dateFormat";

    public static final String CALENDAR_INSTANCE_ID = "calendarInstanceId";

    public static final String LOCALE = "locale";

    public static final String CGT_READ="READ_CGT";
    public static final String CGT_WRITE="WRITE_CGT";
    public static final String ALL_FUNCTIONS="ALL_FUNCTIONS";



    /**
     * Constants to identify which Data Tables have to be shown
     */
    public static final int DATA_TABLE_CLIENTS = 2001;
    public static final int DATA_TABLE_LOANS = 2002;
    public static final int DATA_TABLES_SAVINGS_ACCOUNTS = 2003;

    /**
     * Constants to for Data Table Menu Names
     */
    public static final String DATA_TABLE_CLIENTS_NAME = "More Client Info";
    public static final String DATA_TABLE_LOAN_NAME = "More Loan Info";
    public static final String DATA_TABLE_SAVINGS_ACCOUNTS_NAME = "More Savings Account Info";

    /**
     * User permissions
     *
     * */
    public static final String USER_ALL_PERMISSIONS="ALL_FUNCTIONS";
    public static final String USER_CGT_READ_PERMISSSION="READ_CGT";
    public static final String USER_CGT_WRITE_PERMISSSION="WRITE_CGT";
    public static final String USER_GRT_READ_PERMISSION="READ_GRT";
    public static final String USER_GRT_WRITE_PERMISSION="WRITE_GRT";

    /**
     * grt status
     */
    public static final String PLANNED = "Planned";
    public static final String PASS = "PASS";
    public static final String FAIL = "FAIL";

}

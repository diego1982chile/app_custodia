package test3.ncxchile.cl.login;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import test3.ncxchile.cl.greenDAO.DaoMaster;
import test3.ncxchile.cl.greenDAO.DaoSession;
import test3.ncxchile.cl.greenDAO.PersonaDao;
import test3.ncxchile.cl.greenDAO.UserDao;
import test3.ncxchile.cl.home.HomeActivity;
import test3.ncxchile.cl.session.SessionManager;
import test3.ncxchile.cl.widgets.ErrorDialog;

/**
 * A login screen that offers login via email/password.

 */
public class LoginActivity extends Activity implements LoaderCallbacks<Cursor>{

    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private UserLogin mAuthTask = null;

    // UI references.
    private EditText mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;

    // Variables consulta conexión
    public Boolean isInternetPresent = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Set up the login form.
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        populateAutoComplete();

        final Drawable successIcon = getResources().getDrawable(R.drawable.green_circle_check);
        successIcon.setBounds(new Rect(0, 0, 20, 20));

        View.OnFocusChangeListener fieldValidatorText = new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                String s= mEmailView.getText().toString();
                if(!b)
                {
                    if(!Validator.isRutValid(s))
                        mEmailView.setError(getString(R.string.error_invalid_email));
                    else
                        mEmailView.setError(getString(R.string.prompt_valid_rut), successIcon);
                }
                else
                {
                    if(s.length()==9 || s.length()==8) {
                        if (Validator.isRutValid(s))
                            mEmailView.setError(getString(R.string.prompt_valid_rut), successIcon);
                    }
                }
            }
        };

        TextWatcher fieldValidatorTextWatcher = new TextWatcher() {

            private CharSequence mText;

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void afterTextChanged(Editable s) {
                if (!Validator.isCurrentFormatValid(s))
                {
                    System.out.println("Rut actual="+s+" no es valido");
                    mEmailView.setError(getString(R.string.error_invalid_email));
                }
                if(s.length()==9 || s.length()==8) {
                    if (Validator.isRutValid(s)) {
                        mEmailView.setError(getString(R.string.prompt_valid_rut), successIcon);
                        mPasswordView.requestFocus();
                    }
                }
                if(s.length()==9) {
                    if (!Validator.isRutValid(s)) {
                        mEmailView.setError(getString(R.string.error_invalid_email));
                    }
                }
            }
        };

        mEmailView.setOnFocusChangeListener(fieldValidatorText);
        mEmailView.addTextChangedListener(fieldValidatorTextWatcher);

        mPasswordView = (EditText) findViewById(R.id.password);

        /*
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    try {
                        attemptLogin();
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    }
                    return true;
                }
                return false;
            }
        });
        */
        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);

        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    attemptLogin();
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
    }

    private void populateAutoComplete() {
        getLoaderManager().initLoader(0, null, this);
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    public void attemptLogin() throws NoSuchAlgorithmException {
        /*
        if (mAuthTask != null) {
            return;
        }
        */
        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String rut = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(password) || !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid rut.
        if (TextUtils.isEmpty(rut)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!Validator.isRutValid(rut)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            mAuthTask = new UserLogin(rut, password, getApplicationContext());

            ConnectionDetector cd = new ConnectionDetector(getApplicationContext()); //instancie el objeto
            Boolean isInternetPresent = cd.hayConexion(); // true o false dependiendo de si hay conexion
            // Si hay conexion autenticar online. Si no hay conexion autenticar offline
            int loginResponse;
            if(isInternetPresent)
                loginResponse= mAuthTask.loginOnLine();
            else
                loginResponse= mAuthTask.loginOffLine();

            showProgress(false);
            mEmailView.setError(null);
            ErrorDialog ed= new ErrorDialog(LoginActivity.this);

            switch (loginResponse)
            {
                case -1:
                    System.out.println("No existe el usuario");
                    ed.show();
                    break;
                case -2:
                    System.out.println("el rut es ambiguo: mas de un usuario con el mismo rut");
                    ed.show();
                    break;
                case -3:
                    System.out.println("password incorrecta");
                    ed.show();
                    break;
                case 1:
                    try {// Si el login fue exitoso
                        // Simulate network access.
                        System.out.println("EL LOGIN FUE EXITOSO!!");
                        Thread.sleep(0);
                        Intent myIntent = new Intent(LoginActivity.this, HomeActivity.class);
                        LoginActivity.this.startActivity(myIntent);
                    } catch (InterruptedException e) {
                        System.out.println("Error al cargar Home: "+ e);
                    }
                    break;
            }
        }
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);

                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);

                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);

        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this,
                // Retrieve data rows for the device user's 'profile' contact.
                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.PROJECTION,

                // Select only email addresses.
                ContactsContract.Contacts.Data.MIMETYPE +
                        " = ?", new String[]{ContactsContract.CommonDataKinds.Email
                                                                     .CONTENT_ITEM_TYPE},

                // Show primary email addresses first. Note that there won't be
                // a primary email address if the user hasn't specified one.
                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        List<String> emails = new ArrayList<String>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            emails.add(cursor.getString(ProfileQuery.ADDRESS));
            cursor.moveToNext();
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }

    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
        int IS_PRIMARY = 1;
    }

}




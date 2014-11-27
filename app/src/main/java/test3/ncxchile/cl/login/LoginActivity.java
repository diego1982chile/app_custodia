package test3.ncxchile.cl.login;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.games.internal.GamesLog;

import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.Vector;

import test3.ncxchile.cl.db.Global;
import test3.ncxchile.cl.greenDAO.Logs;
import test3.ncxchile.cl.greenDAO.Sesion;
import test3.ncxchile.cl.greenDAO.User;
import test3.ncxchile.cl.helpers.InternetDetector;
import test3.ncxchile.cl.helpers.Logger;
import test3.ncxchile.cl.home.HomeActivity;
import test3.ncxchile.cl.home.TareaDialogFragment;
import test3.ncxchile.cl.session.SessionManager;
import test3.ncxchile.cl.soap.SoapHandler;
import test3.ncxchile.cl.validators.RutValidator;
import test3.ncxchile.cl.widgets.ErrorDialog;
import test3.ncxchile.cl.widgets.RutEditText;

/**
 * A login screen that offers login via email/password.

 */
public class LoginActivity extends Activity implements LoaderCallbacks<Cursor>, SoapHandler {

    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private LoginController mAuthTask = null;

    // UI references.
    private RutEditText mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;

    // Variables consulta conexión
    public Boolean isInternetPresent = false;

    private String rutActual = null;

    private GruaDialogFragment gruaDialogFragment;

    public static SessionManager session;
    public static User usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEmailView= (RutEditText) findViewById(R.id.email);
        mPasswordView = (EditText) findViewById(R.id.password);

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

        mEmailView.setText("66221261");
        mPasswordView.setText("Ncx123456");

        gruaDialogFragment = new GruaDialogFragment();
        session = new SessionManager(getApplicationContext());
    }

    /*
    @Override
    public void onStart() {
        super.onStart();
        Logs logs=new Logs();
        logs.setTimeStamp(new Date());
        logs.setDescripcion("Application Started");
        DatabaseConnection.daoSession.getLogsDao().insert(logs);
    }

    @Override
    public void onStop() {
        super.onStop();
        Logs logs=new Logs();
        logs.setTimeStamp(new Date());
        logs.setDescripcion("Application Stopped");
        DatabaseConnection.daoSession.getLogsDao().insert(logs);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Logs logs=new Logs();
        logs.setTimeStamp(new Date());
        logs.setDescripcion("Application Destroyed");
        DatabaseConnection.daoSession.getLogsDao().insert(logs);
    }
    */

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
        } else if (!RutValidator.isRutValid(rut)) {
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
            mAuthTask = new LoginController(rut, password, getApplicationContext());

            InternetDetector cd = new InternetDetector(getApplicationContext()); //instancie el objeto
            Boolean isInternetPresent = cd.hayConexion(); // true o false dependiendo de si hay conexion
            // Si hay conexion autenticar online. Si no hay conexion autenticar offline
            int loginResponse;

            System.out.println("Internet=?" + isInternetPresent);
            if(isInternetPresent) {
                mAuthTask.loginOnLine(this);
            }
            else {
                loginResponse= mAuthTask.loginOffLine();
                rutActual = rut;
                postLogin(loginResponse,false);
            }
        }
    }


    private void postLogin(int loginResponse, boolean online) {
        System.out.println("postLogin=" + loginResponse);
        showProgress(false);
        mEmailView.setError(null);
        ErrorDialog ed= new ErrorDialog(LoginActivity.this);

        switch (loginResponse)
        {
            case -1:
                //System.out.println("No existe el usuario");
                ed.show();
                break;
            case -2:
                //System.out.println("el rut es ambiguo: mas de un usuario con el mismo rut");
                ed.show();
                break;
            case -3:
                //System.out.println("password incorrecta");
                ed.show();
                break;
            case 1:
                try {// Si el login fue exitoso
                    // Simulate network access.
                    //System.out.println("EL LOGIN FUE EXITOSO!!");
                    // Session Manager
                    final UUID idSesion= UUID.randomUUID();
                    usuario = mAuthTask.getUsuario();

                    Date timeStamp= new Date();
                    SimpleDateFormat fecha = new SimpleDateFormat("dd-MM-yyyy");
                    SimpleDateFormat hora = new SimpleDateFormat("HH:mm");

                    Global.sesion= new Sesion();
                    Global.sesion.setFecha(fecha.format(timeStamp));
                    Global.sesion.setHora_inicio(hora.format(timeStamp));
                    Global.sesion.setTimeStamp(timeStamp);
                    Global.sesion.setCookies(idSesion.toString());
                    Global.daoSession.getSesionDao().insert(Global.sesion);

                    while (usuario == null) {
                        Thread.sleep(5000);
                        usuario = mAuthTask.getUsuario();
                    }
                    if(online) {
                        // Creating user login session
                        session.createLoginSession(usuario.getRut() + usuario.getDv().toString(), usuario.getNombre(), usuario.getApellidoPaterno(), usuario.getApellidoMaterno());
                        session.setId(idSesion.toString());
                        Logger.log("Login Online:" + usuario.getNombre() + " " + usuario.getApellidoPaterno() + " Grúa:");

                        Intent myIntent = new Intent(LoginActivity.this, HomeActivity.class);
                        LoginActivity.this.startActivity(myIntent);
                    }
                    else{
                        // Si es login offline, solicitar identificación de la grúa
                        // Create an instance of the dialog fragment and show it

                        gruaDialogFragment.show(getFragmentManager(), "NoticeDialogFragment");
                    }

                } catch (InterruptedException e) {
                    //System.out.println("Error al cargar Home: "+ e);
                    e.printStackTrace();
                }
                break;
        }
    }

    @Override
    public void resultValue(String methodName, Vector value) {
        if (value != null) {
            String cod = value.get(0).toString();
            String msg = value.get(1).toString();
            int codigo = Integer.parseInt(cod);
            if (codigo == 00) {
                postLogin(1,true);
            }
            else {
                postLogin(-3,true); //TODO: revisar otros casos
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




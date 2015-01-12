package test3.ncxchile.cl.login;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.test.UiThreadTest;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.games.internal.GamesLog;

import org.ksoap2.serialization.SoapObject;

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
import test3.ncxchile.cl.greenDAO.UserDao;
import test3.ncxchile.cl.greenDAO.UserName;
import test3.ncxchile.cl.helpers.FixturesUpdater;
import test3.ncxchile.cl.helpers.InternetDetector;
import test3.ncxchile.cl.helpers.Logger;
import test3.ncxchile.cl.home.HomeActivity;
import test3.ncxchile.cl.home.TareaDialogFragment;
import test3.ncxchile.cl.session.SessionManager;
import test3.ncxchile.cl.soap.SoapHandler;
import test3.ncxchile.cl.soap.SoapProxy;
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
    public AlertDialog errorDialog;

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

        //mEmailView.setText("118522451"); // DEV VM
        //mPasswordView.setText("Murillo1"); // DEV VM
        //mEmailView.setText("66221261");
        //mPasswordView.setText("Ncx123456");
        FixturesUpdater fixturesUpdater= new FixturesUpdater(this);
        //fixturesUpdater.updateFixtures("grueros");

        gruaDialogFragment = new GruaDialogFragment();
        gruaDialogFragment = new GruaDialogFragment();
        Global.daoSession.clear();
        System.out.println("Nro Usuarios=" + Global.daoSession.getUserDao().getAll().size());

        Global.daoSession.clear();

        if (Global.daoSession.getUserDao().getAll().size() < 2) {
            System.out.println("BackupGruero");
            SoapProxy.backupGruero(this);
        }

        errorDialog = new AlertDialog.Builder(this).create();
        errorDialog.setTitle("Error de Autenticación");
        errorDialog.setMessage("El usuario no existe o la contraseña es incorrecta");

        Drawable errorIcon = getResources().getDrawable(R.drawable.luzroja);

        errorDialog.setIcon(errorIcon);
        errorDialog.setButton(Dialog.BUTTON_POSITIVE, "Aceptar",new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });
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
                rutActual = rut;
                mAuthTask.loginOnLine(this);

            }
            else {
                loginResponse= mAuthTask.loginOffLine();
                rutActual = rut;
                postLogin(loginResponse,false);
            }
        }
    }

    private void postLogin(final int loginResponse, final boolean online) {
        System.out.println("postLogin=" + loginResponse);
        if(online){
            errorDialog.setTitle("Error autenticación Online");
            errorDialog.setMessage("El usuario no existe o la contraseña es incorrecta");
        }
        else{
            errorDialog.setTitle("Error autenticación Offline");
            errorDialog.setMessage("El usuario no existe en la base de datos local, o la contraseña es incorrecta");
        }

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //stuff that updates ui
                mEmailView.setError(null);
                showProgress(false);

                switch (loginResponse)
                {
                    case -1:
                        //System.out.println("No existe el usuario");
                        errorDialog.show();
                        break;
                    case -2:
                        //System.out.println("el rut es ambiguo: mas de un usuario con el mismo rut");
                        errorDialog.show();
                        break;
                    case -3:
                        //System.out.println("password incorrecta");
                        errorDialog.show();
                        break;
                    case 1:
                        // Simulate network access.
                        //System.out.println("EL LOGIN FUE EXITOSO!!");
                        // Session Manager
                        final UUID idSesion= UUID.randomUUID();
                        usuario = mAuthTask.getUsuario();
                        if (usuario == null) {
                            errorDialog.show();
                            break;
                        }

                        Date timeStamp= new Date();
                        SimpleDateFormat fecha = new SimpleDateFormat("dd-MM-yyyy");
                        SimpleDateFormat hora = new SimpleDateFormat("HH:mm");

                        Global.sesion= new Sesion();
                        Global.sesion.setFecha(fecha.format(timeStamp));
                        Global.sesion.setHora_inicio(hora.format(timeStamp));
                        Global.sesion.setTimeStamp(timeStamp);
                        Global.sesion.setCookies(idSesion.toString());
                        Global.daoSession.getSesionDao().insert(Global.sesion);

                        if(online) {
                            // Creating user login session
                            String userName= Global.daoSession.getUserNameDao().getByRut(usuario.getRut()).getUserName();
                            Global.sessionManager.createLoginSession(usuario.getRut() + usuario.getDv().toString(), usuario.getNombre(), usuario.getApellidoPaterno(), usuario.getApellidoMaterno(),userName);
                            Global.sessionManager.setId(idSesion.toString());
                            Logger.log("Login Online:" + usuario.getNombre() + " " + usuario.getApellidoPaterno() + " Grúa:");

                            Intent myIntent = new Intent(LoginActivity.this, HomeActivity.class);
                            LoginActivity.this.startActivity(myIntent);
                        }
                        else{
                            // Si es login offline, solicitar identificación de la grúa
                            // Create an instance of the dialog fragment and show it

                            gruaDialogFragment.show(getFragmentManager(), "NoticeDialogFragment");
                        }
                        break;
                }
            }
        });
    }

    @Override
    public void resultValue(String methodName, Object source, Vector value) {

        if (methodName == null && source == null && value == null) {
            System.out.println("Error de comunicación..");
            postLogin(-1,false);
            return;
        }
        System.out.println("ResultValue=" + methodName);

        if (methodName.equals("loginGruero") && value != null) {
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
        else if (methodName.equals("backupGruero") && value != null) {
            for (int i = 0; i < value.size(); i++) {
                SoapObject item = (SoapObject) value.get(i);
                String rutString = item.getPropertyAsString("rut");
                int rut = Integer.parseInt(rutString);
                String dv = item.getPropertyAsString("dv");
                String password = item.getPropertyAsString("password");
                String nombre = item.getPropertyAsString("nombre");
                String apellidoPaterno = item.getPropertyAsString("apellidoPaterno");
                String apellidoMaterno = item.getPropertyAsString("apellidoMaterno");
                String username = "tester";
                if (item.hasProperty("username")) {
                    username = item.getPropertyAsString("username");
                }

                System.out.println(item);

                 User user = new User();
                    user.setId(null);
                    user.setRut(rut);
                    user.setDv(dv);
                    user.setPassword(password);
                    user.setNombre(nombre);
                    user.setApellidoPaterno(apellidoPaterno);
                    user.setApellidoMaterno(apellidoMaterno);
                    Global.daoSession.getUserDao().insertOrReplace(user); // TODO pasar a tx

                    UserName userName = new UserName();
                    userName.setId(null);
                    userName.setRut((long)rut);
                    userName.setUserName(username);

                    Global.daoSession.getUserNameDao().insertOrReplace(userName);
            }
            System.out.println("Nro Usuarios=" + Global.daoSession.getUserDao().getAll().size());


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




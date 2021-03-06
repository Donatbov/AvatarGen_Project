package com.vivienaly.avatargenerator;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class getAvatars_activity extends AppCompatActivity {

    // Variables globales
    String UrlResult;
    String IPConnection;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_avatars);

        Button ButtonDevice = (Button)findViewById(R.id.buttonGetDevice); // Récupération de l'instance voir les avatars
        Button ButtonBDD = (Button)findViewById(R.id.buttonGetBDD); // Récupération de l'instance voir les avatars
        Button ButtonValider = (Button)findViewById(R.id.buttonValidation); // Récupération de l'instance voir les avatars
        final EditText editText = (EditText) findViewById(R.id.editTextBDD); // Récupération de l'instance voir les avatars
        IPConnection = "192.168.43.182";    // default way
        editText.setText(IPConnection);

        //evenement click sur le bouton
        ButtonDevice.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (v.getId() == R.id.buttonGetDevice) { // C'est notre bouton ? oui, alors affichage d'un message
                    Toast.makeText(getApplicationContext(), "get device", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //evenement click sur le bouton
        ButtonBDD.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (v.getId() == R.id.buttonGetBDD) { // C'est notre bouton ? oui, alors affichage d'un message
                    Toast.makeText(getApplicationContext(), "get BDD", Toast.LENGTH_SHORT).show();

                    ConnexionBDDTest("http://"+IPConnection, "root", "");
                }
            }
        });

        //evenement click sur le bouton
        ButtonValider.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (v.getId() == R.id.buttonValidation) { // C'est notre bouton ? oui, alors affichage d'un message
                    IPConnection = editText.getText().toString();
                }
            }
        });
    }

    /**Méthodes relatives à la récupération des données sur la base externe**/

    //Cette méthode ce connecte à la Base de données exterieure en appelant AsyncTask et demande les données relatives au numéro de de requete passé en parametre. le résultat est ensuite récupéré par notre application
    public void ConnexionBDDTest(String stIpConnection, String StUserBDD, String StPasswordBDD) {
        String stringUrl =stIpConnection +"/android/getImages.php";
        Log.d("e", "ConnexionBDDTest: "+stringUrl);
        // Gets the URL from the UI's text field.
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            Log.d("ee", "The response is: ok ");
            Toast.makeText(this,"connexion au réseau ok", Toast.LENGTH_SHORT).show();
            int iValeurRequeteInterressante = 42;   // test
            String NumRequete = String.valueOf(iValeurRequeteInterressante);
            Log.d("ee", "The response is: 2 "+stringUrl+" ici");
            ConnectionBDDExt Connexion = new ConnectionBDDExt(NumRequete, StUserBDD, StPasswordBDD,
                    // On appelle le constructeur de ConnexionBDDExt dans lequel
                    // On 'override' la méthode processFinish de l'interface AsyncReponce grâce au 'override' définit ci-dessous
                    // ouf..
                    new ConnectionBDDExt.AsyncResponse(){
                        @Override
                        public void processFinish(String output){
                            Log.d("ee", "The response is: " + output);
                            // Ici, on reçoit le résultat de la méthode onPostExecute(result)
                            if(BuildConfig.DEBUG) {
                                Log.d("ee", "The response is: " + output);      //utile lors du debuguage
                            }
                            boolean bTemoin = Conversion (output);
                            if (bTemoin != false) {
                                // la convertion ,c'est bien passé, on peut faire ce que l'on veut avec UrlResult
                                bTemoin = AfficheToast(UrlResult);
                                Log.d("ee", "The response is: " + output);
                                if (bTemoin != false) {
                                    AfficheToast("le fichier json est bon");
                                } else {
                                    AfficheToast("Connexion seveur ok, problème lors de l'ajout des tables dans la BDD");
                                }
                            }else{
                                //la convertion s'est mal passé
                                AfficheToast(output);
                            }
                        }
                    }
            );


            Connexion.execute(stringUrl);

        } else {
            Toast.makeText(this,"connexion au réseau indisponible", Toast.LENGTH_SHORT).show();
        }
    }


    //cette méthode convertit le string au format JSON placé en parametre en objet URL
    public boolean Conversion (String stJSON){
                /*
                notre JSON ressemble à ceci:
                {"data":
                        {"url_image":"/pict/Sir.png"}
                 }
                // Objet JSON: entre {}
                // Tableau: entre []
                */

        try {
            // On récupère le JSON complet
            JSONObject jsonObject = new JSONObject(stJSON);
            //if(BuildConfig.DEBUG) {Log.d("MYAPP", "The response is: " + jsonObject);}      // utilisé lors du débugage
            // On récupère le tableau d'objets qui nous concerne
            JSONArray jsonarraydata = new JSONArray(jsonObject.getString("data"));

            // On récupère un objet JSON du tableau : a l'emplacement 0 ce trouve l'url_image
            JSONObject StringJson = new JSONObject(jsonarraydata.getString(0));
            // On fait le lien url_image - Objet JSON
            UrlResult = ConvertIntoString(StringJson);
            return true;

        }catch (JSONException e) {
            if(BuildConfig.DEBUG) {
                Log.e("MYAPP", "unexpected JSON exception lors de la lecture du format JSON", e);
            }
            return false;
        }
    }


    // Cette méthode convertit les valeurs d'un JSONObjet en String et retourne celui-ci
    private String ConvertIntoString (JSONObject StringJson){
        try {
            return StringJson.getString("url_image");

        }catch(JSONException e){
            if(BuildConfig.DEBUG) {
                Log.e("MYAPP", "unexpected JSON exception lors de la convertion des Strings", e);
            }
            Toast.makeText(this,"Erreur lors de la convertion de la requete en String : JSON exception",Toast.LENGTH_LONG).show();
            return null;
        }
    }


    //Cette méthode permet d'afiicher un toast dans le thread en cours
    public boolean AfficheToast(String stMessage){
        Toast.makeText(this, stMessage, Toast.LENGTH_LONG).show();
        return true;
    }

}

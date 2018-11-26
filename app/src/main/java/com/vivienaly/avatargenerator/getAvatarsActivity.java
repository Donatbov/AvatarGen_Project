package com.vivienaly.avatargenerator;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class getAvatarsActivity extends AppCompatActivity {

    // Variables globales
    String UrlResult;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.get_avatars);

        Button ButtonDevice = (Button)findViewById(R.id.buttonGetDevice); // Récupération de l'instance voir les avatars
        Button ButtonBDD = (Button)findViewById(R.id.buttonGetBDD); // Récupération de l'instance voir les avatars

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

                    ConnexionBDDTest("http://192.168.43.182/android", "root", "");
                }
            }
        });
    }

    /**Méthodes relatives à la récupération des données sur la base externe**/

    //Cette méthode ce connecte à la Base de données exterieure en appelant AsyncTask et demande les données relatives au numéro de journée passé en parametre. le résultat est ensuite mis dans la Base de données interne grace à ajouttable()
    public void ConnexionBDDTest(String stIpConnection, String StUserBDD, String StPasswordBDD) {
        String stringUrl ="http://"+ stIpConnection +"/getImages.php";
        // Gets the URL from the UI's text field.
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            Log.d("ee", "The response is: ok ");
            Toast.makeText(this,"connexion au réseau ok", Toast.LENGTH_SHORT).show();
            int idernierjour = 9;   // test
            String NumJour = String.valueOf(idernierjour);
            Log.d("ee", "The response is: 2 ");
            ConnectionBDDExt Connexion = new ConnectionBDDExt(NumJour, StUserBDD, StPasswordBDD,
                    // On appelle le constructeur de ConnexionBDDExt dans lequel
                    // On 'override' la méthode processFinish de l'interface AsyncReponce grâce au 'override' définit ci-dessous
                    // ouf...
                    new ConnectionBDDExt.AsyncResponse(){
                        @Override
                        public void processFinish(String output){
                            // Ici, on reçoit le résultat de la méthode onPostExecute(result)
                            if(BuildConfig.DEBUG) {
                                Log.d("ee", "The response is: " + output);      //utile lors du debuguage
                            }
                            boolean bTemoin = Conversion (output);
                            if (bTemoin != false) {
                                // la convertion ,c'est bien passé, on peut faire ce que l'on veut avec UrlResult
                                bTemoin = AfficheToast(UrlResult);
                                if (bTemoin != false) {
                                    AfficheToast("l'ajout des tables dans la BDD c'est bien passé");
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
                        {"URLPhoto":"/pict/Sir.png"}
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

            // On récupère un objet JSON du tableau : a l'emplacement 0 ce trouve l'URLPhoto
            JSONObject StringJson = new JSONObject(jsonarraydata.getString(0));
            // On fait le lien URLPhoto - Objet JSON
            UrlResult = ConvertIntoString(StringJson);
            return true;

        }catch (JSONException e) {
            if(BuildConfig.DEBUG) {
                Log.e("MYAPP", "unexpected JSON exception lors de la lecture du format JSON", e);
            }
            return false;
        }
    }


    // Cette méthode rentre les valeurs d'un JSONObjet de type recette dans un objet Recette et retourne celui-ci
    private String  ConvertIntoString (JSONObject StringJson){
        try {
            return StringJson.getString("URLPhoto");

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

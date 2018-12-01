package com.vivienaly.avatargenerator;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;


public class ConnectionBDDExt extends AsyncTask <String, Void, String> {        //  <1,2,3>
                                                                                // 1: Type de parametres envoyés lors de l'appel de l'execution de ConnexionBDDExt
                                                                                // 2: Type des unités publié lors du procédé d'arrière plan
                                                                                // 3: Type du résultat du procédé d'arrière plan
    //déclarations globales
    private static final String DEBUG_TAG = "ConnexionBDDExt";
    private AsyncResponse delegate;
    private String stParamRequete;
    private String stUserBDD;
    private String stPasswordBDD;


    //interface pour pouvoir accéder au résultat depuis la classe principale
    public interface AsyncResponse{
        void processFinish(String output);
    }

    //constructeur de la classe
    public ConnectionBDDExt(String NumJourneeRequete_in,String StUser,String stPassword, AsyncResponse delegate) {
        /**
         * Constructeur de l'asyncTask.
         * @param delegate
         */
        this.delegate = delegate;   //instanciation de l'interface 'delegate' avec la fonction placé en parametre
        this.stParamRequete = NumJourneeRequete_in;
        this.stUserBDD = StUser;
        this.stPasswordBDD = stPassword;
    }


    /********************************/

// méthodes définie par défaut par la classe AsyncTask et 'Overridées' pour faire ce que l'on veut à l'interieur

    // Cette méthode s'execute en premier lors de l'appel de 'execute'
    @Override
    protected void onPreExecute(){
        //vide
    }


    // Cette méthode s'execute après onPreExecute (on y effectue les opérations principales du thread)
    @Override
    protected String doInBackground(String... url) {
        // Les parametres viennent de l'appel de execute() : param[0] correspond à l'url

        try {
            String stResult = downloadUrl(url[0],this.stParamRequete);    //appelle la méthode downloadUrl définie ci-dessous
            if (stResult.equals("{\"data\":null}")){
                return "Connexion serveur ok, aucune donee trouvée à la suite de la requete: "+ stParamRequete + ", arret des requêtes";
            }
            else if (stResult.equals("")){
                return stResult+"aucune réponse de la part du serveur";
            }
            else{
                return stResult;
            }
        } catch (IOException e) {
            Log.d("e", "doInBackground: ");
            return "connexion au serveur indisponible: "+ e;
        }
    }


    // Cette méthode s'execute une fois le doInBackground terminé (avec le return de doInBackground en parametre d'entrée)
    @Override
    protected void onPostExecute(String result) {
        /*
         * Ici, le code exécuté une fois le traitement terminé, par exemple:
         *  -Mise à jour de l'affichage
         *  -Affichage d'une pop-up indiquant la fin du traitement
         *  -Désactivation de la ProgressBar
         *  -...
         */
        delegate.processFinish(result); //renvoie le résultat a l'activité principale
    }


    /********************************/

// méthodes non définies par défaut:

    // On donne un URL, Cette méthode établit un HttpUrlConnection et place le contenu de la page web dans un InputStream, qui est retourné sous forme de String                   (d'où la nécessitée d'afficher le résultat de la requete sur la page web dans le script php)
    private String downloadUrl(String myurl, String stParamRequete_in) throws IOException {
        /**
         * @param myurl : url pour se connecter au serveur apache
         * @param NumJourneeRequete : numero de la journee a envoyer au script PHP
         * @return : String contenant le résultat de la requte au format JSON
         */
        InputStream is = null;

        try {
            URL url = new URL(myurl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            Uri.Builder builder = new Uri.Builder().appendQueryParameter("Parametre", stParamRequete_in)
                    .appendQueryParameter("user", stUserBDD)
                    .appendQueryParameter("password", stPasswordBDD);
            String query = builder.build().getEncodedQuery();
            Log.e("MYAPP", query);


            //L'output stream sert à placer le le parametre de requete, le login, ainsi que le mot de passe en parametre de la requete via l'url via la méthode POST.
            //On peut ainsi la récupérer dans le script PHP
            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(query);
            writer.flush();
            writer.close();
            os.close();

            conn.connect(); //on établit la connexion avec tous les parametres définis ci-dessus

            //L'input stream sert à récupérer le contenu de la page web
            is = conn.getInputStream();
            String result = InputStreamToString(is);

            return result;

            // Pour etre sur que le InputStream soit fermé une fois que l'on a fini de l'utiliser
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }


    // Cette méthode convertit le contenu du stream en une chaine de charactères. Ici, il s'agit de notre objet JSON
    public static String InputStreamToString (InputStream in, int bufSize) {
        /**
         * @param in : buffer with the php result
         * @param bufSize : size of the buffer
         * @return : the string corresponding to the buffer
         */
        final StringBuilder out = new StringBuilder();
        final byte[] buffer = new byte[bufSize];
        try {
            for (int ctr; (ctr = in.read(buffer)) != -1;) {
                out.append(new String(buffer, 0, ctr));
            }
        } catch (IOException e) {
            throw new RuntimeException("Cannot convert stream to string", e);
        }
        // On retourne la chaine contenant les donnees de l'InputStream
        return out.toString();
    }


    // Même méthode que précedement mais avec une taille de buffer par défaut (1024)
    public static String InputStreamToString (InputStream in) {
        /**
         * @param in : buffer with the php result
         * @return : the string corresponding to the buffer
         */
        // On appelle la methode precedente avec une taille de buffer par defaut
        return InputStreamToString(in, 1024);
    }


}

package com.ideotic.edioticideas.assistant;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.SearchManager;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class MainActivity extends Activity implements TextToSpeech.OnInitListener {

    TextView showUspeak, dateView;
    Button help;
    public static String module = "";
    ImageButton speak;
    String command = "blabla";
    boolean check = false;
    private final int REQ_CODE = 100;
    private TextToSpeech tts;
    String welcome, date;
    String city = "pune", country = "India";
    final String baseUrl = "https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20weather.forecast%20where%20woeid%20in%20(select%20woeid%20from%20geo.places(1)%20where%20text%3D%22" +
            city +
            "%2C%20" +
            country +
            "%22)&format=xml&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys";

    String weatherText;
    FragmentManager f;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        f = getFragmentManager();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        welcome = "Hii " + preferences.getString(Needs.NAME, " ") + " how can i help you? ";
        //Grabbing References
        showUspeak = (TextView) findViewById(R.id.textViewShow);
        help = (Button) findViewById(R.id.buttonHelp);
        speak = (ImageButton) findViewById(R.id.imageButtonSpeak);
        tts = new TextToSpeech(this, this);
        @SuppressLint("SimpleDateFormat") DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, h:mm a");
        date = df.format(Calendar.getInstance().getTime());
        dateView = (TextView) findViewById(R.id.textView7Date);
        dateView.setText(date);

        new MyTask().execute();
        //Welcome
        showUspeak.setText(welcome);
        tts.speak(welcome, TextToSpeech.QUEUE_FLUSH, null);


        speak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Prompt speech input
                promptSpeechInput();
                check = true;


            }
        });

        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchModule(Commands.helpModule);
            }
        });
    }

    private void launchModule(String commandTolaunch) {
        switch (commandTolaunch){
            case Commands.q1:
                //Toast.makeText(getBaseContext(), "Q1 :"+Commands.q1, Toast.LENGTH_SHORT).show();
                tts.speak("Dr S U Kaadaam Sir", TextToSpeech.QUEUE_FLUSH, null);
                break;
            case Commands.q2:
            case Commands.q3:
            case Commands.q6:
                //Toast.makeText(getBaseContext(), "Q1 :"+Commands.q1, Toast.LENGTH_SHORT).show();
                tts.speak("Miss Ingavale madam", TextToSpeech.QUEUE_FLUSH, null);
                break;
            case Commands.q4:
            case Commands.q5:
                //Toast.makeText(getBaseContext(), "Q1 :"+Commands.q1, Toast.LENGTH_SHORT).show();
                tts.speak("Professor B P Mullaa sir", TextToSpeech.QUEUE_FLUSH, null);
                break;
            case Commands.q7:
                //Toast.makeText(getBaseContext(), "Q1 :"+Commands.q1, Toast.LENGTH_SHORT).show();
                tts.speak("Mr B P Mullaa sir", TextToSpeech.QUEUE_FLUSH, null);
                break;
            case Commands.q8:
                //Toast.makeText(getBaseContext(), "Q1 :"+Commands.q1, Toast.LENGTH_SHORT).show();
                tts.speak("Mr K A Chavaan madam", TextToSpeech.QUEUE_FLUSH, null);
                break;
            case Commands.q9:
                //Toast.makeText(getBaseContext(), "Q1 :"+Commands.q1, Toast.LENGTH_SHORT).show();
                tts.speak("Miss R B Vaarane madam", TextToSpeech.QUEUE_FLUSH, null);
                break;
            case Commands.q10:
                //Toast.makeText(getBaseContext(), "Q1 :"+Commands.q1, Toast.LENGTH_SHORT).show();
                tts.speak("seven subjects", TextToSpeech.QUEUE_FLUSH, null);
                break;
            case Commands.q11:
                //Toast.makeText(getBaseContext(), "Q1 :"+Commands.q1, Toast.LENGTH_SHORT).show();
                tts.speak("Management,Programming with python,Php,Enterpreneurship development,Mobile application development,Emerging trends in computer and information technology, Capston project execution", TextToSpeech.QUEUE_FLUSH, null);
                break;
            case Commands.q12:
                //Toast.makeText(getBaseContext(), "Q1 :"+Commands.q1, Toast.LENGTH_SHORT).show();
                tts.speak("On the Right side of a building", TextToSpeech.QUEUE_FLUSH, null);
                break;
            case Commands.q13:
                //Toast.makeText(getBaseContext(), "Q1 :"+Commands.q1, Toast.LENGTH_SHORT).show();
                tts.speak("At ground floor of right side of building", TextToSpeech.QUEUE_FLUSH, null);
                break;
            case Commands.q14:
                //Toast.makeText(getBaseContext(), "Q1 :"+Commands.q1, Toast.LENGTH_SHORT).show();
                tts.speak("At 1st floor of right side of A building", TextToSpeech.QUEUE_FLUSH, null);
                break;
            case Commands.q15:
                //Toast.makeText(getBaseContext(), "Q1 :"+Commands.q1, Toast.LENGTH_SHORT).show();
                tts.speak("Dr S P Naarote sir", TextToSpeech.QUEUE_FLUSH, null);
                break;
            case Commands.q16:
                //Toast.makeText(getBaseContext(), "Q1 :"+Commands.q1, Toast.LENGTH_SHORT).show();
                tts.speak("Dr Haampaali M P sir", TextToSpeech.QUEUE_FLUSH, null);
                break;
            case Commands.q17:
                //Toast.makeText(getBaseContext(), "Q1 :"+Commands.q1, Toast.LENGTH_SHORT).show();
                tts.speak("Shri kulkaarni v m", TextToSpeech.QUEUE_FLUSH, null);
                break;
            case Commands.q18:
                //Toast.makeText(getBaseContext(), "Q1 :"+Commands.q1, Toast.LENGTH_SHORT).show();
                tts.speak("Supriya U shinde madam", TextToSpeech.QUEUE_FLUSH, null);
                break;
            case Commands.q19:
                //Toast.makeText(getBaseContext(), "Q1 :"+Commands.q1, Toast.LENGTH_SHORT).show();
                tts.speak("Kaapil A Chaavaan", TextToSpeech.QUEUE_FLUSH, null);
                break;
            case Commands.q20:
                //Toast.makeText(getBaseContext(), "Q1 :"+Commands.q1, Toast.LENGTH_SHORT).show();
                tts.speak("Dr Sandipan P Naarote", TextToSpeech.QUEUE_FLUSH, null);
                break;
            case Commands.q21:
                //Toast.makeText(getBaseContext(), "Q1 :"+Commands.q1, Toast.LENGTH_SHORT).show();
                tts.speak("Praanoti R Aiitawade", TextToSpeech.QUEUE_FLUSH, null);
                break;
            case Commands.q22:
                //Toast.makeText(getBaseContext(), "Q1 :"+Commands.q1, Toast.LENGTH_SHORT).show();
                tts.speak("left side of building", TextToSpeech.QUEUE_FLUSH, null);
                break;
            case Commands.q23:
                //Toast.makeText(getBaseContext(), "Q1 :"+Commands.q1, Toast.LENGTH_SHORT).show();
                tts.speak("Maadaane madam", TextToSpeech.QUEUE_FLUSH, null);
                break;
            case Commands.q24:
                //Toast.makeText(getBaseContext(), "Q1 :"+Commands.q1, Toast.LENGTH_SHORT).show();
                tts.speak("13 subjects are in First year of diploma", TextToSpeech.QUEUE_FLUSH, null);
                break;
            case Commands.q25:
                //Toast.makeText(getBaseContext(), "Q1 :"+Commands.q1, Toast.LENGTH_SHORT).show();
                tts.speak("10 subjects are in second year of diploma", TextToSpeech.QUEUE_FLUSH, null);
                break;

            case Commands.q26:
                //Toast.makeText(getBaseContext(), "Q1 :"+Commands.q1, Toast.LENGTH_SHORT).show();
                tts.speak("Fundamental of Ict,engineering graphics,Workshop Practical,English,Basic Science,mathematics", TextToSpeech.QUEUE_FLUSH, null);
                break;

            case Commands.q27:
                //Toast.makeText(getBaseContext(), "Q1 :"+Commands.q1, Toast.LENGTH_SHORT).show();
                tts.speak("Element of Electrical Engineering,Applied Mathematics,Basic Electronic,Programming in C,Business Communication,Computer peripheral and hardware,web page designing using html", TextToSpeech.QUEUE_FLUSH, null);
                break;
            case Commands.q28:
                //Toast.makeText(getBaseContext(), "Q1 :"+Commands.q1, Toast.LENGTH_SHORT).show();
                tts.speak("Object Oriented programming using c++,Data structure using c,Computer graphics,Database management system, Digital techniques", TextToSpeech.QUEUE_FLUSH, null);
                break;
            case Commands.q29:
                //Toast.makeText(getBaseContext(), "Q1 :"+Commands.q1, Toast.LENGTH_SHORT).show();
                tts.speak("Java Programming,Software engineering,Data communication and computer network,Microprocessor,GUI application development using Vb.net", TextToSpeech.QUEUE_FLUSH, null);
                break;
            case Commands.q30:
                //Toast.makeText(getBaseContext(), "Q1 :"+Commands.q1, Toast.LENGTH_SHORT).show();
                tts.speak("Environmental studies,Operating system, Software testing, Advance java programming, and one elective subject", TextToSpeech.QUEUE_FLUSH, null);
                break;
            case Commands.q31:
                //Toast.makeText(getBaseContext(), "Q1 :"+Commands.q1, Toast.LENGTH_SHORT).show();
                tts.speak("Many Facilities are available like science labs, libraries, theatres, gyms, etc", TextToSpeech.QUEUE_FLUSH, null);
                break;
            case Commands.q32:
                //Toast.makeText(getBaseContext(), "Q1 :"+Commands.q1, Toast.LENGTH_SHORT).show();
                tts.speak("yes", TextToSpeech.QUEUE_FLUSH, null);
                break;
            case Commands.q33:
                //Toast.makeText(getBaseContext(), "Q1 :"+Commands.q1, Toast.LENGTH_SHORT).show();
                tts.speak("yes classes are lecture based or discussion based", TextToSpeech.QUEUE_FLUSH, null);
                break;
            case Commands.q34:
                //Toast.makeText(getBaseContext(), "Q1 :"+Commands.q1, Toast.LENGTH_SHORT).show();
                tts.speak("every courses required reading and writing ", TextToSpeech.QUEUE_FLUSH, null);
                break;
            case Commands.q35:
                //Toast.makeText(getBaseContext(), "Q1 :"+Commands.q1, Toast.LENGTH_SHORT).show();
                tts.speak("Good and Tasty", TextToSpeech.QUEUE_FLUSH, null);
                break;
            case Commands.q36:
                //Toast.makeText(getBaseContext(), "Q1 :"+Commands.q1, Toast.LENGTH_SHORT).show();
                tts.speak("yes there are enough computer labs are available", TextToSpeech.QUEUE_FLUSH, null);
                break;
            case Commands.q37:
                //Toast.makeText(getBaseContext(), "Q1 :"+Commands.q1, Toast.LENGTH_SHORT).show();
                tts.speak("Yes wifi is available in college for labs and for only college work not for students", TextToSpeech.QUEUE_FLUSH, null);
                break;
            case Commands.q38:
                //Toast.makeText(getBaseContext(), "Q1 :"+Commands.q1, Toast.LENGTH_SHORT).show();
                tts.speak("Miss Ingavle madam", TextToSpeech.QUEUE_FLUSH, null);
                break;
            case Commands.q39:
                //Toast.makeText(getBaseContext(), "Q1 :"+Commands.q1, Toast.LENGTH_SHORT).show();
                tts.speak("Jadhav madam", TextToSpeech.QUEUE_FLUSH, null);
                break;
            case Commands.q40:
                //Toast.makeText(getBaseContext(), "Q1 :"+Commands.q1, Toast.LENGTH_SHORT).show();
                tts.speak("Professor B P MUlla sir", TextToSpeech.QUEUE_FLUSH, null);
                break;
            case Commands.q41:
                //Toast.makeText(getBaseContext(), "Q1 :"+Commands.q1, Toast.LENGTH_SHORT).show();
                tts.speak("Tuition fee for SC category is 4500 rs", TextToSpeech.QUEUE_FLUSH, null);
                break;
            case Commands.q42:
                //Toast.makeText(getBaseContext(), "Q1 :"+Commands.q1, Toast.LENGTH_SHORT).show();
                tts.speak("Exam fee for SC category is 600 hundred rs", TextToSpeech.QUEUE_FLUSH, null);
                break;
            case Commands.q43:
                //Toast.makeText(getBaseContext(), "Q1 :"+Commands.q1, Toast.LENGTH_SHORT).show();
                tts.speak("Exam fee for open category is 600rs", TextToSpeech.QUEUE_FLUSH, null);
                break;
            case Commands.q44:
                //Toast.makeText(getBaseContext(), "Q1 :"+Commands.q1, Toast.LENGTH_SHORT).show();
                tts.speak("Tuition fee for SC category 7750", TextToSpeech.QUEUE_FLUSH, null);
                break;
            case Commands.q45:
                //Toast.makeText(getBaseContext(), "Q1 :"+Commands.q1, Toast.LENGTH_SHORT).show();
                tts.speak("Subject wise teachers are available", TextToSpeech.QUEUE_FLUSH, null);
                break;
            case Commands.q46:
                //Toast.makeText(getBaseContext(), "Q1 :"+Commands.q1, Toast.LENGTH_SHORT).show();
                tts.speak("Supriya U Shinde is the main coordinator of hostel", TextToSpeech.QUEUE_FLUSH, null);
                break;
            case Commands.q47:
                //Toast.makeText(getBaseContext(), "Q1 :"+Commands.q1, Toast.LENGTH_SHORT).show();
                tts.speak("Supriya U Shinde.", TextToSpeech.QUEUE_FLUSH, null);
                break;
            case Commands.q48:
                //Toast.makeText(getBaseContext(), "Q1 :"+Commands.q1, Toast.LENGTH_SHORT).show();
                tts.speak("Poonam M shinde", TextToSpeech.QUEUE_FLUSH, null);
                break;
            case Commands.q49:
                //Toast.makeText(getBaseContext(), "Q1 :"+Commands.q1, Toast.LENGTH_SHORT).show();
                tts.speak("Sonali s dohe", TextToSpeech.QUEUE_FLUSH, null);
                break;
            case Commands.q50:
                //Toast.makeText(getBaseContext(), "Q1 :"+Commands.q1, Toast.LENGTH_SHORT).show();
                tts.speak("Only one mess is available", TextToSpeech.QUEUE_FLUSH, null);
                break;
            case Commands.q51:
                //Toast.makeText(getBaseContext(), "Q1 :"+Commands.q1, Toast.LENGTH_SHORT).show();
                tts.speak("Professor B P MUlla sir", TextToSpeech.QUEUE_FLUSH, null);
                break;

            case Commands.mailModule:
                Toast.makeText(getBaseContext(), "Mail", Toast.LENGTH_SHORT).show();
                Intent intentm = new Intent(MainActivity.this, GmailModule.class);
                startActivity(intentm);
                break;
            case Commands.callModule:
                Toast.makeText(getBaseContext(), "Call", Toast.LENGTH_SHORT).show();
                Intent intentc = new Intent(MainActivity.this, PhoneModule.class);
                startActivity(intentc);
                break;
            case Commands.emergencyModule:
                Toast.makeText(getBaseContext(), "Emergency", Toast.LENGTH_SHORT).show();
                Intent intente = new Intent(MainActivity.this, MapModule.class);
                intente.putExtra(Commands.EMERGENCY, true);
                startActivity(intente);
                break;
            case Commands.locModule:
                Toast.makeText(getBaseContext(), "Location", Toast.LENGTH_SHORT).show();
                Intent intentl = new Intent(MainActivity.this, MapModule.class);
                startActivity(intentl);
                break;
            case Commands.musicModule:
                Toast.makeText(getBaseContext(), "Music", Toast.LENGTH_SHORT).show();
                Intent intentmu = new Intent(MainActivity.this, Music.class);
                startActivity(intentmu);
                break;
            case Commands.DATE:
                display_frag d = new display_frag();
                Bundle bundle = new Bundle();
                bundle.putString(Commands.DATE, date);
                d.setArguments(bundle);
                d.show(getFragmentManager(), "sss");
                break;
            case Commands.TIME:
                display_frag d2 = new display_frag();
                Bundle bundle2 = new Bundle();
                bundle2.putString(Commands.DATE, date);
                d2.setArguments(bundle2);
                d2.show(getFragmentManager(), "sss");
                break;
            case Commands.remmodule:
                Toast.makeText(getBaseContext(), "Reminder", Toast.LENGTH_SHORT).show();
                Intent intentr = new Intent(MainActivity.this, ReminderModule.class);
                startActivity(intentr);
                break;
            case Commands.helpModule:
                module = "main";
                Toast.makeText(getBaseContext(), "Help", Toast.LENGTH_SHORT).show();
                HelpFrag frag = new HelpFrag();
                frag.show(f, null);
                break;
            case Commands.noteModule:
                Toast.makeText(getBaseContext(), "Notes", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, NoteModule.class);
                startActivity(intent);
                break;
            case Commands.weather:
                display_frag d1 = new display_frag();
                Bundle bundle1 = new Bundle();
                bundle1.putString(Commands.DATE, weatherText);
                d1.setArguments(bundle1);
                d1.show(getFragmentManager(), "sss");
                break;
            default:
                try {
                    Intent intents = new Intent(Intent.ACTION_WEB_SEARCH);
                    intents.putExtra(SearchManager.QUERY, commandTolaunch);
                    startActivity(intents);
                } catch (Exception e) {
                    // TODO: handle exception
                }
                break;
        }
    }

    /**
     * Showing google speech input dialog
     */
    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                getString(R.string.speech_prompt));
        try {
            startActivityForResult(intent, REQ_CODE);

        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    getString(R.string.speech_not_supported),
                    Toast.LENGTH_SHORT).show();
        }
    }


    @SuppressLint("StaticFieldLeak")
    class MyTask extends AsyncTask<Void, Void, Void> {
        myXMLWorker doing;

        @Override
        protected Void doInBackground(Void... params) {

            try {
                URL web = new URL(baseUrl);
                SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
                SAXParser sp = saxParserFactory.newSAXParser();
                XMLReader xmlReader = sp.getXMLReader();
                doing = new myXMLWorker();
                xmlReader.setContentHandler(doing);
                xmlReader.parse(new InputSource(web.openStream()));

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        String command = Commands.TEMP;

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            switch (command) {
                case Commands.TEMP:
                    weatherText = doing.getTemp();
                    break;
            }
        }
    }

    /**
     * Receiving speech input
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    showUspeak.setText(result.get(0));

                    //Speak out
                    speakOut();

                }
                break;
            }

        }
    }

    //Speak Out
    private void speakOut() {

        String text = showUspeak.getText().toString().toLowerCase();
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
        command = text;
        //Launch Module
        if (check) {
            launchModule(command);
        }
    }

    @Override
    public void onInit(int status) {

        if (status == TextToSpeech.SUCCESS) {

            int result = tts.setLanguage(Locale.getDefault());

            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "This Language is not supported");
            } else {
                speak.setEnabled(true);
                speakOut();
            }

        } else {
            Log.e("TTS", "Initialization Failed!");
        }

    }

    @Override
    public void onDestroy() {
        // Shuts Down TTS
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
    }
}

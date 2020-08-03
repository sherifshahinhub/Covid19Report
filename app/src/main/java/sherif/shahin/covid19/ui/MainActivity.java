package sherif.shahin.covid19.ui;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.Date;
import androidx.appcompat.app.AppCompatActivity;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import sherif.shahin.covid19.R;
import static java.lang.Thread.sleep;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.AdRequest;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private final int totalCases = 1, newCases = 2, totalDeath = 3, newDeath = 4, totalRecovered = 5, activeCases = 7, seriousCritical = 8,
            totaCasesPer1MPop = 9, deathPer1MPop = 10, totalTests = 11, testsPer1MPop = 12, population = 13;

    private Document document;
    private ProgressBar pgsBar;
    private TextView summaryText_TextView;
    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getString(R.string.adUnitID));
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
                Log.d("onAdLoaded", "onAdLoaded()");
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Code to be executed when an ad request fails.
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when the ad is displayed.
            }

            @Override
            public void onAdClicked() {
                // Code to be executed when the user clicks on an ad.

            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when the interstitial ad is closed.
                Log.d("AD closed", "onAdClicked()");
                mInterstitialAd.loadAd(new AdRequest.Builder().build());

            }
        });


        summaryText_TextView = findViewById(R.id.summaryText);
        final Spinner spinner = findViewById(R.id.spinner);
        final GridLayout GridLayout = findViewById(R.id.GridLayout1);
        spinner.setVisibility(View.INVISIBLE);
        GridLayout.setVisibility(View.INVISIBLE);
        summaryText_TextView.setVisibility(View.INVISIBLE);
        pgsBar  = findViewById(R.id.pBar);
        pgsBar.setVisibility(View.VISIBLE);



        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                spinner.setVisibility(View.VISIBLE);
                GridLayout.setVisibility(View.VISIBLE);
                summaryText_TextView.setVisibility(View.VISIBLE);
                pgsBar.setVisibility(View.GONE);
            }
        },3500);

        Object[] urlObject = new Object[1];
        urlObject[0] = "https://www.worldometers.info/coronavirus/";
        Network myNetwork = new Network();
        myNetwork.execute(urlObject);

        while (myNetwork.getDocument() == null) {
            try {
                sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        document = myNetwork.getDocument();


        String[] strText = {"Select a country", "Afghanistan", "Albania", "Algeria", "Andorra", "Angola",
                "Argentina", "Armenia", "Australia", "Austria", "Azerbaijan", "Bahamas", "Bahrain", "Bangladesh",
                "Barbados", "Belarus", "Belgium", "Belize", "Benin", "Bhutan", "Bolivia", "Bosnia and Herzegovina",
                "Botswana", "Brazil", "Brunei", "Bulgaria", "Burkina Faso", "Burundi", "Cambodia", "Cameroon", "Canada",
                "Cabo Verde", "Chad", "Chile", "China", "Colombia", "Comoros", "Congo",
                "Costa Rica", "Croatia", "Cuba", "Cyprus", "Czechia", "Denmark",
                "Djibouti", "Dominica", "Dominican Republic", "Timor-Leste", "Ecuador", "Egypt", "El Salvador",
                "Equatorial Guinea", "Eritrea", "Estonia", "Ethiopia", "Fiji", "Finland", "France", "Gabon", "Gambia",
                "Georgia", "Germany", "Ghana", "Greece", "Grenada", "Guatemala", "Guinea", "Guinea-Bissau", "Guyana",
                "Haiti", "Honduras", "Hungary", "Iceland", "India", "Indonesia", "Iran", "Iraq", "Ireland",
                "Israel", "Italy", "Ivory Coast", "Jamaica", "Japan", "Jordan", "Kazakhstan", "Kenya", "Kiribati",
                "S. Korea", "Kuwait", "Kyrgyzstan", "Laos", "Latvia", "Lebanon", "Lesotho", "Liberia",
                "Libya", "Liechtenstein", "Lithuania", "Luxembourg", "North Macedonia", "Madagascar", "Malawi", "Malaysia",
                "Maldives", "Mali", "Malta", "Mauritania", "Mauritius", "Mexico", "Micronesia", "Moldova",
                "Monaco", "Mongolia", "Montenegro", "Morocco", "Mozambique", "Myanmar", "Namibia", "Nauru", "Nepal",
                "Netherlands", "New Zealand", "Nicaragua", "Niger", "Nigeria", "Norway", "Oman", "Pakistan", "Panama",
                "Papua New Guinea", "Paraguay", "Peru", "Philippines", "Poland", "Portugal", "Qatar", "Romania", "Russia",
                "Rwanda", "Saudi Arabia", "Senegal", "Serbia", "Seychelles", "Sierra Leone", "Singapore", "Slovakia",
                "Slovenia", "Solomon Islands", "Somalia", "South Africa", "South Sudan", "Spain", "Sri Lanka", "Sudan", "Suriname",
                "Swaziland", "Sweden", "Switzerland", "Syria", "Taiwan", "Tajikistan", "Tanzania", "Thailand", "Togo", "Tonga",
                "Tunisia", "Turkey", "Turkmenistan", "Tuvalu", "Uganda", "Ukraine", "UAE", "UK", "USA",
                "Uruguay", "Uzbekistan", "Vanuatu",  "Venezuela", "Vietnam", "Yemen", "Zambia", "Zimbabwe"};



        spinnerAdapter adapter = new spinnerAdapter(this, R.layout.spinner_row, strText);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);


    }

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {

        try {
            // An item was selected. You can retrieve the selected item using
            /**
             *  int totalCases = 1, newCases = 2, totalDeath = 3, newDeath = 4, totalRecovered = 5, activeCases = 7
             */
            if (mInterstitialAd.isLoaded()) {
                mInterstitialAd.show();
            } else {
                Log.d("TAG", "The interstitial wasn't loaded yet.");
            }

            String SelectedCountry = (String) parent.getItemAtPosition(pos);
            System.out.println("Selected Item: " + SelectedCountry);


            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date date = new Date();
            summaryText_TextView.setText("Coronavirus Cases Report  in " + SelectedCountry + " at " + formatter.format(date));
            summaryText_TextView.setTextSize(18);

            System.out.println(document.getElementById("main_table_countries_today").text());
            String[] item = document.getElementById("main_table_countries_today").text().split(SelectedCountry)[1].split(" ");

            for (int i = 0; i < 20; i++) {
                System.out.println("i=" + i + "  -> " + item[i]);
            }

            System.out.println(SelectedCountry + " @totalCases@: " + item[totalCases]);
            System.out.println(SelectedCountry + " @activeCases@: " + item[activeCases]);

            TextView activeCases_TextView = findViewById(R.id.activeCasesId);
            int displacment = 0;
            boolean newCasesNumber = true, newDeathNumber = true;
            if (!item[newCases].contains("+")) {
                displacment++;
                System.out.println("^");
                newCasesNumber = false;
            }

            if (!item[newDeath - displacment].contains("+")) {
                displacment++;
                System.out.println("^^");
                newDeathNumber = false;
            }

            int activeCasesDisplacment = 0;
            if (!newCasesNumber && !newDeathNumber && !isNumeric(item[10])) {
                System.out.println("^^^");
                activeCasesDisplacment++;
            } else if (!newCasesNumber && !newDeathNumber && isNumeric(item[10])) {
                activeCasesDisplacment += 2;
            } else if (!newCasesNumber || !newDeathNumber) {
                activeCasesDisplacment++;
            }

            System.out.println(SelectedCountry + " @totalCases@: " + item[totalCases]);
            System.out.println(SelectedCountry + " @activeCases@: " + item[activeCases]);
            System.out.println(SelectedCountry + " @displacment@: " + displacment);
            System.out.println(SelectedCountry + " @activeCasesDisplacment@: " + activeCasesDisplacment);
            System.out.println(SelectedCountry + " @newCasesNumber@: " + newCasesNumber);
            System.out.println(SelectedCountry + " @newDeathNumber@: " + newCasesNumber);

            activeCases_TextView.setText("Active Cases: " + item[activeCases - displacment - activeCasesDisplacment]);


            TextView totalCases_TextView = findViewById(R.id.totalCasesId);
            String checkDigits = item[totalCases];
            if (checkDigits.length() == 6) {
                //100 K

            } else if (checkDigits.length() == 7 || checkDigits.length() == 8 || checkDigits.length() == 9) {
                //Millions

            } else {

            }
            totalCases_TextView.setText(item[totalCases]);


            TextView newCases_TextView = findViewById(R.id.newCasesId);
            newCases_TextView.setTextSize(11);
            newCases_TextView.setText("No new cases reported from midnight GTM+0 till now");
            newCases_TextView.setTextColor(getResources().getColor(R.color.newDeathColorGray));
            if (newCasesNumber) {
                newCases_TextView.setTextSize(20);
                newCases_TextView.setTextColor(getResources().getColor(R.color.orangeColor));
                newCases_TextView.setText(item[newCases]);
            }


            TextView totalRecovered_TextView = findViewById(R.id.totalRecoveredId);
            totalRecovered_TextView.setText(item[totalRecovered - displacment]);


            TextView newDeaths_TextView = findViewById(R.id.newDeathId);
            newDeaths_TextView.setTextSize(11);
            newDeaths_TextView.setText("No new deaths reported from midnight GTM+0 till now");
            newDeaths_TextView.setTextColor(getResources().getColor(R.color.newDeathColorGray));
            if (newDeathNumber) {
                newDeaths_TextView.setTextSize(20);
                newDeaths_TextView.setTextColor(getResources().getColor(R.color.orangeColor));
                if (!newCasesNumber) newDeaths_TextView.setText("New: " + item[newDeath - 1]);
                else newDeaths_TextView.setText("New: " + item[newDeath]);
            }

            TextView totalDeaths_TextView = findViewById(R.id.totalDeathsId);
            if (!newCasesNumber) totalDeaths_TextView.setText("Total: " + item[totalDeath - 1]);
            else totalDeaths_TextView.setText("Total: " + item[totalDeath]);

        } catch (Exception e){
            summaryText_TextView.setText("Select a Country");
            TextView activeCases_TextView = findViewById(R.id.activeCasesId);
            activeCases_TextView.setText("");
            TextView totalCases_TextView = findViewById(R.id.totalCasesId);
            totalCases_TextView.setText("");
            TextView newCases_TextView = findViewById(R.id.newCasesId);
            newCases_TextView.setText("");
            TextView totalRecovered_TextView = findViewById(R.id.totalRecoveredId);
            totalRecovered_TextView.setText("");
            TextView newDeaths_TextView = findViewById(R.id.newDeathId);
            newDeaths_TextView.setText("");
            TextView totalDeaths_TextView = findViewById(R.id.totalDeathsId);
            totalDeaths_TextView.setText("");
        }

    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }
    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Integer.parseInt(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }


    public class Network extends AsyncTask<Object, String, String> {
        protected Document document = null;

        public Document getDocument(){
            return document;
        }



        @Override
        protected String doInBackground(Object... objects) {
            String url = (String) objects[0];
            try {
                document = Jsoup.connect(url)
                        .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.152 Safari/537.36")
                        .get();


            } catch(Exception e){
                e.printStackTrace();
            }

            return null;
        }
    }


}
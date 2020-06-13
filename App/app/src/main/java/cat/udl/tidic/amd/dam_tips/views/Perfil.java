package cat.udl.tidic.amd.dam_tips.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import cat.udl.tidic.amd.dam_tips.R;
import cat.udl.tidic.amd.dam_tips.dao.AccountDAO;
import cat.udl.tidic.amd.dam_tips.models.Account;
import cat.udl.tidic.amd.dam_tips.network.RetrofitClientInstance;
import cat.udl.tidic.amd.dam_tips.preferences.PreferencesProvider;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Perfil extends AppCompatActivity {
    private TextView nom;
    private TextView cognom;
    private TextView gmail;
    private TextView telefon;
    private TextView genere;
    private TextView punts;
    private TextView username;
    private AccountDAO accountDAO;
    private SharedPreferences mPreferences;
    private String token;
    private String TAG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        Intent intent = getIntent();
        nom = findViewById(R.id.textnom);
        cognom = findViewById(R.id.cognomtext);
        gmail = findViewById(R.id.gmailtext);
        telefon = findViewById(R.id.telefontext);
        genere = findViewById(R.id.generetext);
        punts = findViewById(R.id.punts);
        username = findViewById(R.id.nomusuaritext);


        accountDAO = RetrofitClientInstance.
                getRetrofitInstance().create(AccountDAO.class);

        this.mPreferences = PreferencesProvider.providePreferences();
        token = this.mPreferences.getString("token", "");
        System.out.print(this.accountDAO);
        Call<Account> call_get = this.accountDAO.getUserProfile();
        call_get.enqueue(new Callback<Account>() {
            @Override
            public void onResponse(Call<Account> call, Response<Account> response) {
                if (response.code() == 200){
                    Account con = response.body();
                    nom.setText(con.getName());
                    cognom.setText(con.getSurname());
                    gmail.setText(con.getEmail());
                    telefon.setText(con.getPhone());
                    punts.setText(con.getPoints());
                    username.setText(con.getUsername());
                    if (con.getGenere().equals("F")){
                        genere.setText("Dona");
                    }else {
                        genere.setText("Home");
                    }
                    //imatge.setIma

                }
            }

            @Override
            public void onFailure(Call<Account> call, Throwable t) {

            }
        });
    }
}
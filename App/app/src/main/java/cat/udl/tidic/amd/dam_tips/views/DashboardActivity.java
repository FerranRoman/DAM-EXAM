package cat.udl.tidic.amd.dam_tips.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.Observer;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import cat.udl.tidic.amd.dam_tips.R;
import cat.udl.tidic.amd.dam_tips.viewmodels.LoginViewModel;

public class DashboardActivity extends CustomActivty {

    private Button disconnect;
    private LoginViewModel loginViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        initView();
    }
    public void initRanking(View view){
        Intent intent = new Intent(DashboardActivity.this,RankingUsers.class);
        startActivity(intent);
    }
    public void initPerfil(View view){
        Intent intent = new Intent(DashboardActivity.this, Perfil.class);
        startActivity(intent);
    }


    protected void initView(){
        loginViewModel = new LoginViewModel();
        disconnect = findViewById(R.id.disconnect);
        disconnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    loginViewModel.logout();
            }
        });

        loginViewModel.getResponseLogin().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (!s.startsWith("Error")) {
                    goTo(LoginActivity.class);
                }
            }
        });

    }


    @Override
    public void onBackPressed(){
        finishAffinity();
    }

}

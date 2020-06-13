package cat.udl.tidic.amd.dam_tips.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cat.udl.tidic.amd.dam_tips.R;
import cat.udl.tidic.amd.dam_tips.dao.AccountDAO;
import cat.udl.tidic.amd.dam_tips.models.Account;
import cat.udl.tidic.amd.dam_tips.network.RetrofitClientInstance;
import cat.udl.tidic.amd.dam_tips.preferences.PreferencesProvider;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RankingUsers extends AppCompatActivity {
    private RecyclerView playersListView;
    private AccountDAO accountDao;
    private UserAdapter userAdapter;
    private String TAG = "listUsers";
    ArrayList<Account> players_data = new ArrayList<>();
    private SharedPreferences mPreferences;
    private String token;
    private TextView ranking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking_users);

        Intent intent = getIntent();

        playersListView = findViewById(R.id.userlist);
        playersListView.setLayoutManager(new LinearLayoutManager(this));
        userAdapter = new UserAdapter(new cat.udl.tidic.amd.dam_tips.views.UserDiffCallback());
        playersListView.setAdapter(userAdapter);
        ranking = findViewById(R.id.Rankingtitol);

        accountDao = RetrofitClientInstance.
                getRetrofitInstance().create(AccountDAO.class);

        this.mPreferences = PreferencesProvider.providePreferences();
        token = this.mPreferences.getString("token", "");

        LlistaUsuaris();
    }

    public void LlistaUsuaris() {

        Call<List<Account>> call_get_players = accountDao.getUsers();
        call_get_players.enqueue(new Callback<List<Account>>() {
            @Override
            public void onResponse(Call<List<Account>> call, Response<List<Account>> response) {
                Log.d(TAG, "code:" + response.code());
                if (response.code() == 200) {


                    players_data = (ArrayList<Account>) response.body();
                    userAdapter.submitList(players_data);
                } else {

                }
            }

            @Override
            public void onFailure(Call<List<Account>> call, Throwable t) {
            }
        });
    }
}
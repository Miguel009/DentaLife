package sv.edu.udb.dentalife.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import sv.edu.udb.dentalife.R;
import sv.edu.udb.dentalife.databinding.ActivityDashboardBinding;
import sv.edu.udb.dentalife.fragments.DentistFragment;
import sv.edu.udb.dentalife.fragments.ItemFragment;
import sv.edu.udb.dentalife.fragments.MessagesFragment;
import sv.edu.udb.dentalife.fragments.MyAppointmentsFragment;

public class DashboardActivity extends AppCompatActivity {

    ActivityDashboardBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        binding = ActivityDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new MyAppointmentsFragment());
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId())
            {
                case R.id.history:
                    replaceFragment(new MyAppointmentsFragment());
                    break;
                case R.id.dentists:
                    replaceFragment(new DentistFragment());
                    break;
                case R.id.messages:
                    replaceFragment(new MessagesFragment());
                    break;
                case R.id.profile:
                    replaceFragment(new ItemFragment());
                    break;
            }
            return true;
        });
    }

    private void replaceFragment(Fragment fragment)
    {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment, fragment);
        fragmentTransaction.commit();
    }
}
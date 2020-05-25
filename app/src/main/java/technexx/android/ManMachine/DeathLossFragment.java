package technexx.android.ManMachine;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

public class DeathLossFragment extends Fragment {

    private View root;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.death_loss_fragment, container, false);

        return root;
    }
}

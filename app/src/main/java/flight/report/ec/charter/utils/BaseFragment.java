package flight.report.ec.charter.utils;

import android.support.v4.app.Fragment;
import android.view.View;

public class BaseFragment extends Fragment implements View.OnClickListener {

    public Callback _callback;
    public interface Callback {

    }

    @Override
    public void onClick(View v) {

    }
}

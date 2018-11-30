package multitouchpro.tests;

import android.app.Activity;
import android.os.Bundle;

public class Multitouch extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        getWindow().setFlags(1024, 1024);
        setContentView(new MTView(this));
    }

    public void onBackPressed() {
        finish();
    }
}

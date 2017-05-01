package nhatto.com;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ContactDetail extends AppCompatActivity {

    private TextView tvInputName;
    private Button btnInputNumber,btnMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_detail);

        setWidget();

//        arrContact = new ArrayList<>();

        setDataByBundle();
        checkAndRequestPermissions();
    }

    private void checkAndRequestPermissions() {
        String[] permissions = new String[]{
                android.Manifest.permission.CALL_PHONE,
                android.Manifest.permission.SEND_SMS
        };
        List<String> ListPermissionsNeeded = new ArrayList<>();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                ListPermissionsNeeded.add(permission);
            }
        }
        if (!ListPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, ListPermissionsNeeded.toArray(new String[ListPermissionsNeeded.size()]), 1);
        }
    }

    public void setWidget(){
        tvInputName = (TextView) findViewById(R.id.tv_input_name);
        btnInputNumber = (Button) findViewById(R.id.btn_input_number);
    }

    private void setDataByBundle() {
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra(CreateNewContact.BUNDLE);
        String inputName = bundle.getString(CreateNewContact.INPUTNAME);
        String inputNumber = bundle.getString(CreateNewContact.INPUTNUMBER);
        tvInputName.setText(inputName);
        btnInputNumber.setText(inputNumber);
    }

//    private void intentCall(int position) {
//        Intent intent = new Intent();
//        intent.setAction(Intent.ACTION_CALL);
//        intent.setData(Uri.parse("tel:" + arrContact.get(position).getmNumber()));
//        startActivity(intent);
//    }
//
//    private void intentMessage(int position) {
//        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:" + arrContact.get(position).getmNumber()));
//        startActivity(intent);
//    }
}

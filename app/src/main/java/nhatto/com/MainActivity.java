package nhatto.com;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.jar.Manifest;

import nhatto.com.adapter.ContactAdapter;
import nhatto.com.model.Contact;

public class MainActivity extends AppCompatActivity {

    private List<Contact> arrContact;
    private ContactAdapter adapter;
    private EditText edtName;
    private EditText edtNumber;
    private RadioButton rbtnMale;
    private RadioButton rbtnFelmale;
    private Button btnAddContact;
    private ListView lvContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setWidget();
        arrContact = new ArrayList<>();
        adapter = new ContactAdapter(this, R.layout.item_contact_listview, arrContact);
        lvContact.setAdapter(adapter);
        checkAndRequestPermissions();
        lvContact.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showDialogConfirm(position);
            }
        });
    }

    private void checkAndRequestPermissions(){
        String[] permissions  = new String[]{
                android.Manifest.permission.CALL_PHONE,
                android.Manifest.permission.SEND_SMS
        };
        List<String>ListPermissionsNeeded = new ArrayList<>();
        for (String permission : permissions){
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED){
                ListPermissionsNeeded.add(permission);
            }
        }
        if (!ListPermissionsNeeded.isEmpty()){
            ActivityCompat.requestPermissions(this,ListPermissionsNeeded.toArray(new String[ListPermissionsNeeded.size()]),1);
        }
    }

    public void setWidget() {
        edtName = (EditText) findViewById(R.id.edt_name);
        edtNumber = (EditText) findViewById(R.id.edt_number);
        rbtnMale = (RadioButton) findViewById(R.id.rbtn_male);
        rbtnFelmale = (RadioButton) findViewById(R.id.rbtn_felmale);
        btnAddContact = (Button) findViewById(R.id.btn_add_contact);
        lvContact = (ListView) findViewById(R.id.lv_contact);
    }

    public void addContact(View view) {
        if (view.getId() == R.id.btn_add_contact) {
            String name = edtName.getText().toString().trim();
            String number = edtNumber.getText().toString().trim();
            boolean isMale = true;
            if (rbtnMale.isChecked()) {
                isMale = true;
            } else {
                isMale = false;
            }
            if (TextUtils.isEmpty(name) || TextUtils.isEmpty(number)) {
                Toast.makeText(this, "Please Input Number or Name", Toast.LENGTH_SHORT).show();
            } else {
                Contact contact = new Contact(isMale, name, number);
                arrContact.add(contact);
            }
            adapter.notifyDataSetChanged();
        }
    }

    public void showDialogConfirm(final int position) {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.custom_dialog_layout);
        Button btnCall = (Button) dialog.findViewById(R.id.btn_call);
        Button btnMessage = (Button) dialog.findViewById(R.id.btn_message);

        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intentCall(position);
            }
        });

        btnMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intentMessage(position);
            }
        });
        dialog.show();
    }

    private void intentCall(int position) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + arrContact.get(position).getmNumber()));
        startActivity(intent);
    }

    private void intentMessage(int position) {
        Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse("sms:" + arrContact.get(position).getmNumber()));
        startActivity(intent);
    }
}

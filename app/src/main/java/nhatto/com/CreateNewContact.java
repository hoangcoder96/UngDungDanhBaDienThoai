package nhatto.com;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CreateNewContact extends AppCompatActivity {

    private EditText edtInputName, edtInputNumber;
    private Button btnCreate;

    public static final String INPUTNAME = "INPUTNAME";
    public static final String INPUTNUMBER = "INPUTNUMBER";
    public static final String BUNDLE = "BUNDLE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_contact);

        edtInputName = (EditText) findViewById(R.id.edt_input_name);
        edtInputNumber = (EditText) findViewById(R.id.edt_input_number);
        btnCreate = (Button) findViewById(R.id.btn_create);

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputName = edtInputName.getText().toString();
                String inputNumber = edtInputNumber.getText().toString();
                byBundle (inputName,inputNumber);
            }
        });
    }

    private void byBundle(String inputName, String inputNumber) {
        Intent intent = new Intent(CreateNewContact.this,ContactDetail.class );
        Bundle bundle = new Bundle();
        bundle.putString(INPUTNAME, inputName);
        bundle.putString(INPUTNUMBER,inputNumber);
        intent.putExtra(BUNDLE,bundle);
        startActivity(intent);
    }
}

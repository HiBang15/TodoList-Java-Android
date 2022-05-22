package demo1.ktl.testapp123.ui;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.example.thuchanh2.R;

import demo1.ktl.testapp123.data.db.SQLiteHelper;
import demo1.ktl.testapp123.data.model.Item;

public class EditActivity extends AppCompatActivity {

    Item item;
    EditText etTitle, etDate;
    Button btUpdate, btDelete, btCancel;
    Spinner spStatus;

    private final String[] statusList = {
            "Pending", "Processing", "Complete"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        item = (Item) getIntent().getSerializableExtra("item");
        etTitle = findViewById(R.id.et_title);
        etDate = findViewById(R.id.et_date);
        btUpdate = findViewById(R.id.bt_update);
        btDelete = findViewById(R.id.bt_delete);
        spStatus = findViewById(R.id.sp_status);
        btCancel = findViewById(R.id.bt_cancel);

        initView();
        initListener();

    }

    private void initView() {
        etTitle.setText(item.getName());
        etDate.setText(item.getDate());

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,
                statusList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spStatus.setAdapter(adapter);

        for (int i = 0; i <statusList.length; i++){
            if(item.getStatus().equals(statusList[i])){
                spStatus.setSelection(i);
                break;
            }
        }

    }

    private void initListener() {
        btUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etTitle.getText().toString();
                String date = etDate.getText().toString();
                String status = spStatus.getSelectedItem().toString();;

                if(name.isEmpty() || status.isEmpty() || date.isEmpty()){
                    Toast.makeText(getBaseContext(), "Không được để trống", Toast.LENGTH_SHORT).show();
                }
                else{
                    item.setName(name);
                    item.setStatus(status);
                    item.setDate(date);
                    SQLiteHelper sqLiteHelper = new SQLiteHelper(getBaseContext());
                    sqLiteHelper.updateItem(item);
                    Toast.makeText(getBaseContext(), "Sửa thành công!", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });

        btDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteHelper sqLiteHelper = new SQLiteHelper(getBaseContext());
                sqLiteHelper.deleteItem(item.getId());
                Toast.makeText(getBaseContext(), "Xóa thành công!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedYear = 2022;
                int selectedMonth = 5;
                int selectedDayOfMonth = 4;

                DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        etDate.setText(dayOfMonth + "/" + (month+1 )+ "/" +year );
                    }

                };

                DatePickerDialog datePickerDialog = new DatePickerDialog(EditActivity.this,
                        dateSetListener, selectedYear, selectedMonth, selectedDayOfMonth);

                datePickerDialog.show();
            }
        });
    }

}
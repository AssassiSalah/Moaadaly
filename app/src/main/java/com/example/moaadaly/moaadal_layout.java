package com.example.moaadaly;

import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class moaadal_layout extends AppCompatActivity {

    DatabaseHandler database;
    ArrayList<Module_Only_Exame> modules_list;
    ArrayList<TextView> modules_Moyan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_moaadal_layout);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Accessing TextViews
        TextView nameSpeciality = findViewById(R.id.Name_Speciality);
        nameSpeciality.setText("M1 IA"); // Example: Set new text

        TextView nameSaves = findViewById(R.id.Name_Saves);
        nameSaves.setText("Current Note");

        TextView semesterNumber = findViewById(R.id.Semaster_Number);
        semesterNumber.setText("S1");

        makeSpace();

        database = new DatabaseHandler(this);
        //database.dropTable();
        //database.createTableIfNotExists();
        modules_list = database.getAllModules();//getModuleListDatabase();
        //modules_list = getModuleList();
        modules_Moyan = new ArrayList<>();
        for (Module_Only_Exame module : modules_list)
            test(module);

        //database.dropTable();
    }

    private ArrayList<Module_Only_Exame> getModuleList() {
        ArrayList<Module_Only_Exame> modules = new ArrayList<>();
        modules.add(new Module_With_TD_TP("AAP",3, 6,0.25f,0.25f));
        modules.add(new Module_With_TD_TP("SD",3, 6,0.25f,0.25f));
        modules.add(new Module_With_TP("RC",3, 6,0.5f));
        modules.add(new Module_With_TD_TP("ISD",2, 4,0.25f,0.25f));
        modules.add(new Module_With_TP("AA",2, 4,0.5f));
        modules.add(new Module_Only_Exame("ENG",1, 2));
        modules.add(new Module_Only_Exame("ENT",1, 2));
        return modules;
    }

    private void test(Module_Only_Exame module) {

        // Accessing TableLayout and TableRow
        TableLayout tableLayout = findViewById(R.id.Table_Layout); // Add this id if missing from XML

        // Add the row to the table layout
        tableLayout.addView(getTableRow(module));

        makeSpace();
    }

    private void makeSpace() {
        TableLayout tableLayout = findViewById(R.id.Table_Layout);

        tableLayout.addView(new View(this) {{
            setLayoutParams(new TableRow.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT,
                    (int) (20 * getResources().getDisplayMetrics().density)
            ));
        }});
    }

    @NonNull
    private TableRow getTableRow(Module_Only_Exame module) {
        TableRow tableRow = new TableRow(this);

        // Add dynamic rows to the table
        TextView moduleName = new TextView(this);
        moduleName.setText(module.getName_Module());
        tableRow.addView(moduleName);

        TextView coefficient = new TextView(this);
        coefficient.setText(String.valueOf(module.getCouf()));
        tableRow.addView(coefficient);

        TextView credit = new TextView(this);
        credit.setText(String.valueOf(module.getCred()));
        tableRow.addView(credit);

        EditText td = createEditText();
        td.setTag("TD_" + module.getName_Module()); // Assign a unique tag
        td.addTextChangedListener(getTextWatcher(td)); // Add TextWatcher
        if(module instanceof Module_With_TD) {
            td.setText(((Module_With_TD) module).getTD_Note_String());
            tableRow.addView(td);
        } else
            if(module instanceof Module_With_TD_TP) {
                td.setText(((Module_With_TD_TP) module).getTD_Note_String());
                tableRow.addView(td);
            } else {
                // No Need For Edit Text
                TextView empty = new TextView(this);
                empty.setText("N/A");
                tableRow.addView(empty);
            }


        EditText tp = createEditText();
        tp.setTag("TP_" + module.getName_Module()); // Assign a unique tag
        tp.addTextChangedListener(getTextWatcher(tp)); // Add TextWatcher
        if(module instanceof Module_With_TP) {
            tp.setText(((Module_With_TP) module).getTP_Note_String());
            tableRow.addView(tp);
        } else
            if(module instanceof Module_With_TD_TP) {
                tp.setText(((Module_With_TD_TP) module).getTP_Note_String());
                tableRow.addView(tp);
            } else {
                // No Need For Edit Text
                TextView empty = new TextView(this);
                empty.setText("N/A");
                tableRow.addView(empty);
            }

        EditText exam = createEditText();
        exam.setTag("Exam_" + module.getName_Module()); // Assign a unique tag
        exam.addTextChangedListener(getTextWatcher(exam)); // Add TextWatcher

        exam.setText(module.getExam_Note_String());

        tableRow.addView(exam);

        TextView moyan = new TextView(this);
        moyan.setText(String.valueOf(calculateMoyan(module)));
        tableRow.addView(moyan);

        modules_Moyan.add(moyan);

        return tableRow;
    }

    @NonNull
    private EditText createEditText() {
        EditText editTextDecimal = new EditText(this);

        editTextDecimal.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

        // Apply an InputFilter to restrict the input format
        editTextDecimal.setFilters(new InputFilter[] {
                new InputFilter() {
                    @Override
                    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                        String input = dest.toString() + source.toString();

                        // Regular expression to match XX.XX format
                        if (input.matches("^\\d{0,2}(\\.\\d{0,2})?$")) {
                            return null; // Accept the input
                        }
                        return ""; // Reject the input
                    }
                }
        });

        return editTextDecimal;
    }

    // Create a reusable TextWatcher
    private TextWatcher getTextWatcher(EditText editText) {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Do nothing before text changes
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Optionally handle live changes here
            }

            @Override
            public void afterTextChanged(Editable s) {
                String tag = (String) editText.getTag();
                String nameModule = tag.split("_")[1];
                String newValue = s.toString();
                Log.d("EditTextChange", "Tag: " + tag + ", New Value: " + newValue);

                // Call your custom logic or methods
                handleValueChange(tag, nameModule, newValue);

                Module_Only_Exame module = searchForModule(nameModule);
                if(module == null)
                    return;

                updatTheValue(module, tag, newValue);
                int index = modules_list.indexOf(module);
                if(modules_list.size() <= index || modules_Moyan.size() <= index)
                    return;

                TextView moyanTextView = modules_Moyan.get(index);

                moyanTextView.setText(String.valueOf(calculateMoyan(module)));

                calculateMoyanTotal();
            }
        };
    }

    private Module_Only_Exame searchForModule(String nameModule) {
        for(Module_Only_Exame module : modules_list)
            if(module.getName_Module().equals(nameModule))
                return module;

        return null;
    }

    // Handle the updated value based on the tag
    private void handleValueChange(String tag, String nameModule, String newValue) {
        // Identify the source of the change using the tag
        if (tag.startsWith("TD_")) {
            Log.d("ValueUpdate", "TD for Module: " + nameModule + ", New Value: " + newValue);
            // Handle TD update
        } else if (tag.startsWith("TP_")) {
            Log.d("ValueUpdate", "TP for Module: " + nameModule + ", New Value: " + newValue);
            // Handle TP update
        } else if (tag.startsWith("Exam_")) {
            Log.d("ValueUpdate", "Exam for Module: " + nameModule + ", New Value: " + newValue);
            // Handle Exam update
        }
    }

    private void updatTheValue(Module_Only_Exame module, String tag, String newValueString) {
        float newValue;

        if(newValueString.isEmpty())
            newValue = 0.0f;
        else {
            try {
                newValue = Float.parseFloat(newValueString);  // Try parsing the string to a Float
                // If successful, Continue
            } catch (NumberFormatException e) {
                return;  // If an exception occurs, Exit
            }
        }

        Log.d("Changing Valuer", module.getName_Module());
        if (tag.startsWith("TD_")) {
            if(module instanceof Module_With_TD_TP) {
                Log.d("ValueUpdate", "TD from value: " + ((Module_With_TD_TP) module).getTD_Note() + ", To New Value: " + newValue);
                ((Module_With_TD_TP) module).setTD_Note(newValue);
            } else {
                Log.d("ValueUpdate", "TD from value: " + ((Module_With_TD) module).getTD_Note() + ", To New Value: " + newValue);
                ((Module_With_TD) module).setTD_Note(newValue);
            }
        } else if (tag.startsWith("TP_")) {
            if(module instanceof Module_With_TD_TP) {
                Log.d("ValueUpdate", "TP from value: " + ((Module_With_TD_TP) module).getTP_Note() + ", To New Value: " + newValue);
                ((Module_With_TD_TP) module).setTP_Note(newValue);
            } else {
                Log.d("ValueUpdate", "TP from value: " + ((Module_With_TP) module).getTP_Note() + ", To New Value: " + newValue);
                ((Module_With_TP) module).setTP_Note(newValue);
            }
        } else if (tag.startsWith("Exam_")) {
            Log.d("ValueUpdate", "Exam from value: " + module.getExam_Note() + ", New Value: " + newValue);
            module.setEXAM_Note(newValue);
            // Handle Exam update
        }
    }

    private float calculateMoyan(Module_Only_Exame module) {
        // Cast The Value To The Right Class
        if (module instanceof Module_With_TD)
            return calculateMoyan((Module_With_TD) module);
        if (module instanceof Module_With_TP)
            return calculateMoyan((Module_With_TP) module);
        if (module instanceof Module_With_TD_TP)
            return calculateMoyan((Module_With_TD_TP) module);
        else
            return  module.getExam_Note();
    }

    private float calculateMoyan(Module_With_TD_TP module) {
        return calculateMoyan(module.getTD_Note()
                , module.getTD_Percent()
                , module.getTP_Note()
                , module.getTP_Percent()
                , module.getExam_Note());
    }
    private float calculateMoyan(Module_With_TD module) {
        return calculateMoyan(module.getTD_Note()
                , module.getTD_Percent()
                , module.getExam_Note());
    }
    private float calculateMoyan(Module_With_TP module) {
        return calculateMoyan(module.getTP_Note()
                , module.getTP_Percent()
                , module.getExam_Note());
    }

    private float calculateMoyan(float T, float T_persent, float exam) {
        // Approximate To Two Decimal Places
        return Math.round((T * T_persent + exam * (1 - T_persent)) * 100) / 100.0f;
    }

    private float calculateMoyan(float TD, float TD_persent, float TP, float TP_persent, float exam) {
        // Approximate To Two Decimal Places
        return Math.round((TD * TD_persent + TP * TP_persent + exam * (1 - TD_persent - TP_persent)) * 100) / 100.0f;
    }

    private void calculateMoyanTotal() {
        //chatgbt comblite this
        float moyan_total = 0.0f;
        float couf_total = 0;
        for(Module_Only_Exame module : modules_list) {
            TextView moyanTextView = modules_Moyan.get(modules_list.indexOf(module));
            float moyan_local = calculateMoyan(module);
            moyan_total += moyan_local * module.getCouf();
            couf_total += module.getCouf();
            moyanTextView.setText(String.valueOf(moyan_local));
        }
        Log.d("dbb", "Moyan Total: " + moyan_total + " , Couf Total: " + couf_total + " ,the / of the total: " + (moyan_total / couf_total) + " , the round value: " + (Math.round(moyan_total / couf_total) * 100 / 100.0f));
        TextView moyan = findViewById(R.id.Moyan);
        moyan.setText(String.valueOf(moyan_total / couf_total));
    }

    public void save(View view) {
        int id = 1;
        for(Module_Only_Exame m : modules_list) {
            if(m instanceof Module_With_TD_TP)
                database.addModuleWithTDTP((Module_With_TD_TP) m, id);
            else {
                if (m instanceof Module_With_TD)
                    database.addModuleWithTD((Module_With_TD) m, id);
                else {
                    if (m instanceof Module_With_TP)
                        database.addModuleWithTP((Module_With_TP) m, id);
                    else
                        database.addModule(m, id);
                }
            }
            Log.d("Mod", "--> " + m);
        }
        Toast.makeText(this, "Save Complete", Toast.LENGTH_LONG).show();
    }
}
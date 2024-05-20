package com.example.calculatorapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView resultTv, solutionTv;
    MaterialButton buttonDivide, buttonMultily, buttonPlus,buttonMinus,buttonEquals;
    MaterialButton buttonC, buttonBrackOpen, buttonBrackClose;
    MaterialButton button0,button1,button2,button3, button4,button5,button6, button7,button8,button9;
    MaterialButton buttonAC, buttonDot;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resultTv = findViewById(R.id.result_tv);
        solutionTv = findViewById(R.id.solution_tv);

        assignId(buttonAC, R.id.button_ac);
        assignId(buttonC, R.id.button_c);
        assignId(buttonBrackClose, R.id.button_close_bracket);
        assignId(buttonBrackOpen, R.id.button_open_bracket);
        assignId(buttonDivide, R.id.button_divide);
        assignId(buttonMinus, R.id.button_minus);
        assignId(buttonMultily, R.id.button_multiply);
        assignId(buttonEquals, R.id.button_equals);
        assignId(buttonPlus, R.id.button_plus);
        assignId(button0, R.id.button_0);
        assignId(button1, R.id.button_1);
        assignId(button2, R.id.button_2);
        assignId(button3, R.id.button_3);
        assignId(button4, R.id.button_4);
        assignId(button5, R.id.button_5);
        assignId(button6, R.id.button_6);
        assignId(button7, R.id.button_7);
        assignId(button8, R.id.button_8);
        assignId(button9, R.id.button_9);
        assignId(buttonDot, R.id.button_dot);
    }
    // ánh xạ
    void assignId(MaterialButton btn, int id) {
        btn = findViewById(id);
        btn.setOnClickListener(this);
    }

    void confirm() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("notification");
        alertDialog.setIcon(R.mipmap.ic_launcher);
        alertDialog.setMessage("Are you sure ? ");

        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        //alertDialog.setPositiveButton()
    }
    @Override
    public void onClick(View view) {
        // bắt sk bấm các nút
        MaterialButton button = (MaterialButton) view;
        String buttonText = button.getText().toString();
        String dataToCalcula = solutionTv.getText().toString();

        if (buttonText.equals("AC")) {
            solutionTv.setText("");
            resultTv.setText("0");
            return;

        }
        if(buttonText.equals("=")) {
            solutionTv.setText(resultTv.getText());
            return;
        }
        if(buttonText.equals("C")) {
            dataToCalcula = dataToCalcula.substring(0, dataToCalcula.length() -1);

        }else {
            dataToCalcula = dataToCalcula + buttonText;
        }
        solutionTv.setText(dataToCalcula);

        String finalResult = getResult(dataToCalcula);
        if(!finalResult.equals("Error")) {
            resultTv.setText(finalResult);
        }
    }
    // Phương thức thực hiện tính toán kết quả sử dụng đánh giá biểu thức bằng JavaScript
    String getResult(String data) {
        try {
            // dùng Rhino để đánh giá biểu thức
            Context context = Context.enter();
            context.setOptimizationLevel(-1);
            Scriptable scriptable = context.initSafeStandardObjects();

            // Đánh giá biểu thức và lấy kết quả dưới dạng chuỗi
            String finalResult = context.evaluateString(scriptable,data, "javascript",1,null).toString();

            // loại bỏ thập phân .0
            if(finalResult.endsWith(".0")) {
                finalResult = finalResult.replace(".0","");
            }
            return finalResult;
        }catch (Exception e) {
            return "Error";
        }
    }
}
package com.example.toolsfinder7;



import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.ai.client.generativeai.GenerativeModel;
import com.google.ai.client.generativeai.java.GenerativeModelFutures;
import com.google.ai.client.generativeai.type.Content;
import com.google.ai.client.generativeai.type.GenerateContentResponse;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;

public class MainActivity4 extends AppCompatActivity {



    private EditText inputEditText;
    private TextView textView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


//        inputEditText = findViewById(R.id.input1);
        inputEditText = findViewById(R.id.input1);
        textView = findViewById(R.id.textView);
    }

    public void buttonCallGeminiAPI(View view){
        String searchText = inputEditText.getText().toString().trim();

        if (!searchText.isEmpty()) {
            GenerativeModel gm = new GenerativeModel(/* modelName */ "gemini-pro",
                    // Access your API key as a Build Configuration variable (see "Set up your API key" above)
                    /* apiKey */ "AIzaSyA-d9vOmOTmooRo9aUYAnMIij0mJc24A0s");
            GenerativeModelFutures model = GenerativeModelFutures.from(gm);
            Content content = new Content.Builder()
                    .addText(searchText) // Use the text from the input box
                    .build();

            ListenableFuture<GenerateContentResponse> response = model.generateContent(content);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                Futures.addCallback(response, new FutureCallback<GenerateContentResponse>() {
                    @Override
                    public void onSuccess(GenerateContentResponse result) {
                        String resultText = result.getText();
                        textView.setText(resultText);
                    }
                    @Override
                    public void onFailure(Throwable t) {
                        textView.setText(t.toString());
                    }
                }, this.getMainExecutor());
            }
        }
    }
}
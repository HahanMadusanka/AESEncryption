package com.example.aesencryption;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText etInputText, etInputPassword;
    TextView tvDecryptedText, tvEncryptedText;
    Button bEncrypt, bDecrypt;

    String outputString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etInputText = findViewById(R.id.et_input_text);
        etInputPassword = findViewById(R.id.et_password);
        tvDecryptedText = findViewById(R.id.tv_decrypted_text);
        tvEncryptedText = findViewById(R.id.tv_encrypted_text);
        bEncrypt = findViewById(R.id.b_encrypt);
        bDecrypt = findViewById(R.id.b_decrypt);


        bDecrypt.setOnClickListener(this);
        bEncrypt.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.b_decrypt) {
            String decrypt;
            try {
                decrypt = decrypt(outputString, etInputPassword.getText().toString());
            } catch (Exception e) {
                Log.e("hashan", "Error --> " + e);
                e.printStackTrace();  // TODO remove
                decrypt = "error";
            }
            tvDecryptedText.setText(decrypt);
        } else if (v.getId() == R.id.b_encrypt) {
            try {
                outputString = encrypt(etInputText.getText().toString(), etInputPassword.getText().toString());
                tvEncryptedText.setText(outputString);
            } catch (Exception e) {
                Log.e("hashan", "Error --> " + e);
                e.printStackTrace();  // TODO remove
            }

        }
    }

    private String decrypt(String outputString, String password) throws Exception {
        SecretKeySpec key = generateKey(password);
        Cipher c = Cipher.getInstance("AES");
        c.init(Cipher.DECRYPT_MODE, key);
        byte[] decodedValue = Base64.decode(outputString, Base64.DEFAULT);
        byte[] decValue = c.doFinal(decodedValue);
        return new String(decValue);
    }

    private String encrypt(String data, String password) throws Exception {
        SecretKeySpec key = generateKey(password);
        Cipher c = Cipher.getInstance("AES");
        c.init(Cipher.ENCRYPT_MODE, key);
        byte[] encVal = c.doFinal(data.getBytes());
        String encryptedValue = Base64.encodeToString(encVal, Base64.DEFAULT);
        return encryptedValue;
    }

    private SecretKeySpec generateKey(String password) throws Exception {
        final MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] bytes = password.getBytes(StandardCharsets.UTF_8);
        digest.update(bytes, 0, bytes.length);
        byte[] key = digest.digest();
        SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");
        return secretKeySpec;
    }
}
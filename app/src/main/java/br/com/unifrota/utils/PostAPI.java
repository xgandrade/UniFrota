package br.com.unifrota.utils;

import android.os.AsyncTask;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

import br.com.unifrota.pojo.Carro;

public class PostAPI extends AsyncTask<Void, Void, Boolean> {

    String jsonInput;

    public PostAPI(Carro carro){
        Gson gson = new Gson();
        jsonInput = gson.toJson(carro);
    }

    @Override
    protected Boolean doInBackground(Void... voids) {

        try {
            StringBuilder resp = new StringBuilder();
            URL url = new URL("http://api.traue.com.br/carros/exec.php");
            HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
            conexao.setRequestMethod("POST");
            conexao.setRequestProperty("Content-type", "application/json");
            conexao.setRequestProperty("Accept", "application/json");
            conexao.setDoOutput(true);
            conexao.setDoInput(true);
            conexao.setConnectTimeout(5000);
            conexao.setReadTimeout(5000);
            conexao.connect();


            final OutputStream outputStream = conexao.getOutputStream();
            final BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            try (OutputStream os = conexao.getOutputStream()){
                    byte[] input = jsonInput.getBytes("UTF-8");
                    os.write(input, 0, input.length);
                } catch (UnsupportedEncodingException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            conexao.connect();

            final InputStream stream = conexao.getInputStream();

            try(BufferedReader br = new BufferedReader(
                    new InputStreamReader(conexao.getInputStream(), "UTF-8"))){
                String resposta = null;
                while ((resposta = br.readLine()) != null){
                    resp.append(resposta.trim());
                }
            }
            conexao.disconnect();
        }
        catch (IOException e){
            return false;
        }
        return true;
    }
}

package camila.davi.isabelly.yasmin.domos.model;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;

import camila.davi.isabelly.yasmin.domos.bd.Anuncio;
import camila.davi.isabelly.yasmin.domos.bd.Aviso;
import camila.davi.isabelly.yasmin.domos.bd.NumDivCondominio;
import camila.davi.isabelly.yasmin.domos.bd.Regimento;
import camila.davi.isabelly.yasmin.domos.bd.Usuario;
import camila.davi.isabelly.yasmin.domos.util.Config;
import camila.davi.isabelly.yasmin.domos.util.HttpRequest;
import camila.davi.isabelly.yasmin.domos.util.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Essa classe concentra todos os métodos de conexão entre a app e o servidor web
 */
public class DomosRepository {

    Context context;
    public DomosRepository(Context context) {
        this.context = context;
    }

    /**
     * Método que cria uma requisição HTTP para registrar um novo usuário junto ao servidor web.
     * @param newLogin o login do novo usuário
     * @param newPassword a senha do novo usuário
     * @return true se o usuário foi cadastrado e false caso contrário
     */
    public boolean register(String cpf, String nome, String email, String senha, String codigoCondominio, String nApto, String divisao) {

        // Cria uma requisição HTTP a adiona o parâmetros que devem ser enviados ao servidor
        HttpRequest httpRequest = new HttpRequest(Config.DOMOS_APP_URL + "registrar.php", "POST", "UTF-8");
        httpRequest.addParam("cpf", cpf);
        httpRequest.addParam("nome", nome);
        httpRequest.addParam("email", email);
        httpRequest.addParam("senha", senha);
        httpRequest.addParam("codigo_condominio", codigoCondominio);
        httpRequest.addParam("numero_apartamento", nApto);
        httpRequest.addParam("divisao", divisao);


        String result = "";
        try {
            // Executa a requisição HTTP. É neste momento que o servidor web é contactado. Ao executar
            // a requisição é aberto um fluxo de dados entre o servidor e a app (InputStream is).
            InputStream is = httpRequest.execute();

            // Obtém a resposta fornecida pelo servidor. O InputStream é convertido em uma String. Essa
            // String é a resposta do servidor web em formato JSON.
            //
            // Em caso de sucesso, será retornada uma String JSON no formato:
            //
            // {"sucesso":1}
            //
            // Em caso de falha, será retornada uma String JSON no formato:
            //
            // {"sucesso":0, "erro":"usuario já existe"}
            result = Util.inputStream2String(is, "UTF-8");

            Log.i("HTTP REGISTER RESULT", result);

            // Fecha a conexão com o servidor web.
            httpRequest.finish();

            // A classe JSONObject recebe como parâmetro do construtor uma String no formato JSON e
            // monta internamente uma estrutura de dados similar ao dicionário em python.
            JSONObject jsonObject = new JSONObject(result);

            // obtem o valor da chave sucesso para verificar se a ação ocorreu da forma esperada ou não.
            int success = jsonObject.getInt("sucesso");

            // Se sucesso igual a 1, significa que o usuário foi registrado com sucesso.
            if(success == 1) {
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("HTTP RESULT", result);
        }
        return false;

    }

    /**
     * Método que cria uma requisição HTTP para autenticar um usuário junto ao servidor web.
     * @param login o login do usuário
     * @param password a senha do usuário
     * @return true se o usuário  foi autenticado, false caso contrário
     */
    public boolean login(String login, String password) {

        // Cria uma requisição HTTP a adiona o parâmetros que devem ser enviados ao servidor
        HttpRequest httpRequest = new HttpRequest(Config.DOMOS_APP_URL + "login.php", "POST", "UTF-8");
        httpRequest.setBasicAuth(login, password);

        String result = "";
        try {
            // Executa a requisição HTTP. É neste momento que o servidor web é contactado. Ao executar
            // a requisição é aberto um fluxo de dados entre o servidor e a app (InputStream is).
            InputStream is = httpRequest.execute();

            // Obtém a resposta fornecida pelo servidor. O InputStream é convertido em uma String. Essa
            // String é a resposta do servidor web em formato JSON.
            //
            // Em caso de sucesso, será retornada uma String JSON no formato:
            //
            // {"sucesso":1}
            //
            // Em caso de falha, será retornada uma String JSON no formato:
            //
            // {"sucesso":0, "erro":"usuario ou senha não confere"}
            result = Util.inputStream2String(is, "UTF-8");

            // Fecha a conexão com o servidor web.
            httpRequest.finish();

            Log.i("HTTP LOGIN RESULT", result);

            // A classe JSONObject recebe como parâmetro do construtor uma String no formato JSON e
            // monta internamente uma estrutura de dados similar ao dicionário em python.
            JSONObject jsonObject = new JSONObject(result);

            // obtem o valor da chave sucesso para verificar se a ação ocorreu da forma esperada ou não.
            int success = jsonObject.getInt("sucesso");

            // Se sucesso igual a 1, significa que o usuário foi autenticado com sucesso.
            if(success == 1) {
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("HTTP RESULT", result);
        }
        return false;
    }

    /**
     * Método que cria uma requisição HTTP para cadastrar um novo produto junto ao servidor web.
     * @param name nome do produto
     * @param price preço do produto
     * @param description descrição do produto
     * @param imgLocation endereço do arquivo que contém a imagem do produto
     * @return true se o produto foi cadastrado junto ao servidor, false caso contrário
     */
    public boolean criarAnuncio(String titulo, String tag, String descricao) {

        // Para cadastrar um produto, é preciso estar logado. Então primeiro otemos o login e senha
        // salvos na app.
        String login = Config.getLogin(context);
        String password = Config.getPassword(context);

        // Cria uma requisição HTTP a adiona o parâmetros que devem ser enviados ao servidor
        HttpRequest httpRequest = new HttpRequest(Config.DOMOS_APP_URL + "criar_anuncio.php", "POST", "UTF-8");
        httpRequest.addParam("titulo", titulo);
        httpRequest.addParam("tag", tag);
        httpRequest.addParam("descricao", descricao);
        httpRequest.addParam("cpf", login);

        // httpRequest.addFile("img", new File(imgLocation));

        // Para esta ação, é preciso estar logado. Então na requisição HTTP setamos o login e senha do
        // usuário. Ao executar a requisição, o login e senha do usuário serão enviados ao servidor web,
        // o qual verificará se o login e senha batem com aquilo que está no BD. Somente depois dessa
        // verificação de autenticação é que o servidor web irá realizar esta ação.
        httpRequest.setBasicAuth(login, password);



        String result = "";
        try {
            // Executa a requisição HTTP. É neste momento que o servidor web é contactado. Ao executar
            // a requisição é aberto um fluxo de dados entre o servidor e a app (InputStream is).
            InputStream is = httpRequest.execute();

            // Obtém a resposta fornecida pelo servidor. O InputStream é convertido em uma String. Essa
            // String é a resposta do servidor web em formato JSON.
            //
            // Em caso de sucesso, será retornada uma String JSON no formato:
            //
            // {"sucesso":1}
            //
            // Em caso de falha, será retornada uma String JSON no formato:
            //
            // {"sucesso":0,"erro":"Erro ao criar produto"}
            result = Util.inputStream2String(is, "UTF-8");

            // Fecha a conexão com o servidor web.
            httpRequest.finish();

            Log.i("HTTP ADD PRODUCT RESULT", result);

            // A classe JSONObject recebe como parâmetro do construtor uma String no formato JSON e
            // monta internamente uma estrutura de dados similar ao dicionário em python.
            JSONObject jsonObject = new JSONObject(result);

            // obtem o valor da chave sucesso para verificar se a ação ocorreu da forma esperada ou não.
            int success = jsonObject.getInt("sucesso");

            // Se sucesso igual a 1, significa que o produto foi adicionado com sucesso.
            if(success == 1) {
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("HTTP RESULT", result);
        }
        return false;
    }

    public boolean criarAviso(String titulo, String importancia, String descricao) {

        // Para cadastrar um produto, é preciso estar logado. Então primeiro otemos o login e senha
        // salvos na app.
        String login = Config.getLogin(context);
        String password = Config.getPassword(context);

        // Cria uma requisição HTTP a adiona o parâmetros que devem ser enviados ao servidor
        HttpRequest httpRequest = new HttpRequest(Config.DOMOS_APP_URL + "criar_aviso.php", "POST", "UTF-8");
        httpRequest.addParam("titulo", titulo);
        httpRequest.addParam("importancia", importancia);
        httpRequest.addParam("descricao", descricao);
        httpRequest.addParam("cpf", login);

        // Para esta ação, é preciso estar logado. Então na requisição HTTP setamos o login e senha do
        // usuário. Ao executar a requisição, o login e senha do usuário serão enviados ao servidor web,
        // o qual verificará se o login e senha batem com aquilo que está no BD. Somente depois dessa
        // verificação de autenticação é que o servidor web irá realizar esta ação.
        httpRequest.setBasicAuth(login, password);

        String result = "";
        try {
            // Executa a requisição HTTP. É neste momento que o servidor web é contactado. Ao executar
            // a requisição é aberto um fluxo de dados entre o servidor e a app (InputStream is).
            InputStream is = httpRequest.execute();

            // Obtém a resposta fornecida pelo servidor. O InputStream é convertido em uma String. Essa
            // String é a resposta do servidor web em formato JSON.
            //
            // Em caso de sucesso, será retornada uma String JSON no formato:
            //
            // {"sucesso":1}
            //
            // Em caso de falha, será retornada uma String JSON no formato:
            //
            // {"sucesso":0,"erro":"Erro ao criar produto"}
            result = Util.inputStream2String(is, "UTF-8");

            // Fecha a conexão com o servidor web.
            httpRequest.finish();

            Log.i("HTTP ADD PRODUCT RESULT", result);

            // A classe JSONObject recebe como parâmetro do construtor uma String no formato JSON e
            // monta internamente uma estrutura de dados similar ao dicionário em python.
            JSONObject jsonObject = new JSONObject(result);

            // obtem o valor da chave sucesso para verificar se a ação ocorreu da forma esperada ou não.
            int success = jsonObject.getInt("sucesso");

            // Se sucesso igual a 1, significa que o produto foi adicionado com sucesso.
            if(success == 1) {
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("HTTP RESULT", result);
        }
        return false;
    }

    /**
     * Método que cria uma requisição HTTP para obter uma página/bloco de produtos junto ao servidor web.
     * @param limit a quantidade de produtos a serem obtidos
     * @param offSet a posição a partir da qual a página de produtos deve começar
     * @return lista de produtos
     */
    public NumDivCondominio loadNumDiv(String codigo_condominio) {

        // Cria uma requisição HTTP a adiona o parâmetros que devem ser enviados ao servidor
        HttpRequest httpRequest = new HttpRequest(Config.DOMOS_APP_URL +"pegar_num_div.php", "GET", "UTF-8");
        httpRequest.addParam("codigo_condominio", codigo_condominio);

        String result = "";
        try {
            // Executa a requisição HTTP. É neste momento que o servidor web é contactado. Ao executar
            // a requisição é aberto um fluxo de dados entre o servidor e a app (InputStream is).
            InputStream is = httpRequest.execute();

            result = Util.inputStream2String(is, "UTF-8");

            // Fecha a conexão com o servidor web.
            httpRequest.finish();

            Log.i("HTTP PRODUCTS RESULT", result);

            // A classe JSONObject recebe como parâmetro do construtor uma String no formato JSON e
            // monta internamente uma estrutura de dados similar ao dicionário em python.
            JSONObject jsonObject = new JSONObject(result);

            // obtem o valor da chave sucesso para verificar se a ação ocorreu da forma esperada ou não.
            int success = jsonObject.getInt("sucesso");

            // Se sucesso igual a 1, os produtos são obtidos da String JSON e adicionados à lista de
            // produtos a ser retornada como resultado.
            if(success == 1) {

                List<String> numeros = new ArrayList<>();

                // A chave produtos é um array de objetos do tipo json (JSONArray), onde cada um desses representa
                // um produto
                JSONArray jsonArrayNums = jsonObject.getJSONArray("numeros");

                // Cada elemento do JSONArray é um JSONObject que guarda os dados de um produto
                for(int i = 0; i < jsonArrayNums.length(); i++) {

                    // Obtemos o JSONObject referente a um produto
                    JSONObject jNumero = jsonArrayNums.getJSONObject(i);

                    // Obtemos os dados de um produtos via JSONObject
                    String numero = jNumero.getString("num");

                    numeros.add(numero);
                }

                List<String> divs = new ArrayList<>();

                // A chave produtos é um array de objetos do tipo json (JSONArray), onde cada um desses representa
                // um produto
                JSONArray jsonArrayDivs = jsonObject.getJSONArray("divs");

                // Cada elemento do JSONArray é um JSONObject que guarda os dados de um produto
                for(int i = 0; i < jsonArrayDivs.length(); i++) {

                    // Obtemos o JSONObject referente a um produto
                    JSONObject jDiv = jsonArrayDivs.getJSONObject(i);

                    // Obtemos os dados de um produtos via JSONObject
                    String div = jDiv.getString("div");

                    divs.add(div);
                }

                return new NumDivCondominio(numeros, divs);

            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("HTTP RESULT", result);
        }

        return null;
    }

    public List<String> loadImportancia() {

        // Cria uma requisição HTTP a adiona o parâmetros que devem ser enviados ao servidor
        HttpRequest httpRequest = new HttpRequest(Config.DOMOS_APP_URL +"pegar_importancia.php", "GET", "UTF-8");

        String result = "";
        try {
            // Executa a requisição HTTP. É neste momento que o servidor web é contactado. Ao executar
            // a requisição é aberto um fluxo de dados entre o servidor e a app (InputStream is).
            InputStream is = httpRequest.execute();

            result = Util.inputStream2String(is, "UTF-8");

            // Fecha a conexão com o servidor web.
            httpRequest.finish();

            Log.i("HTTP PRODUCTS RESULT", result);

            // A classe JSONObject recebe como parâmetro do construtor uma String no formato JSON e
            // monta internamente uma estrutura de dados similar ao dicionário em python.
            JSONObject jsonObject = new JSONObject(result);

            // obtem o valor da chave sucesso para verificar se a ação ocorreu da forma esperada ou não.
            int success = jsonObject.getInt("sucesso");

            // Se sucesso igual a 1, os produtos são obtidos da String JSON e adicionados à lista de
            // produtos a ser retornada como resultado.
            if(success == 1) {

                List<String> importancias = new ArrayList<>();

                // A chave produtos é um array de objetos do tipo json (JSONArray), onde cada um desses representa
                // um produto
                JSONArray jsonImportancias = jsonObject.getJSONArray("importancias");

                // Cada elemento do JSONArray é um JSONObject que guarda os dados de um produto
                for(int i = 0; i < jsonImportancias.length(); i++) {

                    // Obtemos o JSONObject referente a um produto
                    JSONObject jImportancia = jsonImportancias.getJSONObject(i);

                    // Obtemos os dados de um produtos via JSONObject
                    String importancia = jImportancia.getString("importancia");

                    importancias.add(importancia);
                }

                return importancias;

            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("HTTP RESULT", result);
        }

        return null;
    }

    public List<String> loadTags() {

        // Cria uma requisição HTTP a adiona o parâmetros que devem ser enviados ao servidor
        HttpRequest httpRequest = new HttpRequest(Config.DOMOS_APP_URL +"pegar_tag.php", "GET", "UTF-8");

        String result = "";
        try {
            // Executa a requisição HTTP. É neste momento que o servidor web é contactado. Ao executar
            // a requisição é aberto um fluxo de dados entre o servidor e a app (InputStream is).
            InputStream is = httpRequest.execute();

            result = Util.inputStream2String(is, "UTF-8");

            // Fecha a conexão com o servidor web.
            httpRequest.finish();

            Log.i("HTTP PRODUCTS RESULT", result);

            // A classe JSONObject recebe como parâmetro do construtor uma String no formato JSON e
            // monta internamente uma estrutura de dados similar ao dicionário em python.
            JSONObject jsonObject = new JSONObject(result);

            // obtem o valor da chave sucesso para verificar se a ação ocorreu da forma esperada ou não.
            int success = jsonObject.getInt("sucesso");

            // Se sucesso igual a 1, os produtos são obtidos da String JSON e adicionados à lista de
            // produtos a ser retornada como resultado.
            if(success == 1) {

                List<String> tags = new ArrayList<>();

                // A chave produtos é um array de objetos do tipo json (JSONArray), onde cada um desses representa
                // um produto
                JSONArray jsonTags = jsonObject.getJSONArray("tags");

                // Cada elemento do JSONArray é um JSONObject que guarda os dados de um produto
                for(int i = 0; i < jsonTags.length(); i++) {

                    // Obtemos o JSONObject referente a um produto
                    JSONObject jTag = jsonTags.getJSONObject(i);

                    // Obtemos os dados de um produtos via JSONObject
                    String tag = jTag.getString("tag");

                    tags.add(tag);
                }

                return tags;

            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("HTTP RESULT", result);
        }

        return null;
    }

    public List<Aviso> loadAvisos(Integer limit, Integer offSet, String importancia) {

        // cria a lista de produtos incicialmente vazia, que será retornada como resultado
        List<Aviso> listaAvisos = new ArrayList<>();

        // Para obter a lista de produtos é preciso estar logado. Então primeiro otemos o login e senha
        // salvos na app.
        String login = Config.getLogin(context);
        String password = Config.getPassword(context);

        // Cria uma requisição HTTP a adiona o parâmetros que devem ser enviados ao servidor
        HttpRequest httpRequest = new HttpRequest(Config.DOMOS_APP_URL +"pegar_avisos.php", "GET", "UTF-8");
        httpRequest.addParam("limit", limit.toString());
        httpRequest.addParam("offset", offSet.toString());
        httpRequest.addParam("cpf", login);
        httpRequest.addParam("importancia", importancia);


        // Para esta ação, é preciso estar logado. Então na requisição HTTP setamos o login e senha do
        // usuário. Ao executar a requisição, o login e senha do usuário serão enviados ao servidor web,
        // o qual verificará se o login e senha batem com aquilo que está no BD. Somente depois dessa
        // verificação de autenticação é que o servidor web irá realizar esta ação.
        httpRequest.setBasicAuth(login, password);

        String result = "";
        try {
            // Executa a requisição HTTP. É neste momento que o servidor web é contactado. Ao executar
            // a requisição é aberto um fluxo de dados entre o servidor e a app (InputStream is).
            InputStream is = httpRequest.execute();

            // Obtém a resposta fornecida pelo servidor. O InputStream é convertido em uma String. Essa
            // String é a resposta do servidor web em formato JSON.
            //
            // Em caso de sucesso, será retornada uma String JSON no formato:
            //
            // {"sucesso":1,
            //  "produtos":[
            //          {"id":"7", "nome":"produto 1", "preco":"10.00", "img":"www.imgur.com/img1.jpg"},
            //          {"id":"8", "nome":"produto 2", "preco":"20.00", "img":"www.imgur.com/img2.jpg"}
            //       ]
            // }
            //
            // Em caso de falha, será retornada uma String JSON no formato:
            //
            // {"sucesso":0,"erro":"Erro ao obter produtos"}
            result = Util.inputStream2String(is, "UTF-8");

            // Fecha a conexão com o servidor web.
            httpRequest.finish();

            Log.i("HTTP PRODUCTS RESULT", result);

            // A classe JSONObject recebe como parâmetro do construtor uma String no formato JSON e
            // monta internamente uma estrutura de dados similar ao dicionário em python.
            JSONObject jsonObject = new JSONObject(result);

            // obtem o valor da chave sucesso para verificar se a ação ocorreu da forma esperada ou não.
            int success = jsonObject.getInt("sucesso");

            // Se sucesso igual a 1, os produtos são obtidos da String JSON e adicionados à lista de
            // produtos a ser retornada como resultado.
            if(success == 1) {

                // A chave produtos é um array de objetos do tipo json (JSONArray), onde cada um desses representa
                // um produto
                JSONArray jsonArray = jsonObject.getJSONArray("avisos");

                // Cada elemento do JSONArray é um JSONObject que guarda os dados de um produto
                for(int i = 0; i < jsonArray.length(); i++) {

                    // Obtemos o JSONObject referente a um produto
                    JSONObject jAviso = jsonArray.getJSONObject(i);

                    // Obtemos os dados de um produtos via JSONObject
                    String codigoPostagem = jAviso.getString("codigo_postagem");
                    String dataHoraPostagem = jAviso.getString("data_hora_postagem");
                    String descricao = jAviso.getString("descricao");
                    String titulo = jAviso.getString("titulo");
                    String usuario = jAviso.getString("cpf");
                    int importancia1 = jAviso.getInt("importancia");

                    // Criamo um objeto do tipo Product para guardar esses dados
                    Aviso aviso = new Aviso(codigoPostagem, importancia1, dataHoraPostagem, descricao, titulo, usuario);

                    // Adicionamos o objeto product na lista de produtos
                    listaAvisos.add(aviso);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("HTTP RESULT", result);
        }

        return listaAvisos;
    }

    public List<Anuncio> loadAnuncios(Integer limit, Integer offSet, String tag) {

        // cria a lista de produtos incicialmente vazia, que será retornada como resultado
        List<Anuncio> listaAnuncios = new ArrayList<>();

        // Para obter a lista de produtos é preciso estar logado. Então primeiro otemos o login e senha
        // salvos na app.
        String login = Config.getLogin(context);
        String password = Config.getPassword(context);

        // Cria uma requisição HTTP a adiona o parâmetros que devem ser enviados ao servidor
        HttpRequest httpRequest = new HttpRequest(Config.DOMOS_APP_URL +"pegar_anuncios.php", "GET", "UTF-8");
        httpRequest.addParam("limit", limit.toString());
        httpRequest.addParam("offset", offSet.toString());
        httpRequest.addParam("cpf", login);
        httpRequest.addParam("tag", tag);

        // Para esta ação, é preciso estar logado. Então na requisição HTTP setamos o login e senha do
        // usuário. Ao executar a requisição, o login e senha do usuário serão enviados ao servidor web,
        // o qual verificará se o login e senha batem com aquilo que está no BD. Somente depois dessa
        // verificação de autenticação é que o servidor web irá realizar esta ação.
        httpRequest.setBasicAuth(login, password);

        String result = "";
        try {
            // Executa a requisição HTTP. É neste momento que o servidor web é contactado. Ao executar
            // a requisição é aberto um fluxo de dados entre o servidor e a app (InputStream is).
            InputStream is = httpRequest.execute();

            // Obtém a resposta fornecida pelo servidor. O InputStream é convertido em uma String. Essa
            // String é a resposta do servidor web em formato JSON.
            //
            // Em caso de sucesso, será retornada uma String JSON no formato:
            //
            // {"sucesso":1,
            //  "produtos":[
            //          {"id":"7", "nome":"produto 1", "preco":"10.00", "img":"www.imgur.com/img1.jpg"},
            //          {"id":"8", "nome":"produto 2", "preco":"20.00", "img":"www.imgur.com/img2.jpg"}
            //       ]
            // }
            //
            // Em caso de falha, será retornada uma String JSON no formato:
            //
            // {"sucesso":0,"erro":"Erro ao obter produtos"}
            result = Util.inputStream2String(is, "UTF-8");

            // Fecha a conexão com o servidor web.
            httpRequest.finish();

            Log.i("HTTP PRODUCTS RESULT", result);

            // A classe JSONObject recebe como parâmetro do construtor uma String no formato JSON e
            // monta internamente uma estrutura de dados similar ao dicionário em python.
            JSONObject jsonObject = new JSONObject(result);

            // obtem o valor da chave sucesso para verificar se a ação ocorreu da forma esperada ou não.
            int success = jsonObject.getInt("sucesso");

            // Se sucesso igual a 1, os produtos são obtidos da String JSON e adicionados à lista de
            // produtos a ser retornada como resultado.
            if(success == 1) {

                // A chave produtos é um array de objetos do tipo json (JSONArray), onde cada um desses representa
                // um produto
                JSONArray jsonArray = jsonObject.getJSONArray("anuncios");

                // Cada elemento do JSONArray é um JSONObject que guarda os dados de um produto
                for(int i = 0; i < jsonArray.length(); i++) {

                    // Obtemos o JSONObject referente a um produto
                    JSONObject jAnuncio = jsonArray.getJSONObject(i);

                    // Obtemos os dados de um produtos via JSONObject
                    String codigoPostagem = jAnuncio.getString("codigo_postagem");
                    String dataHoraPostagem = jAnuncio.getString("data_hora_postagem");
                    String descricao = jAnuncio.getString("descricao");
                    String titulo = jAnuncio.getString("titulo");
                    String usuario = jAnuncio.getString("nome");
                    int tag1 = jAnuncio.getInt("tag");
                    String num = jAnuncio.getString("num_moradia");
                    String divisao = jAnuncio.getString("divisao");

                    // Criamo um objeto do tipo Product para guardar esses dados
                    Anuncio anuncio = new Anuncio(codigoPostagem, dataHoraPostagem, descricao, titulo, usuario, null, tag1, num, divisao);

                    // Adicionamos o objeto product na lista de produtos
                    listaAnuncios.add(anuncio);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("HTTP RESULT", result);
        }

        return listaAnuncios;
    }

    public List<Anuncio> loadMeusAnuncios(Integer limit, Integer offSet) {

        // cria a lista de produtos incicialmente vazia, que será retornada como resultado
        List<Anuncio> listaAnuncios = new ArrayList<>();

        // Para obter a lista de produtos é preciso estar logado. Então primeiro otemos o login e senha
        // salvos na app.
        String login = Config.getLogin(context);
        String password = Config.getPassword(context);

        // Cria uma requisição HTTP a adiona o parâmetros que devem ser enviados ao servidor
        HttpRequest httpRequest = new HttpRequest(Config.DOMOS_APP_URL +"pegar_meus_anuncios.php", "GET", "UTF-8");
        httpRequest.addParam("limit", limit.toString());
        httpRequest.addParam("offset", offSet.toString());
        httpRequest.addParam("cpf", login);

        // Para esta ação, é preciso estar logado. Então na requisição HTTP setamos o login e senha do
        // usuário. Ao executar a requisição, o login e senha do usuário serão enviados ao servidor web,
        // o qual verificará se o login e senha batem com aquilo que está no BD. Somente depois dessa
        // verificação de autenticação é que o servidor web irá realizar esta ação.
        httpRequest.setBasicAuth(login, password);

        String result = "";
        try {
            // Executa a requisição HTTP. É neste momento que o servidor web é contactado. Ao executar
            // a requisição é aberto um fluxo de dados entre o servidor e a app (InputStream is).
            InputStream is = httpRequest.execute();

            // Obtém a resposta fornecida pelo servidor. O InputStream é convertido em uma String. Essa
            // String é a resposta do servidor web em formato JSON.
            //
            // Em caso de sucesso, será retornada uma String JSON no formato:
            //
            // {"sucesso":1,
            //  "produtos":[
            //          {"id":"7", "nome":"produto 1", "preco":"10.00", "img":"www.imgur.com/img1.jpg"},
            //          {"id":"8", "nome":"produto 2", "preco":"20.00", "img":"www.imgur.com/img2.jpg"}
            //       ]
            // }
            //
            // Em caso de falha, será retornada uma String JSON no formato:
            //
            // {"sucesso":0,"erro":"Erro ao obter produtos"}
            result = Util.inputStream2String(is, "UTF-8");

            // Fecha a conexão com o servidor web.
            httpRequest.finish();

            Log.i("HTTP PRODUCTS RESULT", result);

            // A classe JSONObject recebe como parâmetro do construtor uma String no formato JSON e
            // monta internamente uma estrutura de dados similar ao dicionário em python.
            JSONObject jsonObject = new JSONObject(result);

            // obtem o valor da chave sucesso para verificar se a ação ocorreu da forma esperada ou não.
            int success = jsonObject.getInt("sucesso");

            // Se sucesso igual a 1, os produtos são obtidos da String JSON e adicionados à lista de
            // produtos a ser retornada como resultado.
            if(success == 1) {

                // A chave produtos é um array de objetos do tipo json (JSONArray), onde cada um desses representa
                // um produto
                JSONArray jsonArray = jsonObject.getJSONArray("anuncios");

                // Cada elemento do JSONArray é um JSONObject que guarda os dados de um produto
                for(int i = 0; i < jsonArray.length(); i++) {

                    // Obtemos o JSONObject referente a um produto
                    JSONObject jAnuncio = jsonArray.getJSONObject(i);

                    // Obtemos os dados de um produtos via JSONObject
                    String codigoPostagem = jAnuncio.getString("codigo_postagem");
                    String dataHoraPostagem = jAnuncio.getString("data_hora_postagem");
                    String descricao = jAnuncio.getString("descricao");
                    String titulo = jAnuncio.getString("titulo");
                    String usuario = jAnuncio.getString("nome");
                    int tag1 = jAnuncio.getInt("tag");
                    String num = jAnuncio.getString("num_moradia");
                    String divisao = jAnuncio.getString("divisao");

                    // Criamo um objeto do tipo Product para guardar esses dados
                    Anuncio anuncio = new Anuncio(codigoPostagem, dataHoraPostagem, descricao, titulo, usuario, null, tag1, num, divisao);

                    // Adicionamos o objeto product na lista de produtos
                    listaAnuncios.add(anuncio);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("HTTP RESULT", result);
        }

        return listaAnuncios;
    }

    public Usuario loadPerfil() {

        // cria a lista de produtos incicialmente vazia, que será retornada como resultado


        // Para obter a lista de produtos é preciso estar logado. Então primeiro otemos o login e senha
        // salvos na app.
        String login = Config.getLogin(context);
        String password = Config.getPassword(context);

        // Cria uma requisição HTTP a adiona o parâmetros que devem ser enviados ao servidor
        HttpRequest httpRequest = new HttpRequest(Config.DOMOS_APP_URL + "pegar_perfil.php", "GET", "UTF-8");
        httpRequest.addParam("cpf", login);

        // Para esta ação, é preciso estar logado. Então na requisição HTTP setamos o login e senha do
        // usuário. Ao executar a requisição, o login e senha do usuário serão enviados ao servidor web,
        // o qual verificará se o login e senha batem com aquilo que está no BD. Somente depois dessa
        // verificação de autenticação é que o servidor web irá realizar esta ação.
        httpRequest.setBasicAuth(login, password);

        String result = "";
        Usuario usuario = null;
        try {
            // Executa a requisição HTTP. É neste momento que o servidor web é contactado. Ao executar
            // a requisição é aberto um fluxo de dados entre o servidor e a app (InputStream is).
            InputStream is = httpRequest.execute();

            // Obtém a resposta fornecida pelo servidor. O InputStream é convertido em uma String. Essa
            // String é a resposta do servidor web em formato JSON.
            //
            // Em caso de sucesso, será retornada uma String JSON no formato:
            //
            // {"sucesso":1,
            //  "produtos":[
            //          {"id":"7", "nome":"produto 1", "preco":"10.00", "img":"www.imgur.com/img1.jpg"},
            //          {"id":"8", "nome":"produto 2", "preco":"20.00", "img":"www.imgur.com/img2.jpg"}
            //       ]
            // }
            //
            // Em caso de falha, será retornada uma String JSON no formato:
            //
            // {"sucesso":0,"erro":"Erro ao obter produtos"}
            result = Util.inputStream2String(is, "UTF-8");

            // Fecha a conexão com o servidor web.
            httpRequest.finish();

            Log.i("HTTP PRODUCTS RESULT", result);

            // A classe JSONObject recebe como parâmetro do construtor uma String no formato JSON e
            // monta internamente uma estrutura de dados similar ao dicionário em python.
            JSONObject jsonObject = new JSONObject(result);

            // obtem o valor da chave sucesso para verificar se a ação ocorreu da forma esperada ou não.
            int success = jsonObject.getInt("sucesso");

            // Se sucesso igual a 1, os produtos são obtidos da String JSON e adicionados à lista de
            // produtos a ser retornada como resultado.
            if (success == 1) {

                // A chave produtos é um array de objetos do tipo json (JSONArray), onde cada um desses representa
                // um produto

                // Cada elemento do JSONArray é um JSONObject que guarda os dados de um produto

                String nome = jsonObject.getString("nome");
                String email = jsonObject.getString("email");
                String cpf = jsonObject.getString("cpf");
                String num_moradia = jsonObject.getString("num_moradia");
                String divisao = jsonObject.getString("divisao");
                // img

                usuario = new Usuario(cpf, nome, email, num_moradia, divisao);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("HTTP RESULT", result);
        }

        return usuario;
    }
    public Regimento loadRegimento() {

        // cria a lista de produtos incicialmente vazia, que será retornada como resultado


        // Para obter a lista de produtos é preciso estar logado. Então primeiro otemos o login e senha
        // salvos na app.
        String login = Config.getLogin(context);
        String password = Config.getPassword(context);

        // Cria uma requisição HTTP a adiona o parâmetros que devem ser enviados ao servidor
        HttpRequest httpRequest = new HttpRequest(Config.DOMOS_APP_URL + "pegar_regimento.php", "GET", "UTF-8");
        httpRequest.addParam("cpf", login);

        // Para esta ação, é preciso estar logado. Então na requisição HTTP setamos o login e senha do
        // usuário. Ao executar a requisição, o login e senha do usuário serão enviados ao servidor web,
        // o qual verificará se o login e senha batem com aquilo que está no BD. Somente depois dessa
        // verificação de autenticação é que o servidor web irá realizar esta ação.
        httpRequest.setBasicAuth(login, password);

        String result = "";
        Regimento regimento = null;
        try {
            // Executa a requisição HTTP. É neste momento que o servidor web é contactado. Ao executar
            // a requisição é aberto um fluxo de dados entre o servidor e a app (InputStream is).
            InputStream is = httpRequest.execute();
            result = Util.inputStream2String(is, "UTF-8");

            httpRequest.finish();

            Log.i("HTTP PRODUCTS RESULT", result);

            JSONObject jsonObject = new JSONObject(result);

            int success = jsonObject.getInt("sucesso");

            if (success == 1) {

                String regimento1 = jsonObject.getString("regimento");

                regimento = new Regimento(regimento1);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("HTTP RESULT", result);
        }

        return regimento;
    }


}

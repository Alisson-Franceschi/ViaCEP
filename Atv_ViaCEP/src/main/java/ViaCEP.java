import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;

public class ViaCEP {

    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("consomeCepPU");

    public static void main(String[] args) {
        String cep = JOptionPane.showInputDialog("Digite o CEP:");

        Endereco endereco = consultaBancoLocal(cep);

        if (endereco != null) {
            JOptionPane.showMessageDialog(null, "O CEP: " + endereco.getCep() + " já existe no banco\n" +
                    "Informações:\n" +
                    "Logradouro: " + endereco.getLogradouro() + "\n" +
                    "Bairro: " + endereco.getBairro() + "\n" +
                    "Localidade: " + endereco.getLocalidade() + "\n" +
                    "UF: " + endereco.getUf());
        } else {
            endereco = consultaViaCEP(cep);

            if (endereco != null) {
                JOptionPane.showMessageDialog(null, "CEP '" + endereco.getCep() + "' foi encontrado na API \n" +
                        "Informações:\n" +
                        "Logradouro: " + endereco.getLogradouro() + "\n" +
                        "Bairro: " + endereco.getBairro() + "\n" +
                        "Localidade: " + endereco.getLocalidade() + "\n" +
                        "UF: " + endereco.getUf());

                salvar(endereco);
                JOptionPane.showMessageDialog(null, "Dados armazenados para futuras consultas.");
            } else {
                JOptionPane.showMessageDialog(null, "Não foi possível obter informações para o CEP informado.");
            }
        }
    }


    public static Endereco consultaBancoLocal(String cep) {
        EntityManager em = emf.createEntityManager();
        try {
            String cepSemHifen = cep.replace("-", "");


            Endereco endereco = (Endereco) em.createQuery("FROM Endereco WHERE REPLACE(cep, '-', '') = :cep")
                    .setParameter("cep", cepSemHifen)
                    .getSingleResult();

            return endereco;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            em.close();
        }
    }

    public static void salvar(Endereco endereco) {
        endereco.setDataConsulta(LocalDateTime.now());

        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(endereco);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public static Endereco consultaViaCEP(String cep) {
        try {
            URL url = new URL("https://viacep.com.br/ws/" + cep + "/json/");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
                String inputLine;
                StringBuilder response = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }

                ObjectMapper objectMapper = new ObjectMapper();
                return objectMapper.readValue(response.toString(), Endereco.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}

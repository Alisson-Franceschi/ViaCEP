
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    String cep;
    String logradouro;
    String bairro;
    String localidade;
    String uf;
    String ibge;
    String complemento;

    @Column(name = "data_consulta")
    private LocalDateTime dataConsulta;
}

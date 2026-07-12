package repository;

import com.felipereis.quotewatch.producer.model.Cotacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CotacaoRepository extends JpaRepository<Cotacao, Long> {

    Optional<Cotacao> findFirstByMoedaOrderByDataCotacaoDesc(String moeda);

    List<Cotacao> findByMoedaOrderByDataCotacaoDesc(String moeda);
}
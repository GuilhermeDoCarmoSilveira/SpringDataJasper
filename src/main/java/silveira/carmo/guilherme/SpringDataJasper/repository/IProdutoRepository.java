package silveira.carmo.guilherme.SpringDataJasper.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import silveira.carmo.guilherme.SpringDataJasper.model.Produto;

public interface IProdutoRepository extends JpaRepository<Produto, Integer>{
	Integer fn_QtdProdutosAbaixoDoParam(int valor);
}

package silveira.carmo.guilherme.SpringDataJasper.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.NamedNativeQueries;
import jakarta.persistence.NamedNativeQuery;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Produto")
@NamedNativeQuery(
		name = "Produto.fn_QtdProdutosAbaixoDoParam",
		query = "Select dbo.fn_QtdProdutosAbaixoDoParam(?1)",
		resultClass = Integer.class
)
public class Produto {
	
	@Id
	@Column(name = "codigo" ,nullable = false)
	private int codigo;
	
	@Column(name = "nome" , length = 50, nullable = false)
	private String nome;
	
	@Column(name = "valorUnitario" ,nullable = false)
	private double valorUnitario ;
	
	@Column(name = "qtdEstoque" ,nullable = false)
	private int qtdEstoque ;

}

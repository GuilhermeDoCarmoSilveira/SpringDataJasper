package silveira.carmo.guilherme.SpringDataJasper.controller;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.hibernate.boot.archive.internal.ByteArrayInputStreamAccess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.util.JRLoader;
import silveira.carmo.guilherme.SpringDataJasper.model.Produto;
import silveira.carmo.guilherme.SpringDataJasper.repository.IProdutoRepository;

@Controller
public class ProdutoController {

	@Autowired
	IProdutoRepository pRep;

	@Autowired
	DataSource ds;
	
	@RequestMapping(name = "produto", value = "/produto", method = RequestMethod.GET)
	public ModelAndView produtoGet(@RequestParam Map<String, String> param, ModelMap model) {
		return new ModelAndView("produto");
	}

	@RequestMapping(name = "produto", value = "/produto", method = RequestMethod.POST)
	public ModelAndView produtoPost(@RequestParam Map<String, String> param, ModelMap model) {

		String cmd = param.get("botao");
		String nome = param.get("nome");
		String valorUnitario = param.get("valorUnitario");
		String qtdEstoque = param.get("qtdEstoque");
		String codigo = param.get("codigo");

		String erro = "";
		String saida = "";

		Produto produto = new Produto();

		if (cmd.contains("Buscar")) {
			if (codigo.trim().isEmpty()) {
				erro = "Por favor, informe o codgigo";
			}
		} else if (cmd.contains("Cadastrar") || cmd.contains("Alterar")) {
			if (nome.trim().isEmpty() || codigo.trim().isEmpty() || valorUnitario.trim().isEmpty()
					|| qtdEstoque.trim().isEmpty()) {

				erro = "Por favor, preencha todos os campos obrigatorios.";
			}
		}

		if (!erro.isEmpty()) {
			model.addAttribute("erro", erro);
			return new ModelAndView("alunoCadastrar");
		}

		if ((cmd.contains("Cadastrar") || cmd.contains("Alterar"))) {

			produto.setCodigo(Integer.parseInt(codigo));
			produto.setNome(nome);
			produto.setQtdEstoque(Integer.parseInt(qtdEstoque));
			produto.setValorUnitario(Double.parseDouble(valorUnitario));
		}

		if(cmd.equalsIgnoreCase("Buscar")) {
			produto.setCodigo(Integer.parseInt(codigo));
		}
		
		
		List<Produto> produtos = new ArrayList<>();

		if (cmd.equalsIgnoreCase("Cadastrar") || cmd.contains("Alterar")) {
			pRep.save(produto);
			produto = null;
		}
		if (cmd.equalsIgnoreCase("Excluir")) {
			pRep.deleteById(produto.getCodigo());
			produto = null;
		}
		if (cmd.equalsIgnoreCase("Buscar")) {
			produto = pRep.findById(produto.getCodigo()).orElse(new Produto());
			if(produto.getNome() == null) {
				erro = "O codigo nao existe";			
			}
		}
		if (cmd.equalsIgnoreCase("Listar")) {
			produtos = pRep.findAll();
			if(!produtos.isEmpty()) {
				model.addAttribute("produtos", produtos);
			}else {
				erro = "Nao existem produtos";
			}
		}
		if(cmd.equalsIgnoreCase("Verificar")) {
			String valor = param.get("valor");
			int resultado = pRep.fn_QtdProdutosAbaixoDoParam(Integer.parseInt(valor));
			saida = "Existem " + resultado +  " produtos abaixo na qtd de estoque"; 
		}

		model.addAttribute("saida", saida);
		model.addAttribute("erro", erro);
		model.addAttribute("produto", produto);

		return new ModelAndView("produto");
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(name = "produtoRelatorio", value = "/produtoRelatorio", method = RequestMethod.POST)
	public ResponseEntity produtoRelatorioPost(@RequestParam Map<String, String> param) {
		String erro = "";
		
		Map<String, Object> paramRelatorio =  new HashMap<String, Object>();
		paramRelatorio.put("valor", param.get("valorRelatorio"));
		
		byte [] bytes = null;
		
		InputStreamResource resource = null;
		HttpStatus status = null;
		HttpHeaders header = new HttpHeaders();
		
		
		try {
			Connection c = DataSourceUtils.getConnection(ds);
			File arquivo = ResourceUtils.getFile("classpath:reports/RelatorioProduto.jasper");
			JasperReport report = (JasperReport) JRLoader.loadObjectFromFile(arquivo.getAbsolutePath());
			bytes = JasperRunManager.runReportToPdf(report, paramRelatorio, c);
		} catch (FileNotFoundException | JRException e) {
			e.printStackTrace();
			erro = e.getMessage();
			status = HttpStatus.BAD_REQUEST;
		}finally {
			if(erro.equals("")) {
				InputStream inputStream = new ByteArrayInputStream(bytes);
				resource = new InputStreamResource(inputStream);
				header.setContentLength(bytes.length);
				header.setContentType(MediaType.APPLICATION_PDF);
				status = HttpStatus.OK;
			}
		}
		
		return new ResponseEntity(resource, header, status);
		
	}

}

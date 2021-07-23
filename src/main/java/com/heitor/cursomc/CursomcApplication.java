package com.heitor.cursomc;

import com.heitor.cursomc.domain.*;
import com.heitor.cursomc.enums.EstadoPagamento;
import com.heitor.cursomc.enums.TipoCliente;
import com.heitor.cursomc.reposotories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner {

	@Autowired
	private CategoriaRepository categoriaRepository;

	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired
	private EstadoRepository estadoRepository;

	@Autowired
	private CidadeRepository cidadeRepository;

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private EnderecoRepository enderecoRepository;

	@Autowired
	private PedidoRepository pedidoRepository;

	@Autowired
	private PagamentoRepository pagamentoRepository;

	@Autowired
	private ItemPedidoRepository itemPedidoRepository;

	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");
		Categoria cat3 = new Categoria(null, "Perfumaria");
		Categoria cat4 = new Categoria(null, "Drogaria");
		Categoria cat5 = new Categoria(null, "Farmacia");
		Categoria cat6 = new Categoria(null, "Cama, mesa e banho");
		Categoria cat7 = new Categoria(null, "Brinquedos");
		Categoria cat8 = new Categoria(null, "Esporte");
		Categoria cat9 = new Categoria(null, "Animais");
		Categoria cat10 = new Categoria(null, "Alimentos");
		Categoria cat11 = new Categoria(null, "Bebidas");
		Categoria cat12 = new Categoria(null, "Chocolate");
		Categoria cat13 = new Categoria(null, "Fotografia");
		Categoria cat14 = new Categoria(null, "Música");

		categoriaRepository.saveAll(Arrays.asList(cat1, cat2, cat3, cat4, cat5, cat6, cat7, cat8, cat9, cat10, cat11, cat12, cat13, cat14));

		Produto p1 = new Produto(null, "Computador", 2000.00);
		Produto p2 = new Produto(null, "Impressora", 800.00);
		Produto p3 = new Produto(null, "Mouse", 80.00);

		p1.getCategorias().addAll(Arrays.asList(cat1, cat2));
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2));
		p3.getCategorias().addAll(Arrays.asList(cat2));

		produtoRepository.saveAll(Arrays.asList(p1, p2, p3));

		Estado est1 = new Estado(null, "Minas Gerais");
		Estado est2 = new Estado(null, "São Paulos");

		estadoRepository.saveAll(Arrays.asList(est1, est2));

		Cidade c1 = new Cidade(null, "Uberlândia", est1);
		Cidade c2 = new Cidade(null, "São Paulo", est2);
		Cidade c3 = new Cidade(null, "Campinas", est2);

		cidadeRepository.saveAll(Arrays.asList(c1, c2, c3));

		Cliente cli1 = new Cliente(null, "Maria Silva", "maria@gmail.com", "583.315.950-41", TipoCliente.PESSOAFISICA);

		cli1.getTelefones().addAll(Arrays.asList("(21) 982123-92812", "(21) 938392-0932"));

		clienteRepository.saveAll(Arrays.asList(cli1));

		Endereco e1 = new Endereco(null, "Rua das flores", "300", "Apto 203", "Jardim", "38220834", cli1, c1);
		Endereco e2 = new Endereco(null, "Avenida Matos", "105", "Sala 800", "Centro", "38777012", cli1, c2);

		enderecoRepository.saveAll(Arrays.asList(e1, e2));

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

		Pedido ped1 = new Pedido(null, LocalDateTime.parse("2017-09-03 10:32", formatter), cli1, e1);
		Pedido ped2 = new Pedido(null, LocalDateTime.parse("2017-10-10 19:35", formatter), cli1, e2);

		pedidoRepository.saveAll(Arrays.asList(ped1, ped2));

		Pagamento pagto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6);


		DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		Pagamento pagto2 = new PagamentoComBoleto(null , EstadoPagamento.PENDENTE, ped2,  LocalDate.parse("2017-09-20", formatterDate), null);

		pagamentoRepository.saveAll(Arrays.asList(pagto1, pagto2));

		cli1.getPedidos().addAll(Arrays.asList(ped1, ped2));
		clienteRepository.save(cli1);

		ItemPedido ip1 = new ItemPedido(ped1, p1, 0.0, 1, 2000.0);
		ItemPedido ip2 = new ItemPedido(ped1, p3, 0.0, 2, 80.00);
		ItemPedido ip3 = new ItemPedido(ped2, p2, 100.0, 1, 800.00);

		itemPedidoRepository.saveAll(Arrays.asList(ip1, ip2, ip3));
	}
}

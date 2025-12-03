package br.edu.utfpr.td.tsi.web.scraper.raspagem.modelos;

public class Imovel {

	private Integer id;
	private String titulo;
	private String finalidade;
	private String bairro;
	private String cidade;
	private Integer referencia;
	private Integer numeroBanheiros;
	private Integer numeroVagasGaragem;
	private String areaTerreno;
	private String areaConstruida;
	private String areaUtil;
	private String valor;

	public Imovel() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setId(String titulo) {
		this.titulo = titulo;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getFinalidade() {
		return finalidade;
	}

	public void setFinalidade(String finalidade) {
		this.finalidade = finalidade;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public Integer getReferencia() {
		return referencia;
	}

	public void setReferencia(Integer referencia) {
		this.referencia = referencia;
	}

	public Integer getNumeroBanheiros() {
		return numeroBanheiros;
	}

	public void setNumeroBanheiros(Integer numeroBanheiros) {
		this.numeroBanheiros = numeroBanheiros;
	}

	public Integer getNumeroVagasGaragem() {
		return numeroVagasGaragem;
	}

	public void setNumeroVagasGaragem(Integer numeroVagasGaragem) {
		this.numeroVagasGaragem = numeroVagasGaragem;
	}

	public String getAreaTerreno() {
		return areaTerreno;
	}

	public void setAreaTerreno(String areaTerreno) {
		this.areaTerreno = areaTerreno;
	}

	public String getAreaConstruida() {
		return areaConstruida;
	}

	public void setAreaConstruida(String areaConstruida) {
		this.areaConstruida = areaConstruida;
	}

	public String getAreaUtil() {
		return areaUtil;
	}

	public void setAreaUtil(String areaUtil) {
		this.areaUtil = areaUtil;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}
}
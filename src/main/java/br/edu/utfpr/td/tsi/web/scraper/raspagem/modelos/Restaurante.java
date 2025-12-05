package br.edu.utfpr.td.tsi.web.scraper.raspagem.modelos;

public class Restaurante {

	private Integer id; 
	 private String nome;
	 private String endereco;
	 private Integer estrelas;

	 public Restaurante() {}
	 
	 public Integer getId() {
         return id;
     }

     public void setId(Integer id) {
         this.id = id;
     }

	 public String getNome() { 
		 return nome; 
	 }
	 
	 public void setNome(String nome) { 
		 this.nome = nome; 
	 }

	 public String getEndereco() { 
		 return endereco; 
	 }
	 
	 public void setEndereco(String endereco) { 
		 this.endereco = endereco; 
	 }
	 
	 public Integer getEstrelas() { 
		 return estrelas; 
	 }
	 
	 public void setEstrelas(Integer estrelas) { 
		 this.estrelas = estrelas; 
	 }
}

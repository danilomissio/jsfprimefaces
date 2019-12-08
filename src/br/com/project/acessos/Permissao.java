package br.com.project.acessos;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public enum Permissao {
	
	ADMIN("ADMIN", "Administrador"),
	USER("USER", "Usuario Padrao"),
	CADASTRO_ACESSAR("CADASTRO_ACESSAR", "Cadastro - acessar"),
	FINANCEIRO_ACESSAR("FINANCEIRO_ACESSAR", "Financeiro - acessar"),
	MENSAGEM_ACESSAR("MENSAGEM_ACESSAR", "Mensagem recebida - acessar"),
	
	
	BAIRRO_ACESSAR("BAIRRO_ACESSAR", "Bairro - acessar"),
	BAIRRO_NOVO("BAIRRO_NOVO", "Bairro - novo"),
	BAIRRO_EDITAR("BAIRRO_EDITAR", "Bairro - editar"),
	BAIRRO_EXCLUIR("BAIRRO_EXCLUIR", "Bairro - excluir");
	
	private String valor = "";
	private String descricao = "";
	
	private Permissao(String valor, String descricao) {
		this.valor = valor;
		this.descricao = descricao;
	}
	
	private Permissao() {
		// TODO Auto-generated constructor stub
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	@Override
	public String toString() {
		return getValor();
	}
	
	public static List<Permissao> getListPermissao(){
		List<Permissao> permissoes = new ArrayList<Permissao>();
		for(Permissao permissao: Permissao.values()) {
			permissoes.add(permissao);
		}
		
		java.util.Collections.sort(permissoes, new Comparator<Permissao>() {

			@Override
			public int compare(Permissao o1, Permissao o2) {
				// TODO Auto-generated method stub
				return new Integer(o1.ordinal()).compareTo(new Integer(o2.ordinal()));
			}
			
		});
		
		return permissoes;
	}
	
}

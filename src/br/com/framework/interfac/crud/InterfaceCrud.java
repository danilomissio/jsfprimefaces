package br.com.framework.interfac.crud;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public interface InterfaceCrud<T> extends Serializable{
	
	//Salvar os dados
	void save(T obj) throws Exception;
	
	void persist(T obj) throws Exception;
	
	//Salve ou atualiza
	void saveOrUpdate(T obj) throws Exception;
	
	// realiza o update atualizacao de dados
	void update(T obj) throws Exception; 
	
	//realiza o delete de dados
	void delete(T obj) throws Exception;
	
	//Salva ou atualiza e retorna o objeto em estado persistente
	T merge (T obj) throws Exception;
	
	//Carrega a lista de dados de determinada classe
	List<T> findList(Class<T> objs) throws Exception;
	
	//Retorna um objeto buscando pelo id
	Object findById(Class<T> entity, Long id) throws Exception; 
	
	public T findByPorId(Class<T> entity, Long id) throws Exception;
	
	List<T> findListByQueryDinamica(String s) throws Exception;
	
	//Executar update com HQL 
	void executeUpdateQueryDinamica(String s) throws Exception;
	
	//Executar update com SQL puro
	void executeUpdateSQLDinamica(String s) throws Exception;
	
	//Limpa a sess�o do hibernate
	void clearSession() throws Exception;
	
	//Retira um objeto da sess�o do hibernate
	void evict(Object objs) throws Exception;
	
	Session getSession() throws Exception;
	
	List<?> getListSQLDinamica(String sql) throws Exception;
	
	//JDBC do Spring
	JdbcTemplate getJdbcTemplate();
	
	SimpleJdbcTemplate getSimpleJdbcTemplate();
	
	SimpleJdbcInsert getSimpleJdbcInsert();
	
	Long totalRegistro(String table) throws Exception;
	
	Query obterQuery(String query) throws Exception;
	
	//Carregamento dinamico com JSF e Primefaces
	List<?> findListByQueryDinamica(String query, int iniciaNoRegistro, int maximoResultado) throws Exception;
}

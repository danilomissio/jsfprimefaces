package br.com.project.exception;

import java.util.Iterator;
import java.util.Map;

import javax.faces.FacesException;
import javax.faces.application.FacesMessage;
import javax.faces.application.NavigationHandler;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.context.FacesContext;
import javax.faces.event.ExceptionQueuedEvent;
import javax.faces.event.ExceptionQueuedEventContext;

import org.hibernate.SessionFactory;

import br.com.framework.hibernate.session.HibernateUtil;

public class CustomExceptionHandler extends ExceptionHandlerWrapper {

	private ExceptionHandler wrapperd;

	final FacesContext facesContext = FacesContext.getCurrentInstance();

	final Map<String, Object> requestMap = facesContext.getExternalContext().getRequestMap();

	final NavigationHandler navigationHandler = facesContext.getApplication().getNavigationHandler();

	public CustomExceptionHandler(ExceptionHandler e) {
		this.wrapperd = e;
	}

	// Sobrescreve o método ExceptionHandlerExceptionHandler que retorna a "pilha"
	// de exceções
	@Override
	public ExceptionHandler getWrapped() {
		return wrapperd;
	}

	// Sobrescreve o método handle que é responsável por manipular
	// as exceções do JSF
	@Override
	public void handle() throws FacesException {
		final Iterator<ExceptionQueuedEvent> iterator = getUnhandledExceptionQueuedEvents().iterator();

		while (iterator.hasNext()) {
			ExceptionQueuedEvent event = iterator.next();
			ExceptionQueuedEventContext context = (ExceptionQueuedEventContext) event.getSource();

			// Recuperar a exceção do contexto
			Throwable exception = context.getException();

			// Aqui trabalhamos a exceção
			try {

				requestMap.put("exceptionMessage", exception.getMessage());

				if (exception != null && exception.getMessage() != null
						&& exception.getMessage().indexOf("ConstraintViolationException") != -1) {

					FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_WARN,
							"Registro não pode ser removido por estar associado", ""));

				} else if (exception != null && exception.getMessage() != null
						&& exception.getMessage().indexOf("org.hibernate.StaleObjectStateException") != -1) {

					FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Registro foi atualizado ou excluido por outro usuário. Consulte novamente.", ""));

				} else {
					// Avisar o usuário do erro
					FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_FATAL,
							"O Sistema se recuperou de um erro inesperado", ""));

					FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Voce pode continuar usando o sistema normalmente", ""));

					FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_FATAL,
							"O erro foi causado por:\n" + exception.getMessage(), ""));
				}
				
				facesContext.renderResponse();

			} finally {
				SessionFactory sf = HibernateUtil.getSessionFactory();
				if (sf.getCurrentSession().getTransaction().isActive()) {
					sf.getCurrentSession().getTransaction().rollback();
				}
				// Imprime o erro no console
				exception.printStackTrace();

				iterator.remove();
			}

		}
		getWrapped().handle();
	}

}

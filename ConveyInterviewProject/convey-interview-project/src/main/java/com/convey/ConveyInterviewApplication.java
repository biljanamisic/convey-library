package com.convey;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientProperties;

import com.convey.api.AuthorDTO;
import com.convey.api.BookDTO;
import com.convey.client.RestClientController;
import com.convey.client.util.exceptions.InconsistentBookDataException;
import com.convey.core.Author;
import com.convey.core.Book;
import com.convey.db.BookDAO;
import com.convey.resources.BookResourceImpl;
import com.convey.service.BookService;
import com.convey.service.BookServiceImpl;

import io.dropwizard.Application;
import io.dropwizard.client.JerseyClientConfiguration;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class ConveyInterviewApplication extends Application<ConveyInterviewConfiguration> {

    public static void main(final String[] args) throws Exception {
        new ConveyInterviewApplication().run(args);
    }

    @Override
    public String getName() {
        return "interview";
    }

	private final HibernateBundle<ConveyInterviewConfiguration> hibernateBundle = new HibernateBundle<ConveyInterviewConfiguration>(
			Book.class, Author.class) {
		@Override
		public DataSourceFactory getDataSourceFactory(ConveyInterviewConfiguration configuration) {
			return configuration.getDataSourceFactory();
		}
	};

	@Override
	public void initialize(final Bootstrap<ConveyInterviewConfiguration> bootstrap) {
		bootstrap.addBundle(hibernateBundle);
	}
	
	@Override
	public void run(final ConveyInterviewConfiguration configuration, final Environment environment) throws InconsistentBookDataException {
		final BookDAO bookDAO = new BookDAO(hibernateBundle.getSessionFactory()); 
		environment.jersey().register(bookDAO);
		final BookService bookService = new BookServiceImpl(bookDAO); 		
		environment.jersey().register(bookService);
		environment.jersey().register(new BookResourceImpl(bookService)); 		
		
		JerseyClientConfiguration conf = configuration.getJerseyClientConfiguration(); 
        conf.setChunkedEncodingEnabled(false);
   
		ClientConfig config = new ClientConfig();
		config.property(ClientProperties.CONNECT_TIMEOUT, 5000);
		config.property(ClientProperties.READ_TIMEOUT, 5000);
		Client client = ClientBuilder.newClient(config);
        
        environment.jersey().register(new RestClientController(client));
        environment.jersey().register(AuthorDTO.class);
        environment.jersey().register(BookDTO.class);
	}
}

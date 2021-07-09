package billreminder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.io.IOException;
import java.util.Arrays;

@SpringBootApplication
public class BillreminderApplication {
	public static void main(String[] args) {

		/*RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(new HttpHost("localhost", 9200, "http")));

		CreateIndexRequest request = new CreateIndexRequest("sampleindex");
		request.settings(Settings.builder().put("index.number_of_shards", 1).put("index.number_of_replicas", 2));
		CreateIndexResponse createIndexResponse = client.indices().create(request, RequestOptions.DEFAULT);
		System.out.println("response id: " + createIndexResponse.index());*/

		SpringApplication.run(BillreminderApplication.class, args);
		System.out.println("Welcome");
	}

		@Bean
		public CorsFilter corsFilter() {
			CorsConfiguration corsConfiguration = new CorsConfiguration();
			corsConfiguration.setAllowCredentials(true);
			corsConfiguration.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
			corsConfiguration.setAllowedHeaders(Arrays.asList("Origin", "Access-Control-Allow-Origin", "Content-Type",
					"Accept", "Authorization", "Origin, Accept", "X-Requested-With",
					"Access-Control-Request-Method", "Access-Control-Request-Headers"));
			corsConfiguration.setExposedHeaders(Arrays.asList("Origin", "Content-Type", "Accept", "Authorization",
					"Access-Control-Allow-Origin", "Access-Control-Allow-Origin", "Access-Control-Allow-Credentials"));
			corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
			UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
			urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
			return new CorsFilter(urlBasedCorsConfigurationSource);
		}
}

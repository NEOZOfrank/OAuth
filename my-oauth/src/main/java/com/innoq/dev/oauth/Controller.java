package com.innoq.dev.oauth;

import static spark.Spark.get;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import spark.Request;
import spark.Response;
import spark.Route;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import freemarker.template.Version;

public class Controller {
	

	   private static Configuration cfg;

	public static void main(String[] args) throws IOException, TemplateException {
		   
		   initFreeMarker();
		   
		   
			

		// Create the root hash
		Map root = new HashMap();
		// Put string ``user'' into the root
		root.put("user", "Big Joe");
		// Create the hash for ``latestProduct''
		Map latest = new HashMap();
		// and put it into the root
		root.put("latestProduct", latest);
		// put ``url'' and ``name'' into latest
		latest.put("url", "products/greenmouse.html");
		latest.put("name", "green mouse");  
		
		
		Template temp = cfg.getTemplate("start.ftl");  
		
	

		final Writer out = new StringWriter();
		temp.process(root, out);  
		
		
		   
	      
		   get(new Route("/login") {
		      @Override
		      public Object handle(Request request, Response response) {
		    	 response.body(out.toString());
		    	 response.type("application/html");
		         return out;
		      }
		   });   
		   
	   }

	private static void initFreeMarker() {
		cfg = new Configuration();

		// Specify the data source where the template files come from. Here I set a
		// plain directory for it, but non-file-system are possible too:
		try {
			cfg.setDirectoryForTemplateLoading(new File("src/main/templates"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Specify how templates will see the data-model. This is an advanced topic...
		// for now just use this:
		cfg.setObjectWrapper(new DefaultObjectWrapper());

		// Set your preferred charset template files are stored in. UTF-8 is
		// a good choice in most applications:
		cfg.setDefaultEncoding("UTF-8");

		// Sets how errors will appear. Here we assume we are developing HTML pages.
		// For production systems TemplateExceptionHandler.RETHROW_HANDLER is better.
		cfg.setTemplateExceptionHandler(TemplateExceptionHandler.HTML_DEBUG_HANDLER);

		// At least in new projects, specify that you want the fixes that aren't
		// 100% backward compatible too (these are very low-risk changes as far as the
		// 1st and 2nd version number remains):
		cfg.setIncompatibleImprovements(new Version(2, 3, 20));  // FreeMarker 2.3.20  
	}

}

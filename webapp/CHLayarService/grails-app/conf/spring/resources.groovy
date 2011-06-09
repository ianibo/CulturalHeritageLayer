// Place your Spring DSL code here
beans = {
  europeanaRestBuilder(groovyx.net.http.HTTPBuilder, application.config.europeana.host ) {
  }

}

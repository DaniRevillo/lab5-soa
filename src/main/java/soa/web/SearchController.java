package soa.web;

import java.util.HashMap;
import java.util.Map;

import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class SearchController {

  private final ProducerTemplate producerTemplate;

  @Autowired
  public SearchController(ProducerTemplate producerTemplate) {
    this.producerTemplate = producerTemplate;
  }

  @RequestMapping("/")
  public String index() {
    return "index";
  }

  // Añadido parametro max indicando el numero maximo de tweets por busqueda, por defecto 5
  @RequestMapping(value = "/search")
  @ResponseBody
  public Object search(@RequestParam("q") String q, @RequestParam(name = "max", defaultValue = "5") int max) {
    Map<String, Object> cabeceras = new HashMap<String, Object>();
    cabeceras.put("CamelTwitterKeywords", q);
    cabeceras.put("CamelTwitterCount", max);
    return producerTemplate.requestBodyAndHeaders("direct:search", "", cabeceras);
  }
}
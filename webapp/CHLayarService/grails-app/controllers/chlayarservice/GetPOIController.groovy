package chlayarservice

import grails.converters.*
import org.codehaus.groovy.grails.commons.ApplicationHolder
import groovyx.net.http.ContentType
import grails.converters.*


// An example request
// 6f85d06929d160a7c8a3cc1ab4b54b87db99f74b
// culturalheritagelayer
//4.0
//52.377544
//4.887339
//AF
//en
//null
//100
//null
//
// Europeana spatial search http://api.europeana.eu/api/opensearch.rss?searchTerms=enrichment_place_latitude%3A[42+TO+48]+AND+enrichment_place_longitude%3A[10+TO+15]&wskey=xxx
//
// ApplicationHolder.application.config.apikey

class GetPOIController {

    def europeanaRestBuilder

    def index = { 
      println "Params..."
      println "${params.userId}"
      println "${params.layerName}"
      println "${params.version}"
      // 52.377544
      // 4.887339
      println "${params.lat}"
      println "${params.lon}"
      println "${params.countryCode}"
      println "${params.lang}"
      println "${params.action}"
      println "${params.accuracy}"
      println "${params.pageKey}"

      def result_object = [:]

      def hotspots = []

      result_object.identifier = ""
      result_object.layer = 'culturalheritagelayer'
      result_object.hotspots = hotspots

      //result_object.hotspots = [
      //  [ identifier:"id",
      //    distance:"Distance",
      //    title:"Distance",
      //    type:"Distance",
      //    lat:"Distance",
      //    lon:"Distance",
      //    action:[]
      //  ]
      //]

      result_object.errorCode = 0
      result_object.errorString = "ok"
      // result_object.nextPageKey = ""
      // result_object.morePages = ""
      // result_object.radius = ""

    //withHttp(uri: "http://api.europeana.eu") {
    //  def xml = get(path : '/api/opensearch.rss', 
    //                query : [searchTerms : 'text:"art nouveau"', 
    //                         wskey : ApplicationHolder.application.config.apikey], 
    //                contentType : ContentType.XML)

      // println "XML: ${xml}"
    //  println "Class: ${xml.getClass().getName()}."
    //  println "Result count: ${xml.channel.totalResults}"
    //  println "Iterating results....  -${xml.@version}- title:-${xml.channel.title}-"
    //  xml.channel.item.each {
    //    println "Got an item - Title is ${it.title.text()}"
    //  }
    //}

    def plat = params.lat
    def plon = params.lon

    if ( ( params.lat == null ) && ( params.lon == null ) ) {
      plat = 51.39920565355378
      plon = 0.2197265625
    }

    def x1=plat-2
    def y1=plon-2
    def x2=plat+2
    def y2=plon+2

    def europeana_search_result = europeanaRestBuilder.get( path : "/api/opensearch.rss", 
                                                            query : ['wskey' : ApplicationHolder.application.config.apikey,
                                                                      'searchTerms' : 'enrichment_place_latitude:[${x1} TO ${x2}] AND enrichment_place_longitude:[${y1} TO ${y2}]'
                                                                     ],
                                                             contentType: ContentType.XML )

    // println "Class: ${europeana_search_result.getClass().getName()}"

      // def records = new XmlSlurper().parseText(europeana_search_result)

      println "Iterating result items...."
      europeana_search_result.channel.item.each { item ->
        // println "Processing ${item}"
        println "${item.'link'} ${item.'enrichment:place_latitude'} ${item.'enrichment:place_longitide'} ${item.'title'}"   
        if ( ( item.title?.text() != null ) && 
             ( item.title?.text().length() > 0 ) && 
             ( item.'place_latitude'?.text().length() > 0 ) &&
             ( item.'place_longitude'?.text().length() > 0 ) ) {
          int lon = ( Double.parseDouble(item.'place_longitude'.text()) * 1000000 )
          int lat = ( Double.parseDouble(item.'place_latitude'.text()) * 1000000 ) 

          hotspots.add([id:item.link.text(),
                        distance:0,
                        title:item.title?.text(),
                        type:0,
                        lat:lat,
                        lon:lon,
                        actions:[]])
        }
      }

      render(contentType:"text/json") {
 	 result_object
      }
    }
}

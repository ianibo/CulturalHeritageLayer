package chlayarservice

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


class GetPOIController {

    def index = { 
      println "Params..."
      println "${params.userId}"
      println "${params.layerName}"
      println "${params.version}"
      println "${params.lat}"
      println "${params.lon}"
      println "${params.countryCode}"
      println "${params.lang}"
      println "${params.action}"
      println "${params.accuracy}"
      println "${params.pageKey}"

      def result_object = [:]

      result_object.identifier = ""
      result_object.hotspots = [
        [ identifier:"id",
          distance:"Distance",
          title:"Distance",
          type:"Distance",
          lat:"Distance",
          lon:"Distance",
          action:[]
        ]
      ]
      // result_object.errorCode = ""
      // result_object.errorString = ""
      // result_object.nextPageKey = ""
      // result_object.morePages = ""
      // result_object.radius = ""

      render(contentType:"text/json") {
 	 result_object
      }
    }
}

package chlayarservice

import grails.converters.*


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

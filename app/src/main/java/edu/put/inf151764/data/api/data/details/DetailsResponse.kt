package edu.put.inf151764.data.api.data.details

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root

@Root(name = "items")
data class DetailsResponse @JvmOverloads constructor(
    @field:Attribute(name = "termsofuse")
    var termsofuse: String = "",

    @field:ElementList(name = "item", inline = true)
    var items: List<DetailsItem> = mutableListOf()
)

@Root(name = "item")
data class DetailsItem @JvmOverloads constructor(
    @field:Attribute(name = "type")
    var type: String = "",

    @field:Attribute(name = "id")
    var id: String = "",

    @field:Element(name = "thumbnail")
    var thumbnail: String = "",

    @field:Element(name = "image")
    var image: String = "",

    @field:ElementList(entry = "name", inline = true)
    var names: List<Name> = mutableListOf(),

    @field:Element(name = "description")
    var description: String = "",

    @field:Element(name = "yearpublished", required = false)
    var yearPublished: Value = Value(),

    @field:Element(name = "minplayers", required = false)
    var minPlayers: Value = Value(),

    @field:Element(name = "maxplayers", required = false)
    var maxPlayers: Value = Value(),

    @field:Element(name = "statistics")
    var statistics: Statistics = Statistics()

)

@Root(name = "name")
data class Name @JvmOverloads constructor(
    @field:Attribute(name = "type")
    var type: String = "",

    @field:Attribute(name = "sortindex")
    var sortindex: Int = 0,

    @field:Attribute(name = "value")
    var value: String = ""
)

@Root(name = "statistics", strict = false)
data class Statistics @JvmOverloads constructor(

    @field:Element(name = "ratings")
    var ratings: Ratings = Ratings(),

    @field:Attribute(required = false)
    var page: String = "",

    @field:Attribute(required = false)
    var totalitems: String = ""
)

@Root(name = "ratings", strict = false)
data class Ratings @JvmOverloads constructor(

    @field:Element(name = "usersrated")
    var usersRated: Value = Value(),

    @field:Element(name = "average")
    var average: Value = Value(),

    @field:Element(name = "bayesaverage")
    var bayesAverage: Value = Value(),

    @field:Element(name = "stddev")
    var standardDeviation: Value = Value(),

    @field:Element(name = "median")
    var median: Value = Value(),

    @field:Element(name = "owned")
    var owned: Value = Value(),

    @field:Element(name = "trading")
    var trading: Value = Value(),

    @field:Element(name = "wanting")
    var wanting: Value = Value(),

    @field:Element(name = "wishing")
    var wishing: Value = Value(),

    @field:Element(name = "numcomments")
    var numComments: Value = Value(),

    @field:Element(name = "numweights")
    var numWeights: Value = Value(),

    @field:Element(name = "averageweight")
    var averageWeight: Value = Value(),

    @field:ElementList(name = "ranks", inline = false)
    var ranks: List<Rank> = mutableListOf()
)

@Root(name = "value", strict = false)
data class Value @JvmOverloads constructor(

    @field:Attribute(required = false)
    var value: String = ""
)

@Root(name = "rank", strict = false)
data class Rank @JvmOverloads constructor(

    @field:Attribute(name = "id", required = false)
    var id: String = "",

    @field:Attribute(name = "name", required = false)
    var name: String = "",

    @field:Attribute(name = "type", required = false)
    var type: String = "",

    @field:Attribute(name = "bayesaverage", required = false)
    var bayesAverage: String = "",

    @field:Attribute(name = "value", required = false)
    var value: String = ""
)

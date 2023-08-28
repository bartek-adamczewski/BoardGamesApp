package edu.put.inf151764.data.api.data.list

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

@Root(name = "item", strict = false)
data class Item @JvmOverloads constructor(

    @field:Attribute(name = "objecttype")
    var objectType: String = "",

    @field:Attribute(name = "objectid")
    var objectId: String = "",

    @field:Attribute(name = "subtype")
    var subType: String = "",

    @field:Attribute(name = "collid")
    var collId: String = "",

    @field:Element(name = "name")
    var name: String = "",

    @field:Element(name = "yearpublished", required = false)
    var yearPublished: Int = 0,

    @field:Element(name = "image")
    var image: String = "",

    @field:Element(name = "thumbnail")
    var thumbnail: String = "",

    @field:Element(name = "numplays")
    var numPlays: Int = 0
)
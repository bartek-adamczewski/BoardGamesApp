package edu.put.inf151764.data.api.data.list

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root

@Root(name = "items", strict = false)
data class ItemsResponse @JvmOverloads constructor(
    @field:Attribute(name = "totalitems", required = false)
    var totalItems: Int = 0,

    @field:ElementList(name = "item", inline = true)
    var items: List<Item> = mutableListOf()
)










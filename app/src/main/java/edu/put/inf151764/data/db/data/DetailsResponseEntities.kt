package edu.put.inf151764.data.db.data

import androidx.room.Entity

@Entity
data class DetailsItem(
    var type: String,
    var id: String,
    var thumbnail: String,
    var image: String,
    var names: List<Name>,
    var description: String,
    var yearPublished: String?,
    var minPlayers: String?,
    var maxPlayers: String?
)

@Entity
data class Name(
    var type: String,
    var sortindex: Int,
    var value: String
)

@Entity
data class Statistics(
    var ratings: Ratings,
    var page: String,
    var totalitems: String
)

@Entity
data class Ratings(
    var usersRated: Value,
    var average: Value,
    var bayesAverage: Value,
    var standardDeviation: Value,
    var median: Value,
    var owned: Value,
    var trading: Value,
    var wanting: Value,
    var wishing: Value,
    var numComments: Value,
    var numWeights: Value,
    var averageWeight: Value,
    var ranks: List<Rank>
)

@Entity
data class Value(
    var value: String
)

@Entity
data class Rank(
    var id: String,
    var name: String,
    var type: String,
    var bayesAverage: String,
    var value: String
)
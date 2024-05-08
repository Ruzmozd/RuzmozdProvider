package ir.hirkancorp.data.register.mapper

import ir.hirkancorp.data.register.model.CityData
import ir.hirkancorp.domain.register.model.City


fun List<CityData>.toDomain(): List<City> = map { it.toCity() }

fun CityData.toCity(): City = City(
    id = id,
    name = name
)
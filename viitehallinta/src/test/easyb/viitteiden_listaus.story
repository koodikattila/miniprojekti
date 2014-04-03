description 'Järjestelmän viitteet pystyy listaamaan'

scenario "Järjestelmässä olevia viitteitä voi tarkastella listana", {
    given 'järjestelmässä on viitteitä'
    when 'yritetään tarkastella viitteitä'
    then 'viitteet näkyvät listana'
}
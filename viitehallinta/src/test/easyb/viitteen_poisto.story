description 'Järjestelmästä pystyy poistamaan viitteen'

scenario "Järjestelmästä voi poistaa halutun viitteen", {
    given 'Tarkastellaan viitteiden listaa, jossa on viitteitä'
    when 'valitaan viite poistettavaksi'
    then 'viite poistuu järjestelmästä'
}
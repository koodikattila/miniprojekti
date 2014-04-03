description 'Järjestelmään pystyy lisäämään article-tyyppisen viitteen'

scenario "Viite tallentuu järjestelmään", {
    given 'Yritetään tallentaa uusi viite'
    when 'Viitteeseen vaaditut tiedot on annettu'
    then 'Viite tallentuu järjestelmään'
}

scenario "Viite ei tallennu järjestelmään, jos ei anneta kaikkia vaadittuja tietoja", {
    given 'Yritetään tallentaa uusi viite'
    when 'Kaikkia viitteeseen vaadittuja tietoja ei ole annettu'
    then 'Viite ei tallennu järjestelmään'
}
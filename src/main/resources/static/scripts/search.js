const context = document.querySelector('base').getAttribute('href');
const searchInput = document.getElementById("searchInput");
const searchButton = document.getElementById("searchButton");

searchButton.addEventListener("click", function () {
    if (searchInput.value.length > 0) {
        search();
    }
});


searchInput.addEventListener("keyup", function () {
    if (searchInput.value.length > 2) {
        search();
    }
});

function search() {
    let url = context + "item/search?q=" + searchInput.value;
    let options = {method: "GET"};
    fetch(url, options).then(function (response) {
        return response.json();
    }).then(function (items) {
        var container = document.getElementById("itemsContainer");

        container.innerHTML = "";

        var articles = '';
        for (var i = 0; i < items.length; i++) {
            var date = new Date(items[i].date);
            var dateFormatted = date.getDate() + "." + (date.getMonth() + 1) + "." + date.getFullYear() + " - " + date.getHours() + ":" + date.getMinutes();

            articles += createItemCard(items[i].id, items[i].image, items[i].title, items[i].description.substring(0, 32) + " ...", dateFormatted);
        }

        if (items.length == 0) {
            articles = '<article class="col-md-4"><p>Nessun annuncio trovato</p></article>';
        }

        container.innerHTML = '<h2>Risultati ricerca: ' + searchInput.value + ' <a class="btn btn-sm btn-secondary" href="' + window.location.href + '">chiudi</a></h2>' +
            '<section class="row">\n' +
            '        \n' +
            articles +
            '            \n' +
            '        \n' +
            '    </section>';

    });
}

function createItemCard(id, image, title, description, date) {
    return "<div class='card'>\n" +
        "                <img alt='Item image!' class='card-img-top' src=data:image/*;base64," + image + ">\n" +
        "                <div class='card-body d-flex flex-column'>\n" +
        "                    <h4 class='card-title'>" + title + "</h4>\n" +
        "                    <p class='card-text'>" + description + ".</p>\n" +
        "                    <p class='card-text'><small class='text-muted'>" + date + "</small></p>\n" +
        "                    <a class='btn mt-auto btn-primary' href=" + context + "item/" + id + ">Leggi l'annuncio</a>\n" +
        "                </div>\n" +
        "            </div>";
}
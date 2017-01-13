# III BEEVA Afterwork

Aplicación Android que pretende introducir el desarrollo de aplicaciones en este sistema operativo.

## TMDB API

Se utiliza la API v3 de [The Movie DB](https://www.themoviedb.org/) para proveer a la aplicación con información de películas y series de televisión. Actualmente se han implementado las siguientes operaciones:

**Películas**

* `search`: Búsqueda en base a una entrada de texto.
* `discover`: Búsqueda basada en filtros como puntuación media, número de votos, género, región... Por defecto no se aplican filtros.
* `popular`: Lista de las películas más populares actualmente en The Movie DB. Se actualiza diariamente.
* `detail`: Detalle de una película, por ejemplo el identificador IMDB.
* `rate`: Votar una película con una puntuación entre 0.5 y 10.

**Series de TV**

* `search`: Búsqueda en base a una entrada de texto.
* `discover`: Búsqueda basada en filtros como puntuación media, número de votos, género, región... Por defecto no se aplican filtros.
* `popular`: Lista de las series más populares actualmente en The Movie DB. Se actualiza diariamente.
* `detail`: Detalle de una serie, así como información básica de las temporadas.
* `season-detail`: Detalle de una temporada, como su título, poster, y los episodios que contiene.
* `rate`: Votar una serie de TV con una puntuación entre 0.5 y 10.

**Extra**

* `multi-search`: Búsqueda de películas y series de televisión en base a una entrada de texto.

Más información en [https://developers.themoviedb.org/3](https://developers.themoviedb.org/3).

## Ejemplo de uso

A continuación se muestra un ejemplo de cómo buscar películas que coincidan con la palabra "Alien":

```java
SearchMoviesInteractor searchMovies = new SearchMoviesInteractor(getApplicationContext());
searchMovies.execute("Alien", new Callback<List<Movie>>() {
    @Override
    public void onCompleted(@NonNull List<Movie> movies) {
        // Do something with movies...
    }
}, new ErrorCallback() {
    @Override
    public void onError(@NonNull TMDbException exception) {
        // Manage exception...
    }
});
```

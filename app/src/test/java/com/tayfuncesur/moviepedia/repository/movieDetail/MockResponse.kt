package com.tayfuncesur.moviepedia.repository.movieDetail

object MockResponse {

    const val success = """
        {
    "adult": false,
    "backdrop_path": "/nArRVfOmxJvO1Fzz0hPOIX0zueU.jpg",
    "belongs_to_collection": null,
    "budget": 0,
    "genres": [
        {
            "id": 35,
            "name": "Komedi"
        },
        {
            "id": 10751,
            "name": "Aile"
        },
        {
            "id": 14,
            "name": "Fantastik"
        }
    ],
    "homepage": null,
    "id": 106515,
    "imdb_id": "tt0028566",
    "original_language": "en",
    "original_title": "Ali Baba Goes To Town",
    "overview": "",
    "popularity": 1.34,
    "poster_path": "/8s3ApbS97KDPIHi8CeqpulOc980.jpg",
    "production_companies": [
        {
            "id": 25,
            "logo_path": "/qZCc1lty5FzX30aOCVRBLzaVmcp.png",
            "name": "20th Century Fox",
            "origin_country": "US"
        }
    ],
    "production_countries": [
        {
            "iso_3166_1": "US",
            "name": "United States of America"
        }
    ],
    "release_date": "1937-10-29",
    "revenue": 0,
    "runtime": 81,
    "spoken_languages": [
        {
            "iso_639_1": "en",
            "name": "English"
        }
    ],
    "status": "Released",
    "tagline": "",
    "title": "Ali Baba Goes To Town",
    "video": false,
    "vote_average": 6.6,
    "vote_count": 5
}
     """

    const val serviceError = """{
        "status_code": 7,
        "status_message": "Invalid API key: You must be granted a valid key.",
        "success": false
    }"""

}